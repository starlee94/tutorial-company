package com.tca.utils.constant.finals;

import com.tca.utils.constant.interfaces.DbTypeEnumIfc;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class GlobalDbTypeEnumHandler<T extends DbTypeEnumIfc> extends BaseTypeHandler<T> {

    private Class<T> type;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public GlobalDbTypeEnumHandler(Class<T> type) {
        if (type == null){
            throw new RuntimeException("type should not be empty！");
        }
        if (!type.isEnum()){
            throw new RuntimeException("type must inherit from " + DbTypeEnumIfc.class.getName() + " enum!");
        }
        this.type = type;
        logger.info("-----------------------------------Mybatis custom Enum Converter {} initialization Complete! -----------------------------------", this.type.getName());
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        if (jdbcType == null) {
            ps.setInt(i, parameter.getIndex());
        } else {
//            当在属性名称后面指定 jdbcType=XXX 时会走这个逻辑
            ps.setObject(i, parameter.getIndex(), jdbcType.TYPE_CODE);
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int index = rs.getInt(columnName);
        return DbTypeEnumIfc.parseEnum(type, index);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int index = rs.getInt(columnIndex);
        return DbTypeEnumIfc.parseEnum(type, index);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int index = cs.getInt(columnIndex);
        return DbTypeEnumIfc.parseEnum(type, index);
    }
}
