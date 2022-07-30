package com.birberber.domain.product;

import com.birberber.domain.item.Item;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "media")
public class Media extends Item {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
