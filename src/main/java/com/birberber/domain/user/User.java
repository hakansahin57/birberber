package com.birberber.domain.user;

import com.birberber.domain.item.Item;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User extends Item {

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(name = "lastName", nullable = false, length = 20)
    private String lastName;

    @Column(name = "role", nullable = false, length = 20)
    private String role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}