package com.weido.work.pojo;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class RoomToSensor implements Serializable {
    @Id
    private int sid;
    private String roomname;
    private String sname;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public RoomToSensor(int sid, String roomname, String sname) {
        this.sid = sid;
        this.roomname = roomname;
        this.sname = sname;
    }

    public RoomToSensor() {
    }

    @Override
    public String toString() {
        return "RoomToSensor{" +
                "sid=" + sid +
                ", roomname='" + roomname + '\'' +
                ", sname='" + sname + '\'' +
                '}';
    }
}
