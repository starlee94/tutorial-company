package com.experimental.tca.mapper;

import com.experimental.tca.entity.Token;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TokenMapper {

    @Result(property = "id", column = "id")
    @Result(property = "revoked", column = "revoked")
    @Result(property = "token", column = "token")
    @Result(property = "tokenType", column = "token_type")
    @Select("select * from Token")
    List<Token> findAll();


    @Result(property = "id", column = "id")
    @Result(property = "revoked", column = "revoked")
    @Result(property = "token", column = "token")
    @Result(property = "tokenType", column = "token_type")
    @Select("select * from Token where i_emp_id =#{id} and revoked=0")
    List<Token> findAllValidTokenByUser(Integer id);

    @Result(property = "id", column = "id")
    @Result(property = "revoked", column = "revoked")
    @Result(property = "token", column = "token")
    @Result(property = "tokenType", column = "token_type")
    @Result(property = "empAcc.id", column = "i_emp_id")
    @Select("select * from Token where token=#{token}")
    Optional<Token> findByToken(String token);

    @Update("update Token set revoked=1")
    void revokeAll();

    @Insert("insert into Token(revoked,token,token_type,i_emp_id) values(#{revoked}, #{token}, #{tokenType}, #{empAcc.id})")
    void save(Token token);

    @Delete("delete from Token where id=#{id}")
    void delete(Token token);

    @Update("update Token set revoked=1 where token=#{token}")
    void revokeToken(Token token);


}
