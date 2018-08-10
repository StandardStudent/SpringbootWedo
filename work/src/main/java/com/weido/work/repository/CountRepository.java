package com.weido.work.repository;

import com.weido.work.pojo.Count;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CountRepository extends JpaRepository<Count,Integer> {
    @Query(value = "SELECT s.sid,r.roomname,COUNT(*) as countt FROM user_room r,sensors s WHERE r.roomid=s.roomid GROUP BY r.roomid",nativeQuery = true)
    List<Count> findCount();
}
