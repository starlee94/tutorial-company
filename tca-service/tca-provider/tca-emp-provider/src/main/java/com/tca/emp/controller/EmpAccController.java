package com.tca.emp.controller;

import com.tca.core.Response;
import com.tca.core.constant.abstracts.AbstractWebController;
import com.tca.core.constant.enums.GlobalSystemEnum;
import com.tca.core.controller.BearerAuthController;
import com.tca.emp.api.vo.EmployeeDetail;
import com.tca.emp.api.vo.FullEmployeeDetail;
import com.tca.emp.service.EmpAccQueryService;
import com.tca.emp.service.EmpAccTestService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author star.lee
 */
@RestController
@RequestMapping("/emp")
public class EmpAccController extends AbstractWebController implements BearerAuthController {

    @Autowired
    EmpAccQueryService empAccQueryService;

    @GetMapping("/test")
    public Response<Void> testAuth() { return Response.genResp(GlobalSystemEnum.OK, "success access"); }

    @Hidden
    @GetMapping("/query/username")
    public Response<EmployeeDetail> queryEmpByUsername(@RequestParam("username") String username) throws Exception { return empAccQueryService.queryUsername(username); }

    @Autowired
    EmpAccTestService empAccTestService;

    @GetMapping("/get/test/id")
    public Response<Object> getTestId() { return handle(empAccTestService, null); }

    @GetMapping("/query/list")
    public Response<List<FullEmployeeDetail>> queryEmployees() { return empAccQueryService.queryEmployees(); }
}
