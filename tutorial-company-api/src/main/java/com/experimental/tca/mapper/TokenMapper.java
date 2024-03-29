package com.experimental.tca.mapper;

import com.experimental.tca.entity.v1.Token;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * @author star.lee
 */
@Mapper
public interface TokenMapper {

    @Result(property = "id", column = "id")
    @Result(property = "revoked", column = "revoked")
    @Result(property = "tokenString", column = "token")
    @Result(property = "tokenType", column = "token_type")
    @Result(property = "empAcc.id", column = "i_emp_id")
    @Select("select * from token")
    List<Token> findAll();


    @Result(property = "id", column = "id")
    @Result(property = "revoked", column = "revoked")
    @Result(property = "tokenString", column = "token")
    @Result(property = "tokenType", column = "token_type")
    @Result(property = "empAcc.id", column = "i_emp_id")
    @Select("select * from token where i_emp_id =#{id} and revoked=0")
    List<Token> findAllValidTokenByUser(Integer id);

    @Result(property = "id", column = "id")
    @Result(property = "revoked", column = "revoked")
    @Result(property = "tokenString", column = "token")
    @Result(property = "tokenType", column = "token_type")
    @Result(property = "empAcc.id", column = "i_emp_id")
    @Select("select * from token where token=#{token}")
    Optional<Token> findByToken(String token);

    @Update("update token set revoked=1")
    void revokeAll();

    @Insert("insert into token(revoked,token,token_type,i_emp_id) values(#{revoked}, #{tokenString}, #{tokenType}, #{empAcc.id})")
    void save(Token token);

    @Delete("delete from token where token=#{tokenString}")
    void delete(Token token);

    @Update("update token set revoked=1 where token=#{tokenString}")
    void revokeToken(Token token);


}
