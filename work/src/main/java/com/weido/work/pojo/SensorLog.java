package com.weido.work.pojo;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "sensors_log")
/***
 * int log_id;        //历史id
 *String vendor_log; //传感器原始记录
 * int type_id;       //传感器类型id
 * String wd_value;   //传感器的值
 * Date log_time;     //历史时间
 */
public class SensorLog {
    @Id
    @GeneratedValue
    private int log_id;        //历史id
    private String vendor_log; //传感器原始记录
    private int type_id;       //传感器类型id
    private String wd_value;   //传感器的值
    private Date log_time;     //历史时间
    @ManyToOne
    private Sensor sensors;
    public SensorLog() {
    }

    public SensorLog(String vendor_log, int type_id, String wd_value, Date log_time, Sensor sensors) {
        this.vendor_log = vendor_log;
        this.type_id = type_id;
        this.wd_value = wd_value;
        this.log_time = log_time;
        this.sensors = sensors;
    }

    public Sensor getSensors() {
        return sensors;
    }

    public void setSensors(Sensor sensors) {
        this.sensors = sensors;
    }

    public int getLog_id() {
        return log_id;
    }

    public void setLog_id(int log_id) {
        this.log_id = log_id;
    }

    public String getVendor_log() {
        return vendor_log;
    }

    public void setVendor_log(String vendor_log) {
        this.vendor_log = vendor_log;
    }


    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getWd_value() {
        return wd_value;
    }

    public void setWd_value(String wd_value) {
        this.wd_value = wd_value;
    }

    public Date getLog_time() {
        return log_time;
    }

    public void setLog_time(Date log_time) {
        this.log_time = log_time;
    }

}
