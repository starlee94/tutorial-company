package com.experimental.tca.service;

import java.sql.Timestamp;
import java.util.*;

import com.experimental.tca.constant.Message;
import com.experimental.tca.constant.ResultCode;
import com.experimental.tca.domain.Employee;
import com.experimental.tca.domain.req.EmployeeActionReq;
import com.experimental.tca.domain.req.RegisterEmployeeReq;
import com.experimental.tca.domain.req.EmployerActionReq;
import com.experimental.tca.entity.AuditLog;
import com.experimental.tca.mapper.AuditLogMapper;
import com.experimental.tca.mapper.EmpAccMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.experimental.tca.domain.res.Response;
import com.experimental.tca.util.Verification;

import lombok.RequiredArgsConstructor;

/**
 * @author star.lee
 */
@Service
@RequiredArgsConstructor
public class EmpAccService {

	@Autowired
	private final AuditLogMapper auditLogMapper;

	@Autowired
	private final EmpAccMapper empAccMapper;

	@Autowired
	private final Verification verification;

	private ResultCode resultCode;

	private final AuditLog auditLog = new AuditLog();

	private final Timestamp currentTime =  new Timestamp(new Date().getTime());
	private Object[] data;

	public Response registerEmployee(RegisterEmployeeReq request) {

		resultCode = verification.verifyEmployee(request, "register_employee");
		data = new Object[1];

		if (resultCode == null) {
			resultCode = ResultCode.MSG_SYSTEM_SUCCESS;
			try {
				empAccMapper.save(empAccMapper.findAll().size() + 1, request.getUsername());

				data[0] = Message.EMPLOYEE.getMsg() + " " + request.getUsername() + " created.";

				auditLog.setDt_timestamp(new Timestamp(new Date().getTime()));
				auditLog.setVc_audit_descript(data[0].toString());
				auditLog.setI_emp_id(request.getEmployerId());

				auditLogMapper.save(auditLog);
				System.out.println("[" + auditLog.getDt_timestamp() + "] " + data[0]);
			}catch (Exception e){
				e.printStackTrace();
				resultCode = ResultCode.MSG_SYSTEM_ERROR;
			}
		}

		return Response.builder()
				.infoId(resultCode.getCode())
				.infoMsg(resultCode.getMessage())
				.data(data)
				.build();
	}

    public Response viewAllEmployee(Integer id) {

		List<Employee> employees = empAccMapper.findAll();
		resultCode = verification.verifyEmployee(id, "elevated_employee_action");
		data = new Object[1];
		try {

			if (resultCode == null) {
				resultCode = ResultCode.MSG_SYSTEM_SUCCESS;
				System.out.println("["+ new Timestamp(new Date().getTime()) + "] Employee "+ empAccMapper.findById(id).getUsername() + " called get all employees." );
			}
			else {
				employees = null;
			}
			data[0] = employees;

		} catch (Exception e) {
			e.printStackTrace();

			resultCode = ResultCode.MSG_SYSTEM_ERROR;
		}

		return Response.builder()
				.infoId(resultCode.getCode())
				.infoMsg(resultCode.getMessage())
				.data(data)
				.build();
    }

	public Response revokeEmployee(EmployerActionReq request) {

		resultCode = verification.verifyEmployee(request, "revoke_employee");
		data = new Object[1];

		if (resultCode == null) {
			resultCode = ResultCode.MSG_SYSTEM_SUCCESS;
			try {

				Employee employee = empAccMapper.findById(request.getEmployeeId());
				empAccMapper.updateStatusIdById(employee.getId(), 0);
				data[0] = Message.EMPLOYEE.getMsg() + "Employee " + employee.getUsername() + " was deactivated.";

				auditLog.setDt_timestamp((new Timestamp(new Date().getTime())));
				auditLog.setVc_audit_descript(data[0].toString());
				auditLog.setI_emp_id(request.getEmployerId());

				auditLogMapper.save(auditLog);

				System.out.println("[" + auditLog.getDt_timestamp() + "] " + data[0].toString());

			}catch(Exception e) {
				e.printStackTrace();
				resultCode = ResultCode.MSG_SYSTEM_ERROR;
			}
		}
		return Response.builder()
				.infoId(resultCode.getCode())
				.infoMsg(resultCode.getMessage())
				.data(data)
				.build();
	}

	public Response updateEmployee(EmployeeActionReq request){

		resultCode = verification.verifyEmployee(request, "activate_employee");
		data = new Object[1];

		if (resultCode == null) {
			resultCode = ResultCode.MSG_SYSTEM_SUCCESS;
			try {
				if ("PWD".equals(request.getAction())) {
					empAccMapper.updatePasswordById(request.getId(), request.getData());
					empAccMapper.updateStatusIdById(request.getId(), 1);
					data[0] = Message.EMPLOYEE.getMsg() + " " + empAccMapper.findById(request.getId()).getUsername() + " activated.";
				}

				auditLog.setDt_timestamp(new Timestamp(new Date().getTime()));
				auditLog.setVc_audit_descript(data[0].toString());
				auditLog.setI_emp_id(request.getId());

				auditLogMapper.save(auditLog);

			}
			catch (Exception e) {
				e.printStackTrace();
				resultCode = ResultCode.MSG_SYSTEM_ERROR;
			}
		}
		System.out.println("[" + currentTime + "] " + data[0].toString());
		return Response.builder()
				.infoId(resultCode.getCode())
				.infoMsg(resultCode.getMessage())
				.data(data)
				.build();
	}

}
