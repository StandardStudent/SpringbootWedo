package com.weido.engineer.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SeperateNum {
    @Id
    private int eid;
    private int number;
    private String name;

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SeperateNum() {
    }

    public SeperateNum(int number, String name) {
        this.number = number;
        this.name = name;
    }
}
