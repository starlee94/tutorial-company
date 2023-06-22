package com.experimental.tca.util;

import com.experimental.tca.constant.ResultCode;
import com.experimental.tca.domain.Employee;
import com.experimental.tca.domain.req.EmployeeLoginReq;
import com.experimental.tca.domain.req.RegisterEmployeeReq;
import com.experimental.tca.domain.req.EmployerActionReq;
import com.experimental.tca.mapper.EmpAccMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author star.lee
 */
@Component
@RequiredArgsConstructor
public class Verification {

	@Autowired
	private final EmpAccMapper empAccMapper;

	private ResultCode employerAction(Integer id) {

		ResultCode resultCode = null;

		try {
			Employee employee = empAccMapper.findById(id);
			//1. Check if the employer exist
			if (employee == null) {
				resultCode = ResultCode.MSG_SYSTEM_EMPLOYER_NOT_FOUND;
			}
			else {
				if(employee.getTagId() == null || (employee.getTagId() != 1 && employee.getTagId() != 2))
				{
					resultCode = ResultCode.MSG_SYSTEM_EMPLOYER_INVALID_PERMISSION;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return resultCode;
	}

	public ResultCode verifyEmployee(Object obj, String option) {

		Employee employee;

		ResultCode resultCode = null;

		switch (option) {

			case "elevated_employee_action":

				//Employer who plan to access privileged action
				//1. Pass employerAction method
				resultCode = employerAction(Integer.parseInt(obj.toString()));

				break;

			case "register_employee":
			RegisterEmployeeReq regReq = (RegisterEmployeeReq)obj;
			//Employer who plan to register new employee
			//1. Pass employerAction method
				resultCode = employerAction(regReq.getEmployerId());

			//2. Check if the new employee exists in database
			if(resultCode == null && empAccMapper.findByUsername(regReq.getUsername()).isPresent()){
						resultCode = ResultCode.MSG_SYSTEM_EMPLOYEE_EXIST_IN_DB;
					}

			break;

			case "revoke_employee":
				EmployerActionReq revReq = (EmployerActionReq)obj;
				//Employer who plan to revoke employee account
				//1. Pass employerAction method
				resultCode = employerAction(revReq.getEmployerId());

				//2. Check if employee exist in database
				if(resultCode == null){
					employee = empAccMapper.findById(revReq.getEmployeeId());
					if(employee == null) {
						resultCode = ResultCode.MSG_SYSTEM_EMPLOYEE_NOT_FOUND;
					}
				}
				break;

			case "employee_login":
				EmployeeLoginReq loginReq = (EmployeeLoginReq) obj;

				//Employee attempt to log in
				//1. Check if employee exist in database
				if(!empAccMapper.findByUsername(loginReq.getUsername()).isPresent())
				{
					resultCode = ResultCode.MSG_SYSTEM_EMPLOYEE_NOT_FOUND;
				}
				break;

			default:
				resultCode = ResultCode.MSG_SYSTEM_ERROR;
				break;

		}

		return resultCode;
	}


}
