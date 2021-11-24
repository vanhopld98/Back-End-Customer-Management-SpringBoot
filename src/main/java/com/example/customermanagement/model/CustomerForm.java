package com.example.customermanagement.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CustomerForm {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private MultipartFile avatar;
}
