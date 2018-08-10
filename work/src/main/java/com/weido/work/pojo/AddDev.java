package com.weido.work.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class AddDev implements Serializable {
    @Id
    private int sid;
    private String sname;
    private String typename;
    private String png;
    private int roomid;
    private int uid;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getPng() {
        return png;
    }

    public void setPng(String png) {
        this.png = png;
    }

    public int getRoomid() {
        return roomid;
    }

    public void setRoomid(int roomid) {
        this.roomid = roomid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public AddDev(int sid, String sname, String typename, String png, int roomid, int uid) {
        this.sid = sid;
        this.sname = sname;
        this.typename = typename;
        this.png = png;
        this.roomid = roomid;
        this.uid = uid;
    }

    public AddDev() {
    }

    @Override
    public String toString() {
        return "AddDev{" +
                "sid=" + sid +
                ", sname='" + sname + '\'' +
                ", typename='" + typename + '\'' +
                ", png='" + png + '\'' +
                ", roomid=" + roomid +
                ", uid=" + uid +
                '}';
    }
}
