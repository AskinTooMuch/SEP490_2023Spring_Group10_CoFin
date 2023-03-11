 /*
  * Copyright (C) 2023, FPT University <br>
  * SEP490 - SEP490_G10 <br>
  * EIMS <br>
  * Eggs Incubating Management System <br>
  *
  * Record of change:<br>
  * DATE         Version     Author      DESCRIPTION<br>
  * 08/03/2023   1.0         DuongVV     First Deploy<br>
  * 10/03/2023   1.1         DuongVV     Update<br>
  */

 package com.example.eims.service.impl;

 import com.example.eims.dto.auth.*;
 import com.example.eims.entity.Facility;
 import com.example.eims.entity.Registration;
 import com.example.eims.entity.User;
 import com.example.eims.repository.FacilityRepository;
 import com.example.eims.repository.RegistrationRepository;
 import com.example.eims.repository.UserRepository;
 import com.example.eims.repository.WorkInRepository;
 import org.junit.jupiter.api.DisplayName;
 import org.junit.jupiter.api.Test;
 import org.junit.jupiter.api.extension.ExtendWith;
 import org.mockito.InjectMocks;
 import org.mockito.Mock;
 import org.mockito.junit.jupiter.MockitoExtension;
 import org.springframework.http.ResponseEntity;
 import org.springframework.security.authentication.AuthenticationManager;
 import org.springframework.security.crypto.password.PasswordEncoder;

 import java.util.Optional;

 import static org.junit.jupiter.api.Assertions.*;
 import static org.mockito.ArgumentMatchers.any;
 import static org.mockito.Mockito.when;

 @ExtendWith(MockitoExtension.class)
 class AuthServiceImplTest {
     @Mock
     AuthenticationManager authenticationManager;
     @Mock
     UserRepository userRepository;
     @Mock
     FacilityRepository facilityRepository;
     @Mock
     WorkInRepository workInRepository;
     @Mock
     RegistrationRepository registrationRepository;
     @Mock
     PasswordEncoder passwordEncoder;
     @InjectMocks
     AuthServiceImpl authService;

     // Create demo entity
     LoginDTO loginDTO = new LoginDTO("0987654321", "123456Aa@");
     // User
     User user = new User(1L, 2L, "0987654321", "123456Aa@", 1);
     // Facility
     Facility facility = new Facility(1L, user.getUserId(), 1);
     //Registration
     Registration registration = new Registration(user.getUserId(), 2);


     @Test
     void authenticateUser() {
         // Set up
         LoginDTO loginDTO = new LoginDTO("0987654321", "123456Aa@");
         // Define behaviour of repository
         when(userRepository.findByPhone(any(String.class))).thenReturn(Optional.of(user));
         when(facilityRepository.findByUserId(any(Long.class))).thenReturn(Optional.of(facility));

         // Run service method
         ResponseEntity<?> responseEntity = authService.authenticateUser(loginDTO);
         System.out.println(responseEntity.toString());
         SessionDTO sessionDTO = (SessionDTO) responseEntity.getBody();
         // Assert
         assertEquals(sessionDTO.getUserId(), 1L);
         //assertEquals(sessionDTO.getUserId(), 3L);
     }

     @Test
     @DisplayName("Register")
     void registerUser() {
         // Set up Dto
         SignUpDTO signUpDTO = new SignUpDTO();
         signUpDTO.setUsername("Name");
         signUpDTO.setUserDob("2000-01-01");
         signUpDTO.setUserPhone("0999999999");
         signUpDTO.setUserEmail("test@test.com");
         signUpDTO.setUserAddress("Address");
         signUpDTO.setUserPassword("123456Aa@");
         signUpDTO.setFacilityName("F name");
         signUpDTO.setFacilityFoundDate("2000-01-01");
         signUpDTO.setFacilityHotline("0999999999");
         signUpDTO.setFacilityAddress("F address");
         signUpDTO.setBusinessLicenseNumber("11111");
         // Define behaviour of repository
         when(userRepository.existsByPhone(signUpDTO.getUserPhone())).thenReturn(false);
         when(userRepository.save(any(User.class))).thenReturn(user);
         // Run service method
         ResponseEntity<?> responseEntity = authService.registerUser(signUpDTO);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Đăng kí thành công, vui lòng đợi xác nhận tài khoản", responseEntity.getBody());
     }

     @Test
     void changePassword() {
         // Set up Dto
         ChangePasswordDTO dto = new ChangePasswordDTO();
         dto.setUserId(1L);
         dto.setPassword("123456Aa@");
         dto.setNewPassword("123457Aa@");
         // Define behaviour of repository
         when(userRepository.findByUserId(1L)).thenReturn(Optional.of(user));
         when(passwordEncoder.matches(dto.getPassword(), user.getPassword()))
                 .thenReturn((dto.getPassword().equals(user.getPassword())));
         // Run service method
         ResponseEntity<?> responseEntity = authService.changePassword(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Thay đổi mật khẩu thành công", responseEntity.getBody());
     }

     @Test
     void sendOTP() {
         // Set up
         String phone = "0987654321";
         // Define behaviour of repository
         when(userRepository.findByPhone(phone)).thenReturn(Optional.of(user));

         // Run service method
         ResponseEntity<?> responseEntity = authService.sendOTP(phone);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Đã gửi mã OTP", responseEntity.getBody());
     }

     @Test
     void verifyOTP() {
         // Set up
         VerifyOtpDTO verifyOtpDTO = new VerifyOtpDTO();
         verifyOtpDTO.setPhone(user.getPhone());
         verifyOtpDTO.setOTP("123");
         String phone = user.getPhone();
         user.setOtp("123");
         // Define behaviour of repository
         when(userRepository.findByPhone(phone)).thenReturn(Optional.of(user));

         // Run service method
         ResponseEntity<?> responseEntity = authService.verifyOTP(verifyOtpDTO);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Mã OTP đã đúng", responseEntity.getBody());
     }

     @Test
     void resendOTP() {
         // Set up
         String phone = "0987654321";
         // Define behaviour of repository
         when(userRepository.findByPhone(phone)).thenReturn(Optional.of(user));

         // Run service method
         ResponseEntity<?> responseEntity = authService.resendOTP(phone);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Đã gửi lại mã OTP", responseEntity.getBody());
     }

     @Test
     void resetPassword() {
         // Set up
         ForgotPasswordDTO dto = new ForgotPasswordDTO();
         dto.setPhone("0987654321");
         dto.setNewPassword("1234567Aa@");
         dto.setConfirmPassword("1234567Aa@");
         // Define behaviour of repository
         when(userRepository.findByPhone(dto.getPhone())).thenReturn(Optional.of(user));
         when(passwordEncoder.matches(dto.getNewPassword(), dto.getConfirmPassword()))
                 .thenReturn((dto.getNewPassword().equals(dto.getConfirmPassword())));
         // Run service method
         ResponseEntity<?> responseEntity = authService.resetPassword(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Mật khẩu thay đổi thành công", responseEntity.getBody());
     }
 }
