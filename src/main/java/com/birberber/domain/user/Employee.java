package com.birberber.domain.user;

import com.birberber.domain.store.Store;

import javax.persistence.*;

@Entity
@Table(name = "employee")
public class Employee extends User {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    Store store;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}