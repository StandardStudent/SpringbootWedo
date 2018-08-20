package com.weido.engineer.repository;

import com.weido.engineer.pojo.Communities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Communities,Integer> {

    @Query(value = "select c.* from communities c where service_shop_gid=?1",nativeQuery = true)
    List<Communities> findAllByGid(int gid);
}
