package com.experimental.tca.util;

import java.util.List;
import com.experimental.tca.domain.req.Request;
import com.experimental.tca.mapper.EmpAccMapper;
import com.experimental.tca.entity.EmpAcc;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Verification {

	@Autowired
	private final EmpAccMapper empAccMapper;

	private String[] errorMsgConstant = {"Employee not found."};

	private String employerAction(Request<? extends EmpAcc> request, List<EmpAcc> empAccs) {

		EmpAcc empAcc = empAccMapper.findById(request.getData().getId());
		//1. Check if the employer exist
		if (empAcc == null){
			return errorMsgConstant[0];
		}

		if(empAcc.getTagId() == null || (empAcc.getTagId() != 1 && empAcc.getTagId() != 2))
		{return "Employer does not have permission.";}

		return null;
	}

	public String verifyEmployee(Request<EmpAcc> request, List<EmpAcc> empAccs, String option) {

		EmpAcc empAcc;
		String errorMsg = null;

		switch (option) {

			case "elevated_employee_action":

				//Employer who plan to access privileged action
				//1. Pass employerAction method
				errorMsg = employerAction(request, empAccs);

				break;

			case "register_employee":

			//Employer who plan to register new employee
			//1. Pass employerAction method			
			errorMsg = employerAction(request, empAccs);

			//2. Check if the new employee exists in database
			if(errorMsg == null){
					empAcc = empAccMapper.findByUsername(request.getData().getUsername()).orElse(null);
					if(empAcc != null) {errorMsg = "Employee exist in database.";}
				}

			break;

			case "revoke_employee":

				//Employer who plan to revoke employee account
				//1. Pass employerAction method
				errorMsg = employerAction(request, empAccs);

				//2. Check if employee exist in database
				if(errorMsg == null){
					empAcc = empAccMapper.findByUsername(request.getData().getUsername()).orElse(null);
					if(empAcc == null) {errorMsg = errorMsgConstant[0];}
				}
				break;

			case "employee_login":

				//Employee attempt to login
				//1. Check if employee exist in database
				empAcc = empAccMapper.findByUsername(request.getData().getUsername()).orElse(null);

				if(empAcc == null){ errorMsg = errorMsgConstant[0];}

				break;

			default:
				errorMsg = "unexpected error occurred";
				break;

		}

		return errorMsg;
	}


}
