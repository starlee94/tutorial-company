package com.tca.emp.api.domain.req;

import com.tca.emp.api.constant.EmployeeStatus;
import lombok.Data;

@Data
public class SetStatusRequest {
    private Integer employeeId;
    private EmployeeStatus status;
}
