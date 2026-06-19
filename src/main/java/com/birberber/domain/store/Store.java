package com.birberber.domain.store;

import com.birberber.domain.item.Item;
import com.birberber.domain.address.Address;
import com.birberber.domain.user.Employee;

import jakarta.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "store")
public class Store extends Item {

    @Enumerated(EnumType.STRING)
    private StoreType storeType;

    private String phoneNumber;

    @OneToMany(mappedBy = "store")
    public Set<Employee> employees;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<WorkingHour> workingHours;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<com.birberber.domain.product.Product> services;


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

    public StoreType getStoreType() {
        return storeType;
    }

    public void setStoreType(StoreType storeType) {
        this.storeType = storeType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<WorkingHour> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(List<WorkingHour> workingHours) {
        this.workingHours = workingHours;
    }

    public List<com.birberber.domain.product.Product> getServices() {
        return services;
    }

    public void setServices(List<com.birberber.domain.product.Product> services) {
        this.services = services;
    }
}


