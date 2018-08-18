package com.weido.engineer.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

@Entity
@Table(name = "communities")
public class Communities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cid;
    private String location;
    private String description;
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private Cities cities;
    private String abb;
    private String url;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private ServiceShop serviceShop;

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

    public Cities getCities() {
        return cities;
    }

    public void setCities(Cities cities) {
        this.cities = cities;
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

    public ServiceShop getServiceShop() {
        return serviceShop;
    }

    public void setServiceShop(ServiceShop serviceShop) {
        this.serviceShop = serviceShop;
    }

    @Override
    public String toString() {
        return "Communities{" +
                "cid=" + cid +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", cities=" + cities +
                ", abb='" + abb + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
