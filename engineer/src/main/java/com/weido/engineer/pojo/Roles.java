package com.weido.engineer.pojo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rid;
    private int pid;
    private String name;
    private String description;
    @OneToMany(mappedBy = "roles")
    private List<Engineers> engineers;

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
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

    public List<Engineers> getEngineers() {
        return engineers;
    }

    public void setEngineers(List<Engineers> engineers) {
        this.engineers = engineers;
    }

    public Roles(int pid, String name, String description) {
        this.pid = pid;
        this.name = name;
        this.description = description;
    }

    public Roles() {
    }

    @Override
    public String toString() {
        return "Roles{" +
                "rid=" + rid +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
