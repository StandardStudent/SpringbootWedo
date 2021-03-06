package com.weido.work.repository;

import com.weido.work.pojo.UserHome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HomeRepository extends JpaRepository<UserHome,Integer> {
    @Query(value = "select uh.* from user_home uh where master_uid=?1 and cid=?2",nativeQuery = true)
    List<UserHome> findHomeByUidAndCid(int uid,int cid);
    @Query(value = "select h.* from user_home h where users_uid=?1",nativeQuery = true)
    List<UserHome> findAllByUid(int uid);
    @Query(value = "select h.* from user_home h where homeid=?1",nativeQuery = true)
    List<UserHome> findAllByhomeId(int homeid);
    @Query(value = "update user_home set gatewaynumber=?2," +
            "gatewaypwd=?3 where homeid=?1",nativeQuery = true)
    void updateGateway(int homeid,String gatewaynumber,String getewaypwd);
}
