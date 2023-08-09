package com.experimental.tca.service;

import java.sql.Timestamp;
import java.util.*;

import com.experimental.tca.constant.Common;
import com.experimental.tca.constant.ResultCode;
import com.experimental.tca.domain.Employee;
import com.experimental.tca.domain.req.EmployeeActionReq;
import com.experimental.tca.domain.req.RegisterEmployeeReq;
import com.experimental.tca.domain.req.EmployerActionReq;
import com.experimental.tca.entity.AuditLog;
import com.experimental.tca.mapper.AuditLogMapper;
import com.experimental.tca.mapper.EmpAccMapper;
import com.experimental.tca.util.AuditStream;
import com.experimental.tca.util.LogStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

		LogStream.start();
		LogStream.body("registerEmployee ---> " + request.toString());
		resultCode = verification.verifyEmployee(request, "register_employee");
		data = new Object[1];

		if (resultCode == null) {
			resultCode = ResultCode.MSG_SYSTEM_SUCCESS;
			try {
				empAccMapper.save(empAccMapper.findAll().size() + 1, request.getUsername());

				data[0] = String.format("%s %s created.", Common.EMPLOYEE.getMsg(), request.getUsername());

				AuditStream.audit(currentTime,
								  data[0].toString(),
								  request.getEmployerId(),
								  auditLogMapper);

				LogStream.body(data[0].toString());

				LogStream.end();

			}catch (Exception e){

				e.printStackTrace();
				LogStream.error(ResultCode.MSG_SYSTEM_ERROR);
			}
		}
		else{
			data = null;
			LogStream.error(resultCode);
		}
		return Response.builder()
				.infoId(resultCode.getCode())
				.infoMsg(resultCode.getMessage())
				.data(data)
				.build();
	}

    public Response viewAllEmployee(Integer id) {

		LogStream.start();
		LogStream.body("viewAllEmployee ---> Id=" + id);
		List<Employee> employees = empAccMapper.findAll();
		resultCode = verification.verifyEmployee(id, "elevated_employee_action");
		data = new Object[1];

		if (resultCode == null) {
			resultCode = ResultCode.MSG_SYSTEM_SUCCESS;
			LogStream.body(String.format("Employee %s called get all employees.",empAccMapper.findById(id).getUsername()));
			try {data[0] = employees;}
			catch (Exception e) {

				e.printStackTrace();
				LogStream.error(ResultCode.MSG_SYSTEM_ERROR);
			}

			LogStream.end();
		}
		else {
			data = null;
			LogStream.error(resultCode);
		}
		return Response.builder()
				.infoId(resultCode.getCode())
				.infoMsg(resultCode.getMessage())
				.data(data)
				.build();
    }

	public Response revokeEmployee(EmployerActionReq request) {

		LogStream.start();
		LogStream.body("revokeEmployee ---> " + request.toString());

		resultCode = verification.verifyEmployee(request, "revoke_employee");
		data = new Object[1];

		if (resultCode == null) {
			resultCode = ResultCode.MSG_SYSTEM_SUCCESS;
			try {

				Employee employee = empAccMapper.findById(request.getEmployeeId());
				empAccMapper.updateStatusIdById(employee.getId(), 0);
				data[0] = String.format("%s %s was deactivated.", Common.EMPLOYEE.getMsg(), employee.getUsername());

				AuditStream.audit(currentTime,
								  data[0].toString(),
								  request.getEmployerId(),
								  auditLogMapper);

				LogStream.body(data[0].toString());

				LogStream.end();

			}catch(Exception e) {

				e.printStackTrace();
				LogStream.error(ResultCode.MSG_SYSTEM_ERROR);
			}
		}
		else {
			data = null;
			LogStream.error(resultCode);
		}
		return Response.builder()
				.infoId(resultCode.getCode())
				.infoMsg(resultCode.getMessage())
				.data(data)
				.build();
	}

	public Response updateEmployee(EmployeeActionReq request){

		LogStream.start();
		LogStream.body("updateEmployee ---> " + request.toString());

		resultCode = verification.verifyEmployee(request, "employee_action");
		data = new Object[1];

		if (resultCode == null) {
			resultCode = ResultCode.MSG_SYSTEM_SUCCESS;
			try {
				if ("PWD".equals(request.getAction())) {
					BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
					String pwd = passwordEncoder.encode(request.getData());

					empAccMapper.updatePasswordById(request.getId(), pwd);

					Integer status = empAccMapper.findById(request.getId()).getStatus();
					if(status == 0) {
						empAccMapper.updateStatusIdById(request.getId(), 1);
						data[0] = Common.EMPLOYEE.getMsg() + " " + empAccMapper.findById(request.getId()).getUsername() + " activated.";
					}
					data[0] = Common.EMPLOYEE.getMsg() + " " + empAccMapper.findById(request.getId()).getUsername() + " password updated.";
				}

				auditLog.setTimeStamp(currentTime);
				auditLog.setDescription(data[0].toString());
				auditLog.setEmployeeId(request.getId());

				auditLogMapper.save(auditLog);
				LogStream.body(data[0].toString());

				LogStream.end();
			}
			catch (Exception e) {

				e.printStackTrace();
				LogStream.error(ResultCode.MSG_SYSTEM_ERROR);
			}
		}
		else {
			data = null;
			LogStream.error(resultCode);
		}
		return Response.builder()
				.infoId(resultCode.getCode())
				.infoMsg(resultCode.getMessage())
				.data(data)
				.build();
	}

}
