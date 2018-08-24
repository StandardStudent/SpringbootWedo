package com.weido.engineer.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class ScenceModel {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @JSONField(ordinal = 1)
    private int smid;
    @JSONField(ordinal = 2)
    private String scenceName;
    @JSONField(ordinal = 3)
    private int msgStatus;
    @JSONField(ordinal = 4)
    private String action;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private UserHome userHome;
    @OneToMany(mappedBy = "scenceModel",cascade = CascadeType.PERSIST)
    @JSONField(ordinal = 5)
    private List<Sensor> sensors;
    public ScenceModel() {
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public UserHome getUserHome() {
        return userHome;
    }

    public void setUserHome(UserHome userHome) {
        this.userHome = userHome;
    }

    public int getSmid() {
        return smid;
    }

    public void setSmid(int smid) {
        this.smid = smid;
    }

    public String getScenceName() {
        return scenceName;
    }

    public void setScenceName(String scenceName) {
        this.scenceName = scenceName;
    }

    public int getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(int msgStatus) {
        this.msgStatus = msgStatus;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
