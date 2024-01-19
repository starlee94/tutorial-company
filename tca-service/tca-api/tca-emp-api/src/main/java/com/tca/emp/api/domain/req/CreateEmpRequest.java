package com.tca.emp.api.domain.req;

import lombok.Data;

@Data
public class CreateEmpRequest {
    private String username;
    private String firstName;
    private String lastName;
}
