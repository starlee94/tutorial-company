package com.experimental.tca.mapper;

import com.experimental.tca.domain.Employee;
import com.experimental.tca.entity.EmpAcc;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * @author star.lee
 */
@Mapper
public interface EmpAccMapper{
    @Insert("insert into emp_acc(i_emp_id,vc_emp_uname,vc_emp_pword,i_emp_status,i_tag_id) values(#{id},#{username},null,0,null)")
    void save(Integer id, String username);

    @Delete("delete from emp_acc where i_emp_id=#{id}")
    int delete(Employee employee);

    @Update("update emp_acc set vc_emp_pword=#{password} where i_emp_id=#{id}")
    int updatePasswordById(Integer id, String password);

    @Update("update emp_acc set i_emp_status=#{status} where i_emp_id=#{id}")
    void updateStatusIdById(Integer id, Integer status);

    @Update("update emp_acc set i_tag_id=#{tagId} where i_emp_id=#{id}")
    void updateTagIdById(Employee employee);

    @Result(property = "id", column = "i_emp_id")
    @Result(property = "username", column = "vc_emp_uname")
    @Result(property = "status", column = "i_emp_status")
    @Result(property = "tagId", column = "i_tag_id")
    @Select("select * from emp_acc where i_emp_id=#{id}")
    Employee findById(Integer id);

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
    List<Employee> findAll();
}
