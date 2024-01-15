package com.tca.emp.api.vo;

import com.tca.emp.api.po.EmployeeStatus;
import com.tca.emp.api.po.TagType;
import lombok.Data;

@Data
public class EmployeeDetail {

    private Integer id;
    private EmployeeStatus status;
    private TagType tagId;
}
