package com.experimental.tca.service;

import java.sql.Timestamp;
import java.util.*;

import com.experimental.tca.mapper.AuditLogMapper;
import com.experimental.tca.mapper.EmpAccMapper;
import com.experimental.tca.mapper.TokenMapper;
import com.experimental.tca.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.experimental.tca.data.Request;
import com.experimental.tca.data.Response;
import com.experimental.tca.util.Verification;

import lombok.RequiredArgsConstructor;

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
	public Response employeeLogin(Request<EmpAcc> request) {

		EmpAcc empAcc;
		AuditLog auditLog = new AuditLog();

		Response response;

		String infoId = "";
		String infoMsg = verification.verifyEmployee(request, empAccMapper.findAll(), "employee_login");

		try {


			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							request.getData().getUsername(),
							request.getData().getPassword())
					);

			infoId = "0";
			empAcc = empAccMapper.findByUsername(request.getData().getUsername()).orElse(new EmpAcc());
			String token = jwtService.generateToken(new HashMap<>(), request.getData().getUsername());

			if (!tokenMapper.findByToken(token).isPresent()){

				auditLog.setDt_timestamp((new Timestamp(new Date().getTime())));
				auditLog.setVc_audit_descript("Employee " + request.getData().getUsername() + " logged in.");
				auditLog.setI_emp_id(empAcc.getId());

				auditLogMapper.save(auditLog);
				infoMsg = "Employee " + request.getData().getUsername() + " logged in.";

				response = Response.builder()
						.infoId(infoId)
						.infoMsg(infoMsg)
						.data(token)
						.build();

				revokeAllUserTokens(empAcc);
				saveUserToken(empAcc, response.getData().toString());

				System.out.println("[" + auditLog.getDt_timestamp() + "] " + infoMsg);
			}else {
				infoMsg = "Employee " + request.getData().getUsername() + " already logged in.";

				response = Response.builder()
						.infoId(infoId)
						.infoMsg(infoMsg)
						.data(token)
						.build();

				System.out.println(infoMsg);
			}

		}
		catch(Exception e) {
			e.printStackTrace();

			infoMsg = e.toString();
			response = Response.builder()
						.infoId(infoId)
						.infoMsg(infoMsg)
						.build();
		}
		
		
		return response;

	}
	
	private void revokeAllUserTokens(EmpAcc empAcc) {
		List<Token> validUserTokens = tokenMapper.findAllValidTokenByUser(empAcc.getId());
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
