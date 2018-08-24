package com.weido.engineer.repository;

import com.weido.engineer.pojo.SeperateNum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeperateNumRepository extends JpaRepository<SeperateNum,Integer> {

    @Query(value = "select count(*) as number,e.name,e.eid from engineers e,comm_orders c " +
            "where e.eid=?1 and c.finished>=?2 GROUP BY e.eid ",nativeQuery = true)
    SeperateNum findNumberByEid(int eid,int finished);
    @Query(value = "select count(*) as number,e.name,e.eid from engineers e,comm_orders c " +
            "where e.service_shop_gid=?1 and c.finished>=?2 and e.roles_rid=?3 GROUP BY e.eid ",nativeQuery = true)
    List<SeperateNum> findNumberByGid(int gid,int finished,int role);
}
