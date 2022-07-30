package com.birberber.domain.store;

import com.birberber.domain.item.Item;
import com.birberber.domain.address.Address;
import com.birberber.domain.user.Employee;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "store")
public class Store extends Item {

    @OneToMany(mappedBy = "store")
    public Set<Employee> employees;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;


    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}


