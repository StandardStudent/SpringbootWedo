package com.weido.engineer.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

@Entity
@Table(name = "engineer_token")
public class EngineerToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tokenid;
    private String mobile;
    private String token;

    public int getTokenid() {
        return tokenid;
    }

    public void setTokenid(int tokenid) {
        this.tokenid = tokenid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



    public EngineerToken(String mobile, String token, Engineers engineers) {
        this.mobile = mobile;
        this.token = token;
//        this.engineers = engineers;
    }

    public EngineerToken() {
    }

    @Override
    public String toString() {
        return "EngineerToken{" +
                "tokenid=" + tokenid +
                ", mobile='" + mobile + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
