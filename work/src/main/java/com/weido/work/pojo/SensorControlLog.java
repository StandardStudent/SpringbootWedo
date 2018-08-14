package com.weido.work.pojo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sensors_control_log")
/***
 * control_id 控制ID
 * log          历史
 * log_time     时间
 * sensors      传感器表
 */
public class SensorControlLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int  control_id;
    private String log;       //历史
    private Date log_time;    //时间
    @ManyToOne
    private Sensor sensors;

    public SensorControlLog() {
    }

    public int getControl_id() {
        return control_id;
    }

    public void setControl_id(int control_id) {
        this.control_id = control_id;
    }

    public Sensor getSensors() {
        return sensors;
    }

    public void setSensors(Sensor sensors) {
        this.sensors = sensors;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Date getLog_time() {
        return log_time;
    }

    public void setLog_time(Date log_time) {
        this.log_time = log_time;
    }

}
