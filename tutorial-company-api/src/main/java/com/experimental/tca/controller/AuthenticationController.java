package com.experimental.tca.controller;

import com.experimental.tca.constant.UriConstant;
import com.experimental.tca.domain.req.EmployeeLoginReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.experimental.tca.service.v1.AuthenticationService;

import lombok.RequiredArgsConstructor;

/**
 * @author star.lee
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping(UriConstant.AUTH.VERSION.ONE.LOGIN)
	public ResponseEntity<Object> employeeLogin(@RequestBody EmployeeLoginReq request){	return ResponseEntity.ok(authenticationService.employeeLogin(request));}
}
