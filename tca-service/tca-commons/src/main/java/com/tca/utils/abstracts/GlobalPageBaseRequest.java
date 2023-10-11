package com.tca.utils.abstracts;

import com.google.gson.Gson;
import com.tca.utils.enums.GlobalRequestEnum;
import com.tca.utils.exception.SurfaceException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 全局基础Req，提供共有tostring 和 分页参数
 * 建议查询业务中的Req都继承该类
 * @author Laban
 */
@Data
public abstract class GlobalPageBaseRequest implements Serializable {

    /**
     * 当前页 默认为1
     */
    @ApiModelProperty(value = "当前页码",name = "page",example = "1",required = true)
    private Integer page = 1;


    /**
     * 每页展示条数 默认为10
     */
    @ApiModelProperty(value = "每页显示条数",name = "limit",example = "10",required = true)
    private Integer limit = 10;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }


    public void validate(){
        if (page == null || limit == null){
            throw new SurfaceException(GlobalRequestEnum.PARAM_LOSS);
        }
        if (page < 1 || limit < 1){
            throw new SurfaceException(GlobalRequestEnum.PARAM_UNSUPPORT);
        }
    }


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
