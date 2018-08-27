package com.weido.engineer.repository;

import com.weido.engineer.pojo.OrderAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface OrderAlertRepository extends JpaRepository<OrderAlert,Integer> {

    @Transactional
    @Query(value = "update order_alert o set o.finished=1 where alert_id=?1",nativeQuery = true)
    @Modifying
    void changeFinishedByAlertId(int alert_id);
}
