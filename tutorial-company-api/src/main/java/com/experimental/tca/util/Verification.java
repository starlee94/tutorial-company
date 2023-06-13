package com.experimental.tca.util;

import java.util.List;
import java.util.NoSuchElementException;

import com.experimental.tca.data.Request;
import com.experimental.tca.model.EmpAcc;

public class Verification {


	private String employerAction(Request<? extends EmpAcc> request, List<EmpAcc> empAccs) {

		EmpAcc empAcc;
		//1. Check if the employer exist
		try {

			empAcc = empAccs.stream()
					.filter(emp -> emp.getId() == request.getData().getId())
					.findFirst().get();

		}catch (NoSuchElementException e) {
			return "Employer not found.";
		}			

		//2. Check if the employer have permission
		if(empAcc.getTagId() == null ||
				(empAcc.getTagId() != 1 &&
				empAcc.getTagId() != 2)) return "Employer does not have permission.";

		return null;
	}

	public String verify_employee(Request<EmpAcc> request, List<EmpAcc> empAccs, String option) {

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
			empAcc = null;
			try {

				empAcc = empAccs.stream()
						.filter(emp -> emp.getUsername().equals(request.getData().getUsername()))
						.findFirst().get();

			}catch (NoSuchElementException e) {
				errorMsg = null;
			}

			if(empAcc != null) errorMsg = "Employee exist in database.";
			break;

		case "revoke_employee":

			//Employer who plan to revoke employee account
			//1. Pass employerAction method
			errorMsg = employerAction(request, empAccs);

			//2. Check if employee exist in database
			empAcc = null;
			try {

				empAcc = empAccs.stream()
						.filter(emp -> emp.getUsername().equals(request.getData().getUsername()))
						.findFirst().get();

			}catch (NoSuchElementException e) {
				errorMsg = "Employee not found.";
			}
			break;
			
		case "employee_login":
			
			//Employee attempt to login
			//1. Check if employee exist in database
			empAcc = null;
			try {

				empAcc = empAccs.stream()
						.filter(emp -> emp.getUsername().equals(request.getData().getUsername()))
						.findFirst().get();

			}catch (NoSuchElementException e) {
				errorMsg = "Employee not found.";
			}			
			break;

		}

		return errorMsg;
	}


}
