package com.example.customermanagement.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "customers")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String avatar;

    public Customer() {
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
