package com.experimental.tca.service;

import java.sql.Timestamp;
import java.util.*;

import com.experimental.tca.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.experimental.tca.data.Request;
import com.experimental.tca.data.Response;
import com.experimental.tca.repository.AuditLogRepository;
import com.experimental.tca.repository.EmpAccRepository;
import com.experimental.tca.repository.TokenRepository;
import com.experimental.tca.util.Verification;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	@Autowired
	private final EmpAccRepository empAccRepository;
	
	@Autowired
	private final AuditLogRepository auditLogRepository;
	
	@Autowired
	private final TokenRepository tokenRepository;
	
	private final AuthenticationManager authenticationManager;
	
	private final JwtService jwtService;

	private Verification verification = new Verification();

	private ObjectMapper oMap = new ObjectMapper();
	
	private ArrayList<String> removeKeyList = new ArrayList<>();

//	private Map<String, Object> processResponse(EmpAcc empAcc, RmAccess rmAccess) {
//
//		data = new ResponseData(info.getInfoId(), info.getInfoMsg(), new Data(empAcc, rmAccess));
//		data.getData().getEmpAcc().setRole(Role.USER);
//
//		Map<String, Object> responseObj = oMap.convertValue(data, Map.class);
//
//		((Map<String, Object>)responseObj.get("data")).entrySet().forEach(entry -> removeKeyList.add(entry.getKey()));
//
//		removeKeyList.removeIf(item -> item.equals("username"));
//
//		((Map<String, Object>)responseObj.get("data")).entrySet().removeIf(entry -> removeKeyList.contains(entry.getKey()));
//
//		return responseObj;
//	}
	
	public Response employee_login(Request<EmpAcc> request) {

		System.out.println(request);
		String infoId = "";
		String infoMsg;

		List<EmpAcc> empAccs;
		EmpAcc empAcc;
		AuditLog auditLog;

		auditLog = new AuditLog();

		empAccs = empAccRepository.findAll();
		infoMsg = verification.verify_employee(request, empAccs, "employee_login");
		if(null == infoMsg) {
			infoMsg = "SUCCESS";
		}

		Response response = null;
		
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							request.getData().getUsername(),
							request.getData().getPassword())
					);

			infoId = "0";

			auditLog.setDt_timestamp((new Timestamp(new Date().getTime())));
			auditLog.setVc_audit_descript(request.getData().getUsername() + " logged in.");
			auditLog.setI_emp_id(empAccs.stream().filter(emp -> emp.getUsername().equals((request.getData()
																	   		  				.getUsername())))
																			  				.findFirst().get()
																			  				.getId());

			auditLogRepository.save(auditLog);

			System.out.println("[" + auditLog.getDt_timestamp() + "] Employee: "
							   + request.getData().getUsername() + " logged in." );

			response = Response.builder()
						.infoId(infoId)
						.infoMsg(infoMsg)
						.data(jwtService.generateToken(new HashMap<>(), request.getData().getUsername()))
					   	.build();

			empAcc = empAccs.stream().filter(emp -> emp.getUsername()
					 						  .equals(request.getData()
					 						  .getUsername()))
							  				  .findFirst()
							  				  .get();

			revokeAllUserTokens(empAcc);

			saveUserToken(empAcc, response.getData().toString());
		}
		catch(Exception e) {
			e.printStackTrace();

			infoMsg = e.toString();
			response = Response.builder()
						.infoId(infoId)
						.infoMsg(infoMsg)
						.data(jwtService.generateToken(new HashMap<>(), ""))
						.build();
		}
		
		
		return response;
		
	}
	
	private void revokeAllUserTokens(EmpAcc empAcc) {
		List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(empAcc.getId());
		if(validUserTokens.isEmpty()) return;
		
		validUserTokens.forEach(t -> {
			t.setRevoked(true);
		});
		tokenRepository.saveAll(validUserTokens);
	}
	
	private void saveUserToken(EmpAcc savedAcc, String jwtToken) {
		Token token = Token.builder()
						 .empAcc(savedAcc)
				   		 .token(jwtToken)
				   		 .tokenType(TokenType.BEARER)
				   		 .revoked(false)
				   		 .build();
		
		tokenRepository.save(token);
	}
	
}
