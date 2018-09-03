package com.weido.engineer.pojo;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class CountFault {
    @Id
    private int fault_id;
    private int count;

    public int getFault_id() {
        return fault_id;
    }

    public void setFault_id(int fault_id) {
        this.fault_id = fault_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public CountFault() {
    }
}
