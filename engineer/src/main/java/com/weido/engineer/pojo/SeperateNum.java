package com.weido.engineer.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SeperateNum {
    @Id
    private int number;
    private String name;

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
