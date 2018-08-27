package com.weido.engineer.pojo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "fault")
public class Fault {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int faultid;
    private String faultname;
    @OneToMany(mappedBy = "fault")
    private List<CommOrders> commOrders;

    public int getFaultid() {
        return faultid;
    }

    public void setFaultid(int faultid) {
        this.faultid = faultid;
    }

    public String getFaultname() {
        return faultname;
    }

    public void setFaultname(String faultname) {
        this.faultname = faultname;
    }

    public List<CommOrders> getCommOrders() {
        return commOrders;
    }

    public void setCommOrders(List<CommOrders> commOrders) {
        this.commOrders = commOrders;
    }

    public Fault() {
    }

    public Fault(String faultname) {
        this.faultname = faultname;
    }
}
