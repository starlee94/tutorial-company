package com.experimental.tca.service;

import java.sql.Timestamp;
import java.util.*;

import com.experimental.tca.constant.Common;
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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

	private String[] data = null;

	public Response employeeLogin(EmployeeLoginReq request) {

		log.info(Common.LOG_START.getMsg());
		log.info("employeeLogin --> (username:\"{}\", password:\"{}\")", request.getUsername(), request.getPassword());
		EmpAcc empAcc =new EmpAcc();
		AuditLog auditLog = new AuditLog();
		String token;

		ResultCode resultCode = verification.verifyEmployee(request, "employee_login");

		if (resultCode == null) {
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
				for (Token t : tokenMapper.findAll()) {
					if(jwtService.extractUsername(t.getToken()).equals(request.getUsername())) {

						data[0] = token;
						break;
					}
				}
				data[0] = token;

				auditLog.setDt_timestamp(currentTime);
				auditLog.setVc_audit_descript(String.format("%s %s logged in.",Common.EMPLOYEE.getMsg(), request.getUsername()));
				auditLog.setI_emp_id(empAcc.getId());

				auditLogMapper.save(auditLog);

				revokeAllUserTokens(empAcc.getId());
				saveUserToken(empAcc, token);

				log.info("{}", auditLog.getVc_audit_descript());

			}
			catch (ExpiredJwtException e)
			{
				recursionTokenRemoval(empAcc);
				log.info("Token expired removed.");
				log.info(Common.LOG_END.getMsg());
				employeeLogin(request);
			}
			catch (BadCredentialsException e){
				resultCode = ResultCode.MSG_SYSTEM_EMPLOYEE_INVALID_PASSWORD;
				e.printStackTrace();
				log.error(String.format(Common.ERROR_MSG.getMsg(), resultCode.getCode(), resultCode.getMessage()));
			}
			catch(Exception e)
			{
				resultCode = ResultCode.MSG_SYSTEM_ERROR;
				e.printStackTrace();
				log.error(String.format(Common.ERROR_MSG.getMsg(), resultCode.getCode(), resultCode.getMessage()));
			}
		}else{
			data = null;
			log.error(String.format(Common.ERROR_MSG.getMsg(), resultCode.getCode(), resultCode.getMessage()));
		}
		log.info(Common.LOG_END.getMsg());
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
