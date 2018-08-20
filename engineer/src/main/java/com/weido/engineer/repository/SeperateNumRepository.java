package com.weido.engineer.repository;

import com.weido.engineer.pojo.SeperateNum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeperateNumRepository extends JpaRepository<SeperateNum,Integer> {

    @Query(value = "select count(*) as number,e.name from engineers e,comm_orders c GROUP BY c.engineers_eid",nativeQuery = true)
    List<SeperateNum> findNumberByEid(int eid);
}
