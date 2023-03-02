/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 18/01/2023   1.0         ChucNV      First Deploy<br>
 * 28/02/2023   2.0         ChucNV      Enable Security
 * 02/03/2023   2.1         ChucNV      Move Signup service to Service file
 */

package com.example.eims.controller;

import com.example.eims.dto.auth.ChangePasswordDTO;
import com.example.eims.dto.auth.ForgotPasswordDTO;
import com.example.eims.dto.auth.LoginDTO;
import com.example.eims.dto.auth.SignUpDTO;
import com.example.eims.entity.User;
import com.example.eims.repository.FacilityRepository;
import com.example.eims.repository.UserRoleRepository;
import com.example.eims.repository.UserRepository;
import com.example.eims.service.impl.AuthServiceImpl;
import com.example.eims.service.interfaces.IAuthService;
import com.example.eims.utils.StringDealer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
    private FacilityRepository facilityRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Sign in .
     *
     * @param loginDTO contains the login phone and password
     * @return
     */
    @PostMapping("/signin")
    public ResponseEntity<Long> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getPhone().trim(), loginDTO.getPassword().trim()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        User user = userRepository.findByPhone(loginDTO.getPhone()).get();
        return new ResponseEntity<>(user.getUserId(), HttpStatus.OK);
    }

    /**
     * Sign up.
     *
     * @param signUpDTO contains the User's name, email, phone number, date of birth, address.
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDTO) {
        //Check if already Existed: username or email
        if (userRepository.existsByPhone(signUpDTO.getUserPhone())) {
            return new ResponseEntity<>("Phone already registered.", HttpStatus.BAD_REQUEST);
        }
        IAuthService iAuthService = new AuthServiceImpl(
                userRepository, facilityRepository, passwordEncoder
        );
        System.out.println(signUpDTO);
        ResponseEntity re = iAuthService.registerUser(signUpDTO);
        if (re==null) return new ResponseEntity<>("User registered failed", HttpStatus.BAD_REQUEST);
        else return re;
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
        //Encode the passwords
        changePasswordDTO.setNewPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword().trim()));
        //Local variable for the user
        Optional<User> userOpt;
        //Check credentials, if not valid then return Bad request (403)
        userOpt = userRepository.findByUserId(changePasswordDTO.getUserId());
        if (userOpt.isEmpty() ||
                !passwordEncoder.matches(changePasswordDTO.getPassword().trim(), userOpt.get().getPassword().trim())) {
            return new ResponseEntity<>("Old password is wrong.", HttpStatus.BAD_REQUEST);
        }
        //If the old password is correct:
        User user = userOpt.get();
        user.setPassword(changePasswordDTO.getNewPassword().trim());
        userRepository.save(user);
        return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
    }

    /**
     * Send OTP to reset password.
     *
     * @param phone the phone number of the account
     * @return
     */
    @GetMapping("/forgotPassword/sendOTP")
    public ResponseEntity<?> sendOTP(@RequestParam String phone) {
        //Check credentials, if not valid then return Bad request (403)
        User user = userRepository.findByPhone(phone).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("No phone number found!", HttpStatus.BAD_REQUEST);
        } else {
            // create OTP

            // send OTP

            return new ResponseEntity<>("OTP send!", HttpStatus.OK);
        }
    }

    /**
     * Verify OTP forgot password.
     *
     * @param OTP code to verify phone number
     * @return
     */
    @PostMapping("/forgotPassword/verifyOTP")
    public ResponseEntity<?> verifyOTP(@RequestBody String OTP) {
        //Check if the OTP match
        String OTP_REAL = "111";
        if (OTP.equals(OTP_REAL)){      /* OTP match*/
            return new ResponseEntity<>("OTP confirmed! Please enter your new password.", HttpStatus.OK);
        } else {                        /* OTP not match*/

            return new ResponseEntity<>("OTP wrong!", HttpStatus.OK);
        }
    }

    /**
     * Re-send OTP forgot password.
     *
     * @return
     */
    @GetMapping("/forgotPassword/resendOTP")
    public ResponseEntity<?> resendOTP() {
        // create OTP

        // send OTP

        return new ResponseEntity<>("OTP re-send!", HttpStatus.OK);
    }

    /**
     * Change password after verify OTP.
     *
     * @param forgotPasswordDTO contains the new password, login phone
     * @return
     */
    @PostMapping("/forgotPassword/changePassword")
    public ResponseEntity<?> resetPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        //Encode the passwords
        forgotPasswordDTO.setNewPassword(passwordEncoder.encode(forgotPasswordDTO.getNewPassword().trim()));
        //Get user
        User user = userRepository.findByPhone(forgotPasswordDTO.getPhone()).get();
        //Confirm password
        if (passwordEncoder.matches(forgotPasswordDTO.getConfirmPassword(), forgotPasswordDTO.getNewPassword())) {
            user.setPassword(forgotPasswordDTO.getNewPassword().trim());
            userRepository.save(user);
            return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Password not match!", HttpStatus.OK);
        }
    }
}
