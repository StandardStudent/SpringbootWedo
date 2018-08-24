package com.weido.engineer.repository;

import com.weido.engineer.pojo.Seperate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeperateRepository extends JpaRepository<Seperate,Integer> {
    @Query(value = "SELECT e.eid, e.`name`," +
            " AVG(c.appraise_score) as appraise_score FROM " +
            " comm_orders c, engineers e WHERE" +
            " c.finished >=?2 AND e.eid = ?1 ",nativeQuery = true)
    Seperate findScoreByEid(int eid,int finished);

    @Query(value = "SELECT e.eid,e.`name`," +
            " AVG(c.appraise_score) as appraise_score FROM" +
            " comm_orders c,engineers e WHERE" +
            " c.finished >=?2 AND c.service_shop_gid = ?1 and e.roles_rid=?3 group by e.eid",nativeQuery = true)
    List<Seperate> findScoreByGid(int gid,int finished,int role);
}
