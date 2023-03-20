/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 10/03/2023   1.0         DuongVV     First Deploy<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.customer.CreateCustomerDTO;
import com.example.eims.dto.customer.UpdateCustomerDTO;
import com.example.eims.entity.Customer;
import com.example.eims.entity.User;
import com.example.eims.repository.CustomerRepository;
import com.example.eims.repository.UserRepository;
import jdk.jfr.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    Page customerPage;
    @InjectMocks
    CustomerServiceImpl customerService;

    @Test
    @DisplayName("getAllCustomerUTCID01")
    void getAllCustomerUTC01() {
        // Set up
        Long userId = 1L;
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer1);
        customerList.add(customer2);
        Optional<List<Customer>> returnForUserId1 = Optional.empty();
        // Define behaviour of repository
        when(customerRepository.findByUserId(1L)).thenReturn(returnForUserId1);
        // Run service method
        ResponseEntity<?> responseEntity = customerService.getAllCustomer(userId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    @DisplayName("getAllCustomerUTCID02")
    void getAllCustomerUTC02() {
        // Set up
        Long userId = 15L;
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer1);
        customerList.add(customer2);
        Optional<List<Customer>> returnForUserId15 = Optional.empty();
        // Define behaviour of repository
        when(customerRepository.findByUserId(15L)).thenReturn(returnForUserId15);
        // Run service method
        ResponseEntity<?> responseEntity = customerService.getAllCustomer(userId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    @DisplayName("getAllCustomerUTCID03")
    void getAllCustomerUTC03() {
        // Set up
        Long userId = 2L;
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer1);
        customerList.add(customer2);
        Optional<List<Customer>> returnForUserId2 = Optional.of(customerList);
        // Define behaviour of repository
        when(customerRepository.findByUserId(2L)).thenReturn(returnForUserId2);
        // Run service method
        ResponseEntity<?> responseEntity = customerService.getAllCustomer(userId);
        System.out.println(responseEntity.toString());
        // Assert
        assertNotEquals(0,((List<Customer>) responseEntity.getBody()).size());
    }

    @Test
    @DisplayName("getCustomerUTCID01")
    void getCustomerUTCID01() {
        // Set up
        Long customerId = 0L;
        // Define behaviour of repository
        Optional<Customer> repositoryReturn = Optional.empty();
        when(customerRepository.findByCustomerId(0L)).thenReturn(repositoryReturn);

        // Run service method
        ResponseEntity<?> responseEntity = customerService.getCustomer(customerId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    @DisplayName("getCustomerUTCID02")
    void getCustomerUTCID02() {
        // Set up
        Long customerId = 1L;
        Customer customer = new Customer();
        // Define behaviour of repository
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));

        // Run service method
        ResponseEntity<?> responseEntity = customerService.getCustomer(customerId);
        System.out.println(responseEntity.toString());
        // Assert
        assertNotEquals(null, responseEntity.getBody());
    }

    @Test
    @DisplayName("createCustomerUTCID01")
    void createCustomerUTCID01() {
        // Set up
        CreateCustomerDTO dto = new CreateCustomerDTO();
        User user = new User();
        user.setUserId(1L);
        user.setStatus(1);
        dto.setUserId(1L);

        dto.setCustomerName("Nguyen Van C");
        dto.setCustomerPhone("0987654321");
        dto.setCustomerAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setCustomerMail("duonghoang01@gmail.com");
        // Define behaviour of repository
        when(userRepository.getStatusByUserId(user.getUserId())).thenReturn(user.getStatus() == 1);
        when(customerRepository.existsByCustomerPhoneAndUserId(dto.getCustomerPhone(), user.getUserId()))
                .thenReturn(false);

        // Run service method
        ResponseEntity<?> responseEntity = customerService.createCustomer(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Thêm khách hàng mới thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("createCustomerUTCID02")
    void createCustomerUTCID02() {
        // Set up
        CreateCustomerDTO dto = new CreateCustomerDTO();
        User user = new User();
        user.setUserId(1L);
        user.setStatus(1);
        dto.setUserId(1L);

        dto.setCustomerName("Nguyen Van C");
        dto.setCustomerPhone("abcdefgh");
        dto.setCustomerAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setCustomerMail("duonghoang01@gmail.com");
        // Define behaviour of repository
        when(userRepository.getStatusByUserId(user.getUserId())).thenReturn(user.getStatus() == 1);
        //when(customerRepository.existsByCustomerPhoneAndUserId(dto.getCustomerPhone(), user.getUserId())).thenReturn(false);

        // Run service method
        ResponseEntity<?> responseEntity = customerService.createCustomer(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Số điện thoại không hợp lệ", responseEntity.getBody());
    }

    @Test
    @DisplayName("createCustomerUTCID03")
    void createCustomerUTCID03() {
        // Set up
        CreateCustomerDTO dto = new CreateCustomerDTO();
        User user = new User();
        user.setUserId(1L);
        user.setStatus(1);
        dto.setUserId(1L);

        dto.setCustomerName("Nguyen Van C");
        dto.setCustomerPhone("0987654321");
        dto.setCustomerAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setCustomerMail("12@3tungdt@gmail.com");
        // Define behaviour of repository
        when(userRepository.getStatusByUserId(user.getUserId())).thenReturn(user.getStatus() == 1);
        //when(customerRepository.existsByCustomerPhoneAndUserId(dto.getCustomerPhone(), user.getUserId())).thenReturn(false);

        // Run service method
        ResponseEntity<?> responseEntity = customerService.createCustomer(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Email không hợp lệ", responseEntity.getBody());
    }

    @Test
    @DisplayName("createCustomerUTCID04")
    void createCustomerUTCID04() {
        // Set up
        CreateCustomerDTO dto = new CreateCustomerDTO();
        User user = new User();
        user.setUserId(1L);
        user.setStatus(1);
        dto.setUserId(1L);

        dto.setCustomerName("Nguyen Van C");
        dto.setCustomerPhone("");
        dto.setCustomerAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setCustomerMail("duonghoang01@gmail.com");
        // Define behaviour of repository
        when(userRepository.getStatusByUserId(user.getUserId())).thenReturn(user.getStatus() == 1);
        //when(customerRepository.existsByCustomerPhoneAndUserId(dto.getCustomerPhone(), user.getUserId())).thenReturn(false);

        // Run service method
        ResponseEntity<?> responseEntity = customerService.createCustomer(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Số điện thoại không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("createCustomerUTCID05")
    void createCustomerUTCID05() {
        // Set up
        CreateCustomerDTO dto = new CreateCustomerDTO();
        User user = new User();
        user.setUserId(1L);
        user.setStatus(1);
        dto.setUserId(1L);

        dto.setCustomerName(null);
        dto.setCustomerPhone("0987654321");
        dto.setCustomerAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setCustomerMail("duonghoang01@gmail.com");
        // Define behaviour of repository
        when(userRepository.getStatusByUserId(user.getUserId())).thenReturn(user.getStatus() == 1);
        //when(customerRepository.existsByCustomerPhoneAndUserId(dto.getCustomerPhone(), user.getUserId())).thenReturn(false);

        // Run service method
        ResponseEntity<?> responseEntity = customerService.createCustomer(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("createCustomerUTCID06")
    void createCustomerUTCID06() {
        // Set up
        CreateCustomerDTO dto = new CreateCustomerDTO();
        User user = new User();
        user.setUserId(1L);
        user.setStatus(1);
        dto.setUserId(1L);

        dto.setCustomerName("Nguyen Van C");
        dto.setCustomerPhone("0987654321");
        dto.setCustomerAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setCustomerMail("a_b_c@gmail.com");
        // Define behaviour of repository
        when(userRepository.getStatusByUserId(user.getUserId())).thenReturn(user.getStatus() == 1);
        //when(customerRepository.existsByCustomerPhoneAndUserId(dto.getCustomerPhone(), user.getUserId())).thenReturn(false);

        // Run service method
        ResponseEntity<?> responseEntity = customerService.createCustomer(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Email không hợp lệ", responseEntity.getBody());
    }

    @Test
    @DisplayName("createCustomerUTCID07")
    void createCustomerUTCID07() {
        // Set up
        CreateCustomerDTO dto = new CreateCustomerDTO();
        User user = new User();
        user.setUserId(1L);
        user.setStatus(1);
        dto.setUserId(1L);

        dto.setCustomerName("Nguyen Van C");
        dto.setCustomerPhone("098765432");
        dto.setCustomerAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setCustomerMail("duonghoang01@gmail.com");
        // Define behaviour of repository
        when(userRepository.getStatusByUserId(user.getUserId())).thenReturn(user.getStatus() == 1);
        //when(customerRepository.existsByCustomerPhoneAndUserId(dto.getCustomerPhone(), user.getUserId())).thenReturn(false);

        // Run service method
        ResponseEntity<?> responseEntity = customerService.createCustomer(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Số điện thoại không hợp lệ", responseEntity.getBody());
    }

    @Test
    @DisplayName("createCustomerUTCID08")
    void createCustomerUTCID08() {
        // Set up
        CreateCustomerDTO dto = new CreateCustomerDTO();
        User user = new User();
        user.setUserId(1L);
        user.setStatus(1);
        dto.setUserId(1L);

        dto.setCustomerName("Nguyen Van C");
        dto.setCustomerPhone("0987654321");
        dto.setCustomerAddress(null);
        dto.setCustomerMail("duonghoang01@gmail.com");
        // Define behaviour of repository
        when(userRepository.getStatusByUserId(user.getUserId())).thenReturn(user.getStatus() == 1);
        //when(customerRepository.existsByCustomerPhoneAndUserId(dto.getCustomerPhone(), user.getUserId())).thenReturn(false);

        // Run service method
        ResponseEntity<?> responseEntity = customerService.createCustomer(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Địa chỉ không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("createCustomerUTCID09")
    void createCustomerUTCID09() {
        // Set up
        CreateCustomerDTO dto = new CreateCustomerDTO();
        User user = new User();
        user.setUserId(1L);
        user.setStatus(1);
        dto.setUserId(1L);

        dto.setCustomerName("Nguyen Van C");
        dto.setCustomerPhone("0987654321");
        dto.setCustomerAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"So 27@ duong Truong Trinh\"}");
        dto.setCustomerMail("duonghoang01@gmail.com");
        // Define behaviour of repository
        when(userRepository.getStatusByUserId(user.getUserId())).thenReturn(user.getStatus() == 1);
        //when(customerRepository.existsByCustomerPhoneAndUserId(dto.getCustomerPhone(), user.getUserId())).thenReturn(false);

        // Run service method
        ResponseEntity<?> responseEntity = customerService.createCustomer(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Số nhà sai định dạng", responseEntity.getBody());
    }

    @Test
    void createCustomer() {
        // Set up
        CreateCustomerDTO dto = new CreateCustomerDTO();
        User user = new User();
        user.setUserId(1L);
        user.setStatus(1);
        dto.setUserId(1L);
        dto.setCustomerName("name");
        dto.setCustomerPhone("0987654321");
        dto.setCustomerMail("a@a.com");
        dto.setCustomerAddress("address");
        // Define behaviour of repository
        when(userRepository.getStatusByUserId(user.getUserId())).thenReturn(user.getStatus() == 1);
        when(customerRepository.existsByCustomerPhoneAndUserId(dto.getCustomerPhone(), user.getUserId()))
                .thenReturn(false);

        // Run service method
        ResponseEntity<?> responseEntity = customerService.createCustomer(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Thêm khách hàng mới thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("showFormUpdateUTCID01")
    void showFormUpdateUTCID01() {
        // Set up
        Long customerId = 0L;
        Customer customer = new Customer();
        Optional<Customer> optionalCustomer = Optional.empty();
        // Define behaviour of repository
        when(customerRepository.findByCustomerId(0L)).thenReturn(optionalCustomer);

        // Run service method
        ResponseEntity<?> responseEntity = customerService.showFormUpdate(customerId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    @DisplayName("showFormUpdateUTCID02")
    void showFormUpdateUTCID02() {
        // Set up
        Long customerId = 1L;
        Customer customer = new Customer();
        // Define behaviour of repository
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));

        // Run service method
        ResponseEntity<?> responseEntity = customerService.showFormUpdate(customerId);
        System.out.println(responseEntity.toString());
        // Assert
        assertNotEquals(null, responseEntity.getBody());
    }

    @Test
    void showFormUpdate() {
        // Set up
        Customer customer = new Customer();
        // Define behaviour of repository
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));

        // Run service method
        ResponseEntity<?> responseEntity = customerService.showFormUpdate(1L);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(customer, responseEntity.getBody());
    }

    @Test
    @DisplayName("updateCustomerUTCID01")
    void updateCustomerUTCID01() {
        // Set up
        UpdateCustomerDTO dto = new UpdateCustomerDTO();
        Customer customer = new Customer();
        dto.setUserId(1L);
        dto.setCustomerId(1L);
        dto.setCustomerName("Nguyen Van C");
        dto.setCustomerPhone("0932321198");
        dto.setCustomerAddress("address");
        dto.setCustomerMail("a@a.com");
        dto.setStatus(1);
        String oldPhone = "0987654320";
        // Define behaviour of repository
        when(customerRepository.findCustomerPhoneById(1L)).thenReturn(oldPhone);
        when(customerRepository.existsByCustomerPhoneAndUserId(dto.getCustomerPhone(), dto.getUserId()))
                .thenReturn(false);
        when(customerRepository.findByCustomerId(dto.getCustomerId())).thenReturn(Optional.of(customer));
        // Run service method
        ResponseEntity<?> responseEntity = customerService.updateCustomer(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cập nhật thông tin khách hàng thành công", responseEntity.getBody());
    }

    @Test
    void updateCustomer() {
        // Set up
        UpdateCustomerDTO dto = new UpdateCustomerDTO();
        Customer customer = new Customer();
        dto.setUserId(1L);
        dto.setCustomerId(1L);
        dto.setCustomerName("name");
        dto.setCustomerPhone("0987654321");
        dto.setCustomerAddress("address");
        dto.setCustomerMail("a@a.com");
        dto.setStatus(1);
        String oldPhone = "0987654320";
        // Define behaviour of repository
        when(customerRepository.findCustomerPhoneById(1L)).thenReturn(oldPhone);
        when(customerRepository.existsByCustomerPhoneAndUserId(dto.getCustomerPhone(), dto.getUserId()))
                .thenReturn(false);
        when(customerRepository.findByCustomerId(dto.getCustomerId())).thenReturn(Optional.of(customer));
        // Run service method
        ResponseEntity<?> responseEntity = customerService.updateCustomer(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cập nhật thông tin khách hàng thành công", responseEntity.getBody());
    }

    @Test
    void searchCustomer() {
        // Set up
        String key1 = "uye";
        String key2 = "0987";
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer1);
        customerList.add(customer2);
        // Define behaviour of repository
        when(customerRepository.searchByUsernameOrPhone(1L, key1)).thenReturn(Optional.of(customerList));
        // Run service method
        ResponseEntity<?> responseEntity = customerService.searchCustomer(1L, key1);
        List<Customer> resultList = (List<Customer>) responseEntity.getBody();
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(customerList, responseEntity.getBody());
    }

    @Test
    void getAllCustomerPaging() {
        // Set up
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer1);
        customerList.add(customer2);
        int page = 1;
        int size = 2;
        String sort = "ASC";
        Sort sortable = Sort.by("customerId").ascending();
        Pageable pageable = PageRequest.of(page, size, sortable);
        // Define behaviour of repository
        when(customerRepository.findAllByUserId(1L, pageable)).thenReturn(customerPage);
        // Run service method
        ResponseEntity<?> responseEntity = customerService.getAllCustomerPaging(1L, 1, 2, "ASC");
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(customerPage, responseEntity.getBody());
    }
}