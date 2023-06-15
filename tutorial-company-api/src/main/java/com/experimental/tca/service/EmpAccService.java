package com.experimental.tca.service;

import java.sql.Timestamp;
import java.util.*;

import com.experimental.tca.entity.AuditLog;
import com.experimental.tca.entity.EmpAcc;
import com.experimental.tca.mapper.AuditLogMapper;
import com.experimental.tca.mapper.EmpAccMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.experimental.tca.domain.req.Request;
import com.experimental.tca.domain.res.Response;
import com.experimental.tca.util.Verification;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

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

	public Response registerEmployee(Request<EmpAcc> request) {

		String infoId = "0";
		String infoMsg = "";
		EmpAcc empAcc = new EmpAcc();
		AuditLog auditLog = new AuditLog();

		if (null == verification.verifyEmployee(request, empAccMapper.findAll(), "register_employee")) {

			empAcc = request.getData();

			try {
				empAccMapper.save(empAcc);
			}catch (Exception e){
				e.printStackTrace();
				infoId = "1";
			}

		}
		if ("0".equals(infoId)) {
			infoMsg = "Employee " + empAcc.getUsername() + " created.";

			auditLog.setDt_timestamp(new Timestamp(new Date().getTime()));
			auditLog.setVc_audit_descript(infoMsg);
			auditLog.setI_emp_id(request.getData().getId());

		}


		return Response.builder()
				.infoId(infoId)
				.infoMsg(infoMsg)
				.build();
	}

    public Response viewAllEmployee(Request<EmpAcc> request) {

		List<EmpAcc> empAccs = empAccMapper.findAll();

        String infoId = "";
        String infoMsg = verification.verifyEmployee(request, empAccs, "elevated_employee_action");

        if (null == infoMsg) {
			infoId = "0";
			infoMsg = "SUCCESS";
        }
		else {
			infoId = "1";
			empAccs = null;
		}

		System.out.println("["+ new Timestamp(new Date().getTime()) + "] Employee "+ empAccMapper.findById(request.getData().getId()) );

		return Response.builder()
				.infoId(infoId)
				.infoMsg(infoMsg)
				.data(empAccs)
				.build();
    }

	public Response revokeEmployee(Request<EmpAcc> request) {

		String infoId = "";
		String infoMsg = "";

		AuditLog auditLog;

		EmpAcc empAcc;

		if (null == verification.verifyEmployee(request, empAccMapper.findAll(), "revoke_employee")) {

			try {

				empAcc = empAccMapper.findByUsername(request.getData().getUsername()).orElse(new EmpAcc());

				empAcc.setStatus(0);

				empAccMapper.updateStatusIdById(empAcc);

				infoId = "0";
				infoMsg = "Employee " + request.getData().getUsername() + " was deactivated.";

				auditLog = new AuditLog();

				auditLog.setDt_timestamp((new Timestamp(new Date().getTime())));
				auditLog.setVc_audit_descript(infoMsg);
				auditLog.setI_emp_id(request.getData().getId());

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

}
