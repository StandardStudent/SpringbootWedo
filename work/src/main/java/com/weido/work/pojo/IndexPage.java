package com.weido.work.pojo;

import java.util.List;

public class IndexPage {
    private int homeid;
    private String homename;
    private int roomid;
    private String roomname;
    private int sid;
    private String sname;
    private int sd_id;
    private String name;

    public int getHomeid() {
        return homeid;
    }

    public void setHomeid(int homeid) {
        this.homeid = homeid;
    }

    public String getHomename() {
        return homename;
    }

    public void setHomename(String homename) {
        this.homename = homename;
    }

    public int getRoomid() {
        return roomid;
    }

    public void setRoomid(int roomid) {
        this.roomid = roomid;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

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

    public int getSd_id() {
        return sd_id;
    }

    public void setSd_id(int sd_id) {
        this.sd_id = sd_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IndexPage(int homeid, String homename, int roomid, String roomname, int sid, String sname, int sd_id, String name) {
        this.homeid = homeid;
        this.homename = homename;
        this.roomid = roomid;
        this.roomname = roomname;
        this.sid = sid;
        this.sname = sname;
        this.sd_id = sd_id;
        this.name = name;
    }

    public IndexPage() {
    }

    @Override
    public String toString() {
        return "IndexPage{" +
                "homeid=" + homeid +
                ", homename='" + homename + '\'' +
                ", roomid=" + roomid +
                ", roomname='" + roomname + '\'' +
                ", sid=" + sid +
                ", sname='" + sname + '\'' +
                ", sd_id=" + sd_id +
                ", name='" + name + '\'' +
                '}';
    }
}
