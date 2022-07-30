package com.birberber.domain.address;

import com.birberber.domain.item.Item;

import javax.persistence.*;
import java.util.List;

@Entity
public class Neighborhood extends Item {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    private District district;

    @OneToMany(mappedBy = "neighborhood")
    private List<Street> streets;
}
