package com.experimental.tca.controller;

import com.experimental.tca.constant.UriConstant;
import com.experimental.tca.domain.req.EmployeeActionReq;
import com.experimental.tca.domain.req.RegisterEmployeeReq;
import com.experimental.tca.domain.req.EmployerActionReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.experimental.tca.domain.res.Response;
import com.experimental.tca.service.v1.EmpAccService;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author star.lee
 */
@RestController
@RequestMapping("/api")
public class EmployeeController implements BearerAuthController{
	
	@Autowired
	private EmpAccService empAccService;
	
	@PostMapping(UriConstant.EMP.VERSION.ONE.CREATE_ACC)
	public ResponseEntity<Object> createEmployeeAccount(@RequestBody RegisterEmployeeReq request) {
		return ResponseEntity.ok(empAccService.registerEmployee(request));
	}

	@GetMapping(UriConstant.EMP.VERSION.ONE.GET_ALL)
	public ResponseEntity<Object> getAllEmployeeAccount(@RequestParam Integer id) { return  ResponseEntity.ok(empAccService.viewAllEmployee(id)); }

	@PostMapping(UriConstant.EMP.VERSION.ONE.REVOKE_ACC)
	public ResponseEntity<Object> revokeEmployeeAccount(@RequestBody EmployerActionReq request) {
		return ResponseEntity.ok(empAccService.revokeEmployee(request));
	}

	@PostMapping(UriConstant.EMP.VERSION.ONE.UPDATE_ACC)
	public ResponseEntity<Object> updateEmployeeAccount(@RequestBody EmployeeActionReq request) {	return ResponseEntity.ok(empAccService.updateEmployee(request));}
}