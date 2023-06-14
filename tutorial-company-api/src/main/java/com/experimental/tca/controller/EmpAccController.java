package com.experimental.tca.controller;

import com.experimental.tca.model.EmpAcc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.experimental.tca.data.Request;
import com.experimental.tca.data.Response;
import com.experimental.tca.service.EmpAccService;

@RestController
@RequestMapping("/api/v1/emp")
public class EmpAccController{
	
	@Autowired
	private EmpAccService empAccService;
	
	@PostMapping("/createEmpAcc")
	public ResponseEntity<Response> createEmpAcc(@RequestBody Request<EmpAcc> request) {
		return ResponseEntity.ok(empAccService.registerEmployee(request));
	}

	@GetMapping("/getAllEmpAcc")
	public ResponseEntity<Response> getAllEmpAcc(@RequestBody Request<EmpAcc> request) { return  ResponseEntity.ok(empAccService.viewAllEmployee(request)); }
	
	@PutMapping("/revokeEmpAcc")
	public ResponseEntity<Response> deleteEmpAcc(@RequestBody Request<EmpAcc> request) {
		return ResponseEntity.ok(empAccService.revokeEmployee(request));
	}
	
}