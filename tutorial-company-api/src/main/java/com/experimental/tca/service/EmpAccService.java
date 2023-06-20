package com.experimental.tca.service;

import java.sql.Timestamp;
import java.util.*;

import com.experimental.tca.domain.Employee;
import com.experimental.tca.domain.req.EmployeeActionReq;
import com.experimental.tca.domain.req.RegisterEmployeeReq;
import com.experimental.tca.domain.req.EmployerActionReq;
import com.experimental.tca.entity.AuditLog;
import com.experimental.tca.mapper.AuditLogMapper;
import com.experimental.tca.mapper.EmpAccMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.experimental.tca.domain.res.Response;
import com.experimental.tca.util.Verification;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

/**
 * @author star.lee
 */
@Service
@RequiredArgsConstructor
public class EmpAccService {

	@Autowired
	private AuditLogMapper auditLogMapper;
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private final EmpAccMapper empAccMapper;

	private final JwtService jwtService;

	private final PasswordEncoder passwordEncoder;

	private final ObjectMapper oMap = new ObjectMapper();

	@Autowired
	private final Verification verification;

	public Response registerEmployee(RegisterEmployeeReq request) {

		String infoId = "0";
		String infoMsg = verification.verifyEmployee(request, "register_employee");
		AuditLog auditLog = new AuditLog();

		if (null == infoMsg) {

			try {

				empAccMapper.save(empAccMapper.findAll().size() + 1, request.getUsername());
			}catch (Exception e){
				e.printStackTrace();
				infoId = "1";
			}

			if ("0".equals(infoId)) {
				infoMsg = "Employee " + request.getUsername() + " created.";

				auditLog.setDt_timestamp(new Timestamp(new Date().getTime()));
				auditLog.setVc_audit_descript(infoMsg);
				auditLog.setI_emp_id(request.getEmployerId());

				auditLogMapper.save(auditLog);
				System.out.println("[" + auditLog.getDt_timestamp() + "] " + infoMsg);
			}

		}

		return Response.builder()
				.infoId(infoId)
				.infoMsg(infoMsg)
				.build();
	}

    public Response viewAllEmployee(Integer id) {

		List<Employee> employees = empAccMapper.findAll();


        String infoId = "";
        String infoMsg = verification.verifyEmployee(id, "elevated_employee_action");

        if (null == infoMsg) {
			infoId = "0";
			infoMsg = "SUCCESS";
        }
		else {
			infoId = "1";
			employees = null;
		}

		System.out.println("["+ new Timestamp(new Date().getTime()) + "] Employee "+ empAccMapper.findById(id).getUsername() + " called get all employees." );

		return Response.builder()
				.infoId(infoId)
				.infoMsg(infoMsg)
				.data(employees)
				.build();
    }

	public Response revokeEmployee(EmployerActionReq request) {

		String infoId = "";
		String infoMsg = "";

		AuditLog auditLog;

		Employee employee;

		if (null == verification.verifyEmployee(request, "revoke_employee")) {

			try {

				employee = empAccMapper.findById(request.getEmployeeId());

				empAccMapper.updateStatusIdById(employee.getId(), 0);

				infoId = "0";
				infoMsg = "Employee " + employee.getUsername() + " was deactivated.";

				auditLog = new AuditLog();

				auditLog.setDt_timestamp((new Timestamp(new Date().getTime())));
				auditLog.setVc_audit_descript(infoMsg);
				auditLog.setI_emp_id(request.getEmployerId());

				auditLogMapper.save(auditLog);

				System.out.println("[" + auditLog.getDt_timestamp() + "] " + infoMsg);

			}catch(Exception e) {

				infoMsg = e.toString();
				e.printStackTrace();
			}

		}

		return Response.builder()
				.infoId(infoId)
				.infoMsg(infoMsg)
				.build();
	}

	public Response updateEmployee(EmployeeActionReq request){

		String infoId = "0";
		String infoMsg = verification.verifyEmployee(request, "activate_employee");
		Timestamp currentTime =  new Timestamp(new Date().getTime());
		AuditLog auditLog = new AuditLog();



		if (null == infoMsg) {
			try {
				if ("PWD".equals(request.getAction())) {
					empAccMapper.updatePasswordById(request.getId(), request.getData());
					empAccMapper.updateStatusIdById(request.getId(), 1);
					infoMsg = "Employee " + empAccMapper.findById(request.getId()).getUsername() + " activated.";
				}

				auditLog.setDt_timestamp(new Timestamp(new Date().getTime()));
				auditLog.setVc_audit_descript(infoMsg);
				auditLog.setI_emp_id(request.getId());

				auditLogMapper.save(auditLog);

			}
			catch (Exception e) {
				infoMsg = e.toString();
			}
		}
		System.out.println("[" + currentTime + "] " + infoMsg);
		return Response.builder()
				.infoId(infoId)
				.infoMsg(infoMsg)
				.build();
	}

}
