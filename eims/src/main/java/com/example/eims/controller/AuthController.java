/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 18/01/2023   1.0         ChucNV      First Deploy<br>
 * 28/02/2023   2.0         ChucNV      Enable Security<br>
 * 02/03/2023   2.1         ChucNV      Move Signup service to Service file<br>
 */

package com.example.eims.controller;

import com.example.eims.dto.auth.*;
import com.example.eims.service.interfaces.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired IAuthService authService;

    /**
     * Sign in .
     *
     * @param loginDTO contains the login phone and password
     * @return
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginDTO) {
        return authService.authenticateUser(loginDTO);
    }

    /**
     * Sign up.
     *
     * @param signUpDTO contains the User's name, email, phone number, date of birth, address.
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDTO) {
        System.out.println(signUpDTO);
        return authService.registerUser(signUpDTO);
    }

    /**
     * Change password.
     *
     * @param changePasswordDTO contains the login phone, old password and new password
     * @return
     */
    @PostMapping("/changePassword")
    @Secured({"ROLE_OWNER", "ROLE_EMPLOYEE", "ROLE_MODERATOR", "ROLE_ADMIN"})
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        return authService.changePassword(changePasswordDTO);
    }

    /**
     * Send OTP to reset password.
     *
     * @param phone the phone number of the account
     * @return
     */
    @GetMapping("/forgotPassword/sendOTP")
    public ResponseEntity<?> sendOTPForgotPassword(@RequestParam String phone) {
        return authService.sendOTP(phone);
    }

    /**
     * Verify OTP forgot password.
     *
     * @param verifyOtpDTO
     * @return
     */
    @PostMapping("/forgotPassword/verifyOTP")
    public ResponseEntity<?> verifyOTPForgotPassword(@RequestBody VerifyOtpDTO verifyOtpDTO) {
        return authService.verifyOTP(verifyOtpDTO);
    }

    /**
     * Re-send OTP forgot password.
     *
     * @return
     */
    @GetMapping("/forgotPassword/resendOTP")
    public ResponseEntity<?> resendOTPForgotPassword(@RequestParam String phone) {
        return authService.resendOTP(phone);
    }

    /**
     * Send OTP to reset password.
     *
     * @param phone the phone number of the account
     * @return
     */
    @GetMapping("/register/sendOTP")
    public ResponseEntity<?> sendOTPRegisterOwner(@RequestParam String phone) {
        return authService.sendOTPRegister(phone);
    }

    /**
     * Verify OTP forgot password.
     *
     * @param verifyOtpDTO
     * @return
     */
    @PostMapping("/register/verifyOTP")
    public ResponseEntity<?> verifyOTPRegisterOwner(@RequestBody VerifyOtpDTO verifyOtpDTO) {
        return authService.verifyOTPRegister(verifyOtpDTO);
    }

    /**
     * Re-send OTP forgot password.
     *
     * @param phone
     * @return
     */
    @GetMapping("/register/resendOTP")
    public ResponseEntity<?> resendOTPRegisterOwner(@RequestParam String phone) {
        return authService.resendOTPRegister(phone);
    }

    /**
     * Change password after verify OTP.
     *
     * @param forgotPasswordDTO contains the new password, login phone
     * @return
     */
    @PostMapping("/forgotPassword/changePassword")
    public ResponseEntity<?> resetPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        return authService.resetPassword(forgotPasswordDTO);
    }
}
