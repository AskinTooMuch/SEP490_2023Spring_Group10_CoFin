package com.example.eims.dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {
    private String phone;
    private String password;
    private String newPassword;
}
