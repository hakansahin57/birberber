package com.birberber.domain.product;

import com.birberber.domain.item.Item;
import com.birberber.domain.price.Price;
import com.birberber.domain.store.Store;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product extends Item {

    private int durationMinutes;

    @OneToOne(cascade = jakarta.persistence.CascadeType.ALL)
    @JoinColumn(name = "price_id")
    private Price price;

    @OneToOne(cascade = jakarta.persistence.CascadeType.ALL)
    @JoinColumn(name = "media_id")
    private Media media;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
