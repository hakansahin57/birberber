package com.birberber.domain.store;

import com.birberber.domain.item.Item;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "workingHour")
public class WorkingHour extends Item {

    private String day;

    private String openingHour;

    private String closingHour;


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
}
