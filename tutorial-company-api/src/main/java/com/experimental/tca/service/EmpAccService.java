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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.experimental.tca.domain.res.Response;
import com.experimental.tca.util.Verification;

import lombok.RequiredArgsConstructor;

/**
 * @author star.lee
 */
@Service
@Slf4j
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

		log.info(Common.LOG_START.getMsg());
		log.info("registerEmployee --> (employerId:{}, username:\"{}\")", request.getEmployerId(), request.getUsername());
		resultCode = verification.verifyEmployee(request, "register_employee");
		data = new Object[1];

		if (resultCode == null) {
			resultCode = ResultCode.MSG_SYSTEM_SUCCESS;
			try {
				empAccMapper.save(empAccMapper.findAll().size() + 1, request.getUsername());

				data[0] = String.format("%s %s created.", Common.EMPLOYEE.getMsg(), request.getUsername());

				auditLog.setDt_timestamp(currentTime);
				auditLog.setVc_audit_descript(data[0].toString());
				auditLog.setI_emp_id(request.getEmployerId());

				auditLogMapper.save(auditLog);
				log.info("{}", data[0]);

			}catch (Exception e){
				resultCode = ResultCode.MSG_SYSTEM_ERROR;
				e.printStackTrace();
				log.error(String.format(Common.ERROR_MSG.getMsg(), resultCode.getCode(), resultCode.getMessage()));
			}
		}
		else{
			data = null;
			log.error(String.format(Common.ERROR_MSG.getMsg(), resultCode.getCode(), resultCode.getMessage()));
		}

		log.info(Common.LOG_END.getMsg());
		return Response.builder()
				.infoId(resultCode.getCode())
				.infoMsg(resultCode.getMessage())
				.data(data)
				.build();
	}

    public Response viewAllEmployee(Integer id) {

		log.info(Common.LOG_START.getMsg());
		log.info("viewAllEmployee --> (id:{})", id);
		List<Employee> employees = empAccMapper.findAll();
		resultCode = verification.verifyEmployee(id, "elevated_employee_action");
		data = new Object[1];

		if (resultCode == null) {
			resultCode = ResultCode.MSG_SYSTEM_SUCCESS;
			log.info("Employee {} called get all employees.", empAccMapper.findById(id).getUsername());
			try {data[0] = employees;}
			catch (Exception e) {
				resultCode = ResultCode.MSG_SYSTEM_ERROR;
				e.printStackTrace();
				log.error(String.format(Common.ERROR_MSG.getMsg(), resultCode.getCode(), resultCode.getMessage()));
			}
		}
		else {
			data = null;
			log.error(String.format(Common.ERROR_MSG.getMsg(), resultCode.getCode(), resultCode.getMessage()));
		}

		log.info(Common.LOG_END.getMsg());
		return Response.builder()
				.infoId(resultCode.getCode())
				.infoMsg(resultCode.getMessage())
				.data(data)
				.build();
    }

	public Response revokeEmployee(EmployerActionReq request) {

		log.info(Common.LOG_START.getMsg());
		log.info("revokeEmployee --> (employerId:{}, employeeId:{})", request.getEmployerId(), request.getEmployeeId());
		resultCode = verification.verifyEmployee(request, "revoke_employee");
		data = new Object[1];

		if (resultCode == null) {
			resultCode = ResultCode.MSG_SYSTEM_SUCCESS;
			try {

				Employee employee = empAccMapper.findById(request.getEmployeeId());
				empAccMapper.updateStatusIdById(employee.getId(), 0);
				data[0] = String.format("%s %s was deactivated.", Common.EMPLOYEE.getMsg(), employee.getUsername());

				auditLog.setDt_timestamp(currentTime);
				auditLog.setVc_audit_descript(data[0].toString());
				auditLog.setI_emp_id(request.getEmployerId());

				auditLogMapper.save(auditLog);

				log.info("{}", data[0].toString());

			}catch(Exception e) {
				resultCode = ResultCode.MSG_SYSTEM_ERROR;
				e.printStackTrace();
				log.error(String.format(Common.ERROR_MSG.getMsg(), resultCode.getCode(), resultCode.getMessage()));
			}
		}
		else {
			data = null;
			log.error(String.format(Common.ERROR_MSG.getMsg(), resultCode.getCode(), resultCode.getMessage()));
		}

		log.info(Common.LOG_END.getMsg());
		return Response.builder()
				.infoId(resultCode.getCode())
				.infoMsg(resultCode.getMessage())
				.data(data)
				.build();
	}

	public Response updateEmployee(EmployeeActionReq request){

		log.info(Common.LOG_START.getMsg());
		log.info("updateEmployee --> (id:{}, action:\"{}\", data:\"{}\")", request.getId(), request.getAction(), request.getData());
		resultCode = verification.verifyEmployee(request, "activate_employee");
		data = new Object[1];

		if (resultCode == null) {
			resultCode = ResultCode.MSG_SYSTEM_SUCCESS;
			try {
				if ("PWD".equals(request.getAction())) {
					empAccMapper.updatePasswordById(request.getId(), request.getData());
					empAccMapper.updateStatusIdById(request.getId(), 1);
					data[0] = Common.EMPLOYEE.getMsg() + " " + empAccMapper.findById(request.getId()).getUsername() + " activated.";
				}

				auditLog.setDt_timestamp(currentTime);
				auditLog.setVc_audit_descript(data[0].toString());
				auditLog.setI_emp_id(request.getId());

				auditLogMapper.save(auditLog);
				log.info("{}", data[0].toString());
			}
			catch (Exception e) {
				resultCode = ResultCode.MSG_SYSTEM_ERROR;
				e.printStackTrace();
				log.error(String.format(Common.ERROR_MSG.getMsg(), resultCode.getCode(), resultCode.getMessage()));
			}
		}
		else {
			data = null;
			log.error(String.format(Common.ERROR_MSG.getMsg(), resultCode.getCode(), resultCode.getMessage()));
		}
		log.info(Common.LOG_END.getMsg());
		return Response.builder()
				.infoId(resultCode.getCode())
				.infoMsg(resultCode.getMessage())
				.data(data)
				.build();
	}

}
