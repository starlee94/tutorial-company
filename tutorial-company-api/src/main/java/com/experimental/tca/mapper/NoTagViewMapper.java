package com.experimental.tca.mapper;

import com.experimental.tca.entity.NoTagView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NoTagViewMapper {

    @Select("select * from no_tag_view")
    List<NoTagView> findAll();
}
