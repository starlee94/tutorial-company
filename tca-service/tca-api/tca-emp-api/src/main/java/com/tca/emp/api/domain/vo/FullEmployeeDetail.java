package com.tca.emp.api.domain.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class FullEmployeeDetail extends EmployeeDetail{

    private String firstName;
    private String lastName;
    private Date createTime;
    private Date updateTime;
}
