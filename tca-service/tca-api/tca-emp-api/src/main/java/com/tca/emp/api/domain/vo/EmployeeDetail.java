package com.tca.emp.api.domain.vo;

import com.tca.emp.api.constant.EmployeeStatus;
import com.tca.emp.api.constant.TagType;
import lombok.Data;

@Data
public class EmployeeDetail {

    private Integer id;
    private EmployeeStatus status;
    private TagType tagId;
}
