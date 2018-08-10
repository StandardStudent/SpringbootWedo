package com.weido.work.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

@Entity
@Table(name = "scene_mine")
public class SceneMine {
    @Id
    @GeneratedValue
    private int sm_id;
    private String scene_name;
    private String description;
    private String actions;
    private int devid;
    private int vendorid;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private UserHome userHome;

    public int getSm_id() {
        return sm_id;
    }

    public void setSm_id(int sm_id) {
        this.sm_id = sm_id;
    }

    public String getScene_name() {
        return scene_name;
    }

    public void setScene_name(String scene_name) {
        this.scene_name = scene_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    public int getDevid() {
        return devid;
    }

    public void setDevid(int devid) {
        this.devid = devid;
    }

    public int getVendorid() {
        return vendorid;
    }

    public void setVendorid(int vendorid) {
        this.vendorid = vendorid;
    }

    public UserHome getUserHome() {
        return userHome;
    }

    public void setUserHome(UserHome userHome) {
        this.userHome = userHome;
    }

    public SceneMine(String scene_name, String description, String actions, int devid, int vendorid) {
        this.scene_name = scene_name;
        this.description = description;
        this.actions = actions;
        this.devid = devid;
        this.vendorid = vendorid;
    }

    public SceneMine(String scene_name, String description, String actions, int devid, int vendorid, UserHome userHome) {
        this.scene_name = scene_name;
        this.description = description;
        this.actions = actions;
        this.devid = devid;
        this.vendorid = vendorid;
        this.userHome = userHome;
    }

    public SceneMine() {
    }

    @Override
    public String toString() {
        return "{" +
                "'sm_id':"+"'" + sm_id+"'" +
                ", 'scene_name':'" + scene_name + '\'' +
                ", 'description':'" + description + '\'' +
                ", 'actions':'" + actions + '\'' +
                ", 'devid':" + devid +
                ", 'vendorid':" + vendorid +
                '}';
    }
}
