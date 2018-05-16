package com.actec.ms.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TableDao {

    Integer tableNum(@Param("tableName") String tableName);

    void deleteTableByName(@Param("tableName") String tableName);

    void deleteColumn(@Param("columnName") String columnName, @Param("tableName") String tableName);

    int columnExisted(@Param("columnName") String columnName, @Param("tableName") String tableName);

    void addVarCharColumn(@Param("columnName") String columnName, @Param("tableName") String tableName);

    void addBigVarCharColumn(@Param("columnName") String columnName, @Param("tableName") String tableName);

    void addIntColumn(@Param("columnName") String columnName, @Param("tableName") String tableName);
}
