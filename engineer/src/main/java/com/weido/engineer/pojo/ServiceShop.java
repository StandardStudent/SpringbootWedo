package com.weido.engineer.pojo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "service_shop")
public class ServiceShop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gid;
    private String gname;
    private int pid;
    private String description;
    private String address;
    private String phone;
    @OneToMany(mappedBy = "serviceShop",fetch = FetchType.EAGER)
    private List<Engineers> engineers;
    @OneToMany(mappedBy = "serviceShop")
    private List<Communities> communities;

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Engineers> getEngineers() {
        return engineers;
    }

    public void setEngineers(List<Engineers> engineers) {
        this.engineers = engineers;
    }

    public List<Communities> getCommunities() {
        return communities;
    }

    public void setCommunities(List<Communities> communities) {
        this.communities = communities;
    }

    public ServiceShop(String gname, int pid, String description, String address, String phone) {
        this.gname = gname;
        this.pid = pid;
        this.description = description;
        this.address = address;
        this.phone = phone;
    }

    public ServiceShop() {
    }

    @Override
    public String toString() {
        return "ServiceShop{" +
                "gid=" + gid +
                ", gname='" + gname + '\'' +
                ", pid=" + pid +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
