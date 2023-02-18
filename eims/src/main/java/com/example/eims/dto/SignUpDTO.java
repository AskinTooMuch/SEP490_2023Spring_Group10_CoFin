package com.example.eims.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SignUpDTO {
    private Long roleId;
    private String username;
    private String dob;
    private String phone;
    private String email;
    private String password;
    private String address;
}
