package com.experimental.tca.controller;

import com.experimental.tca.domain.req.Request;
import com.experimental.tca.entity.EmpAcc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.experimental.tca.domain.res.Response;
import com.experimental.tca.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping("/login")
	public ResponseEntity<Response> employeeLogin(@RequestBody Request<EmpAcc> request){
		return ResponseEntity.ok(authenticationService.employeeLogin(request));
	}
}
