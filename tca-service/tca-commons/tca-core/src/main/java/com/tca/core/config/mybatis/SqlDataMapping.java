package com.tca.core.config.mybatis;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Devin.qi < devin.qi@ixsecurities.com />
 */
public class SqlDataMapping {


    /**
     * 默认值禁止修改！
     * @author Devin.qi < devin.qi@ixsecurities.com />
     */
    private String sqlData = "sql_data";

    /**
     * 默认值禁止修改！
     * @author Devin.qi < devin.qi@ixsecurities.com />
     */
    private String userRelaAlias = "rela";


    /**
     * 默认值禁止修改！
     * @author Devin.qi < devin.qi@ixsecurities.com />
     */
    private String accRelaAlias = "acc_rela";


    /**
     * 默认值禁止修改！
     * @author Devin.qi < devin.qi@ixsecurities.com />
     */
    private SqlDataType sqlDataType = SqlDataType.ONLY_USER;

    private SqlDataMapping(){}

    public final static SqlDataMapping convertToSqlDataMapping(SqlDataPermission sqlDataPermission){
        SqlDataMapping sqlDataMapping = new SqlDataMapping();
        String value = sqlDataPermission.value();
        if (!StringUtils.isBlank(value)){
            sqlDataMapping.sqlData = value.trim();
        }
        String userRelaAlias = sqlDataPermission.userRelaAlias();
        if (!StringUtils.isBlank(userRelaAlias)){
            sqlDataMapping.userRelaAlias = userRelaAlias.trim();
        }
        String accRelaAlias = sqlDataPermission.accRelaAlias();
        if (!StringUtils.isBlank(accRelaAlias)){
            sqlDataMapping.accRelaAlias = accRelaAlias.trim();
        }
        SqlDataType sqlDataType = sqlDataPermission.sqlDataType();
        if (sqlDataType != null){
            sqlDataMapping.sqlDataType = sqlDataType;
        }
        return sqlDataMapping;
    }

    public String getSqlData() {
        return sqlData;
    }

    public String getUserRelaAlias() {
        return userRelaAlias;
    }

    public String getAccRelaAlias() {
        return accRelaAlias;
    }

    public SqlDataType getSqlDataType() {
        return sqlDataType;
    }
}
