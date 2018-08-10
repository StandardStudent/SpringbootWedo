package com.weido.work.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "communitys")
public class Community {
    @Id
    @GeneratedValue
    private int cid;
    private String location;
    private String description;
    private String name;
    private int property_id;
    private int cityid;
    private String abb;
    private String url;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProperty_id() {
        return property_id;
    }

    public void setProperty_id(int property_id) {
        this.property_id = property_id;
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public String getAbb() {
        return abb;
    }

    public void setAbb(String abb) {
        this.abb = abb;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Community(String location, String description, String name, int property_id, int cityid, String abb, String url) {
        this.location = location;
        this.description = description;
        this.name = name;
        this.property_id = property_id;
        this.cityid = cityid;
        this.abb = abb;
        this.url = url;
    }

    public Community() {
    }

    @Override
    public String toString() {
        return "Community{" +
                "cid=" + cid +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", property_id=" + property_id +
                ", cityid=" + cityid +
                ", abb='" + abb + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
