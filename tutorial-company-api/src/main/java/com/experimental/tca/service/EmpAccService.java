package com.experimental.tca.service;

import java.sql.Timestamp;
import java.util.*;

import com.experimental.tca.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.experimental.tca.data.Request;
import com.experimental.tca.data.Response;
import com.experimental.tca.repository.AuditLogRepository;
import com.experimental.tca.repository.EmpAccRepository;
import com.experimental.tca.util.Verification;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpAccService {

	@Autowired
	private final EmpAccRepository empAccRepository;

	@Autowired
	private final AuditLogRepository auditLogRepository;

	@Autowired
	private EntityManager entityManager;

	private final JwtService jwtService;

	private final PasswordEncoder passwordEncoder;

	private final ObjectMapper oMap = new ObjectMapper();

	private final ArrayList<String> sqlReturn = new ArrayList<>();

	private final ArrayList<String> removeKeyList = new ArrayList<>();

	private final Verification verification = new Verification();

	@SuppressWarnings("unchecked")
	private List<EmpAcc> processEmpAcc(List<EmpAcc> empAcc) {


		return responseObj;
	}

	public Response register_employee(Request<EmpAcc> request) {

		String infoId = "";
		String infoMsg = "";
		EmpAcc empAcc;

		if (null == verification.verify_employee(request, empAccRepository.findAll(), "register_employee")) {

			empAcc = request.getData();

			infoId = entityManager.createNamedStoredProcedureQuery("api_create_emp_acc")
						 .setParameter("emp_id", empAcc.getId())
						 .setParameter("uname",empAcc.getUsername())
						 .setParameter("pword", passwordEncoder.encode(empAcc.getPassword()))
						 .setParameter("action_timestamp", new Timestamp(new Date().getTime()))
						 .getResultList().get(0).toString();

		}

		if (1 < sqlReturn.size()) infoMsg = sqlReturn.get(1);
		if ("0".equals(infoId)) infoMsg = "Employee created.";


		return Response.builder()
				.infoId(infoId)
				.infoMsg(infoMsg)
				.build();
	}

    public Response view_all_employee(Request<EmpAcc> request) {

        String infoId = "";
        String infoMsg = "";
		List<EmpAcc> empAccs;
		empAccs =  empAccRepository.findAll();
		infoMsg = verification.verify_employee(request, empAccs, "elevated_employee_action");
        if (null == infoMsg) {
			infoId = "0";
			infoMsg = "SUCCESS";
        }
		else {
			infoId = "1";
			empAccs = null;
		}

		return Response.builder()
				.infoId(infoId)
				.infoMsg(infoMsg)
				.data(empAccs)
				.build();
    }

	public Response revoke_employee(Request<EmpAcc> request) {

		String infoId = "";
		String infoMsg = "";

		AuditLog auditLog;

		List<EmpAcc> empAccs;
		EmpAcc empAcc;

		empAccs = empAccRepository.findAll();

		if (null == verification.verify_employee(request, empAccs, "revoke_employee")) {

			try {

				empAcc = empAccs.stream().filter(emp -> emp.getUsername()
										 .equals(request.getData()
										 .getUsername()))
									     .findFirst()
										 .get();
				empAcc.setStatus(0);

				empAccRepository.save(empAcc);


				auditLog = new AuditLog();

				auditLog.setDt_timestamp((new Timestamp(new Date().getTime())));
				auditLog.setVc_audit_descript(request.getData().getUsername() + " was deactivated.");
				auditLog.setI_emp_id(request.getData().getId());

				auditLogRepository.save(auditLog);

				infoId = "0";
				infoMsg = request.getData().getUsername() + " was deactivated.";

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
