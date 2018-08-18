package com.weido.engineer.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.util.List;

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
    private String address;
    private int post;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private User users;
    @OneToMany(mappedBy = "userHome",fetch = FetchType.EAGER)
    private List<UserRoom> userRooms;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private Communities communities;
    public UserHome() {
    }

    public UserHome(String homename,  String address, int post, User users) {
        this.homename = homename;
        this.address = address;
        this.post = post;
        this.users = users;
    }

    public UserHome(String homename, String address, Communities communities) {
        this.homename = homename;
        this.address = address;
        this.communities = communities;
    }

    public UserHome(String homename, String address, int post, User users, List<UserRoom> userRooms) {
        this.homename = homename;
        this.address = address;
        this.post = post;
        this.users = users;
        this.userRooms = userRooms;
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

    public Communities getCommunities() {
        return communities;
    }

    public void setCommunities(Communities communities) {
        this.communities = communities;
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



    @Override
    public String toString() {
        return "UserHome{" +
                "homeid=" + homeid +
                ", homename='" + homename + '\'' +
                ", address='" + address + '\'' +
                ", post=" + post +
                ", users=" + users +
                ", communities=" + communities +
                '}';
    }
}
