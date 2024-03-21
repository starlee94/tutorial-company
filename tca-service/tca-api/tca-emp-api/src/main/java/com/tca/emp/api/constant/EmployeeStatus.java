package com.tca.emp.api.constant;

import com.tca.utils.constant.interfaces.DbTypeEnumIfc;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmployeeStatus implements DbTypeEnumIfc {

    DISABLED(0, "Account Disabled"),
    ACTIVE(1, "Account Active"),
    NEW_EMPLOYEE(2, "New Account")
    ;

    final Integer idx;
    final String desc;

    @Override
    public int getIndex() {
        return idx;
    }
}
