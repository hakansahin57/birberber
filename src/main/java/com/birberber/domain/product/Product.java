package com.birberber.domain.product;

import com.birberber.domain.item.Item;
import com.birberber.domain.price.Price;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product extends Item {

    @OneToOne
    @JoinColumn(name = "id")
    private Price price;

    @OneToOne
    @JoinColumn(name = "id")
    private Media media;

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
}
