package com.weido.work.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "familys")
public class Family {
    @Id
    @GeneratedValue
    private int family_id;
    private int master_uid;

    public int getFamily_id() {
        return family_id;
    }

    public void setFamily_id(int family_id) {
        this.family_id = family_id;
    }

    public int getMaster_uid() {
        return master_uid;
    }

    public void setMaster_uid(int master_uid) {
        this.master_uid = master_uid;
    }

    public Family(int master_uid) {
        this.master_uid = master_uid;
    }

    public Family() {
    }

    @Override
    public String toString() {
        return "Family{" +
                "family_id=" + family_id +
                ", master_uid=" + master_uid +
                '}';
    }
}
