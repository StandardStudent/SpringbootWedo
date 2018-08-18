package com.weido.engineer.pojo;

import javax.persistence.*;

@Entity
@Table(name = "order_status")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int status_id;
    private String status_name;

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public OrderStatus() {
    }

    public OrderStatus(String status_name) {
        this.status_name = status_name;
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "status_id=" + status_id +
                ", status_name='" + status_name + '\'' +
                '}';
    }
}
