package com.weido.work.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户家表
 * homeid  家的ID
 * cid     社区ID
 * address  地址
 * post     邮编
 * users   用户表
 * userRooms 房间表
 */
@Entity
@Table(name = "user_home")
public class UserHome{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer homeid;
    private String homename;
    private int cid;
    private String address;
    private int post;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private User users;
    @OneToMany(mappedBy = "userHome")
    private List<UserRoom> userRooms;
    @OneToMany(mappedBy = "userHome")
    private List<SceneMine> sceneMines;

    public UserHome() {
    }

    public UserHome(String homename, int cid, String address, int post, User users) {
        this.homename = homename;
        this.cid = cid;
        this.address = address;
        this.post = post;
        this.users = users;
    }

    public UserHome(String homename, int cid, String address, int post, User users, List<UserRoom> userRooms, List<SceneMine> sceneMines) {
        this.homename = homename;
        this.cid = cid;
        this.address = address;
        this.post = post;
        this.users = users;
        this.userRooms = userRooms;
        this.sceneMines = sceneMines;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public Integer getHomeid() {
        return homeid;
    }

    public void setHomeid(Integer homeid) {
        this.homeid = homeid;
    }

    public List<UserRoom> getUserRooms() {
        return userRooms;
    }

    public void setUserRooms(List<UserRoom> userRooms) {
        this.userRooms = userRooms;
    }

    public String getHomename() {
        return homename;
    }

    public void setHomename(String homename) {
        this.homename = homename;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    public List<SceneMine> getSceneMines() {
        return sceneMines;
    }

    public void setSceneMines(List<SceneMine> sceneMines) {
        this.sceneMines = sceneMines;
    }

    @Override
    public String toString() {
        return "UserHome{" +
                "homeid=" + homeid +
                ", homename='" + homename + '\'' +
                ", cid=" + cid +
                ", address='" + address + '\'' +
                ", post=" + post +
                ", users=" + users +
                '}';
    }
}
