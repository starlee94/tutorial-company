package com.tca.core.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface TokenMapper {

     Optional<String> verifyToken (String token);
    int clearToken (String token);
}
