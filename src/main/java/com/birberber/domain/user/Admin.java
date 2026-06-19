package com.birberber.domain.user;

import com.birberber.domain.item.Item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin extends User {

    @Column(name = "asd", length = 20)
    private String asd;

    public String getAsd() {
        return asd;
    }

    public void setAsd(String asd) {
        this.asd = asd;
    }
}