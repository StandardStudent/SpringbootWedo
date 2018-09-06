package com.weido.engineer.repository;

import com.weido.engineer.pojo.Seperate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeperateRepository extends JpaRepository<Seperate,Integer> {
    @Query(value = "SELECT e.eid, e.`name`," +
            " AVG(c.appraise_score) as appraise_score FROM " +
            "  engineers e LEFT JOIN comm_orders c ON e.eid = c.engineers_eid" +
            " and c.finished>=?2 WHERE" +
            " e.eid = ?1 ",nativeQuery = true)
    Seperate findScoreByEid(int eid,int finished);

    @Query(value = "SELECT" +
            " es.eid," +
            " es.`name`," +
            " AVG(cs.appraise_score) AS appraise_score " +
            "FROM " +
            " engineers AS es " +
            " LEFT JOIN comm_orders AS cs ON es.eid = cs.engineers_eid and cs.finished>=?2 " +
            "WHERE " +
            "es.roles_rid=?3 and es.service_shop_gid=?1 " +
            "GROUP BY" +
            " es.eid ",nativeQuery = true)
    List<Seperate> findScoreByGid(int gid,int finished,int role);
}
