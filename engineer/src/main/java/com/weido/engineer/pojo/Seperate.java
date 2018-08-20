package com.weido.engineer.pojo;

public class Seperate {
    private String name;
    private String appraise_score;

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
