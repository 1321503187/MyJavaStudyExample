package com.example.readinglist.mapper;

import com.example.readinglist.dto.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
@MapperScan("com.example.readinglist.mapper")
public interface TagMapper {
    public List<Tag> getTagsByBookId(@Param("id") Integer id);
}
