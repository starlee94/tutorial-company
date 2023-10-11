package com.tca.utils.abstracts;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

abstract class BaseController extends ProjectBase{

    @Autowired
    protected HttpServletResponse httpServletResponse;

    @Autowired
    protected HttpServletRequest httpServletRequest;
}
