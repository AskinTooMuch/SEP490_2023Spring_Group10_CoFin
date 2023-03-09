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
import com.example.eims.entity.Registration;
import com.example.eims.entity.User;
import com.example.eims.entity.WorkIn;
import com.example.eims.repository.FacilityRepository;
import com.example.eims.repository.RegistrationRepository;
import com.example.eims.repository.UserRepository;
import com.example.eims.repository.WorkInRepository;
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
    private final WorkInRepository workInRepository;
    @Autowired
    private final RegistrationRepository registrationRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final StringDealer stringDealer;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
                           FacilityRepository facilityRepository, WorkInRepository workInRepository,
                           RegistrationRepository registrationRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.facilityRepository = facilityRepository;
        this.workInRepository = workInRepository;
        this.registrationRepository = registrationRepository;
        this.passwordEncoder = passwordEncoder;
        this.stringDealer = new StringDealer();
    }

    /**
     * Sign in.
     *
     * @param loginDTO contains the login phone and password
     * @return
     */
    @Override
    public ResponseEntity<?> authenticateUser(LoginDTO loginDTO) {
        String phone = stringDealer.trimMax(loginDTO.getPhone());
        if (phone.equals("")) { /* Phone number is empty */
            return new ResponseEntity<>("Số điện thoại không được để trống", HttpStatus.BAD_REQUEST);
        }
        String password = stringDealer.trimMax(loginDTO.getPassword());
        if (password.equals("")) { /* Password is empty */
            return new ResponseEntity<>("Mật khẩu không được để trống", HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.findByPhone(loginDTO.getPhone()).get();
        // Check status of Account
        if (user.getStatus() == 0) {
            return new ResponseEntity<>("Tài khoản đã bị vô hiệu hóa", HttpStatus.BAD_REQUEST);
        }
        /*        if (user.getRoleId() == 2L) { *//*role is OWNER (have Facility)*//*
            // Check status of registration
            // 0 - considering
            // 1 - rejected
            // 2 - approved
            Registration registration = registrationRepository.findByUserId(user.getUserId()).get();
            if (registration.getStatus() == 0L) { *//* status = 0 (considering) *//*
                return new ResponseEntity<>("Wait", HttpStatus.BAD_REQUEST);
            }
            if (registration.getStatus() == 1L) { *//* status = 1 (rejected) *//*
                return new ResponseEntity<>("Rejected", HttpStatus.BAD_REQUEST);
            }
        }
        if (user.getRoleId() == 3L) { *//*role is EMPLOYEE (work in Facility)*//*
            WorkIn workIn = workInRepository.findByUserId(user.getUserId()).get();
            Long facilityId = workIn.getFacilityId();
            if (facilityRepository.getStatusById(facilityId) == 0) { *//* status = 0 (facility stopped running) *//*
                return new ResponseEntity<>("Facility stopped running", HttpStatus.BAD_REQUEST);
            }
        }*/

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(phone, password));
        SecurityContextHolder.getContext().setAuthentication(auth);
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        // Set attribute to sessionDTO
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setUserId(user.getUserId());
        if (user.getRoleId() == 2L) { /*role is OWNER (have Facility)*/
            Facility facility = facilityRepository.findByUserId(user.getUserId()).get();
            sessionDTO.setFacilityId(facility.getFacilityId());
            return new ResponseEntity<>(sessionDTO, HttpStatus.OK);
        } else if (user.getRoleId() == 3L) { /*role is EMPLOYEE (work in Facility)*/
            WorkIn workIn = workInRepository.findByUserId(user.getUserId()).get();
            sessionDTO.setFacilityId(workIn.getFacilityId());
            return new ResponseEntity<>(sessionDTO, HttpStatus.OK);
        } else { /*role USER, MODERATOR, ADMIN (no Facility)*/
            sessionDTO.setFacilityId(null);
            return new ResponseEntity<>(sessionDTO, HttpStatus.OK);
        }
    }

    /**
     * Sign up.
     *
     * @param signUpDTO contains the User's name, email, phone number, date of birth, address.
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<?> registerUser(SignUpDTO signUpDTO) {
        // If not existed: Create new user, add into database and return.
        User user = new User();
        // Check input
        // Username
        String username = stringDealer.trimMax(signUpDTO.getUsername());
        if (username.equals("")) { /* Username is empty */
            return new ResponseEntity<>("Tên không được để trống", HttpStatus.BAD_REQUEST);
        }
        // Date of birth
        String sDate = stringDealer.trimMax(signUpDTO.getUserDob());
        if (sDate.equals("")) { /* Date of birth is empty */
            return new ResponseEntity<>("Ngày sinh không được để trống", HttpStatus.BAD_REQUEST);
        }
        // Phone number
        String phone = stringDealer.trimMax(signUpDTO.getUserPhone());
        if (phone.equals("")) { /* Phone number is empty */
            return new ResponseEntity<>("Số điện thoại không được để trống", HttpStatus.BAD_REQUEST);
        }
        if (!stringDealer.checkPhoneRegex(phone)) { /* Phone number is not valid*/
            return new ResponseEntity<>("Số điện thoại không đúng đinh dạng", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByPhone(phone)) {/* Phone number is existed*/
            return new ResponseEntity<>("Số điện thoại đã được sử dụng", HttpStatus.BAD_REQUEST);
        }
        // Email
        String email = stringDealer.trimMax(signUpDTO.getUserEmail());
        if (!stringDealer.checkEmailRegex(email)) { /* Email is not valid */
            return new ResponseEntity<>("Email không đúng định dạng", HttpStatus.BAD_REQUEST);
        }
        // Address
        String address = stringDealer.trimMax(signUpDTO.getUserAddress());
        if (address.equals("")) { /* Address is empty */
            return new ResponseEntity<>("Địa chỉ không được để trống", HttpStatus.BAD_REQUEST);
        }
        // Password
        String password = stringDealer.trimMax(signUpDTO.getUserPassword());
        if (password.equals("")) { /* Password is empty */
            return new ResponseEntity<>("Mật khẩu không được để trống", HttpStatus.BAD_REQUEST);
        }
        if (!stringDealer.checkPasswordRegex(password)) { /* Password is not valid */
            return new ResponseEntity<>("Mật khẩu không đúng định dạng", HttpStatus.BAD_REQUEST);
        }
        // Confirm password
        String rePassword = stringDealer.trimMax(signUpDTO.getUserPassword());
        if (rePassword.equals("")) { /* Confirm password is empty */
            return new ResponseEntity<>("Mật khẩu không được để trống", HttpStatus.BAD_REQUEST);
        }
        // Password match
        if (!password.equals(rePassword)) { /* Password not match */
            return new ResponseEntity<>("Mật khẩu không khớp", HttpStatus.BAD_REQUEST);
        }
        // Set attribute
        user.setUsername(username);
        user.setEmail(email);
        Date date = stringDealer.convertToDateAndFormat(sDate);
        user.setDob(date);
        user.setPhone(phone);
        user.setAddress(address);
        user.setRoleId(2L);     /* Role OWNER */
        user.setStatus(0);      /* Inactivated, need registration's approval */
        //Encode password
        user.setPassword(passwordEncoder.encode(password));
        try {
            User returnUser = userRepository.save(user);
            // Facility
            Facility facility = new Facility();
            facility.setUserId(returnUser.getUserId());
            // Name
            String fName = stringDealer.trimMax(signUpDTO.getFacilityName());
            if (fName.equals("")) { /* Facility name is empty */
                return new ResponseEntity<>("Tên cơ sở không được để trống", HttpStatus.BAD_REQUEST);
            }
            facility.setFacilityName(fName);
            // Found date
            sDate = stringDealer.trimMax(signUpDTO.getFacilityFoundDate());
            if (sDate.equals("")) {  /* Found date is empty */
                return new ResponseEntity<>("Ngày thành lập không được để trống", HttpStatus.BAD_REQUEST);
            }
            date = stringDealer.convertToDateAndFormat(sDate);
            facility.setFacilityFoundDate(date);
            // Hotline (Using the same format with cellphone number)
            String hotline = stringDealer.trimMax(signUpDTO.getFacilityHotline());
            if (hotline.equals("")) { /* Hotline is empty */
                return new ResponseEntity<>("Hotline không được để trống", HttpStatus.BAD_REQUEST);
            }
            if (!stringDealer.checkPhoneRegex(hotline)) { /* Hotline is not valid */
                return new ResponseEntity<>("Hotline không đúng định dạng", HttpStatus.BAD_REQUEST);
            }
            facility.setHotline(hotline);
            // Address
            String fAddress = stringDealer.trimMax(signUpDTO.getFacilityAddress());
            if (fAddress.equals("")) { /* Address is empty */
                return new ResponseEntity<>("Địa chỉ cơ sở không được để trống", HttpStatus.BAD_REQUEST);
            }
            facility.setFacilityAddress(fAddress);
            // Business license number
            String licenseNumber = stringDealer.trimMax(signUpDTO.getBusinessLicenseNumber());
            if (licenseNumber.equals("")) { /* Business license number is empty */
                return new ResponseEntity<>("Số đăng kí kinh doanh không được để trống", HttpStatus.BAD_REQUEST);
            }
            facility.setBusinessLicenseNumber(licenseNumber);

            facility.setStatus(0);  /* Inactivated, need registration's approval */
            facilityRepository.save(facility);
            return new ResponseEntity<>("Đăng kí thành công, vui lòng đợi xác nhận tài khoản", HttpStatus.OK);
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
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
        // Check blank input
        String password = stringDealer.trimMax(changePasswordDTO.getPassword());
        if (password.equals("")) { /* Password is empty */
            return new ResponseEntity<>("Mật khẩu không được để trống", HttpStatus.BAD_REQUEST);
        }
        String newPassword = stringDealer.trimMax(changePasswordDTO.getNewPassword());
        if (newPassword.equals("")) { /* New password is empty */
            return new ResponseEntity<>("Mật khẩu mới không được để trống", HttpStatus.BAD_REQUEST);
        }
        if (!stringDealer.checkPasswordRegex(newPassword)) { /* New password is not valid */
            return new ResponseEntity<>("Mật khẩu mới không đúng định dạng", HttpStatus.BAD_REQUEST);
        }
        //Local variable for the user
        Optional<User> userOpt;
        //Check credentials, if not valid then return Bad request (403)
        userOpt = userRepository.findByUserId(changePasswordDTO.getUserId());
        if (userOpt.isEmpty() ||
                !passwordEncoder.matches(password, userOpt.get().getPassword())) { /* Old password not match*/
            return new ResponseEntity<>("Mật khẩu cũ sai", HttpStatus.BAD_REQUEST);
        } else { /* Old password is correct */
            //Encode the passwords
            newPassword = passwordEncoder.encode(newPassword);
            User user = userOpt.get();
            user.setPassword(newPassword);
            userRepository.save(user);
            return new ResponseEntity<>("Thay đổi mật khẩu thành công", HttpStatus.OK);
        }
    }

    /**
     * Send OTP to reset password.
     *
     * @param phone the phone number of the account
     * @return
     */
    @Override
    public ResponseEntity<?> sendOTP(String phone) {
        // Check credentials, if not valid then return Bad request (403)
        Optional<User> userOptional = userRepository.findByPhone(phone);
        if (!userOptional.isPresent()) { /* No user found */
            return new ResponseEntity<>("Không tìm thấy số điện thoại", HttpStatus.BAD_REQUEST);
        } else {
            // Create OTP
            String OTP = "111";
            User user = userOptional.get();
            user.setOtp(OTP);
            userRepository.save(user);
            // Send OTP

            return new ResponseEntity<>("Đã gửi mã OTP", HttpStatus.OK);
        }
    }

    /**
     * Verify OTP forgot password.
     *
     * @param phone the phone number of the account
     * @param otp   code to verify phone number
     * @return
     */
    @Override
    public ResponseEntity<?> verifyOTP(String phone, String otp) {
        // Check if the OTP match
        User user = userRepository.findByPhone(phone).get();
        String OTP_REAL = user.getOtp();
        if (otp.equals(OTP_REAL)) {      /* OTP match*/
            // Reset otp
            user.setOtp("");
            userRepository.save(user);
            return new ResponseEntity<>("Mã OTP đã đúng", HttpStatus.OK);
        } else {                        /* OTP not match*/

            return new ResponseEntity<>("Mã OTP sai", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Re-send OTP forgot password.
     *
     * @return
     */
    @Override
    public ResponseEntity<?> resendOTP(String phone) {
        // Check credentials, if not valid then return Bad request (403)
        Optional<User> userOptional = userRepository.findByPhone(phone);
        if (!userOptional.isPresent()) { /* No user found */
            return new ResponseEntity<>("Không tìm thấy số điện thoại", HttpStatus.BAD_REQUEST);
        } else {
            // Create OTP
            String OTP = "111";
            User user = userOptional.get();
            user.setOtp(OTP);
            userRepository.save(user);
            // Send OTP

            return new ResponseEntity<>("Đã gửi lại mã OTP", HttpStatus.OK);
        }
    }


    /**
     * Change password after verify OTP.
     *
     * @param forgotPasswordDTO contains the new password, login phone
     * @return
     */
    @Override
    public ResponseEntity<?> resetPassword(ForgotPasswordDTO forgotPasswordDTO) {
        String phone = stringDealer.trimMax(forgotPasswordDTO.getPhone());
        if (phone.equals("")) { /* Phone number is empty */
            return new ResponseEntity<>("Số điện thoại không được để trống", HttpStatus.BAD_REQUEST);
        }
        String password = stringDealer.trimMax(forgotPasswordDTO.getNewPassword());
        if (password.equals("")) { /* Password is empty */
            return new ResponseEntity<>("Mật khẩu không được để trống", HttpStatus.BAD_REQUEST);
        }
        String confirmPassword = stringDealer.trimMax(forgotPasswordDTO.getConfirmPassword());
        if (confirmPassword.equals("")) { /* Confirm Password is empty */
            return new ResponseEntity<>("Xác nhận mật khẩu không được để trống", HttpStatus.BAD_REQUEST);
        }
        if (!stringDealer.checkPasswordRegex(password)) { /* Password is not valid */
            return new ResponseEntity<>("Mật khẩu không đúng định dạng", HttpStatus.BAD_REQUEST);
        }
        // Encode the passwords
        password = passwordEncoder.encode(password);
        // Get user
        User user = userRepository.findByPhone(forgotPasswordDTO.getPhone()).get();
        // Confirm password match
        if (passwordEncoder.matches(forgotPasswordDTO.getConfirmPassword(), forgotPasswordDTO.getNewPassword())) {
            user.setPassword(password);
            userRepository.save(user);
            return new ResponseEntity<>("Mật khẩu thay đổi thành công", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Mật khẩu không khớp", HttpStatus.BAD_REQUEST);
        }
    }
}
