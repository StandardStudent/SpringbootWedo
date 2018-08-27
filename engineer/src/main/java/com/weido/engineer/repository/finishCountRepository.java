package com.weido.engineer.repository;

import com.weido.engineer.pojo.finishCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface finishCountRepository extends JpaRepository<finishCount,Integer> {
    @Query(value = "select count(*) as count,c.finished as finished from comm_orders c " +
            "where engineers_eid=?1 and c.service_shop_gid=?2 and finished=1",nativeQuery = true)
    finishCount findCountByEidAndGid(int eid, int gid);

    @Query(value = "select count(*) as count,c.finished from comm_orders c " +
            "where engineers_eid=?1 and finished=?2",nativeQuery = true)
    finishCount findCountByEid(int eid,int finished);

    @Query(value = "select count(*) as count,c.finished as finished from comm_orders c " +
            "where finished=?1",nativeQuery = true)
    finishCount findCountByFinished(int finished);

    @Query(value = "select count(*) as count,c.finished from comm_orders c " +
            "where engineers_eid=?1 and finished=?2 and order_type_order_type=2",nativeQuery = true)
    finishCount findCountByEidAndType(int eid,int finished);

}
