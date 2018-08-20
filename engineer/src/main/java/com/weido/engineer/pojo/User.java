package com.weido.engineer.pojo;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;
    private String pid;
    private String mobile;
    private String uname;
    private boolean sex;
    private String email;
    private String password;
    private Date registTime;
    private int vipStatus;
    private Date vipExpiration;
    private int vipLevel;
    @OneToMany(mappedBy = "users")
    private List<UserHome> userHome;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    public int getVipStatus() {
        return vipStatus;
    }

    public void setVipStatus(int vipStatus) {
        this.vipStatus = vipStatus;
    }

    public Date getVipExpiration() {
        return vipExpiration;
    }

    public void setVipExpiration(Date vipExpiration) {
        this.vipExpiration = vipExpiration;
    }

    public List<UserHome> getUserHome() {
        return userHome;
    }

    public void setUserHome(List<UserHome> userHome) {
        this.userHome = userHome;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public User() {
    }

    public User(String pid, String mobile, String uname, boolean sex, String password, Date registTime, int vipStatus, Date vipExpiration) {
        this.pid = pid;
        this.mobile = mobile;
        this.uname = uname;
        this.sex = sex;
        this.password = password;
        this.registTime = registTime;
        this.vipStatus = vipStatus;
        this.vipExpiration = vipExpiration;
        this.userHome = userHome;
    }
}
