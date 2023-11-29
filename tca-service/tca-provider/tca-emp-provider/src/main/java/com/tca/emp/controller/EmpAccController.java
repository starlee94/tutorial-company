package com.tca.emp.controller;

import com.tca.core.Response;
import com.tca.core.constant.abstracts.AbstractWebController;
import com.tca.emp.service.EmpAccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author star.lee
 */
@RestController
@RequestMapping("/emp")
public class EmpAccController extends AbstractWebController {

    @Autowired
    EmpAccService empAccService;

    @GetMapping("/test")
    public Response<Void> testAuth() throws Exception { return empAccService.test(); }
}
