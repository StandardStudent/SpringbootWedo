package com.weido.work.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 *用户表
 * uid  用户的ID
 * pid  身份证
 * mobile 手机号
 * uname 用户名称
 * sex  性别  true：男 false：女
 * email 邮箱
 * password 密码
 * userHomes 家的表
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer uid;
    private String pid;
    private String mobile;
    private String uname;
    private boolean sex;
    private String email;
    private String password;
    @OneToMany(mappedBy = "users")
    private List<UserHome> userHomes;

    public User() {
    }

    public User(String pid, String mobile, String uname, boolean sex, String email, String password) {
        this.pid = pid;
        this.mobile = mobile;
        this.uname = uname;
        this.sex = sex;
        this.email = email;
        this.password = password;
    }

    public User(String pid, String mobile, String uname, boolean sex, String email, String password, List<UserHome> userHomes) {
        this.pid = pid;
        this.mobile = mobile;
        this.uname = uname;
        this.sex = sex;
        this.email = email;
        this.password = password;
        this.userHomes = userHomes;
    }

    public List<UserHome> getUserHomes() {
        return userHomes;
    }

    public void setUserHomes(List<UserHome> userHomes) {
        this.userHomes = userHomes;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
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


    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", pid='" + pid + '\'' +
                ", mobile='" + mobile + '\'' +
                ", uname='" + uname + '\'' +
                ", sex=" + sex +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
