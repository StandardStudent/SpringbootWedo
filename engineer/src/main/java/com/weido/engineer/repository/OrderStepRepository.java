package com.weido.engineer.repository;

import com.weido.engineer.pojo.OrderStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderStepRepository extends JpaRepository<OrderStep,Integer> {

    @Query(value = "select o.* from order_step o where comm_order_oid=?1 " +
            "and engineers_eid=?2 order by order_status_status_id desc",nativeQuery = true)
    List<OrderStep> findAllByOidAndEid(int oid,int eid);

    @Query(value = "select o.* from order_step o where comm_order_oid=?1 " +
            " order by order_status_status_id desc",nativeQuery = true)
    List<OrderStep> findAllByOid(int oid);

    @Query(value = "select o.* from order_step o where comm_order_oid=?1",nativeQuery = true)
    List<OrderStep> findAllStepByOid(int oid);

    @Transactional
    @Query(value = "update order_step o set o.late=1 where o.step_id=?1 and o.engineers_eid=?2 and order_status_status_id=?3",nativeQuery = true)
    @Modifying
    void updateLate(int stepId,int eid,int status);

    @Query(value = "select o.* from order_step o where comm_order_oid=?1 " +
            "and engineers_eid=?2 and order_status_status_id=?3",nativeQuery = true)
    List<OrderStep> findAllByOidAndEidAndStatusId(int oid,int eid,int status_id);
}
