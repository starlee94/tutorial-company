package com.tca.emp.api.po;

import com.tca.utils.constant.interfaces.DbTypeEnumIfc;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmployeeStatus implements DbTypeEnumIfc {

    DISABLED(0, "Account Disabled"),
    ACTIVE(1, "Account Active")
    ;

    final Integer idx;
    final String desc;

    @Override
    public int getIndex() {
        return idx;
    }
}
