package com.birberber.domain.price;

import com.birberber.domain.item.Item;

import javax.persistence.Entity;
import javax.persistence.Table;

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
