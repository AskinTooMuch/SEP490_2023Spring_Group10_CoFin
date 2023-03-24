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
 import com.example.eims.entity.*;
 import com.example.eims.repository.*;
 import jakarta.servlet.ServletContext;
 import jakarta.servlet.http.HttpServletRequest;
 import jakarta.servlet.http.HttpServletResponse;
 import jakarta.servlet.http.HttpSession;
 import org.junit.jupiter.api.DisplayName;
 import org.junit.jupiter.api.Test;
 import org.junit.jupiter.api.extension.ExtendWith;
 import org.mockito.InjectMocks;
 import org.mockito.Mock;
 import org.mockito.junit.jupiter.MockitoExtension;
 import org.springframework.http.ResponseEntity;
 import org.springframework.mock.web.MockHttpServletRequest;
 import org.springframework.security.authentication.AuthenticationManager;
 import org.springframework.security.authentication.AuthenticationProvider;
 import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
 import org.springframework.security.core.Authentication;
 import org.springframework.security.core.GrantedAuthority;
 import org.springframework.security.core.context.SecurityContextHolder;
 import org.springframework.security.crypto.password.PasswordEncoder;

 import java.io.IOException;
 import java.util.*;

 import static org.junit.jupiter.api.Assertions.*;
 import static org.mockito.ArgumentMatchers.any;
 import static org.mockito.Mockito.when;

 @ExtendWith(MockitoExtension.class)
 class AuthServiceImplTest {
     @Mock
     AuthenticationProvider authenticationProvider;
     @Mock
     SecurityContextHolder securityContextHolder;
     @Mock
     UserRepository userRepository;
     @Mock
     FacilityRepository facilityRepository;
     @Mock
     WorkInRepository workInRepository;
     @Mock
     RegistrationRepository registrationRepository;
     @Mock
     OtpRepository otpRepository;
     @Mock
     PasswordEncoder passwordEncoder;
     @Mock
     HttpServletRequest httpServletRequest;
     @Mock
     HttpServletResponse httpServletResponse;
     @InjectMocks
     AuthServiceImpl authService;

     // Create demo entity
     LoginDTO loginDTO = new LoginDTO("0987654321", "@User123");
     // User
     User user = new User(6L, "0987654321", "$2a$10$4GrQ4t2aPfe8mnGuWwmsB.YACEEyiZ8BAnEuSK70IpROqu.Bv9Ttm", 1);
     // Facility
     Facility facility = new Facility(4L, user.getUserId(), 1);
     //Registration
     Registration registration = new Registration(user.getUserId(), 2);


     @Test
     @DisplayName("RegisterUTCID02")
     void registerUTCID02() {
         // Set up Dto
         SignUpDTO signUpDTO = new SignUpDTO();
         signUpDTO.setUsername("Bùi Thanh T");
         signUpDTO.setUserDob("");
         signUpDTO.setUserPhone("0987654321");
         signUpDTO.setUserEmail("tungduong71@gmail.com"	);
         signUpDTO.setUserAddress("So 27 duong Truong Trinh");
         signUpDTO.setUserPassword("@User123");
         signUpDTO.setFacilityName("BùiThanhT");
         signUpDTO.setFacilityFoundDate("");
         signUpDTO.setFacilityHotline("0987654321");
         signUpDTO.setFacilityAddress("So 27 duong Truong Trinh");
         signUpDTO.setBusinessLicenseNumber("12345678");
         // Run service method
         ResponseEntity<?> responseEntity = authService.registerUser(signUpDTO);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Ngày sinh không được để trống", responseEntity.getBody());
     }

     @Test
     @DisplayName("RegisterUTCID03")
     void registerUTCID03() {
         // Set up Dto
         SignUpDTO signUpDTO = new SignUpDTO();
         signUpDTO.setUsername("& HCm");
         signUpDTO.setUserDob("2000-02-03");
         signUpDTO.setUserPhone("0987654321");
         signUpDTO.setUserEmail("tungduong71@gmail.com"	);
         signUpDTO.setUserAddress("So 27 duong Truong Trinh");
         signUpDTO.setUserPassword("@User123");
         signUpDTO.setFacilityName("BùiThanhT");
         signUpDTO.setFacilityFoundDate("2000-02-03");
         signUpDTO.setFacilityHotline("0987654321");
         signUpDTO.setFacilityAddress("So 27 duong Truong Trinh");
         signUpDTO.setBusinessLicenseNumber("12345678");
         // Define behaviour of repository
         when(userRepository.existsByPhone(signUpDTO.getUserPhone())).thenReturn(false);
         when(userRepository.save(any(User.class))).thenReturn(user);
         // Run service method
         ResponseEntity<?> responseEntity = authService.registerUser(signUpDTO);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Tên không đúng định dạng", responseEntity.getBody());
     }

     @Test
     @DisplayName("RegisterUTCID04")
     void registerUTCID04() {
         // Set up Dto
         SignUpDTO signUpDTO = new SignUpDTO();
         signUpDTO.setUsername("BùiThanhT");
         signUpDTO.setUserDob("2000-02-03");
         signUpDTO.setUserPhone("0987654321");
         signUpDTO.setUserEmail("a@b_c@gmail.com");
         signUpDTO.setUserAddress("Hà Nội, Việt Nam");
         signUpDTO.setUserPassword("@User123");
         signUpDTO.setFacilityName("BùiThanhT");
         signUpDTO.setFacilityFoundDate("2000-02-03");
         signUpDTO.setFacilityHotline("0987654321");
         signUpDTO.setFacilityAddress("Hà Nội, Việt Nam");
         signUpDTO.setBusinessLicenseNumber("12345678");
         // Define behaviour of repository
         //when(userRepository.existsByPhone(signUpDTO.getUserPhone())).thenReturn(false);
         //when(userRepository.save(any(User.class))).thenReturn(user);
         // Run service method
         ResponseEntity<?> responseEntity = authService.registerUser(signUpDTO);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Email không đúng định dạng", responseEntity.getBody());
     }

     @Test
     @DisplayName("RegisterUTCID05")
     void registerUTCID05() {
         // Set up Dto
         SignUpDTO signUpDTO = new SignUpDTO();
         signUpDTO.setUsername("Lê Văn Đ");
         signUpDTO.setUserDob("2000-02-03");
         signUpDTO.setUserPhone("0987654321");
         signUpDTO.setUserEmail("tungduong71@gmail.com"	);
         signUpDTO.setUserAddress("So 27 duong Truong Trinh");
         signUpDTO.setUserPassword("eims");
         signUpDTO.setFacilityName("BùiThanhT");
         signUpDTO.setFacilityFoundDate("2000-02-03");
         signUpDTO.setFacilityHotline("0987654321");
         signUpDTO.setFacilityAddress("So 27 duong Truong Trinh");
         signUpDTO.setBusinessLicenseNumber("12345678");
         // Define behaviour of repository
//         when(userRepository.existsByPhone(signUpDTO.getUserPhone())).thenReturn(false);
//         when(userRepository.save(any(User.class))).thenReturn(user);
         // Run service method
         ResponseEntity<?> responseEntity = authService.registerUser(signUpDTO);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Mật khẩu không đúng định dạng", responseEntity.getBody());
     }

     @Test
     @DisplayName("RegisterUTCID06")
     void registerUTCID06() {
         // Set up Dto
         SignUpDTO signUpDTO = new SignUpDTO();
         signUpDTO.setUsername("BùiThanhT");
         signUpDTO.setUserDob("2000-02-03");
         signUpDTO.setUserPhone("abcdefgh");
         signUpDTO.setUserEmail("tungduong71@gmail.com"	);
         signUpDTO.setUserAddress("Hà Nội, Việt Nam");
         signUpDTO.setUserPassword("@User123");
         signUpDTO.setFacilityName("BùiThanhT");
         signUpDTO.setFacilityFoundDate("2000-02-03");
         signUpDTO.setFacilityHotline("abcdefgh");
         signUpDTO.setFacilityAddress("Hà Nội, Việt Nam");
         signUpDTO.setBusinessLicenseNumber("12345678");
         // Define behaviour of repository
//         when(userRepository.existsByPhone(signUpDTO.getUserPhone())).thenReturn(false);
//         when(userRepository.save(any(User.class))).thenReturn(user);
         // Run service method
         ResponseEntity<?> responseEntity = authService.registerUser(signUpDTO);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Số điện thoại không đúng đinh dạng", responseEntity.getBody());
     }

     @Test
     @DisplayName("RegisterUTCID07")
     void registerUTCID07() {
         // Set up Dto
         SignUpDTO signUpDTO = new SignUpDTO();
         signUpDTO.setUsername("BùiThanhT");
         signUpDTO.setUserDob("2000-02-03");
         signUpDTO.setUserPhone("0987654321");
         signUpDTO.setUserEmail("tungduong71@gmail.com"	);
         signUpDTO.setUserAddress("");
         signUpDTO.setUserPassword("@User123");
         signUpDTO.setFacilityName("BùiThanhT");
         signUpDTO.setFacilityFoundDate("2000-02-03");
         signUpDTO.setFacilityHotline("0987654321");
         signUpDTO.setFacilityAddress("");
         signUpDTO.setBusinessLicenseNumber("12345678");
         // Define behaviour of repository
//         when(userRepository.existsByPhone(signUpDTO.getUserPhone())).thenReturn(false);
//         when(userRepository.save(any(User.class))).thenReturn(user);
         // Run service method
         ResponseEntity<?> responseEntity = authService.registerUser(signUpDTO);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Địa chỉ không được để trống", responseEntity.getBody());
     }

     @Test
     @DisplayName("RegisterUTCID08")
     void registerUTCID08() {
         // Set up Dto
         SignUpDTO signUpDTO = new SignUpDTO();
         signUpDTO.setUsername("BùiThanhT");
         signUpDTO.setUserDob("2000-02-03");
         signUpDTO.setUserPhone("0987654321");
         signUpDTO.setUserEmail("tungduong71@gmail.com"	);
         signUpDTO.setUserAddress("So 27 duong Truong Trinh");
         signUpDTO.setUserPassword("@User123");
         signUpDTO.setFacilityName("BùiThanhT");
         signUpDTO.setFacilityFoundDate("2000-02-03");
         signUpDTO.setFacilityHotline("0987654321");
         signUpDTO.setFacilityAddress("So 27 duong Truong Trinh");
         signUpDTO.setBusinessLicenseNumber("");
         // Define behaviour of repository
         when(userRepository.existsByPhone(signUpDTO.getUserPhone())).thenReturn(false);
         when(userRepository.save(any(User.class))).thenReturn(user);
         // Run service method
         ResponseEntity<?> responseEntity = authService.registerUser(signUpDTO);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Số đăng kí kinh doanh không được để trống", responseEntity.getBody());
     }

     @Test
     @DisplayName("RegisterUTCID09")
     void registerUTCID09() {
         // Set up Dto
         SignUpDTO signUpDTO = new SignUpDTO();
         signUpDTO.setUsername("Lê Văn Đ");
         signUpDTO.setUserDob("2000-02-03");
         signUpDTO.setUserPhone("0987654321");
         signUpDTO.setUserEmail("12@3tungdt@gmail.com");
         signUpDTO.setUserAddress("So 27 duong Truong Trinh");
         signUpDTO.setUserPassword("@User123");
         signUpDTO.setFacilityName("Lê Văn Đ");
         signUpDTO.setFacilityFoundDate("2000-02-03");
         signUpDTO.setFacilityHotline("0987654321");
         signUpDTO.setFacilityAddress("So 27 duong Truong Trinh");
         signUpDTO.setBusinessLicenseNumber("12345678");
         // Define behaviour of repository
         //when(userRepository.existsByPhone(signUpDTO.getUserPhone())).thenReturn(false);
         //when(userRepository.save(any(User.class))).thenReturn(user);
         // Run service method
         ResponseEntity<?> responseEntity = authService.registerUser(signUpDTO);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Email không đúng định dạng", responseEntity.getBody());
     }

     @Test
     @DisplayName("RegisterUTCID10")
     void registerUTCID10() {
         // Set up Dto
         SignUpDTO signUpDTO = new SignUpDTO();
         signUpDTO.setUsername("");
         signUpDTO.setUserDob("2000-02-03");
         signUpDTO.setUserPhone("0987654321");
         signUpDTO.setUserEmail("tungduong71@gmail.com"	);
         signUpDTO.setUserAddress("So 27 duong Truong Trinh");
         signUpDTO.setUserPassword("@User123");
         signUpDTO.setFacilityName("");
         signUpDTO.setFacilityFoundDate("2000-02-03");
         signUpDTO.setFacilityHotline("0987654321");
         signUpDTO.setFacilityAddress("So 27 duong Truong Trinh");
         signUpDTO.setBusinessLicenseNumber("12345678");
         // Define behaviour of repository
         //when(userRepository.existsByPhone(signUpDTO.getUserPhone())).thenReturn(false);
         //when(userRepository.save(any(User.class))).thenReturn(user);
         // Run service method
         ResponseEntity<?> responseEntity = authService.registerUser(signUpDTO);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Tên không được để trống", responseEntity.getBody());
     }

     @Test
     @DisplayName("RegisterUTCID11")
     void registerUTCID11() {
         // Set up Dto
         SignUpDTO signUpDTO = new SignUpDTO();
         signUpDTO.setUsername("BùiThanhT");
         signUpDTO.setUserDob(null);
         signUpDTO.setUserPhone("0987654321");
         signUpDTO.setUserEmail("tungduong71@gmail.com"	);
         signUpDTO.setUserAddress("So 27 duong Truong Trinh");
         signUpDTO.setUserPassword("@User123");
         signUpDTO.setFacilityName("BùiThanhT");
         signUpDTO.setFacilityFoundDate(null);
         signUpDTO.setFacilityHotline("0987654321");
         signUpDTO.setFacilityAddress("So 27 duong Truong Trinh");
         signUpDTO.setBusinessLicenseNumber("12345678");
         // Define behaviour of repository
         //when(userRepository.existsByPhone(signUpDTO.getUserPhone())).thenReturn(false);
         //when(userRepository.save(any(User.class))).thenReturn(user);
         // Run service method
         ResponseEntity<?> responseEntity = authService.registerUser(signUpDTO);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Ngày sinh không được để trống", responseEntity.getBody());
     }

     @Test
     @DisplayName("RegisterUTCID12")
     void registerUTCID12() {
         // Set up Dto
         SignUpDTO signUpDTO = new SignUpDTO();
         signUpDTO.setUsername("BùiThanhT");
         signUpDTO.setUserDob("2000-02-03");
         signUpDTO.setUserPhone("098765432");
         signUpDTO.setUserEmail("tungduong71@gmail.com"	);
         signUpDTO.setUserAddress("So 27 duong Truong Trinh");
         signUpDTO.setUserPassword("@User123");
         signUpDTO.setFacilityName("BùiThanhT");
         signUpDTO.setFacilityFoundDate("2000-02-03");
         signUpDTO.setFacilityHotline("098765432");
         signUpDTO.setFacilityAddress("So 27 duong Truong Trinh");
         signUpDTO.setBusinessLicenseNumber("12345678");
         // Define behaviour of repository
         //when(userRepository.existsByPhone(signUpDTO.getUserPhone())).thenReturn(false);
         //when(userRepository.save(any(User.class))).thenReturn(user);
         // Run service method
         ResponseEntity<?> responseEntity = authService.registerUser(signUpDTO);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Số điện thoại không đúng đinh dạng", responseEntity.getBody());
     }

     @Test
     @DisplayName("RegisterUTCID13")
     void registerUTCID13() {
         // Set up Dto
         SignUpDTO signUpDTO = new SignUpDTO();
         signUpDTO.setUsername("Lê Văn Đ");
         signUpDTO.setUserDob("2000-02-03");
         signUpDTO.setUserPhone("");
         signUpDTO.setUserEmail("tungduong71@gmail.com"	);
         signUpDTO.setUserAddress("So 27 duong Truong Trinh");
         signUpDTO.setUserPassword("@User123");
         signUpDTO.setFacilityName("Lê Văn Đ");
         signUpDTO.setFacilityFoundDate("2000-02-03");
         signUpDTO.setFacilityHotline("");
         signUpDTO.setFacilityAddress("So 27 duong Truong Trinh");
         signUpDTO.setBusinessLicenseNumber("12345678");
         // Define behaviour of repository
         //when(userRepository.existsByPhone(signUpDTO.getUserPhone())).thenReturn(false);
         //when(userRepository.save(any(User.class))).thenReturn(user);
         // Run service method
         ResponseEntity<?> responseEntity = authService.registerUser(signUpDTO);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Số điện thoại không được để trống", responseEntity.getBody());
     }

     @Test
     @DisplayName("RegisterUTCID14")
     void registerUTCID14() {
         // Set up Dto
         SignUpDTO signUpDTO = new SignUpDTO();
         signUpDTO.setUsername("Lê Văn Đ");
         signUpDTO.setUserDob("2000-02-03");
         signUpDTO.setUserPhone("0987654321");
         signUpDTO.setUserEmail(null);
         signUpDTO.setUserAddress("Hà Nội, Việt Nam");
         signUpDTO.setUserPassword("@User123");
         signUpDTO.setFacilityName("Lê Văn Đ");
         signUpDTO.setFacilityFoundDate("2000-02-03");
         signUpDTO.setFacilityHotline("0987654321");
         signUpDTO.setFacilityAddress("Hà Nội, Việt Nam");
         signUpDTO.setBusinessLicenseNumber("12345678");
         // Define behaviour of repository
         //when(userRepository.existsByPhone(signUpDTO.getUserPhone())).thenReturn(false);
         //when(userRepository.save(any(User.class))).thenReturn(user);
         // Run service method
         ResponseEntity<?> responseEntity = authService.registerUser(signUpDTO);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Email không được để trống", responseEntity.getBody());
     }

     @Test
     @DisplayName("RegisterUTCID15")
     void registerUTCID15() {
         // Set up Dto
         SignUpDTO signUpDTO = new SignUpDTO();
         signUpDTO.setUsername("Lê Văn Đ");
         signUpDTO.setUserDob("2000-02-03");
         signUpDTO.setUserPhone("0987654321");
         signUpDTO.setUserEmail("tungduong71@gmail.com"	);
         signUpDTO.setUserAddress("Hà Nội, Việt Nam");
         signUpDTO.setUserPassword("123456789");
         signUpDTO.setFacilityName("Lê Văn Đ");
         signUpDTO.setFacilityFoundDate("2000-02-03");
         signUpDTO.setFacilityHotline("0987654321");
         signUpDTO.setFacilityAddress("Hà Nội, Việt Nam");
         signUpDTO.setBusinessLicenseNumber("12345678");
         // Define behaviour of repository
         //when(userRepository.existsByPhone(signUpDTO.getUserPhone())).thenReturn(false);
         //when(userRepository.save(any(User.class))).thenReturn(user);
         // Run service method
         ResponseEntity<?> responseEntity = authService.registerUser(signUpDTO);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Mật khẩu không đúng định dạng", responseEntity.getBody());
     }

     @Test
     @DisplayName("RegisterUTCID16")
     void registerUTCID16() {
         // Set up Dto
         SignUpDTO signUpDTO = new SignUpDTO();
         signUpDTO.setUsername("BùiThanhT");
         signUpDTO.setUserDob("2000-02-03");
         signUpDTO.setUserPhone("0987654321");
         signUpDTO.setUserEmail("tungduong71@gmail.com"	);
         signUpDTO.setUserAddress("So 27 duong Truong Trinh");
         signUpDTO.setUserPassword("");
         signUpDTO.setFacilityName("BùiThanhT");
         signUpDTO.setFacilityFoundDate("2000-02-03");
         signUpDTO.setFacilityHotline("0987654321");
         signUpDTO.setFacilityAddress("So 27 duong Truong Trinh");
         signUpDTO.setBusinessLicenseNumber("12345678");
         // Define behaviour of repository
         //when(userRepository.existsByPhone(signUpDTO.getUserPhone())).thenReturn(false);
         //when(userRepository.save(any(User.class))).thenReturn(user);
         // Run service method
         ResponseEntity<?> responseEntity = authService.registerUser(signUpDTO);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Mật khẩu không được để trống", responseEntity.getBody());
     }

     @Test
     @DisplayName("RegisterUTCID01")
     void registerUTCID01() {
         // Set up Dto
         SignUpDTO signUpDTO = new SignUpDTO();
         signUpDTO.setUsername("Lê Văn Đ");
         signUpDTO.setUserDob("2000-02-03");
         signUpDTO.setUserPhone("0987654321");
         signUpDTO.setUserEmail("tungduong71@gmail.com"	);
         signUpDTO.setUserAddress("Hà Nội, Việt Nam");
         signUpDTO.setUserPassword("@User123");
         signUpDTO.setFacilityName("BùiThanhT");
         signUpDTO.setFacilityFoundDate("2000-02-03");
         signUpDTO.setFacilityHotline("0987654321");
         signUpDTO.setFacilityAddress("Hà Nội, Việt Nam");
         signUpDTO.setBusinessLicenseNumber("12345678");
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
     @DisplayName("authenticateUserUTCID01")
     void authenticateUserUTC01() {
         // Set up
         LoginDTO loginDTO = new LoginDTO("0987654321", "@User123");
         List<Role> role = new ArrayList<>();
         role.add(new Role(2, "ROLE_OWNER", true));
         user.setRoles(role);

         UserDetailsImpl userDetails = new UserDetailsImpl();
         userDetails.setUser(user);
         Authentication auth = new Authentication() {
             @Override
             public Collection<? extends GrantedAuthority> getAuthorities() {
                 return null;
             }

             @Override
             public Object getCredentials() {
                 return null;
             }

             @Override
             public Object getDetails() {
                 return null;
             }

             @Override
             public Object getPrincipal() {
                 return null;
             }

             @Override
             public boolean isAuthenticated() {
                 return false;
             }

             @Override
             public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

             }

             @Override
             public String getName() {
                 return null;
             }};
         // Define behaviour of repository
         when(userRepository.findByPhone(user.getPhone())).thenReturn(Optional.of(user));
         when(facilityRepository.findByUserId(user.getUserId())).thenReturn(Optional.of(facility));
         when(registrationRepository.findByUserId(user.getUserId())).thenReturn(Optional.of(registration));
         when(passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())).thenReturn(true);
         when(authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getPhone(), loginDTO.getPassword()))).thenReturn(auth);
         when(httpServletRequest.getSession(true)).thenReturn(new HttpSession() {
             @Override
             public long getCreationTime() {
                 return 0;
             }

             @Override
             public String getId() {
                 return null;
             }

             @Override
             public long getLastAccessedTime() {
                 return 0;
             }

             @Override
             public ServletContext getServletContext() {
                 return null;
             }

             @Override
             public void setMaxInactiveInterval(int i) {

             }

             @Override
             public int getMaxInactiveInterval() {
                 return 0;
             }

             @Override
             public Object getAttribute(String s) {
                 return null;
             }

             @Override
             public Enumeration<String> getAttributeNames() {
                 return null;
             }

             @Override
             public void setAttribute(String s, Object o) {

             }

             @Override
             public void removeAttribute(String s) {

             }

             @Override
             public void invalidate() {

             }

             @Override
             public boolean isNew() {
                 return false;
             }
         });
         when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(auth);
         when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);
         // Run service method
         ResponseEntity<?> responseEntity = authService.authenticateUser(httpServletRequest,httpServletResponse, loginDTO);
         System.out.println(responseEntity.toString());
         SessionDTO sessionDTO = (SessionDTO) responseEntity.getBody();
         // Assert
         assertEquals(user.getUserId(), sessionDTO.getUserId());
         //assertEquals(sessionDTO.getUserId(), 3L);
     }

     @Test
     @DisplayName("authenticateUserUTCID02")
     void authenticateUserUTC02() {
         // Set up
         LoginDTO loginDTO = new LoginDTO("", "@User123");

         // Run service method
         ResponseEntity<?> responseEntity = authService.authenticateUser(httpServletRequest,httpServletResponse, loginDTO);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Số điện thoại không được để trống", responseEntity.getBody());
         //assertEquals(sessionDTO.getUserId(), 3L);
     }

     @Test
     @DisplayName("authenticateUserUTCID03")
     void authenticateUserUTC03() {
         // Set up
         LoginDTO loginDTO = new LoginDTO(null, "@User123");

         // Run service method
         ResponseEntity<?> responseEntity = authService.authenticateUser(httpServletRequest,httpServletResponse, loginDTO);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Số điện thoại không được để trống", responseEntity.getBody());
         //assertEquals(sessionDTO.getUserId(), 3L);
     }

     @Test
     @DisplayName("authenticateUserUTCID04")
     void authenticateUserUTC04() {
         // Set up
         LoginDTO loginDTO = new LoginDTO("0987654321", "eimslogin");
         List<Role> role = new ArrayList<>();
         role.add(new Role(2, "ROLE_OWNER", true));
         user.setRoles(role);
         // Define behaviour of repository
         when(userRepository.findByPhone(user.getPhone())).thenReturn(Optional.of(user));
         //when(facilityRepository.findByUserId(user.getUserId())).thenReturn(Optional.of(facility));
         when(registrationRepository.findByUserId(user.getUserId())).thenReturn(Optional.of(registration));

         // Run service method
         ResponseEntity<?> responseEntity = authService.authenticateUser(httpServletRequest,httpServletResponse, loginDTO);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Mật khẩu hoặc tài khoản sai",responseEntity.getBody());
         //assertEquals(sessionDTO.getUserId(), 3L);
     }

     @Test
     @DisplayName("authenticateUserUTCID05")
     void authenticateUserUTC05() {
         // Set up
         LoginDTO loginDTO = new LoginDTO("0987654321", "");

         // Run service method
         ResponseEntity<?> responseEntity = authService.authenticateUser(httpServletRequest,httpServletResponse, loginDTO);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Mật khẩu không được để trống", responseEntity.getBody());
         //assertEquals(sessionDTO.getUserId(), 3L);
     }

     @Test
     @DisplayName("authenticateUserUTCID06")
     void authenticateUserUTC06() {
         // Set up
         LoginDTO loginDTO = new LoginDTO("0987654321", null);

         // Run service method
         ResponseEntity<?> responseEntity = authService.authenticateUser(httpServletRequest,httpServletResponse, loginDTO);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Mật khẩu không được để trống", responseEntity.getBody());
         //assertEquals(sessionDTO.getUserId(), 3L);
     }
     @Test
     @DisplayName("changePasswordUTCID01")
     void changePasswordUTCID01() {
         // Set up Dto
         ChangePasswordDTO dto = new ChangePasswordDTO();
         dto.setUserId(user.getUserId());
         dto.setPassword("@User123");
         dto.setNewPassword("User@111");
         // Define behaviour of repository
         when(userRepository.findByUserId(user.getUserId())).thenReturn(Optional.of(user));
         when(passwordEncoder.matches(dto.getPassword(), user.getPassword()))
                 .thenReturn((dto.getPassword().equals(user.getPassword())));
         when(passwordEncoder.matches(dto.getPassword(),user.getPassword())).thenReturn(true);
         // Run service method
         ResponseEntity<?> responseEntity = authService.changePassword(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Thay đổi mật khẩu thành công", responseEntity.getBody());
     }

     @Test
     @DisplayName("changePasswordUTCID02")
     void changePasswordUTCID02() {
         // Set up Dto
         ChangePasswordDTO dto = new ChangePasswordDTO();
         dto.setUserId(user.getUserId());
         dto.setPassword("eimslogin");
         dto.setNewPassword("User@111");
         // Define behaviour of repository
         when(userRepository.findByUserId(user.getUserId())).thenReturn(Optional.of(user));
         when(passwordEncoder.matches(dto.getPassword(), user.getPassword()))
                 .thenReturn((dto.getPassword().equals(user.getPassword())));
         // Run service method
         ResponseEntity<?> responseEntity = authService.changePassword(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Mật khẩu cũ sai", responseEntity.getBody());
     }

     @Test
     @DisplayName("changePasswordUTCID03")
     void changePasswordUTCID03() {
         // Set up Dto
         ChangePasswordDTO dto = new ChangePasswordDTO();
         dto.setUserId(user.getUserId());
         dto.setPassword("");
         dto.setNewPassword("User@111");
         // Run service method
         ResponseEntity<?> responseEntity = authService.changePassword(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Mật khẩu không được để trống", responseEntity.getBody());
     }

     @Test
     @DisplayName("changePasswordUTCID04")
     void changePasswordUTCID04() {
         // Set up Dto
         ChangePasswordDTO dto = new ChangePasswordDTO();
         dto.setUserId(user.getUserId());
         dto.setPassword(null);
         dto.setNewPassword("User@111");
         // Run service method
         ResponseEntity<?> responseEntity = authService.changePassword(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Mật khẩu không được để trống", responseEntity.getBody());
     }

     @Test
     @DisplayName("changePasswordUTCID05")
     void changePasswordUTCID05() {
         // Set up Dto
         ChangePasswordDTO dto = new ChangePasswordDTO();
         dto.setUserId(user.getUserId());
         dto.setPassword("@User123");
         dto.setNewPassword("12456789");
         // Run service method
         ResponseEntity<?> responseEntity = authService.changePassword(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Mật khẩu mới không đúng định dạng", responseEntity.getBody());
     }

     @Test
     @DisplayName("changePasswordUTCID06")
     void changePasswordUTCID06() {
         // Set up Dto
         ChangePasswordDTO dto = new ChangePasswordDTO();
         dto.setUserId(user.getUserId());
         dto.setPassword("@User123");
         dto.setNewPassword("eimslogin");
         // Run service method
         ResponseEntity<?> responseEntity = authService.changePassword(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Mật khẩu mới không đúng định dạng", responseEntity.getBody());
     }

     @Test
     @DisplayName("changePasswordUTCID07")
     void changePasswordUTCID07() {
         // Set up Dto
         ChangePasswordDTO dto = new ChangePasswordDTO();
         dto.setUserId(user.getUserId());
         dto.setPassword("@User123");
         dto.setNewPassword("");
         // Run service method
         ResponseEntity<?> responseEntity = authService.changePassword(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Mật khẩu mới không được để trống", responseEntity.getBody());
     }

     @Test
     @DisplayName("changePasswordUTCID08")
     void changePasswordUTCID08() {
         // Set up Dto
         ChangePasswordDTO dto = new ChangePasswordDTO();
         dto.setUserId(user.getUserId());
         dto.setPassword("@User123");
         dto.setNewPassword(null);
         // Run service method
         ResponseEntity<?> responseEntity = authService.changePassword(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Mật khẩu mới không được để trống", responseEntity.getBody());
     }

     @Test
     void sendOTP() throws IOException{
         // Set up
         String phone = "0987654321";
         // Define behaviour of repository
         when(userRepository.findByPhone(phone)).thenReturn(Optional.of(user));

         // Run service method
         ResponseEntity<?> responseEntity = authService.sendOTPResetPass(phone);
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
         Otp otp = new Otp();
         otp.setOtp("123");
         // Define behaviour of repository
         when(otpRepository.findByPhoneNumber(phone)).thenReturn(Optional.of(otp));

         // Run service method
         ResponseEntity<?> responseEntity = authService.verifyOTPResetPass(verifyOtpDTO);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Mã OTP đã đúng", responseEntity.getBody());
     }

     @Test
     void resendOTP() throws IOException {
         // Set up
         String phone = "0987654321";
         Otp otp = new Otp();

         // Define behaviour of repository
         when(otpRepository.findByPhoneNumber(phone)).thenReturn(Optional.of(otp));

         // Run service method
         ResponseEntity<?> responseEntity = authService.resendOTPResetPass(phone);
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
         // Run service method
         ResponseEntity<?> responseEntity = authService.resetPassword(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Mật khẩu thay đổi thành công", responseEntity.getBody());
     }
 }
