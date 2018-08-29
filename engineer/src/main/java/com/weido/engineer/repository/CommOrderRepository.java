package com.weido.engineer.repository;

import com.weido.engineer.pojo.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface CommOrderRepository extends JpaRepository<CommOrders,Integer> {

    @Query(value = "select c.* from comm_orders c where c.engineers_eid=?1 " +
            "and to_days(order_time)= to_days(now());",nativeQuery = true)
    List<CommOrders> findAllByEidAndCurrentTime(int eid);

    @Query(value = "select c.* from comm_orders c where c.engineers_eid=?1 " +
            "and order_time between ?2 and DATE_ADD(?3,INTERVAL 1 DAY) and finished>=0",nativeQuery = true)
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
            " where (engineers_eid=?1 or engineers_eid is null) and shop.gid=?2" +
            " and c.finished=?3 and c.order_type_order_type=?4  limit ?4,20",nativeQuery = true)
    List<CommOrders> findAllByEidAndOrdertypeAndNoEid(int eid,int gid,int status_id,int order_type,int page);

    @Query(value = "select c.* from comm_orders c,service_shop shop " +
            " where shop.gid=?2 and c.finished=?3 and c.order_type_order_type=?4" +
            " and engineers_eid is null  limit ?4,20",nativeQuery = true)
    List<CommOrders> findAllByNullEid(int eid,int gid,int status_id,int order_type,int page);

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

    @Query(value = "select count(*) from comm_orders c where c.engineers_eid=?1 " +
            "and c.finished=1",nativeQuery = true)
    int findtodayWorking(int eid);

    @Query(value = "select c.* from comm_orders c,engineers e where e.eid=?4 and c.late=?1 and order_time between ?2 and DATE_ADD(?3,INTERVAL 1 DAY) limit ?5,20",nativeQuery = true)
    List<CommOrders> findAllBylate(int late,Date Monday,Date Friday,int eid,int page);

    @Transactional
    @Query(value = "update comm_orders set finished=?1 where oid=?2",nativeQuery = true)
    @Modifying
    void changefinish(int finished,int oid);

    @Query(value = "select " +
            "engineers.mobile as mobile , " +
            "comm_orders.oid as oid , " +
            "order_step.step_id as step_id, " +
            "comm_orders.order_time as order_time , " +
            "comm_orders.engineers_eid as eid , " +
            "comm_orders.service_shop_gid as gid , " +
            "comm_orders.finished as order_finished , " +
            "comm_orders.order_type_order_type as order_type , " +
            "order_step.order_status_status_id as step_status , " +
            "UNIX_TIMESTAMP( DATE_FORMAT(order_step.acttime,'%Y-%m-%d %H:%i:%s ')) as step_time, " +
            "b.ut_now as ut_now , " +
            "if(comm_orders.finished =-1 ,(b.ut_now-UNIX_TIMESTAMP( DATE_FORMAT(comm_orders.order_time,'%Y-%m-%d %H:%i:%s ')))/60,0) as step1, " +
            "if(comm_orders.order_type_order_type =1,if(if(comm_orders.finished =-1 ,b.ut_now-UNIX_TIMESTAMP( DATE_FORMAT(comm_orders.order_time,'%Y-%m-%d %H:%i:00 ')),0)>3*60,1,0), " +
            "if(if(comm_orders.finished =-1 ,b.ut_now-UNIX_TIMESTAMP( DATE_FORMAT(comm_orders.order_time,'%Y-%m-%d %H:%i:00 ')),0)>5*60,1,0)) as step1_timeout, " +
            "if(comm_orders.finished =0,(b.ut_now-UNIX_TIMESTAMP( DATE_FORMAT(comm_orders.order_time,'%Y-%m-%d %H:%i:%s ')))/(60),0) as step2, " +
            "if(comm_orders.order_type_order_type =1,if(if(comm_orders.finished =0 ,b.ut_now-UNIX_TIMESTAMP( DATE_FORMAT(order_step.acttime,'%Y-%m-%d %H:%i:00 ')),0)>3*60,1,0), " +
            "if(if(comm_orders.finished =0 ,b.ut_now-UNIX_TIMESTAMP( DATE_FORMAT(order_step.acttime,'%Y-%m-%d %H:%i:00 ')),0)>5*60,1,0)) as step2_timeout, " +
            "if(comm_orders.finished =1 and order_step.order_status_status_id<3 ,if(comm_orders.order_type_order_type =1,(b.ut_now-UNIX_TIMESTAMP( DATE_FORMAT(comm_orders.order_time,'%Y-%m-%d %H:%i:%s ')))/(60), " +
            "(b.ut_now-UNIX_TIMESTAMP( DATE_FORMAT(comm_orders.order_time,'%Y-%m-%d %H:%i:%s ')))/(60*60)),0) as step3, " +
            "if(comm_orders.order_type_order_type =1,if(if(comm_orders.finished =1 and order_step.order_status_status_id<3  ,b.ut_now-UNIX_TIMESTAMP( DATE_FORMAT(order_step.acttime,'%Y-%m-%d %H:%i:00 ')),0)>26*60,1,0), " +
            "if(if(comm_orders.finished =1 and order_step.order_status_status_id<3 ,b.ut_now-UNIX_TIMESTAMP( DATE_FORMAT(order_step.acttime,'%Y-%m-%d %H:%i:00 ')),0)>(2*60+10)*60,1,0)) as step3_timeout, " +
            "if(comm_orders.finished =1 and order_step.order_status_status_id=3 ,if(comm_orders.order_type_order_type =1,(b.ut_now-UNIX_TIMESTAMP( DATE_FORMAT(comm_orders.order_time,'%Y-%m-%d %H:%i:%s ')))/(60), " +
            "(b.ut_now-UNIX_TIMESTAMP( DATE_FORMAT(comm_orders.order_time,'%Y-%m-%d %H:%i:%s ')))/(60*60)),0) as step4,\n" +
            "if(comm_orders.order_type_order_type =1,if(if(comm_orders.finished =1 and order_step.order_status_status_id=3  ,b.ut_now-UNIX_TIMESTAMP( DATE_FORMAT(order_step.acttime,'%Y-%m-%d %H:%i:00 ')),0)>46*60,1,0), " +
            "if(if(comm_orders.finished =1 and order_step.order_status_status_id=3 ,b.ut_now-UNIX_TIMESTAMP( DATE_FORMAT(order_step.acttime,'%Y-%m-%d %H:%i:00 ')),0)>(4*60+10)*60,1,0)) as step4_timeout" +
            " from   order_step " +
            " left join  comm_orders on comm_orders.oid = order_step.comm_order_oid " +
            " left join  engineers on engineers.eid =order_step.engineers_eid " +
            " join (select UNIX_TIMESTAMP( DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:00 ')) as ut_now )as b " +
            " where  " +
            " step_id   IN (SELECT max(step_id) FROM order_step GROUP BY comm_order_oid) " +
            " and comm_orders.oid IS NOT NULL " +
            " and ( comm_orders.finished = -1 or comm_orders.finished =0 or comm_orders.finished =1) " +
            "GROUP BY comm_order_oid " +
            "ORDER BY comm_order_oid ",nativeQuery = true)
    List<Delay> findAllByDelay();

    @Transactional
    @Query(value = "update comm_orders set late = 1 where oid=?1",nativeQuery = true)
    @Modifying
    void updateCommLate(int oid);

    @Query(value = "select c.* from comm_orders c where service_shop_gid=1",nativeQuery = true)
    List<CommOrders> findAllCommOderByGid();

    @Query(value = "select c.* from comm_orders c group by fault_faultid",nativeQuery = true)
    List<CommOrders> findAllByFault();

    @Query(value = "select count(*) from comm_orders c group by fault_faultid",nativeQuery = true)
    List findCountByFault();
}
