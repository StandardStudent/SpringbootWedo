package com.weido.engineer.repository;

import com.weido.engineer.pojo.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface CommOrderRepository extends JpaRepository<CommOrders,Integer> {

    @Query(value = "select c.* from comm_orders c where engineers_eid=?1 " +
            "and to_days(order_time)= to_days(now());",nativeQuery = true)
    List<CommOrders> findAllByEidAndCurrentTime(int eid);

    @Query(value = "select c.* from comm_orders c where engineers_eid=?1 " +
            "and order_time between ?2 and ?3",nativeQuery = true)
    List<CommOrders> findWMByEidAndCurrent(int eid,Date Monday,Date Friday);
    @Query(value = "select c.* from comm_orders c,service_shop shop " +
            "where engineers_eid=?1 and shop.gid=?2 and c.finished=?3 and order_type_order_type=?4 limit ?5,20"
            ,nativeQuery = true)
    List<CommOrders> findAllByEidAndGidByType(int eid,int gid,int status_id,int order_type,int page_no);
    @Query(value = "select c.* from comm_orders c,service_shop shop " +
            "where engineers_eid=?1 and c.finished=?2",nativeQuery = true)
    List<CommOrders> findAllByEid(int eid,int status_id);
    @Query(value = "select c.* from comm_order c,order_status s " +
            "where enineers_eid=?1 and s.service_shop_gid=?2 and s.ordertime=?3",nativeQuery = true)
    List<CommOrders> findAllByEidAndGidAndDate(int eid,int gid,Date date);
    @Query(value = "select c.* from comm_orders c,service_shop shop " +
            "where engineers_eid=?1 and shop.gid=?2 and c.finished=?3 limit ?4,20"
            ,nativeQuery = true)
    List<CommOrders> findAllByEidAndGid(int eid,int gid,int status_id,int page);

    @Query(value = "select c.* from comm_orders c,service_shop shop " +
            " where engineers_eid=?1 or engineers_eid is null and shop.gid=?2 and c.finished=?3 limit ?4,20",nativeQuery = true)
    List<CommOrders> findAllByEidAndEidAndNoEid(int eid,int gid,int status_id,int page);

    @Query(value = "select c.* from comm_orders c,service_shop shop " +
            "where engineers_eid=?1 and shop.gid=?2 and ?3<=c.finished<=3 limit ?4,20"
            ,nativeQuery = true)
    List<CommOrders> findRangByEidAndGid(int eid,int gid,int status_id,int page);

    @Transactional
    @Query(value = "update comm_orders set finished=?2 where oid=?1",nativeQuery = true)
    @Modifying
    void changeFinished(int oid,int finished);

    @Transactional
    @Query(value = "update comm_orders c,order_step o " +
            "set c.finished=?2 and o.engineers_eid=?3 where oid=?1",nativeQuery = true)
    @Modifying
    void changeStep(int oid,int finished,int eid);


    @Transactional
    @Query(value = "update comm_orders set engineers_eid = ?1 where oid=?2",nativeQuery = true)
    @Modifying
    void transfer(int eid,int oid);

    @Query(value = "select count(*) from comm_orders c where " +
            "c.engineers_eid=?1 and c.service_shop_gid=?2 " +
            "and c.finished=?3 and c.order_type_order_type=?4",nativeQuery = true)
    int count (int eid,int gid,int finished,int order_type);

    @Query(value = "select c.* from comm_orders c where c.engineers_eid=?1 " +
            "and to_days(order_time)= to_days(now()) and ?2<=c.finished<3 limit ?3,20",nativeQuery = true)
    List<CommOrders> findAllByEidAndFinished(int eid,int finished,int page);

    @Query(value = "select count(*) from comm_orders c where c.engineers_eid=?1 " +
            "and ?2<=c.finished<3 and to_days(order_time)= to_days(now());",nativeQuery = true)
    int findCountByEidAndFinished(int eid,int finished);

    @Query(value = "select c.* from comm_orders c where c.late=?1 and order_time between ?2 and ?3",nativeQuery = true)
    List<CommOrders> findAllBylate(int late,Date Monday,Date Friday);

    @Transactional
    @Query(value = "update comm_orders set finished=?1 where oid=?2",nativeQuery = true)
    @Modifying
    void changefinish(int finished,int oid);
}
