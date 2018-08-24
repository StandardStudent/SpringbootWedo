package com.weido.engineer.repository;

import com.weido.engineer.pojo.OrderStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderStepRepository extends JpaRepository<OrderStep,Integer> {

    @Query(value = "select o.* from order_step o where comm_order_oid=?1 " +
            "and engineers_eid=?2 order by order_status_status_id desc",nativeQuery = true)
    List<OrderStep> findAllByOidAndEid(int oid,int eid);
}
