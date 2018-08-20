package com.weido.engineer.repository;

import com.weido.engineer.pojo.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderTypeRepository extends JpaRepository<OrderType,Integer> {
    @Query(value = "select o.* from order_type o,comm_orders c" +
            " where c.engineers_eid=?1 and c.service_shop_gid=?2 and c.finished=?3",nativeQuery = true)
    List<OrderType> findAllByEGS(int eid,int gid,int finished);
}
