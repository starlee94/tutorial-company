package com.experimental.tca.service;

import java.sql.Timestamp;
import java.util.*;

import com.experimental.tca.constant.Message;
import com.experimental.tca.constant.ResultCode;
import com.experimental.tca.domain.req.EmployeeLoginReq;
import com.experimental.tca.entity.AuditLog;
import com.experimental.tca.constant.TokenType;
import com.experimental.tca.entity.EmpAcc;
import com.experimental.tca.entity.Token;
import com.experimental.tca.mapper.AuditLogMapper;
import com.experimental.tca.mapper.EmpAccMapper;
import com.experimental.tca.mapper.TokenMapper;
import io.jsonwebtoken.ExpiredJwtException;
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

	@Autowired
	private final Verification verification;

	@Autowired
	private final JwtService jwtService;

	@Autowired
	private final AuthenticationManager authenticationManager;

	private final Timestamp currentTime = new Timestamp(new Date().getTime());

	private final String[] data = new String[2];

	public Response employeeLogin(EmployeeLoginReq request) throws NullPointerException{

		EmpAcc empAcc =new EmpAcc();
		AuditLog auditLog = new AuditLog();
		String token = "";

		ResultCode resultCode = verification.verifyEmployee(request, "employee_login");
		if (resultCode != null) {
			return Response.builder()
					.infoId(resultCode.getCode())
					.infoMsg(resultCode.getMessage())
					.build();
		}
		resultCode = ResultCode.MSG_SYSTEM_SUCCESS;
		try {

			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							request.getUsername(),
							request.getPassword())
					);
			empAcc = empAccMapper.findByUsername(request.getUsername()).orElse(new EmpAcc());
			token = jwtService.generateToken(new HashMap<>(), request.getUsername());
			for (Token t : tokenMapper.findAll()) {
				if(jwtService.extractUsername(t.getToken()).equals(request.getUsername())) {

					data[0] = token;
					data[1] = Message.EMPLOYEE.getMsg() + " " + request.getUsername() + " already logged in";
					break;
				}
			}
				auditLog.setDt_timestamp(currentTime);
				auditLog.setVc_audit_descript("Employee " + request.getUsername() + " logged in.");
				auditLog.setI_emp_id(empAcc.getId());

				auditLogMapper.save(auditLog);
				data[0] = token;
				data[1] = Message.EMPLOYEE.getMsg() + " " + request.getUsername() + " logged in.";

				revokeAllUserTokens(empAcc.getId());
				saveUserToken(empAcc, token);

				System.out.println("[" + currentTime + "] " + data[1]);

		}
		catch (ExpiredJwtException e)
		{
			recursionTokenRemoval(empAcc);
			System.out.println("Token expired removed.");
			employeeLogin(request);
		}
		catch(Exception e)
		{
			resultCode = ResultCode.MSG_SYSTEM_ERROR;
			e.printStackTrace();
		}

		return Response.builder()
				.infoId(resultCode.getCode())
				.infoMsg(resultCode.getMessage())
				.data(data)
				.build();
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

	private void recursionTokenRemoval(EmpAcc empAcc){
		List<Token> tokens = tokenMapper.findAll();
		for(Token t : tokens){
			if (Objects.equals(t.getEmpAcc().getId(), empAcc.getId())){
				tokenMapper.delete(t);
			}
		}
	}
}
