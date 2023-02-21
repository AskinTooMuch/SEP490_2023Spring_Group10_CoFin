package com.example.eims.dto;
import lombok.Data;

@Data
public class NewCustomerDTO {
    private Long userId;
    private String name;
    private String phone;
    private String address;
}
