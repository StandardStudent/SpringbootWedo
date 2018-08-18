package com.weido.engineer.repository;

import com.weido.engineer.pojo.CommOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface CommOrderRepository extends JpaRepository<CommOrders,Integer> {

    @Query(value = "select c.* from comm_orders c where engineers_eid=?1 " +
            "and order_time=?2",nativeQuery = true)
    List<CommOrders> findAllByEidAndCurrentTime(int eid,Date time);
    @Query(value = "select c.* from comm_orders c where engineers_eid=?1 " +
            "and order_time between ?2 and ?3",nativeQuery = true)
    List<CommOrders> findWMByEidAndCurrent(int eid,Date Monday,Date Friday);
    @Query(value = "select c.* from comm_orders c,service_shop shop " +
            "where engineers_eid=?1 and shop.gid=?2 and c.finished=?3 and order_type_order_type=?4"
            ,nativeQuery = true)
    List<CommOrders> findAllByEidAndGidByType(int eid,int gid,int status_id,int order_type);
    @Query(value = "select c.* from comm_orders c,service_shop shop " +
            "where engineers_eid=?1 and c.finished=?2",nativeQuery = true)
    List<CommOrders> findAllByEid(int eid,int status_id);
    @Query(value = "select c.* from comm_order c,order_status s " +
            "where enineers_eid=?1 and s.service_shop_gid=?2 and s.ordertime=?3",nativeQuery = true)
    List<CommOrders> findAllByEidAndGidAndDate(int eid,int gid,Date date);
    @Query(value = "select c.* from comm_orders c,service_shop shop " +
            "where engineers_eid=?1 and shop.gid=?2 and c.finished=?3"
            ,nativeQuery = true)
    List<CommOrders> findAllByEidAndGid(int eid,int gid,int status_id);

    @Transactional
    @Query(value = "update comm_orders set finished=?2 where oid=?1",nativeQuery = true)
    @Modifying
    void changeFinished(int oid,int finished);

    @Transactional
    @Query(value = "update comm_orders set order_step_step_id=?2 where oid=?1",nativeQuery = true)
    @Modifying
    void changeStep(int oid,int step);
}
