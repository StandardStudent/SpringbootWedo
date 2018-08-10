package com.weido.work.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue
    private int cityid;
    private String city_name;
    private int parentid;
    private String description;
    private String abb;

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAbb() {
        return abb;
    }

    public void setAbb(String abb) {
        this.abb = abb;
    }

    public City(int cityid, String city_name, int parentid, String description, String abb) {
        this.cityid = cityid;
        this.city_name = city_name;
        this.parentid = parentid;
        this.description = description;
        this.abb = abb;
    }

    public City() {
    }

    @Override
    public String toString() {
        return "City{" +
                "cityid=" + cityid +
                ", city_name='" + city_name + '\'' +
                ", parentid=" + parentid +
                ", description='" + description + '\'' +
                ", abb='" + abb + '\'' +
                '}';
    }
}
