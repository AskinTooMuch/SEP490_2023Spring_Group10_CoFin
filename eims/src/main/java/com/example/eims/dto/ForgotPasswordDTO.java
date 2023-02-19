package com.example.eims.dto;

import lombok.Data;

@Data
public class ForgotPasswordDTO {
    private String phone;
    private String newPassword;
    private String confirmPassword;
    private String OTP;
}
