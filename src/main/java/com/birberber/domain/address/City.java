package com.birberber.domain.address;

import com.birberber.domain.item.Item;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
public class City extends Item {

    @OneToMany(mappedBy = "city")
    private List<District> districts;

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }
}
