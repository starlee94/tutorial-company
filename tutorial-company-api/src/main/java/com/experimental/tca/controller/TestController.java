package com.experimental.tca.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author star.lee
 */
@RestController
@RequestMapping("/api/v1/demo-controller")
public class TestController implements BearerAuthController{
	
	@GetMapping
	public ResponseEntity<String> sayHello(){
		return ResponseEntity.ok("Hello from secured endpoint.");
	}

}
