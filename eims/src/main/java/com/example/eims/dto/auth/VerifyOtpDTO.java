package com.example.eims.dto.auth;

import lombok.Data;

@Data
public class VerifyOtpDTO {
    private String phone;
    private String OTP;
}
