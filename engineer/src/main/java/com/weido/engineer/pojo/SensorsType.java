package com.weido.engineer.pojo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sensors_type")
public class SensorsType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int type_id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "sensorsType",fetch = FetchType.EAGER)
    private List<Sensor> sensors;

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
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

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public SensorsType() {
    }

    @Override
    public String toString() {
        return "SensorsType{" +
                "type_id=" + type_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
