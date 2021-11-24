package com.example.customermanagement.controller;

import com.example.customermanagement.model.Customer;
import com.example.customermanagement.model.Role;
import com.example.customermanagement.secutiry.JwtService;
import com.example.customermanagement.secutiry.model.JwtResponse;
import com.example.customermanagement.secutiry.model.UserPrinciple;
import com.example.customermanagement.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private ICustomerService customerService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Customer customer) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(customer.getUsername(), customer.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = JwtService.generateTokenLogin(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Customer currentCustomer = customerService.findByUsername(customer.getUsername()).get();
        JwtResponse jwtResponse = new JwtResponse(
                jwt,
                currentCustomer.getId(),
                currentCustomer.getUsername(),
                userPrinciple.getAuthorities()
        );
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<Customer> register(@RequestBody Customer customer) {
        if (customer.getRoles() == null) {
            List<Role> roles = new ArrayList<>();
            roles.add(new Role(2L, null));
            roles.add(new Role(1L, null));
            customer.setRoles(roles);
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        Customer customerRegister = customerService.save(customer);
        if (customerRegister == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(customerRegister, HttpStatus.CREATED);
    }
}
