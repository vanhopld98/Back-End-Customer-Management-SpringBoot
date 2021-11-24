package com.example.customermanagement.repository;

import com.example.customermanagement.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    Page<Customer> findAllByLastNameContaining(String lastname, Pageable pageable);

    Optional<Customer> findByUsername(String username);
}
