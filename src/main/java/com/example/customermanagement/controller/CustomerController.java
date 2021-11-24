package com.example.customermanagement.controller;

import com.example.customermanagement.model.Customer;
import com.example.customermanagement.model.CustomerForm;
import com.example.customermanagement.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/customers")
public class CustomerController {
    @Value("${file-upload}")
    private String fileUpload;
    @Autowired
    private ICustomerService customerService;

    @GetMapping
    public ResponseEntity<Page<Customer>> findAll(@RequestParam(name = "q") Optional<String> q, @PageableDefault Pageable pageable) {
        Page<Customer> customerPage;
        if (q.isPresent()) {
            customerPage = customerService.findAllByLastNameContaining(q.get(), pageable);
        } else
            customerPage = customerService.findAll(pageable);
        return new ResponseEntity<>(customerPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findById(@PathVariable Long id) {
        Optional<Customer> customerOptional = customerService.findById(id);
        return customerOptional.map(customer -> new ResponseEntity<>(customer, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Customer> create(CustomerForm customerForm) throws IOException {
        MultipartFile multipartFile = customerForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        FileCopyUtils.copy(customerForm.getAvatar().getBytes(), new File(fileUpload + fileName));
        Customer customer = new Customer(
                customerForm.getFirstName(),
                customerForm.getLastName(),
                customerForm.getAddress(),
                customerForm.getPhone(),
                fileName);
        return new ResponseEntity<>(customerService.save(customer), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id, CustomerForm customerForm) throws IOException {
        MultipartFile multipartFile = customerForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        FileCopyUtils.copy(customerForm.getAvatar().getBytes(), new File(fileUpload + fileName));
        Optional<Customer> customerOptional = customerService.findById(id);
        if (!customerOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Customer customer = new Customer(customerForm.getId(),
                customerForm.getFirstName(),
                customerForm.getLastName(),
                customerForm.getAddress(),
                customerForm.getPhone(),
                fileName);
        customer.setId(id);
        customerService.save(customer);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> delete(@PathVariable Long id) {
        Optional<Customer> customerOptional = customerService.findById(id);
        if (!customerOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        customerService.remove(id);
        return new ResponseEntity<>(customerOptional.get(), HttpStatus.OK);
    }
}