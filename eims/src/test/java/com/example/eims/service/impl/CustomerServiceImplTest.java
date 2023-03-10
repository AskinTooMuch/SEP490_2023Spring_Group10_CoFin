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
    void getAllCustomer() {
        // Set up
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer1);
        customerList.add(customer2);
        // Define behaviour of repository
        when(customerRepository.findByUserId(1L)).thenReturn(Optional.of(customerList));

        // Run service method
        ResponseEntity<?> responseEntity = customerService.getAllCustomer(1L);
        System.out.println(responseEntity.toString());
        List<Customer> resultList = (List<Customer>) responseEntity.getBody();
        // Assert
        assertEquals(2, resultList.size());
    }

    @Test
    void getCustomer() {
        // Set up
        Customer customer = new Customer();
        // Define behaviour of repository
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));

        // Run service method
        ResponseEntity<?> responseEntity = customerService.getCustomer(1L);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(customer, responseEntity.getBody());
    }

    @Test
    void createCustomer() {
        // Set up
        CreateCustomerDTO dto = new CreateCustomerDTO();
        User user = new User();
        user.setUserId(1L);
        user.setStatus(1);
        dto.setUserId(1L);
        dto.setName("name");
        dto.setPhone("0987654321");
        dto.setMail("a@a.com");
        dto.setAddress("address");
        // Define behaviour of repository
        when(userRepository.getStatusByUserId(user.getUserId())).thenReturn(user.getStatus() == 1);
        when(customerRepository.existsByCustomerPhone(dto.getPhone())).thenReturn(false);

        // Run service method
        ResponseEntity<?> responseEntity = customerService.createCustomer(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Thêm khách hàng mới thành công", responseEntity.getBody());
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
    void updateCustomer() {
        // Set up
        UpdateCustomerDTO dto = new UpdateCustomerDTO();
        Customer customer = new Customer();
        dto.setCustomerId(1L);
        dto.setName("name");
        dto.setPhone("0987654321");
        dto.setAddress("address");
        dto.setMail("a@a.com");
        dto.setStatus(1);
        String oldPhone = "0987654320";
        // Define behaviour of repository
        when(customerRepository.findCustomerPhoneById(1L)).thenReturn(oldPhone);
        when(customerRepository.findByCustomerPhone(dto.getPhone())).thenReturn(Optional.empty());
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