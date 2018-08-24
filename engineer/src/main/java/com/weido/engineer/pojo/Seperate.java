package com.weido.engineer.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Seperate {
    @Id
    private int eid;
    private String name;
    private String appraise_score;

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppraise_score() {
        return appraise_score;
    }

    public void setAppraise_score(String appraise_score) {
        this.appraise_score = appraise_score;
    }

    public Seperate() {
    }

    public Seperate(String name, String appraise_score) {
        this.name = name;
        this.appraise_score = appraise_score;
    }
}
