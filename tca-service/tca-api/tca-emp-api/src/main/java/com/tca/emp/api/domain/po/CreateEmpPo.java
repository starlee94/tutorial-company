package com.tca.emp.api.domain.po;

import com.tca.emp.api.constant.EmployeeStatus;
import com.tca.emp.api.domain.req.CreateEmpRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateEmpPo extends CreateEmpRequest {
    private EmployeeStatus status;
    private Date createTime;
    private Date updateTime;

    public static CreateEmpPo init(CreateEmpRequest createEmpRequest){
        CreateEmpPo createEmpPo = new CreateEmpPo();
        BeanUtils.copyProperties(createEmpRequest, createEmpPo);
        createEmpPo.setStatus(EmployeeStatus.ACTIVE);
        createEmpPo.setCreateTime(new Date());
        createEmpPo.setUpdateTime(new Date());
        return createEmpPo;
    }
}


