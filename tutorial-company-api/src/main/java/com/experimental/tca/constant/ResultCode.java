package com.experimental.tca.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author star.lee
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    MSG_SYSTEM_SUCCESS(200, "Success"),
    MSG_SYSTEM_TOKEN_INVALID(1000,"Token invalid"),
    MSG_SYSTEM_TOKEN_EXPIRED(1001,"Token Expired"),
    MSG_SYSTEM_EMPLOYER_NOT_FOUND(1002, "Employer not found"),
    MSG_SYSTEM_EMPLOYEE_NOT_FOUND(1003, "Employee not found"),
    MSG_SYSTEM_EMPLOYER_INVALID_PERMISSION(1004, "Employer does not have permission"),
    MSG_SYSTEM_EMPLOYEE_INVALID_PERMISSION(1005, "Employee does not have permission"),
    MSG_SYSTEM_EMPLOYEE_INVALID_PASSWORD(1006, "Employee credential incorrect"),
    MSG_SYSTEM_EMPLOYEE_EXIST_IN_DB(1007, "Employee exist in database"),
    MSG_SYSTEM_ERROR(9999, "Error occurred");


    private final Integer code;
    private final String message;

}
