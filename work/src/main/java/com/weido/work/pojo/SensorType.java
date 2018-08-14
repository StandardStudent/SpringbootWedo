package com.weido.work.pojo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sensors_type")
/***
 * type_id   传感器id
 *  name    传感器名字
 *  description  描述
 *  sensors  传感器表
 */
public class SensorType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int typeid;
    private String name;
    private String description;
    @OneToMany(mappedBy = "sensorsType")
    private List<Sensor> sensors;

    public SensorType() {
    }

    public SensorType(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
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

    public void setDescription(String desciption) {
        this.description = desciption;
    }


}
