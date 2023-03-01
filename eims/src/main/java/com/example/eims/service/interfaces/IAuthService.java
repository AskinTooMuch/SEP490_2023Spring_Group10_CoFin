package com.example.eims.service.interfaces;

import com.example.eims.dto.auth.ChangePasswordDTO;
import com.example.eims.dto.auth.ForgotPasswordDTO;
import com.example.eims.dto.auth.LoginDTO;
import com.example.eims.dto.auth.SignUpDTO;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    public ResponseEntity<?> authenticateUser(LoginDTO loginDTO);
    public ResponseEntity<?> registerUser(SignUpDTO signUpDTO);
    public ResponseEntity<?> changePassword(ChangePasswordDTO changePasswordDTO);
    public ResponseEntity<?> sendOTP(String phone);
    public ResponseEntity<?> verifyOTP(String OTP);
    public ResponseEntity<?> resendOTP();
    public ResponseEntity<?> resetPassword(ForgotPasswordDTO forgotPasswordDTO);
}
