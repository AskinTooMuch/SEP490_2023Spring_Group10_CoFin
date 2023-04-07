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
 * 24/03/2023   2.2         ChucNV      Update security for login<br>
 */

package com.example.eims.controller;

import com.example.eims.dto.auth.*;
import com.example.eims.entity.User;
import com.example.eims.service.interfaces.IAuthService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

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
    public ResponseEntity<?> authenticateUser(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginDTO loginDTO) {
        System.out.println("authenticate user" + request.getContextPath());
        return authService.authenticateUser(request, response, loginDTO);
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
     * Change password after verify OTP.
     *
     * @param forgotPasswordDTO contains the new password, login phone
     * @return
     */
    @PostMapping("/forgotPassword/changePassword")
    public ResponseEntity<?> resetPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        return authService.resetPassword(forgotPasswordDTO);
    }

    /**
     * Check if phone number is used to create account or not.
     *
     * @param phone the phone number
     * @return
     */
    @GetMapping("/register/check")
    public ResponseEntity<?> checkPhoneRegister(@RequestParam String phone) {
        return authService.checkPhoneRegister(phone);
    }
    /**
     * Check phone number when reset password
     *
     * @param phone the phone number
     * @return
     */
    @GetMapping("/forgotPassword/check")
    public ResponseEntity<?> checkPhoneForgotPassword(String phone) {
        return authService.checkPhoneForgotPassword(phone);
    }
}
