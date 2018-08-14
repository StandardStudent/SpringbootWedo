package com.weido.work.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

@Entity
@Table(name = "scene_default")
public class SceneDefault {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sd_id;
    private int vendorid;
    private String name;
    private String png;
    private String description;
    private String actions;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private UserHome userHome;

    public int getSd_id() {
        return sd_id;
    }

    public void setSd_id(int sd_id) {
        this.sd_id = sd_id;
    }

    public int getVendorid() {
        return vendorid;
    }

    public void setVendorid(int vendorid) {
        this.vendorid = vendorid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPng() {
        return png;
    }

    public void setPng(String png) {
        this.png = png;
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

    public UserHome getUserHome() {
        return userHome;
    }

    public void setUserHome(UserHome userHome) {
        this.userHome = userHome;
    }

    public SceneDefault(int vendorid, String name, String png, String description, String actions) {
        this.vendorid = vendorid;
        this.name = name;
        this.png = png;
        this.description = description;
        this.actions = actions;
    }

    public SceneDefault(int vendorid, String name, String png, String description, String actions, UserHome userHome) {
        this.vendorid = vendorid;
        this.name = name;
        this.png = png;
        this.description = description;
        this.actions = actions;
        this.userHome = userHome;
    }

    public SceneDefault() {
    }

    @Override
    public String toString() {
        return "{" +
                "'sd_id':"+"'" + sd_id +"'"+
                ", 'vendorid':" +"'"+ vendorid+"'" +
                ", 'name':'" + name + '\'' +
                ", 'png':'" + png + '\'' +
                ", 'description':'" + description + '\'' +
                ", 'actions':'" + actions + '\'' +
                '}';
    }
}
