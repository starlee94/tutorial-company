package com.experimental.tca.service.v1;

import java.sql.Timestamp;
import java.util.*;

import com.experimental.tca.constant.Common;
import com.experimental.tca.constant.ResultCode;
import com.experimental.tca.domain.req.EmployeeLoginReq;
import com.experimental.tca.entity.v1.AuditLog;
import com.experimental.tca.constant.TokenType;
import com.experimental.tca.entity.v1.EmpAcc;
import com.experimental.tca.entity.v1.Token;
import com.experimental.tca.mapper.AuditLogMapper;
import com.experimental.tca.mapper.EmpAccMapper;
import com.experimental.tca.mapper.TokenMapper;
import com.experimental.tca.service.JwtService;
import com.experimental.tca.util.AuditStream;
import com.experimental.tca.util.LogStream;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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

	public Response<Object> employeeLogin(EmployeeLoginReq request) {

		LogStream.start();
		LogStream.body("employeeLogin ---> " + request.toString());
		EmpAcc empAcc =new EmpAcc();
		AuditLog auditLog;
		String token;

		ResultCode resultCode = verification.verifyEmployee(request, "employee_login");

		String[] data;
		if (null == resultCode) {
			data = new String[1];
			resultCode = ResultCode.MSG_SYSTEM_SUCCESS;
			try {

				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(
								request.getUsername(),
								request.getPassword())
				);

				empAcc = empAccMapper.findByUsername(request.getUsername()).orElse(new EmpAcc());

				token = jwtService.generateToken(new HashMap<>(), request.getUsername());

				data[0] = token;

				auditLog = AuditStream.audit(currentTime,
						          String.format("%s %s logged in.",Common.EMPLOYEE.getMsg(),
								  request.getUsername()),empAcc.getId(),
								  auditLogMapper);

				revokeAllUserTokens(empAcc.getId());
				saveUserToken(empAcc, token);

				LogStream.body(auditLog.getDescription());
				LogStream.body(token);

				LogStream.end();

			}
			catch (ExpiredJwtException e)
			{
				recursionTokenRemoval(empAcc);
				LogStream.body("Token expired removed.");
				LogStream.end();
				return employeeLogin(request);
			}
			catch (BadCredentialsException e){
				e.printStackTrace();
				LogStream.error(ResultCode.MSG_SYSTEM_EMPLOYEE_INVALID_PASSWORD);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				LogStream.error(ResultCode.MSG_SYSTEM_ERROR);
			}
		}else{
			data = null;
			LogStream.error(resultCode);
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
				   		 .tokenString(jwtToken)
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
