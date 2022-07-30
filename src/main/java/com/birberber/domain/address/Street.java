package com.birberber.domain.address;

import com.birberber.domain.item.Item;

import javax.persistence.*;

@Entity
public class Street extends Item {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "neighborhood_id", referencedColumnName = "id")
    private Neighborhood neighborhood;
}
