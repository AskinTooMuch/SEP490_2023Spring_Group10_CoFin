package com.example.eims.service.impl;

import com.example.eims.dto.auth.ChangePasswordDTO;
import com.example.eims.dto.auth.ForgotPasswordDTO;
import com.example.eims.dto.auth.LoginDTO;
import com.example.eims.dto.auth.SignUpDTO;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class AuthServiceImpl implements IAuthService{
    private final UserRepository userRepository;
    private final FacilityRepository facilityRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, FacilityRepository facilityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.facilityRepository = facilityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<?> authenticateUser(LoginDTO loginDTO) {
        return null;
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
        user.setStatus(1);
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

    @Override
    public ResponseEntity<?> changePassword(ChangePasswordDTO changePasswordDTO) {
        return null;
    }

    @Override
    public ResponseEntity<?> sendOTP(String phone) {
        return null;
    }

    @Override
    public ResponseEntity<?> verifyOTP(String OTP) {
        return null;
    }

    @Override
    public ResponseEntity<?> resendOTP() {
        return null;
    }

    @Override
    public ResponseEntity<?> resetPassword(ForgotPasswordDTO forgotPasswordDTO) {
        return null;
    }
}
