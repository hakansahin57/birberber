package com.birberber.domain.price;

import com.birberber.domain.item.Item;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "price")
public class Price extends Item {

    private double value;

    @OneToOne(cascade = jakarta.persistence.CascadeType.ALL)
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
