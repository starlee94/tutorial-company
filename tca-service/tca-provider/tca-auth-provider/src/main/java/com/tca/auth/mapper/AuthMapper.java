package com.tca.auth.mapper;

import com.tca.core.entity.EmpAcc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

/**
 * @author star.lee
 */
@Mapper
public interface AuthMapper {

    Optional<EmpAcc> findByUsername(String username);

    Optional<String> verifyToken(@Param("token") String token);

    Void clearToken(@Param("token") Integer id);

    Void updateToken(@Param("id") Integer id,
                     @Param("token") String token);
}
