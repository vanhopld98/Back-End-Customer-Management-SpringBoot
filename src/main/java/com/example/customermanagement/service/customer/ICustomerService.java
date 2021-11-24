package com.example.customermanagement.service.customer;

import com.example.customermanagement.model.Customer;
import com.example.customermanagement.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICustomerService extends IGeneralService<Customer> {
    Page<Customer> findAllByLastNameContaining(String lastname, Pageable pageable);
}
