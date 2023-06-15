package com.experimental.tca.mapper;

import com.experimental.tca.entity.RmAccess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface RmAccessMapper {

    @Select("select i_entry_count_daily from rm_access where i_rm_access_id=#{id}")
    int getAccessCountById(int id);

    @Update("update rm_access set i_entry_count_daily=#{i_entry_count_daily} where i_rm_access_id=#{i_rm_access_id}")
    void updateAccessCount(RmAccess rmAccess);
}
