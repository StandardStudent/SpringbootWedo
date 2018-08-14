package com.weido.work.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int muid;
    private String mname;
    private String mphone;
    private String role;
    private int status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private UserHome userHome;

    public int getMuid() {
        return muid;
    }

    public void setMuid(int muid) {
        this.muid = muid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMphone() {
        return mphone;
    }

    public void setMphone(String mphone) {
        this.mphone = mphone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UserHome getUserHome() {
        return userHome;
    }

    public void setUserHome(UserHome userHome) {
        this.userHome = userHome;
    }

    public Member(String mname, String mphone, String role, int status, UserHome userHome) {
        this.mname = mname;
        this.mphone = mphone;
        this.role = role;
        this.status = status;
        this.userHome = userHome;
    }

    public Member() {
    }

    @Override
    public String toString() {
        return "Member{" +
                "muid=" + muid +
                ", mname='" + mname + '\'' +
                ", mphone='" + mphone + '\'' +
                ", role='" + role + '\'' +
                ", status=" + status +
                ", userHome=" + userHome +
                '}';
    }
}
