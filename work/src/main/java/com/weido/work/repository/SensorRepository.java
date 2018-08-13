package com.weido.work.repository;

import com.weido.work.pojo.Count;
import com.weido.work.pojo.RoomToSensor;
import com.weido.work.pojo.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor,Integer> {
    @Query(value = "select s.* from sensors s,user_room r " +
            "where s.user_room_roomid=r.roomid and r.user_home_homeid=?1",
            nativeQuery = true)
    List<Sensor> findAllByHomeId(int homeid);
    @Query(value = "select s.* from sensors s,user_room r" +
            " where s.sid in (?1) AND r.user_home_homeid=?2" +
            " and s.user_room_roomid=r.roomid",
            nativeQuery = true)
    List<Sensor> findAllBySID(String sid,int homeid);

    @Query(value = "select s.* from sensors s where s.user_room_roomid in (?1)",
            nativeQuery = true)
    List<Sensor> findAllByRoomid(String roomid);

    @Query(value = "select s.* from sensors s where s.user_room_roomid=?1",
            nativeQuery = true)
    List<Sensor> findAllByRoomidInt(int roomid);

//    @Query(value = "select s.* from sensors s,user_home h,user_room r" +
//            " where h.users_uid=?1 and h.homeid=?2" +
//            " and h.homeid=r.user_home_homeid and ")
//    List<Sensor> findAllByHomeIdAndUid();
}
