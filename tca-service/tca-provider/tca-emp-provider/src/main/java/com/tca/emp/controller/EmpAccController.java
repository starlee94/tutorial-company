package com.tca.emp.controller;

import com.tca.emp.service.EmpAccService;
import com.tca.utils.abstracts.AbstractWebController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/emp")
public class EmpAccController extends AbstractWebController {

    @Autowired
    EmpAccService empAccService;

}
