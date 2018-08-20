package com.weido.engineer.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comm_orders")
public class CommOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int oid;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private OrderType orderType;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private UserHome userHome;
    private String appraise_byuser;
    private String appraise_description;
    private int finished;
    private String appraise_score;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private OrderStep orderStep;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private Engineers engineers;
    @ManyToOne(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private ServiceShop serviceShop;
    private Date order_time;
    private Date end_time;
    private String solution;
    private String reason;
    private String matter;

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserHome getUserHome() {
        return userHome;
    }

    public void setUserHome(UserHome userHome) {
        this.userHome = userHome;
    }

    public String getAppraise_byuser() {
        return appraise_byuser;
    }

    public void setAppraise_byuser(String appraise_byuser) {
        this.appraise_byuser = appraise_byuser;
    }

    public String getAppraise_description() {
        return appraise_description;
    }

    public void setAppraise_description(String appraise_description) {
        this.appraise_description = appraise_description;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public String getAppraise_score() {
        return appraise_score;
    }

    public void setAppraise_score(String appraise_score) {
        this.appraise_score = appraise_score;
    }

    public OrderStep getOrderStep() {
        return orderStep;
    }

    public void setOrderStep(OrderStep orderStep) {
        this.orderStep = orderStep;
    }

    public Engineers getEngineers() {
        return engineers;
    }

    public void setEngineers(Engineers engineers) {
        this.engineers = engineers;
    }

    public Date getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Date order_time) {
        this.order_time = order_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMatter() {
        return matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public CommOrders() {
    }

    public ServiceShop getServiceShop() {
        return serviceShop;
    }

    public void setServiceShop(ServiceShop serviceShop) {
        this.serviceShop = serviceShop;
    }

    public CommOrders(OrderType orderType, String description, User user, UserHome userHome, String appraise_byuser, String appraise_description, int finished, String appraise_score, OrderStep orderStep, Engineers engineers) {
        this.orderType = orderType;
        this.description = description;
        this.user = user;
        this.userHome = userHome;
        this.appraise_byuser = appraise_byuser;
        this.appraise_description = appraise_description;
        this.finished = finished;
        this.appraise_score = appraise_score;
        this.orderStep = orderStep;
        this.engineers = engineers;
    }

    @Override
    public String toString() {
        return "CommOrders{" +
                "oid=" + oid +
                ", orderType=" + orderType +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", userHome=" + userHome +
                ", appraise_byuser='" + appraise_byuser + '\'' +
                ", appraise_description='" + appraise_description + '\'' +
                ", finished=" + finished +
                ", appraise_score='" + appraise_score + '\'' +
                '}';
    }
}
