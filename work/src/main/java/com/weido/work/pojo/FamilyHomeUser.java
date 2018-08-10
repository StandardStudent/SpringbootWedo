package com.weido.work.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "family_home_user")
public class FamilyHomeUser {
    @Id
    private int uid;
    private int homeid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getHomeid() {
        return homeid;
    }

    public void setHomeid(int homeid) {
        this.homeid = homeid;
    }

    public FamilyHomeUser(int uid, int homeid) {
        this.uid = uid;
        this.homeid = homeid;
    }

    public FamilyHomeUser() {
    }

    @Override
    public String toString() {
        return "FamilyHomeUser{" +
                "uid=" + uid +
                ", homeid=" + homeid +
                '}';
    }
}
