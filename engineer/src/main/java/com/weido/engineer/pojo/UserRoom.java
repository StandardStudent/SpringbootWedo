package com.weido.engineer.pojo;


import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

import javax.persistence.*;
import java.util.List;

/**
 * 房间表
 * roomid 房间号
 * roomname 房间名称
 * enable 开关状态
 * png    图标
 * userhome 家庭表
 * sensors  传感器表
 */
@Entity
@Table(name = "user_room")
@JSONType(orders = {"roomid","roomname","enable","png","sensors","userHome"})
public class UserRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer roomid;        //房间号
    private  String roomname;       //房间名称
    private  int enable;        //开关状态
    private  String png;            //图片
    @OneToMany(mappedBy = "userRoom",cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    private List<Sensor> sensors;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private UserHome userHome;
    public List<Sensor> getSensors() {
        return sensors;
    }

    public UserHome getUserHome() {
        return userHome;
    }

    public void setUserHome(UserHome userHome) {
        this.userHome = userHome;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public Integer getRoomid() {
        return roomid;
    }

    public void setRoomid(Integer roomid) {
        this.roomid = roomid;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }


    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public String getPng() {
        return png;
    }

    public void setPng(String png) {
        this.png = png;
    }

    public UserRoom() {
    }

    public UserRoom(String roomname, int enable,UserHome userHome) {
        this.roomname = roomname;
        this.enable = enable;
        this.userHome = userHome;
    }

    public UserRoom(UserHome userHome) {
        this.userHome = userHome;
    }

    @Override
    public String toString() {
        return "{" +
                "'roomid':" +"'"+ roomid+"'" +
                ", 'roomname':" +"'"+ roomname + '\'' +
                ", 'enable':" +"'"+ enable +"'"+
                ", 'png':" +"'"+ png + '\'' +
                ", 'userHome':" +"'"+ userHome+
                '}';
    }
}
