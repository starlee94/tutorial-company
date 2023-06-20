package com.experimental.tca.domain.req;

import lombok.Data;

/**
 * @author star.lee
 */
@Data
public class EmployeeActionReq {

    private Integer id;
    private String action;
    private String data;
}
