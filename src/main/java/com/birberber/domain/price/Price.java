package com.birberber.domain.price;

import com.birberber.domain.item.Item;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "price")
public class Price extends Item {

    private double value;

    @OneToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
