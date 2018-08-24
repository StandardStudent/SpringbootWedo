package com.weido.engineer.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

import javax.persistence.*;

@Entity
@Table(name = "sensors")
/***
 * sid        传感器id
 * sn        设备序列号
 * sname     传感器名称
 * parentid  父节点ID
 * userroom  房间表
 * sensorsType 传感器类型
 * sensorsVendor 传感器供应商表
 */
@JSONType(orders = {"sid","sn","sname","parentid",
        "userRoom","sensorsType","sensorsVendor",
        "sensorsLogs","sensorsControlLogs"})
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sid;
    private String sn;
    private String sname;
    private int parentid;
    private int value;
    private String status;
    private int collectstatus;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private UserRoom userRoom;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private SensorsType sensorsType;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private SensorVendor sensorsVendor;
    @ManyToOne
    @JSONField(serialize = false)
    private ScenceModel scenceModel;
    @ManyToOne
    @JSONField(serialize = false)
    private Devs devs;


//    @OneToMany(mappedBy = "sensors")
//    private List<SensorLog> sensorsLogs;
//    @OneToMany(mappedBy = "sensors")
//    private List<SensorControlLog> sensorsControlLogs;

    public SensorVendor getSensorsVendor() {
        return sensorsVendor;
    }

    public void setSensorsVendor(SensorVendor sensorsVendor) {
        this.sensorsVendor = sensorsVendor;
    }

    public SensorsType getSensorsType() {
        return sensorsType;
    }

    public void setSensorsType(SensorsType sensorsType) {
        this.sensorsType = sensorsType;
    }

    public UserRoom getUserRoom() {
        return userRoom;
    }

    public void setUserRoom(UserRoom userRoom) {
        this.userRoom = userRoom;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }


    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCollectstatus() {
        return collectstatus;
    }

    public void setCollectstatus(int collectstatus) {
        this.collectstatus = collectstatus;
    }

    public ScenceModel getScenceModel() {
        return scenceModel;
    }

    public void setScenceModel(ScenceModel scenceModel) {
        this.scenceModel = scenceModel;
    }

    public Sensor(String sn, String sname, int parentid, int value, String status) {
        this.sn = sn;
        this.sname = sname;
        this.parentid = parentid;
        this.value = value;
        this.status = status;
    }

    public Sensor(String sn, String sname, int parentid) {
        this.sn = sn;
        this.sname = sname;
        this.parentid = parentid;
    }

    public Sensor(String status, UserRoom userRoom) {
        this.status = status;
        this.userRoom = userRoom;
    }

    public Sensor(String sn, String sname, UserRoom userRoom) {
        this.sn = sn;
        this.sname = sname;
        this.userRoom = userRoom;
    }

    public Sensor(String sname) {
        this.sname = sname;
    }

    public Sensor() {
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "sid=" + sid +
                ", sn='" + sn + '\'' +
                ", sname='" + sname + '\'' +
                ", parentid=" + parentid +
                ", value=" + value +
                ", status='" + status + '\'' +
                ", collectstatus=" + collectstatus +
                ", userRoom=" + userRoom +
                ", sensorsType=" + sensorsType +
                ", sensorsVendor=" + sensorsVendor +
                '}';
    }
}
