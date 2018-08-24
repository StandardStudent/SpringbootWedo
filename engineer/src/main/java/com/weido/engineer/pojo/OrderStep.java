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
    @OneToOne
    private OrderStatus orderStatus;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private CommOrders commOrder;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private Engineers engineers;
    private int late;


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

    public CommOrders getCommOrder() {
        return commOrder;
    }

    public void setCommOrder(CommOrders commOrder) {
        this.commOrder = commOrder;
    }

    public Engineers getEngineers() {
        return engineers;
    }

    public void setEngineers(Engineers engineers) {
        this.engineers = engineers;
    }

    public int getLate() {
        return late;
    }

    public void setLate(int late) {
        this.late = late;
    }


    public OrderStep() {
    }

    public OrderStep(Date acttime, String description, Engineers engineers) {
        this.acttime = acttime;
        this.description = description;
        this.engineers = engineers;
    }

    public OrderStep(Date acttime, String description, OrderStatus orderStatus, CommOrders commOrder, Engineers engineers) {
        this.acttime = acttime;
        this.description = description;
        this.orderStatus = orderStatus;
        this.commOrder = commOrder;
        this.engineers = engineers;
    }

    @Override
    public String toString() {
        return "OrderStep{" +
                "step_id=" + step_id +
                ", acttime=" + acttime +
                ", description='" + description + '\'' +
                ", orderStatus=" + orderStatus +
                ", engineers=" + engineers +
                '}';
    }

}
