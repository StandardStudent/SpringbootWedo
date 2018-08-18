package com.weido.engineer.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sensors_alert")
public class OrderAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int alert_id;
    @OneToOne(mappedBy = "orderAlert",fetch = FetchType.EAGER)
    private SensorLog sensorLogs;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private User user;
    private boolean finished;

    public int getAlert_id() {
        return alert_id;
    }

    public void setAlert_id(int alert_id) {
        this.alert_id = alert_id;
    }

    public SensorLog getSensorLogs() {
        return sensorLogs;
    }

    public void setSensorLogs(SensorLog sensorLogs) {
        this.sensorLogs = sensorLogs;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public OrderAlert() {
    }

    public OrderAlert(SensorLog sensorLogs, User user, boolean finished) {
        this.sensorLogs = sensorLogs;
        this.user = user;
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "OrderAlert{" +
                "alert_id=" + alert_id +
                ", sensorLogs=" + sensorLogs +
                ", user=" + user +
                ", finished=" + finished +
                '}';
    }
}
