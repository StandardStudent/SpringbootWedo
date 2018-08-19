package com.weido.engineer.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

@Entity
@Table(name = "sensor_vendor")
public class SensorVendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sv_id;
    private String name;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private Sensor sensor;

    public int getSv_id() {
        return sv_id;
    }

    public void setSv_id(int sv_id) {
        this.sv_id = sv_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public SensorVendor() {
    }

    public SensorVendor(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "SensorVendor{" +
                "sv_id=" + sv_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
