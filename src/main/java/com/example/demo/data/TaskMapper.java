package com.example.demo.data;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TaskMapper {
    List<String> getAllTables();
    List<Map<String,Object>> fetData(@Param("tableName")String tableName);

    List<Map<String,Object>> getStructure(@Param("tableName")String tableName);
}
