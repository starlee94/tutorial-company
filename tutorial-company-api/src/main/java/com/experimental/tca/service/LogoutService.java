package com.experimental.tca.service;

import com.experimental.tca.constant.Common;
import com.experimental.tca.mapper.AuditLogMapper;
import com.experimental.tca.mapper.EmpAccMapper;
import com.experimental.tca.mapper.TokenMapper;
import com.experimental.tca.entity.AuditLog;
import com.experimental.tca.entity.EmpAcc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.experimental.tca.entity.Token;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author star.lee
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler{

	@Autowired
	private final TokenMapper tokenMapper;

	private final JwtService jwtService;

	@Autowired
	private final AuditLogMapper auditLogMapper;

	@Autowired
	private final EmpAccMapper empAccMapper;

	private final Timestamp currentTime = new Timestamp(new Date().getTime());

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		AuditLog auditLog = new AuditLog();

		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		
		jwt = authHeader.substring(7);
		tokenMapper.findByToken(jwt).ifPresent(tokenMapper::revokeToken);

		String username = jwtService.extractUsername(jwt);
		String msg = String.format("%s %s logged out.", Common.EMPLOYEE.getMsg(), username);

		auditLog.setDt_timestamp(currentTime);
		auditLog.setVc_audit_descript(msg);
		auditLog.setI_emp_id(empAccMapper.findByUsername(username).orElse(new EmpAcc()).getId());

		auditLogMapper.save(auditLog);
		log.info("{}", msg);
	}
}
