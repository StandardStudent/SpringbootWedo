package com.weido.engineer.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order_step")
public class OrderStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int step_id;
    private Date acttime;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private OrderStatus orderStatus;
    @OneToMany(mappedBy = "orderStep")
    private List<CommOrders> commOrders;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private Engineers engineers;

    public int getStep_id() {
        return step_id;
    }

    public void setStep_id(int step_id) {
        this.step_id = step_id;
    }

    public Date getActtime() {
        return acttime;
    }

    public void setActtime(Date acttime) {
        this.acttime = acttime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<CommOrders> getCommOrders() {
        return commOrders;
    }

    public void setCommOrders(List<CommOrders> commOrders) {
        this.commOrders = commOrders;
    }

    public Engineers getEngineers() {
        return engineers;
    }

    public void setEngineers(Engineers engineers) {
        this.engineers = engineers;
    }

    public OrderStep() {
    }

    public OrderStep(Date acttime, String description, OrderStatus orderStatus, List<CommOrders> commOrders, Engineers engineers) {
        this.acttime = acttime;
        this.description = description;
        this.orderStatus = orderStatus;
        this.commOrders = commOrders;
        this.engineers = engineers;
    }

    @Override
    public String toString() {
        return "OrderStep{" +
                "step_id=" + step_id +
                ", acttime=" + acttime +
                ", description='" + description + '\'' +
                ", orderStatus=" + orderStatus +
                ", commOrders=" + commOrders +
                ", engineers=" + engineers +
                '}';
    }

}
