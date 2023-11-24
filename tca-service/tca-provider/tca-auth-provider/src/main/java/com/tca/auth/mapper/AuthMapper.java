package com.tca.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

/**
 * @author star.lee
 */
@Mapper
public interface AuthMapper {

//    Optional<EmpAcc> findByUsername(String username);

    Optional<String> verifyToken(@Param("token") String token);

    Void clearToken(@Param("token") Integer id);
}
