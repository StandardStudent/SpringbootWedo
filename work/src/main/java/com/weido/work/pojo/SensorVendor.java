package com.weido.work.pojo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sensors_vendor")
/***
 * svid 传感器供应商ID
 * name 传感器供应商名字
 * description 描述
 * sensors 传感器表
 */
public class SensorVendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int svid;
    private String name;
    private String description;
    @OneToMany(mappedBy = "sensorsVendor")
    private List<Sensor> sensors;
    public SensorVendor() {
    }


    public int getSvid() {
        return svid;
    }

    public void setSvid(int svid) {
        this.svid = svid;
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

    public void setDescription(String description) {
        this.description = description;
    }

}
