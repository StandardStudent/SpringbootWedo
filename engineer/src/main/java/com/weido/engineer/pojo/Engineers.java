package com.weido.engineer.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "engineers")
public class Engineers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eid;
    private String mobile;
    private String password;
    private String name;
    private String description;
    private boolean sex;
    private Date birthday;
    private String title;
    private String photo;
    private String pid;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private ServiceShop serviceShop;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private Roles roles;
    private Double locationLon;//经度
    private Double locationLat;//纬度
    private Date locationTime;
    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public ServiceShop getServiceShop() {
        return serviceShop;
    }

    public void setServiceShop(ServiceShop serviceShop) {
        this.serviceShop = serviceShop;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Double getLocationLon() {
        return locationLon;
    }

    public void setLocationLon(Double locationLon) {
        this.locationLon = locationLon;
    }

    public Double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(Double locationLat) {
        this.locationLat = locationLat;
    }

    public Date getLocationTime() {
        return locationTime;
    }

    public void setLocationTime(Date locationTime) {
        this.locationTime = locationTime;
    }

    public Engineers(String mobile, String password, String name, String description, boolean sex, Date birthday, String title, String photo, ServiceShop serviceShop, Roles roles) {
        this.mobile = mobile;
        this.password = password;
        this.name = name;
        this.description = description;
        this.sex = sex;
        this.birthday = birthday;
        this.title = title;
        this.photo = photo;
//        this.serviceShop = serviceShop;
//        this.roles = roles;
    }

    public Engineers() {
    }


    public Engineers(int eid) {
        this.eid = eid;
    }

    @Override
    public String toString() {
        return "Engineers{" +
                "eid=" + eid +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", title='" + title + '\'' +
                ", photo='" + photo + '\'' +
                ", pid=" + pid +
                ", serviceShop=" + serviceShop +
                ", roles=" + roles +
                '}';
    }
}
