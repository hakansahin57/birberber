package com.birberber.domain.store;

import com.birberber.domain.item.Item;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "workingHour")
public class WorkingHour extends Item {

    private String day;

    private String openingHour;

    private String closingHour;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private com.birberber.domain.store.Store store;


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(String openingHour) {
        this.openingHour = openingHour;
    }

    public String getClosingHour() {
        return closingHour;
    }

    public void setClosingHour(String closingHour) {
        this.closingHour = closingHour;
    }

    public com.birberber.domain.store.Store getStore() {
        return store;
    }

    public void setStore(com.birberber.domain.store.Store store) {
        this.store = store;
    }
}
