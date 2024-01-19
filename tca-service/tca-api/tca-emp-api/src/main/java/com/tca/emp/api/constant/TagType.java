package com.tca.emp.api.constant;

import com.tca.utils.constant.interfaces.DbTypeEnumIfc;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TagType implements DbTypeEnumIfc {
    NOT_ASSIGN(0, "Unassigned"),
    CEO(1, "Chief Executive Officer"),
    HR(2, "Human Resource"),
    TL(3, "Tech Lead"),
    SD(4, "Senior Developer"),
    D(5, "Developer"),
    JD(6, "Junior Developer")
    ;

    final Integer idx;
    final String desc;

    @Override
    public int getIndex() {
        return idx;
    }
}
