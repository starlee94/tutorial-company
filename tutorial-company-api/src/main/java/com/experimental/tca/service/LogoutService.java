package com.experimental.tca.service;

import com.experimental.tca.constant.Common;
import com.experimental.tca.constant.ResultCode;
import com.experimental.tca.mapper.AuditLogMapper;
import com.experimental.tca.mapper.EmpAccMapper;
import com.experimental.tca.mapper.TokenMapper;
import com.experimental.tca.entity.EmpAcc;
import com.experimental.tca.util.AuditStream;
import com.experimental.tca.util.LogStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author star.lee
 */
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler{

	@Autowired
	private final TokenMapper tokenMapper;

	@Autowired
	private final JwtService jwtService;

	@Autowired
	private final AuditLogMapper auditLogMapper;

	@Autowired
	private final EmpAccMapper empAccMapper;

	private final Timestamp currentTime = new Timestamp(new Date().getTime());

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

		LogStream.start();
		final String authHeader = request.getHeader("Authorization");
		final String jwt;

		LogStream.body("logout --> header=" + authHeader);

		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			LogStream.error(ResultCode.MSG_SYSTEM_ERROR);
			return;
		}
		
		jwt = authHeader.substring(7);
		tokenMapper.findByToken(jwt).ifPresent(tokenMapper::revokeToken);

		String username = jwtService.extractUsername(jwt);
		String msg = String.format("%s %s logged out.", Common.EMPLOYEE.getMsg(), username);

		AuditStream.audit(currentTime,
						  msg,
						  empAccMapper.findByUsername(username).orElse(new EmpAcc()).getId(),
						  auditLogMapper);

		LogStream.body(msg);
		LogStream.end();
	}
}
