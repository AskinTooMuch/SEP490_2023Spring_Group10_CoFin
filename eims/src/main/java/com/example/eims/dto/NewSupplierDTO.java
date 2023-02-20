package com.example.eims.dto;

import lombok.Data;

@Data
public class NewSupplierDTO {
    private Long userId;
    private String name;
    private String phone;
    private String address;
}
