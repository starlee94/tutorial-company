package com.experimental.tca.controller;

import com.experimental.tca.domain.req.EmployeeActionReq;
import com.experimental.tca.domain.req.RegisterEmployeeReq;
import com.experimental.tca.domain.req.EmployerActionReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.experimental.tca.domain.res.Response;
import com.experimental.tca.service.EmpAccService;

/**
 * @author star.lee
 */
@RestController
@RequestMapping("/api/v1/emp")
public class EmployeeController implements BearerAuthController{
	
	@Autowired
	private EmpAccService empAccService;
	
	@PostMapping("/create/account")
	public ResponseEntity<Response> createEmployeeAccount(@RequestBody RegisterEmployeeReq request) {
		return ResponseEntity.ok(empAccService.registerEmployee(request));
	}

	@GetMapping("/getAll")
	public ResponseEntity<Response> getAllEmployeeAccount(@RequestParam Integer id) { return  ResponseEntity.ok(empAccService.viewAllEmployee(id)); }

	@PostMapping("/revoke/account")
	public ResponseEntity<Response> revokeEmployeeAccount(@RequestBody EmployerActionReq request) {
		return ResponseEntity.ok(empAccService.revokeEmployee(request));
	}

	@PostMapping("/update/account")
	public ResponseEntity<Response> updateEmployeeAccount(@RequestBody EmployeeActionReq request) { return ResponseEntity.ok(empAccService.updateEmployee(request));}
}