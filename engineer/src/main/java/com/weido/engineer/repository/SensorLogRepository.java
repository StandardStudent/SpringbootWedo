package com.weido.engineer.repository;


import com.weido.engineer.pojo.SensorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SensorLogRepository extends JpaRepository<SensorLog,Integer> {
    @Query(value = "select s.order_alert_alert_id from sensors_log s where s.log_id=?1",nativeQuery = true)
    String findAllByLog_id(int logId);
}
