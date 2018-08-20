package com.weido.engineer.repository;

import com.weido.engineer.pojo.Engineers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    @Transactional
    @Query(value = "update engineers set password=?2 where mobile=?1",nativeQuery = true)
    @Modifying
    void updatePassword(String mobile,String password);

    @Query(value = "select e.* from engineers e where mobile=?1",nativeQuery = true)
    List<Engineers> findAllByMobile(String mobile);

    @Query(value = "select e.* from engineers e where eid=?1 and roles_rid=?2",nativeQuery = true)
    List<Engineers> findAllByRidAndGid(int eid,int rid);

    @Transactional
    @Query(value = "update Engineers e set e.locationLon=?1,e.locationLat=?2,e.locationTime=?3 where e.eid=?4")
    @Modifying
    void updateLocation(Double locationLon, Double locationLat, Date locationTime, int eid);
}
