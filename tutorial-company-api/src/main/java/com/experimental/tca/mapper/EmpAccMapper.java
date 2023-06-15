package com.experimental.tca.mapper;

import com.experimental.tca.entity.EmpAcc;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface EmpAccMapper{
    @Insert("insert into emp_acc(i_emp_id,vc_emp_uname,vc_emp_pword,i_emp_status,i_tag_id) values(#{id},#{username},null,#{status},null)")
    void save(EmpAcc empAcc);

    @Delete("delete from emp_acc where i_emp_id=#{id}")
    int delete(EmpAcc empAcc);

    @Update("update emp_acc set vc_emp_pword=#{password} where i_emp_id=#{id}")
    int updatePasswordById(EmpAcc empAcc);

    @Update("update emp_acc set i_emp_status=#{status} where i_emp_id=#{id}")
    void updateStatusIdById(EmpAcc empAcc);

    @Update("update emp_acc set i_tag_id=#{tagId} where i_emp_id=#{id}")
    void updateTagIdById(EmpAcc empAcc);

    @Select("select vc_emp_uname as username, i_emp_status as status, i_tag_id as tagId from emp_acc where i_emp_id=#{id}")
    EmpAcc findById(Integer id);

    @Result(property = "id", column = "i_emp_id")
    @Result(property = "username", column = "vc_emp_uname")
    @Result(property = "password", column = "vc_emp_pword")
    @Result(property = "status", column = "i_emp_status")
    @Result(property = "tagId", column = "i_tag_id")
    @Select("select * from emp_acc where vc_emp_uname=#{username}")
    Optional<EmpAcc> findByUsername(String username);


    @Result(property = "id", column = "i_emp_id")
    @Result(property = "username", column = "vc_emp_uname")
    @Result(property = "status", column = "i_emp_status")
    @Result(property = "tagId", column = "i_tag_id")
    @Select("select * from emp_acc")
    List<EmpAcc> findAll();
}
