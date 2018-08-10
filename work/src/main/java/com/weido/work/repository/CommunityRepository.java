package com.weido.work.repository;

import com.weido.work.pojo.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommunityRepository extends JpaRepository<Community,Integer> {
    @Query(value = "select com from communitys com where master_uid=?1 and cid=?2",nativeQuery = true)
    Community findComByUidAndCid(int uid,int cid);
}
