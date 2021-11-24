package com.example.customermanagement.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String avatar;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    public Customer() {
    }

    public Customer(Long id, String username, String password, String firstName, String lastName, String address, String phone, String avatar, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.avatar = avatar;
        this.roles = roles;
    }

    public Customer(Long id, String firstName, String lastName, String address, String phone, String avatar) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.avatar = avatar;
    }

    public Customer(String firstName, String lastName, String address, String phone, String avatar) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.avatar = avatar;
    }
}
