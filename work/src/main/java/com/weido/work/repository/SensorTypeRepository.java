package com.weido.work.repository;

import com.weido.work.pojo.SensorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SensorTypeRepository extends JpaRepository<SensorType,Integer> {
    @Query(value = "select typeid from SensorType where name=?1")
    int findIdByName(String typeName);
}
