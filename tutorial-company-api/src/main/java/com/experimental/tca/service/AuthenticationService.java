package com.experimental.tca.service;

import java.sql.Timestamp;
import java.util.*;

import com.experimental.tca.domain.req.EmployeeLoginReq;
import com.experimental.tca.entity.AuditLog;
import com.experimental.tca.constant.TokenType;
import com.experimental.tca.entity.EmpAcc;
import com.experimental.tca.entity.Token;
import com.experimental.tca.mapper.AuditLogMapper;
import com.experimental.tca.mapper.EmpAccMapper;
import com.experimental.tca.mapper.TokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.experimental.tca.domain.res.Response;
import com.experimental.tca.util.Verification;

import lombok.RequiredArgsConstructor;

/**
 * @author star.lee
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

	@Autowired
	private final EmpAccMapper empAccMapper;

	@Autowired
	private final AuditLogMapper auditLogMapper;

	@Autowired
	private final TokenMapper tokenMapper;
	
	private final AuthenticationManager authenticationManager;
	
	private final JwtService jwtService;

	@Autowired
	private final Verification verification;
	public Response employeeLogin(EmployeeLoginReq request) {

		EmpAcc empAcc;
		AuditLog auditLog = new AuditLog();

		Timestamp currentTime = new Timestamp(new Date().getTime());

		String infoId = "";
		String infoMsg = verification.verifyEmployee(request, "employee_login");
		if (infoMsg != null) {

			return Response.builder()
					.infoId(infoId)
					.infoMsg(infoMsg)
					.build();
		}
		try {


			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							request.getUsername(),
							request.getPassword())
					);

			infoId = "0";
			empAcc = empAccMapper.findByUsername(request.getUsername()).orElse(new EmpAcc());
			String token = jwtService.generateToken(new HashMap<>(), request.getUsername());

			for (Token t : tokenMapper.findAll()) {
				if(jwtService.extractUsername(t.getToken()).equals(request.getUsername())){
					infoMsg = "Employee " + request.getUsername() + " already logged in";
					break;
				}
			}

			if (infoMsg == null) {

				auditLog.setDt_timestamp(currentTime);
				auditLog.setVc_audit_descript("Employee " + request.getUsername() + " logged in.");
				auditLog.setI_emp_id(empAcc.getId());

				auditLogMapper.save(auditLog);
				infoMsg = "Employee " + request.getUsername() + " logged in.";

				revokeAllUserTokens(empAcc.getId());
				saveUserToken(empAcc, token);

				System.out.println("[" + currentTime + "] " + infoMsg);

				return Response.builder()
						.infoId(infoId)
						.infoMsg(infoMsg)
						.data(token)
						.build();

			}else {

				System.out.println("[" + currentTime + "] " + infoMsg);
				return Response.builder()
						.infoId(infoId)
						.infoMsg(infoMsg)
						.build();

			}

		}
		catch(Exception e) {

			e.printStackTrace();

			infoMsg = e.toString();
			return Response.builder()
						.infoId(infoId)
						.infoMsg(infoMsg)
						.build();
		}

	}
	
	private void revokeAllUserTokens(Integer id) {
		List<Token> validUserTokens = tokenMapper.findAllValidTokenByUser(id);
		if(validUserTokens.isEmpty()) {
			return;
		}

		tokenMapper.revokeAll();
	}
	
	private void saveUserToken(EmpAcc savedAcc, String jwtToken) {
		Token token = Token.builder()
						 .empAcc(savedAcc)
				   		 .token(jwtToken)
				   		 .tokenType(TokenType.BEARER)
				   		 .revoked(false)
				   		 .build();
		
		tokenMapper.save(token);
	}
	
}
