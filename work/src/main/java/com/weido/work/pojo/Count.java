package com.weido.work.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
@Entity
public class Count implements Serializable {
    @Id
    private int sid;
    private String roomname;
    private int countt;

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

    public int getCountt() {
        return countt;
    }

    public void setCountt(int countt) {
        this.countt = countt;
    }

    @Override
    public String toString() {
        return "Count{" +
                "sid=" + sid +
                ", roomname='" + roomname + '\'' +
                ", countt=" + countt +
                '}';
    }
}
