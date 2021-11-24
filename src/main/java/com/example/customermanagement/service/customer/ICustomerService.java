package com.example.customermanagement.service.customer;

import com.example.customermanagement.model.Customer;
import com.example.customermanagement.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface ICustomerService extends IGeneralService<Customer> , UserDetailsService {
    Page<Customer> findAllByLastNameContaining(String lastname, Pageable pageable);
    Optional<Customer> findByUsername(String username);
}
