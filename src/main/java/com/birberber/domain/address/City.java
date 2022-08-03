package com.birberber.domain.address;

import com.birberber.domain.item.Item;

import javax.persistence.*;
import java.util.List;

@Entity
public class City extends Item {

    @OneToMany(mappedBy = "city")
    private List<District> districts;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
