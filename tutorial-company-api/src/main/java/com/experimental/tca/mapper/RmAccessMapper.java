package com.experimental.tca.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author star.lee
 */
@Mapper
public interface RmAccessMapper {

    @Select("select i_entry_count_daily from rm_access where i_rm_access_id=#{id}")
    int getAccessCountById(int id);

}
