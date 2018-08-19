package com.weido.engineer.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.List;

@Entity
public class Devs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int devid;
    private String bindid; //网关id
    private String pwd;  //网关密码
    @OneToMany(mappedBy = "devs",cascade = CascadeType.PERSIST)
    @JSONField(ordinal = 5)
    private List<Sensor> sensors;

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public Devs() {
    }

    public Devs(String bindid, String pwd) {
        this.bindid = bindid;
        this.pwd = pwd;
    }

    public int getDevid() {
        return devid;
    }

    public void setDevid(int devid) {
        this.devid = devid;
    }

    public String getBindid() {
        return bindid;
    }

    public void setBindid(String bindid) {
        this.bindid = bindid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
