package com.weido.work.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "family_users")
public class FamilyUser {
    @Id
    private int family_id;
    private int uid;
    private String role;

    public int getFamily_id() {
        return family_id;
    }

    public void setFamily_id(int family_id) {
        this.family_id = family_id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public FamilyUser(int family_id, int uid, String role) {
        this.family_id = family_id;
        this.uid = uid;
        this.role = role;
    }

    public FamilyUser() {
    }

    @Override
    public String toString() {
        return "FamilyUser{" +
                "family_id=" + family_id +
                ", uid=" + uid +
                ", role='" + role + '\'' +
                '}';
    }
}
