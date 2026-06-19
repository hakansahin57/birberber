package com.birberber.domain.price;

import com.birberber.domain.item.Item;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "currency")
public class Currency extends Item {

    private String isocode;

    public String getIsocode() {
        return isocode;
    }

    public void setIsocode(String isocode) {
        this.isocode = isocode;
    }
}
