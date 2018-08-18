package com.weido.engineer.repository;

import com.weido.engineer.pojo.Engineers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EngineersRepository extends JpaRepository<Engineers,Integer> {
    @Query(value = "select e.* from engineers e where mobile=?1 and password=?2",nativeQuery = true)
    Engineers findAllByMobileAndPwd(String mobile,String pwd);

    @Transactional
    @Query(value = "update engineers set photo =?1 where eid=?2",nativeQuery = true)
    @Modifying
    void updatePng(String png,int eid);

    @Query(value = "select e.* from engineers e where eid=?1",nativeQuery = true)
    List<Engineers> findAllByEid(int eid);

    @Query(value = "select e.* from engineers e where service_shop_gid=?1",nativeQuery = true)
    List<Engineers> findAllByGid(int gid);
}
