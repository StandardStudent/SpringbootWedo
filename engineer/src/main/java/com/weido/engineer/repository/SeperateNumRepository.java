package com.weido.engineer.repository;

import com.weido.engineer.pojo.SeperateNum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeperateNumRepository extends JpaRepository<SeperateNum,Integer> {

    @Query(value = "select count(c.oid) as number,e.name,e.eid from  engineers AS e "  +
            "LEFT JOIN comm_orders AS c ON e.eid = c.engineers_eid and c.finished>=?2  " +
            "where e.eid=?1 GROUP BY e.eid ",nativeQuery = true)
    SeperateNum findNumberByEid(int eid,int finished);
    @Query(value = "SELECT" +
            " es.eid," +
            " es.`name`," +
            " count(cs.oid) as number " +
            "FROM" +
            " engineers AS es " +
            "LEFT JOIN comm_orders AS cs ON es.eid = cs.engineers_eid and cs.finished>=?2 " +
            "WHERE " +
            "es.roles_rid=?3 and es.service_shop_gid=?1 " +
            "GROUP BY" +
            " es.eid ",nativeQuery = true)
    List<SeperateNum> findNumberByGid(int gid,int finished,int role);
}
