package com.experimental.tca.util;

import com.experimental.tca.domain.Employee;
import com.experimental.tca.domain.req.EmployeeLoginReq;
import com.experimental.tca.domain.req.RegisterEmployeeReq;
import com.experimental.tca.domain.req.EmployerActionReq;
import com.experimental.tca.mapper.EmpAccMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Verification {

	@Autowired
	private final EmpAccMapper empAccMapper;

	private String[] errorMsgConstant = {"Employee not found."};

	private String employerAction(Integer id) {

		Employee employee = empAccMapper.findById(id);
		//1. Check if the employer exist
		if (employee == null){
			return errorMsgConstant[0];
		}

		if(employee.getTagId() == null || (employee.getTagId() != 1 && employee.getTagId() != 2))
		{return "Employer does not have permission.";}

		return null;
	}

	public String verifyEmployee(Object obj, String option) {

		Employee employee;

		String errorMsg = null;

		switch (option) {

			case "elevated_employee_action":

				//Employer who plan to access privileged action
				//1. Pass employerAction method
				errorMsg = employerAction(Integer.parseInt(obj.toString()));

				break;

			case "register_employee":
			RegisterEmployeeReq regReq = (RegisterEmployeeReq)obj;
			//Employer who plan to register new employee
			//1. Pass employerAction method			
			errorMsg = employerAction(regReq.getEmployerId());

			//2. Check if the new employee exists in database
			if(errorMsg == null && empAccMapper.findByUsername(regReq.getUsername()).isPresent()){
						errorMsg = "Employee exist in database.";
					}

			break;

			case "revoke_employee":
				EmployerActionReq revReq = (EmployerActionReq)obj;
				//Employer who plan to revoke employee account
				//1. Pass employerAction method
				errorMsg = employerAction(revReq.getEmployerId());

				//2. Check if employee exist in database
				if(errorMsg == null){
					employee = empAccMapper.findById(revReq.getEmployeeId());
					if(employee == null) {errorMsg = errorMsgConstant[0];}
				}
				break;

			case "employee_login":
				EmployeeLoginReq loginReq = (EmployeeLoginReq) obj;

				//Employee attempt to login
				//1. Check if employee exist in database
				if(!empAccMapper.findByUsername(loginReq.getUsername()).isPresent())
				{
					errorMsg = errorMsgConstant[0];
				}

				break;

			default:
				errorMsg = "unexpected error occurred";
				break;

		}

		return errorMsg;
	}


}
