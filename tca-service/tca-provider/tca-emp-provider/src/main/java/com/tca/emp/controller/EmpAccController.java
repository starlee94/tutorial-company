package com.tca.emp.controller;

import com.tca.core.Response;
import com.tca.core.constant.abstracts.AbstractWebController;
import com.tca.core.constant.enums.GlobalSystemEnum;
import com.tca.core.controller.BearerAuthController;
import com.tca.emp.api.domain.req.SetTagRequest;
import com.tca.emp.api.domain.req.CreateEmpRequest;
import com.tca.emp.api.domain.req.SetStatusRequest;
import com.tca.emp.api.domain.vo.EmployeeDetail;
import com.tca.emp.api.domain.vo.FullEmployeeDetail;
import com.tca.emp.service.EmpAccActionService;
import com.tca.emp.service.EmpAccQueryService;
import com.tca.emp.service.EmpAccTestService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author star.lee
 */
@RestController
@RequestMapping("/emp")
public class EmpAccController extends AbstractWebController implements BearerAuthController {

    @Autowired
    EmpAccQueryService empAccQueryService;

    @Autowired
    EmpAccActionService empAccActionService;

    @GetMapping("/test")
    public Response<Void> testAuth() { return Response.genResp(GlobalSystemEnum.OK, "success access"); }

    @Autowired
    EmpAccTestService empAccTestService;

    @GetMapping("/get/test/id")
    public Response<Object> getTestId() { return handle(empAccTestService, null); }

    @Hidden
    @GetMapping("/query/username")
    public Response<EmployeeDetail> queryEmpByUsername(@RequestParam("username") String username) throws Exception { return empAccQueryService.queryUsername(username); }

    @GetMapping("/query/list")
    public Response<List<FullEmployeeDetail>> queryEmployees() { return empAccQueryService.queryEmployees(); }

    @PostMapping("/create")
    public Response<Void> createEmployee(CreateEmpRequest createEmpRequest) throws Exception { return empAccActionService.createEmployee(createEmpRequest); }

    @PutMapping("/set/tag")
    public Response<Void> setTag(SetTagRequest setTagRequest) throws Exception { return empAccActionService.setTag(setTagRequest); }

    @PutMapping("/set/status")
    public Response<Void> setStatus(SetStatusRequest setStatusRequest) throws Exception { return empAccActionService.setStatus(setStatusRequest); }

}
