package com.weido.work.repository;

import com.weido.work.pojo.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoomRepository extends JpaRepository<UserRoom,Integer> {
    @Transactional
    @Modifying
    @Query(value = "update user_room set enable=?1 where homeid=?2",
            nativeQuery = true)
    void leaveHome(int enable,int homeid);

    @Query(value = "select r.* from user_room r where user_home_homeid=?1",
            nativeQuery = true)
    List<UserRoom> findAllByhoomid(int homeid);

    @Query(value = "select h.* from user_home h" +
            " where user_home_homeid=?1" +
            " and h.users_uid=?2" +
            " and r.user_home_homeid=h.roomid",nativeQuery = true)
    List<UserRoom> findAllByhomeidAndUid(int homeid,int uid);

    @Query(value = "select r.* from user_room r,user_home h,users u" +
            " where h.homeid=r.user_home_homeid and h.users_uid=?1",
            nativeQuery = true)
    List<UserRoom> findAllByUid(int uid);

    @Query(value = "update sensors s set s.status=?1 where user_room_roomid in (?2)",
            nativeQuery = true)
    void leaveHome(int homeid);

    @Query(value = "select r.* from user_room r where roomid=?1",
            nativeQuery = true)
    List<UserRoom> findAllByRoomid(int roomid);

}
