package com.experimental.tca.service;

import com.experimental.tca.mapper.AuditLogMapper;
import com.experimental.tca.mapper.EmpAccMapper;
import com.experimental.tca.mapper.TokenMapper;
import com.experimental.tca.model.AuditLog;
import com.experimental.tca.model.EmpAcc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.experimental.tca.model.Token;

import java.sql.Timestamp;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler{

	@Autowired
	private final TokenMapper tokenMapper;

	private final JwtService jwtService;

	@Autowired
	private final AuditLogMapper auditLogMapper;

	@Autowired
	private final EmpAccMapper empAccMapper;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		AuditLog auditLog = new AuditLog();

		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		
		jwt = authHeader.substring(7);
		Token storedToken = tokenMapper.findByToken(jwt)
										 .orElse(null);

		if(storedToken != null) {
			tokenMapper.revokeToken(storedToken);
		}

		String username = jwtService.extractUsername(jwt);
		String msg = "Employee " + username + " logged out.";

		auditLog.setDt_timestamp((new Timestamp(new Date().getTime())));
		auditLog.setVc_audit_descript(msg);
		auditLog.setI_emp_id(empAccMapper.findByUsername(username).orElse(new EmpAcc()).getId());

		auditLogMapper.save(auditLog);
		System.out.println("[" + auditLog.getDt_timestamp() + "] " + msg);
	}
}
