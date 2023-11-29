package com.tca.core.config.mybatis;

import java.lang.annotation.*;

/**
 * 该注解标识的mybatis方法会自动添加数据权限片段
 * @author Devin.qi < devin.qi@ixsecurities.com />
 * @see SqlDataInterceptor
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SqlDataPermission {


    /**
     * sql中变量的名称，默认是 sql_data，
     * 当 value = "sql_data" 时，在语句中写的值是 -{sql_data}
     * @return
     * @see SqlDataMapping#getSqlData()
     */
    String value() default "";


    /**
     * user_relation 关系表别名，默认是 rela，
     * 语句中使用时是 rela.user_id = XXX 的模式
     * @return
     * @see SqlDataMapping#getUserRelaAlias()
     */
    String userRelaAlias() default "";


    /**
     * account_relation 关系表别名，默认是 acc_rela，
     * 语句中使用时是 acc_rela.`org_id` in (1,2,4,7,8,5,6,9,12,13,10,14,15,11,3) 的模式;
     * @return
     * @see SqlDataMapping#getAccRelaAlias() ()
     */
    String accRelaAlias() default "";


    /**
     * 数据权限控制类型，默认只用用户关系来控制。
     * 默认值不要修改！！
     * @return
     * @see SqlDataType
     */
    SqlDataType sqlDataType() default SqlDataType.ONLY_USER;


}
