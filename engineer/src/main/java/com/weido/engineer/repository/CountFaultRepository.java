package com.weido.engineer.repository;

import com.weido.engineer.pojo.CountFault;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CountFaultRepository extends JpaRepository<CountFault,Integer> {

    @Query(value = "select c.fault_faultid as fault_id ,count(c.fault_faultid) as count " +
            "from comm_orders c GROUP BY c.fault_faultid",nativeQuery = true)
    List<CountFault> findCountByFault();
}
