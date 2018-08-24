package com.weido.engineer.repository;

import com.weido.engineer.pojo.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRoomRepository extends JpaRepository<UserRoom,Integer> {
    @Transactional
    @Modifying
    @Query(value = "insert into user_room(roomname,enable,png,user_home_homeid) values (?1,?2,?3,?4)",nativeQuery = true)
    void addRoom(String name,int enable,String png,int homeid);
}
