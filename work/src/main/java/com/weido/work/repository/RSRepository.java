package com.weido.work.repository;

import com.weido.work.pojo.RoomToSensor;

import com.weido.work.pojo.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface RSRepository extends JpaRepository<RoomToSensor,Integer> {
    @Query(value = "select s.sid,r.roomname,s.sname from sensors s LEFT JOIN user_room r on s.roomid=r.roomid"
            ,nativeQuery = true)
    List<RoomToSensor> findSensorByRId();
}
