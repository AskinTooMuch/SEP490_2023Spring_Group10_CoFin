/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 02/03/2023   1.0         ChucNV      First Deploy<br>
 * 02/03/2023   2.0         ChucNV      Implement signup service
 */
package com.example.eims.service.impl;

import com.example.eims.dto.auth.*;
import com.example.eims.entity.Facility;
import com.example.eims.entity.User;
import com.example.eims.repository.FacilityRepository;
import com.example.eims.repository.UserRepository;
import com.example.eims.service.interfaces.IAuthService;
import com.example.eims.utils.StringDealer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final FacilityRepository facilityRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, FacilityRepository facilityRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.facilityRepository = facilityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Sign in.
     *
     * @param loginDTO contains the login phone and password
     * @return
     */
    @Override
    public ResponseEntity<?> authenticateUser(LoginDTO loginDTO) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getPhone().trim(), loginDTO.getPassword().trim()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        User user = userRepository.findByPhone(loginDTO.getPhone()).get();
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setUserId(user.getUserId());
        if (user.getRoleId() == 2 || user.getRoleId() == 3) { /*role is OWNER or EMPLOYEE (have Facility)*/
            Facility facility = facilityRepository.findByUserId(user.getUserId()).get();
            sessionDTO.setFacilityId(facility.getFacilityId());
            return new ResponseEntity<>(sessionDTO, HttpStatus.OK);
        } else { /*role USER, MODERATOR, ADMIN (no Facility)*/
            sessionDTO.setFacilityId(null);
            return new ResponseEntity<>(sessionDTO, HttpStatus.OK);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> registerUser(SignUpDTO signUpDTO) {
        //If not existed: Create new user, add into database and return.
        User user = new User();
        user.setUsername(signUpDTO.getUsername().trim());
        user.setEmail(signUpDTO.getUserEmail().trim());

        String sDate = signUpDTO.getUserDob().trim();
        StringDealer stringDealer = new StringDealer();
        Date date = stringDealer.convertToDateAndFormat(sDate);
        user.setDob(date);

        user.setPhone(signUpDTO.getUserPhone().trim());
        user.setAddress(signUpDTO.getUserAddress().trim());
        user.setRoleId(2L);
        user.setStatus(2);
        //Encode password
        user.setPassword(passwordEncoder.encode(signUpDTO.getUserPassword().trim()));
        try {
            User returnUser = userRepository.save(user);
            Facility facility = new Facility();
            facility.setUserId(returnUser.getUserId());
            facility.setFacilityName(signUpDTO.getFacilityName().trim());

            sDate = signUpDTO.getFacilityFoundDate().trim();
            date = stringDealer.convertToDateAndFormat(sDate);
            facility.setFacilityFoundDate(date);
            facility.setHotline(signUpDTO.getFacilityHotline());
            facility.setFacilityAddress(signUpDTO.getFacilityAddress());
            facility.setBusinessLicenseNumber(signUpDTO.getBusinessLicenseNumber());
            Facility returnFacility = facilityRepository.save(facility);
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        } catch (IllegalArgumentException iae) {
            return null;
        }
    }

    /**
     * Change password.
     *
     * @param changePasswordDTO contains the login phone, old password and new password
     * @return
     */
    @Override
    public ResponseEntity<?> changePassword(ChangePasswordDTO changePasswordDTO) {
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
    @Override
    public ResponseEntity<?> sendOTP(String phone) {
        //Check credentials, if not valid then return Bad request (403)
        Boolean existed = userRepository.existsByPhone(phone);
        if (!existed) {
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
    @Override
    public ResponseEntity<?> verifyOTP(String OTP) {
        //Check if the OTP match
        String OTP_REAL = "111";
        if (OTP.equals(OTP_REAL)) {      /* OTP match*/
            return new ResponseEntity<>("OTP confirmed! Please enter your new password.", HttpStatus.OK);
        } else {                        /* OTP not match*/

            return new ResponseEntity<>("OTP wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Re-send OTP forgot password.
     *
     * @return
     */
    @Override
    public ResponseEntity<?> resendOTP(String phone) {
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
    @Override
    public ResponseEntity<?> resetPassword(ForgotPasswordDTO forgotPasswordDTO) {
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
            return new ResponseEntity<>("Password not match!", HttpStatus.BAD_REQUEST);
        }
    }


}
