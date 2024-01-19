package com.tca.auth.mapper;

import com.tca.core.entity.EmpAcc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author star.lee
 */
@Mapper
public interface AuthMapper {

    EmpAcc findByUsername(@Param("username") String username);

    String verifyToken(@Param("token") String token);

    Void clearToken(@Param("token") Integer id);

    Void updateToken(@Param("id") Integer id,
                     @Param("token") String token);
}
