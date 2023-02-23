/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 18/01/2023    1.0        ChucNV           First Deploy<br>
 */

package com.example.eims.controller;

import com.example.eims.dto.auth.ChangePasswordDTO;
import com.example.eims.dto.auth.ForgotPasswordDTO;
import com.example.eims.dto.auth.LoginDTO;
import com.example.eims.dto.auth.SignUpDTO;
import com.example.eims.entity.User;
import com.example.eims.repository.UserRoleRepository;
import com.example.eims.repository.UserRepository;
import com.example.eims.utils.StringDealer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * API for the user to sign in.
     * The DTO contains the login phone and password
     *
     * @param loginDTO
     * @return
     */
    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getPhone(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return new ResponseEntity<>("User signed-in successfully.", HttpStatus.OK);
    }

    /**
     * API for the user to sign up.
     * The DTO contains the User's name, email, phone number, date of birth, address.
     *
     * @param signUpDTO
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDTO) {
        //Check if already Existed: username or email
        if (userRepository.existsByPhone(signUpDTO.getPhone())) {
            return new ResponseEntity<>("Phone already registered.", HttpStatus.BAD_REQUEST);
        }

        //If not existed: Create new user, add into database and return.
        User user = new User();
        user.setUsername(signUpDTO.getUsername());
        user.setEmail(signUpDTO.getEmail());

        String sDate = signUpDTO.getDob();
        StringDealer stringDealer = new StringDealer();
        Date date = stringDealer.convertToDateAndFormat(sDate);
        user.setDob(date);

        user.setPhone(signUpDTO.getPhone());
        user.setAddress(signUpDTO.getAddress());
        user.setRoleId(signUpDTO.getRoleId());
        user.setStatus(1);
        //Encode password
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        System.out.println(user.toString());
        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    /**
     * API for the user to change password.
     * The DTO contains the login phone, old password and new password
     *
     * @param changePasswordDTO
     * @return
     */
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        //Encode the passwords
        changePasswordDTO.setNewPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        //Local variable for the user
        Optional<User> userOpt;
        //Check credentials, if not valid then return Bad request (403)
        userOpt = userRepository.findByPhone(changePasswordDTO.getPhone());
        if (userOpt.isEmpty() ||
                !passwordEncoder.matches(changePasswordDTO.getPassword(), userOpt.get().getPassword())) {
            return new ResponseEntity<>("Old password is wrong.", HttpStatus.BAD_REQUEST);
        }
        //If the old password is correct:
        User user = userOpt.get();
        user.setPassword(changePasswordDTO.getNewPassword());
        userRepository.save(user);
        return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
    }

    /**
     * API for the user to receive OTP to reset password.
     * The DTO contains the login phone
     *
     * @param forgotPasswordDTO
     * @return
     */
    @PostMapping("/forgotPassword/sendOTP")
    public ResponseEntity<?> sendOTPForgotPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        //Local variable for the user
        Optional<User> userOpt;
        //Check credentials, if not valid then return Bad request (403)
        userOpt = userRepository.findByPhone(forgotPasswordDTO.getPhone());
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>("No phone number found!", HttpStatus.BAD_REQUEST);
        } else {
            // send OTP
            return new ResponseEntity<>("OTP send!", HttpStatus.OK);
        }
    }

    /**
     * API for the user to verify OTP to reset password.
     * The DTO contains the OTP, login phone
     *
     * @param forgotPasswordDTO
     * @return
     */
    @PostMapping("/forgotPassword/verifyOTP")
    public ResponseEntity<?> verifyOTPForgotPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        //Check if the OTP match
        String OTP_REAL = "111";
        if (forgotPasswordDTO.getOTP().equalsIgnoreCase(OTP_REAL)){
            return new ResponseEntity<>("OTP confirmed! Please enter your new password.", HttpStatus.OK);
        } else {
            // wait 3m to re-send the OTP
            // resend OTP
            return new ResponseEntity<>("OTP re-send!", HttpStatus.OK);
        }
    }

    /**
     * API for the user to reset password after verify OTP.
     * The DTO contains the new password, login phone
     *
     * @param forgotPasswordDTO
     * @return
     */
    @PostMapping("/forgotPassword")
    public ResponseEntity<?> resetPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        //Encode the passwords
        forgotPasswordDTO.setNewPassword(passwordEncoder.encode(forgotPasswordDTO.getNewPassword()));
        //Get user
        User user = userRepository.findByPhone(forgotPasswordDTO.getPhone()).get();
        //Confirm password
        if (passwordEncoder.matches(forgotPasswordDTO.getConfirmPassword(), forgotPasswordDTO.getNewPassword())) {
            user.setPassword(forgotPasswordDTO.getNewPassword());
            userRepository.save(user);
            return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Password not match!", HttpStatus.OK);
        }
    }
}
