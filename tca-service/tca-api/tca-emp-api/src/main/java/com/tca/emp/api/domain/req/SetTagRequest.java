package com.tca.emp.api.domain.req;

import com.tca.emp.api.constant.TagType;
import lombok.Data;

@Data
public class SetTagRequest {
    private Integer employeeId;
    private TagType tagId;
}
