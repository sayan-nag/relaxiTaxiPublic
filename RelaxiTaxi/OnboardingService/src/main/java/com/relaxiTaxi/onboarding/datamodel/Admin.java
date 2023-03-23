package com.relaxiTaxi.onboarding.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin {
    @Id
    private String adminId;
    @Column(nullable = false)
    private String adminUser;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(String adminUser) {
        this.adminUser = adminUser;
    }

    public Admin(String adminId, String adminUser) {
        this.adminId = adminId;
        this.adminUser = adminUser;
    }

    public Admin() {

    }
}

