package com.weido.engineer.pojo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "order_type")
public class OrderType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int order_type;
    private String ot_name;
    @OneToMany(mappedBy = "orderType",fetch = FetchType.EAGER)
    private List<CommOrders> commOrders;

    public int getOrder_type() {
        return order_type;
    }

    public void setOrder_type(int order_type) {
        this.order_type = order_type;
    }

    public String getOt_name() {
        return ot_name;
    }

    public void setOt_name(String ot_name) {
        this.ot_name = ot_name;
    }

    public List<CommOrders> getCommOrders() {
        return commOrders;
    }

    public void setCommOrders(List<CommOrders> commOrders) {
        this.commOrders = commOrders;
    }

    public OrderType() {
    }



    @Override
    public String toString() {
        return "OrderType{" +
                "order_type=" + order_type +
                ", ot_name='" + ot_name + '\'' +
                ", commOrders=" + commOrders +
                '}';
    }
}
