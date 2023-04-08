/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 10/03/2023   1.0         DuongVV     First Deploy<br>
 * 24/03/2023   2.0         DuongNH     Add test case<br>
 * 05/02/2023   2.1         DuongNH     Update test<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.supplier.CreateSupplierDTO;
import com.example.eims.dto.supplier.SupplierDetailDTO;
import com.example.eims.dto.supplier.UpdateSupplierDTO;
import com.example.eims.entity.Customer;
import com.example.eims.entity.ImportReceipt;
import com.example.eims.entity.Supplier;
import com.example.eims.repository.ImportReceiptRepository;
import com.example.eims.repository.SupplierRepository;
import com.example.eims.repository.UserRepository;
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
class SupplierServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    SupplierRepository supplierRepository;
    @Mock
    ImportReceiptRepository importReceiptRepository;
    @Mock
    Page supplierPage;
    @InjectMocks
    SupplierServiceImpl supplierService;

    @Test
    @DisplayName("getAllSupplierUTCID01")
    void getAllSupplierUTCID01() {
        // Set up
        Long userId = 1L;
        Supplier supplier1 = new Supplier();
        Supplier supplier2 = new Supplier();
        List<Supplier> supplierList = new ArrayList<>();
        supplierList.add(supplier1);
        supplierList.add(supplier2);
        // Define behaviour of repository
        when(supplierRepository.findByUserId(1L)).thenReturn(Optional.of(supplierList));

        // Run service method
        ResponseEntity<?> responseEntity = supplierService.getAllSupplier(userId);
        System.out.println(responseEntity.toString());
        List<Supplier> resultList = (List<Supplier>) responseEntity.getBody();
        // Assert
        assertEquals(2, resultList.size());
    }

    @Test
    @DisplayName("getAllSupplierUTCID02")
    void getAllSupplierUTCID02() {
        // Set up
        Long userId = 15L;
        // Define behaviour of repository
        when(supplierRepository.findByUserId(15L)).thenReturn(Optional.empty());

        // Run service method
        ResponseEntity<?> responseEntity = supplierService.getAllSupplier(userId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Không tìm thấy nhà cung cấp", responseEntity.getBody());
    }

    @Test
    @DisplayName("getSupplierUTCID01")
    void getSupplierUTCID01() {
        // Set up
        Long supplierId = 1L;
        Supplier supplier = new Supplier();
        supplier.setSupplierId(supplierId);
        supplier.setSupplierName("Nguyễn Văn A");
        supplier.setSupplierPhone("0987654321");
        supplier.setSupplierMail("abc@gmail.com");
        supplier.setFacilityName("abc123");
        supplier.setSupplierAddress("Hải Dương, Việt Nam");
        supplier.setStatus(1);
        SupplierDetailDTO dto = new SupplierDetailDTO();
        dto.getFromEntity(supplier);
        // Define behaviour of repository
        when(supplierRepository.findBySupplierId(1L)).thenReturn(Optional.of(supplier));

        // Run service method
        ResponseEntity<?> responseEntity = supplierService.getSupplier(supplierId);
        System.out.println(responseEntity.toString());
        // Assert
        assertNotEquals(null, responseEntity.getBody());
    }

    @Test
    @DisplayName("getSupplierUTCID02")
    void getSupplierUTCID02() {
        // Set up
        Long supplierId = 0L;
        // Define behaviour of repository
        when(supplierRepository.findBySupplierId(0L)).thenReturn(Optional.empty());

        // Run service method
        ResponseEntity<?> responseEntity = supplierService.getSupplier(supplierId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    @DisplayName("createSupplierUTCID01")
    void createSupplierUTCID01() {
        // Set up
        Long userId = 1L;
        CreateSupplierDTO dto = new CreateSupplierDTO();
        dto.setUserId(userId);
        dto.setSupplierName("Nguyễn Văn A");
        dto.setFacilityName("Nguyễn Văn A");
        dto.setSupplierPhone("0987654321");
        dto.setSupplierAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}"	);
        dto.setSupplierMail("BNV71@gmail.com");

        // Define behaviour of repository
        when(userRepository.getStatusByUserId(1L)).thenReturn(2);
        when(supplierRepository.existsBySupplierPhoneAndUserId("0987654321", dto.getUserId()))
                .thenReturn(false);

        // Run service method
        ResponseEntity<?> responseEntity = supplierService.createSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Thêm nhà cung cấp thành công.", responseEntity.getBody());
    }

    @Test
    @DisplayName("createSupplierUTCID02")
    void createSupplierUTCID02() {
        // Set up
        Long userId = 1L;
        CreateSupplierDTO dto = new CreateSupplierDTO();
        dto.setUserId(userId);
        dto.setSupplierName("Nguyễn Văn A");
        dto.setFacilityName("ABC123");
        dto.setSupplierPhone("0987654321");
        dto.setSupplierAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}"	);
        dto.setSupplierMail("BNV71@gmail.com");

        // Define behaviour of repository
        when(userRepository.getStatusByUserId(1L)).thenReturn(2);
        when(supplierRepository.existsBySupplierPhoneAndUserId("0987654321", dto.getUserId()))
                .thenReturn(false);

        // Run service method
        ResponseEntity<?> responseEntity = supplierService.createSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Thêm nhà cung cấp thành công.", responseEntity.getBody());
    }

    @Test
    @DisplayName("createSupplierUTCID03")
    void createSupplierUTCID03() {
        // Set up
        Long userId = 1L;
        CreateSupplierDTO dto = new CreateSupplierDTO();
        dto.setUserId(userId);
        dto.setSupplierName("");
        dto.setFacilityName("ABC123");
        dto.setSupplierPhone("0987654321");
        dto.setSupplierAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}"	);
        dto.setSupplierMail("BNV71@gmail.com");

        // Define behaviour of repository
        when(userRepository.getStatusByUserId(1L)).thenReturn(2);

        // Run service method
        ResponseEntity<?> responseEntity = supplierService.createSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên nhà cung cấp không được để trống.", responseEntity.getBody());
    }

    @Test
    @DisplayName("createSupplierUTCID04")
    void createSupplierUTCID04() {
        // Set up
        Long userId = 1L;
        CreateSupplierDTO dto = new CreateSupplierDTO();
        dto.setUserId(userId);
        dto.setSupplierName(null);
        dto.setFacilityName("ABC123");
        dto.setSupplierPhone("0987654321");
        dto.setSupplierAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}"	);
        dto.setSupplierMail("BNV71@gmail.com");

        // Define behaviour of repository
        when(userRepository.getStatusByUserId(1L)).thenReturn(2);
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.createSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên nhà cung cấp không được để trống.", responseEntity.getBody());
    }

    @Test
    @DisplayName("createSupplierUTCID05")
    void createSupplierUTCID05() {
        // Set up
        Long userId = 1L;
        CreateSupplierDTO dto = new CreateSupplierDTO();
        dto.setUserId(userId);
        dto.setSupplierName("Nguyễn Văn A");
        dto.setFacilityName("");
        dto.setSupplierPhone("0987654321");
        dto.setSupplierAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}"	);
        dto.setSupplierMail("BNV71@gmail.com");

        // Define behaviour of repository
        when(userRepository.getStatusByUserId(1L)).thenReturn(2);
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.createSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên cơ sở không được để trống.", responseEntity.getBody());
    }

    @Test
    @DisplayName("createSupplierUTCID06")
    void createSupplierUTCID06() {
        // Set up
        Long userId = 1L;
        CreateSupplierDTO dto = new CreateSupplierDTO();
        dto.setUserId(userId);
        dto.setSupplierName("Nguyễn Văn A");
        dto.setFacilityName(null);
        dto.setSupplierPhone("0987654321");
        dto.setSupplierAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}"	);
        dto.setSupplierMail("BNV71@gmail.com");

        // Define behaviour of repository
        when(userRepository.getStatusByUserId(1L)).thenReturn(2);
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.createSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên cơ sở không được để trống.", responseEntity.getBody());
    }

    @Test
    @DisplayName("createSupplierUTCID07")
    void createSupplierUTCID07() {
        // Set up
        Long userId = 1L;
        CreateSupplierDTO dto = new CreateSupplierDTO();
        dto.setUserId(userId);
        dto.setSupplierName("Nguyễn Văn A");
        dto.setFacilityName("Nguyễn Văn A");
        dto.setSupplierPhone("0987654321");
        dto.setSupplierAddress("");
        dto.setSupplierMail("BNV71@gmail.com");

        // Define behaviour of repository
        when(userRepository.getStatusByUserId(1L)).thenReturn(2);
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.createSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Địa chỉ không được để trống.", responseEntity.getBody());
    }

    @Test
    @DisplayName("createSupplierUTCID08")
    void createSupplierUTCID08() {
        // Set up
        Long userId = 1L;
        CreateSupplierDTO dto = new CreateSupplierDTO();
        dto.setUserId(userId);
        dto.setSupplierName("Nguyễn Văn A");
        dto.setFacilityName("Nguyễn Văn A");
        dto.setSupplierPhone("0987654321");
        dto.setSupplierAddress(null);
        dto.setSupplierMail("BNV71@gmail.com");

        // Define behaviour of repository
        when(userRepository.getStatusByUserId(1L)).thenReturn(2);
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.createSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Địa chỉ không được để trống.", responseEntity.getBody());
    }

    @Test
    @DisplayName("createSupplierUTCID09")
    void createSupplierUTCID09() {
        // Set up
        Long userId = 1L;
        CreateSupplierDTO dto = new CreateSupplierDTO();
        dto.setUserId(userId);
        dto.setSupplierName("Nguyễn Văn A");
        dto.setFacilityName("Nguyễn Văn A");
        dto.setSupplierPhone("abcdefgh");
        dto.setSupplierAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}"	);
        dto.setSupplierMail("BNV71@gmail.com");

        // Define behaviour of repository
        when(userRepository.getStatusByUserId(1L)).thenReturn(2);
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.createSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Số điện thoại không hợp lệ.", responseEntity.getBody());
    }

    @Test
    @DisplayName("createSupplierUTCID10")
    void createSupplierUTCID10() {
        // Set up
        Long userId = 1L;
        CreateSupplierDTO dto = new CreateSupplierDTO();
        dto.setUserId(userId);
        dto.setSupplierName("Nguyễn Văn A");
        dto.setFacilityName("Nguyễn Văn A");
        dto.setSupplierPhone("098765432");
        dto.setSupplierAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}"	);
        dto.setSupplierMail("BNV71@gmail.com");

        // Define behaviour of repository
        when(userRepository.getStatusByUserId(1L)).thenReturn(2);
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.createSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Số điện thoại không hợp lệ.", responseEntity.getBody());
    }

    @Test
    @DisplayName("createSupplierUTCID11")
    void createSupplierUTCID11() {
        // Set up
        Long userId = 1L;
        CreateSupplierDTO dto = new CreateSupplierDTO();
        dto.setUserId(userId);
        dto.setSupplierName("Nguyễn Văn A");
        dto.setFacilityName("Nguyễn Văn A");
        dto.setSupplierPhone("");
        dto.setSupplierAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}"	);
        dto.setSupplierMail("BNV71@gmail.com");

        // Define behaviour of repository
        when(userRepository.getStatusByUserId(1L)).thenReturn(2);
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.createSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Số điện thoại không được để trống.", responseEntity.getBody());
    }

    @Test
    @DisplayName("createSupplierUTCID12")
    void createSupplierUTCID12() {
        // Set up
        Long userId = 1L;
        CreateSupplierDTO dto = new CreateSupplierDTO();
        dto.setUserId(userId);
        dto.setSupplierName("Nguyễn Văn A");
        dto.setFacilityName("Nguyễn Văn A");
        dto.setSupplierPhone(null);
        dto.setSupplierAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}"	);
        dto.setSupplierMail("BNV71@gmail.com");

        // Define behaviour of repository
        when(userRepository.getStatusByUserId(1L)).thenReturn(2);
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.createSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Số điện thoại không được để trống.", responseEntity.getBody());
    }

    @Test
    @DisplayName("createSupplierUTCID13")
    void createSupplierUTCID13() {
        // Set up
        Long userId = 1L;
        CreateSupplierDTO dto = new CreateSupplierDTO();
        dto.setUserId(userId);
        dto.setSupplierName("Nguyễn Văn A");
        dto.setFacilityName("Nguyễn Văn A");
        dto.setSupplierPhone("0987654321");
        dto.setSupplierAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}"	);
        dto.setSupplierMail("a_b_c@gmail.com");

        // Define behaviour of repository
        when(userRepository.getStatusByUserId(1L)).thenReturn(2);
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.createSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Email không hợp lệ.", responseEntity.getBody());
    }

    @Test
    @DisplayName("createSupplierUTCID14")
    void createSupplierUTCID14() {
        // Set up
        Long userId = 1L;
        CreateSupplierDTO dto = new CreateSupplierDTO();
        dto.setUserId(userId);
        dto.setSupplierName("Nguyễn Văn A");
        dto.setFacilityName("Nguyễn Văn A");
        dto.setSupplierPhone("0987654321");
        dto.setSupplierAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}"	);
        dto.setSupplierMail("12@3tungdt@gmail.com");

        // Define behaviour of repository
        when(userRepository.getStatusByUserId(1L)).thenReturn(2);
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.createSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Email không hợp lệ.", responseEntity.getBody());
    }

    @Test
    @DisplayName("showFormUpdateUTCID01")
    void showFormUpdateUTCID01() {
        // Set up
        Long supplierId = 1L;
        Supplier supplier = new Supplier();
        supplier.setSupplierId(supplierId);

        // Define behaviour of repository
        when(supplierRepository.findBySupplierId(1L)).thenReturn(Optional.of(supplier));

        // Run service method24/3
        ResponseEntity<?> responseEntity = supplierService.showFormUpdate(supplierId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(supplier, responseEntity.getBody());
    }

    @Test
    @DisplayName("showFormUpdateUTCID02")
    void showFormUpdateUTCID02() {
        // Set up
        Long supplierId = 0L;

        // Define behaviour of repository
        when(supplierRepository.findBySupplierId(0L)).thenReturn(Optional.empty());

        // Run service method
        ResponseEntity<?> responseEntity = supplierService.showFormUpdate(supplierId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(null, responseEntity.getBody());
    }


    @Test
    @DisplayName("updateSupplierUTCID01")
    void updateSupplierUTCID01() {
        // Set up
        Supplier supplier = new Supplier();
        supplier.setSupplierId(1L);
        UpdateSupplierDTO dto = new UpdateSupplierDTO();
        dto.setUserId(1L);
        dto.setSupplierId(1L);
        dto.setSupplierName("Nguyễn Văn A");
        dto.setFacilityName("Nguyễn Văn A");
        dto.setSupplierPhone("0987654321");
        dto.setSupplierAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}"	);
        dto.setSupplierMail("BNV71@gmail.com");
        dto.setStatus(1);
        String oldPhone = "0987654320";
        // Define behaviour of repository
        when(supplierRepository.findSupplierPhoneById(dto.getSupplierId())).thenReturn(oldPhone);
        when(supplierRepository.existsBySupplierPhoneAndUserId(dto.getSupplierPhone(), dto.getUserId()))
                .thenReturn(false);
        when(supplierRepository.findBySupplierId(1L)).thenReturn(Optional.of(supplier));
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.updateSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cập nhật thông tin nhà cung cấp thành công.", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateSupplierUTCID02")
    void updateSupplierUTCID02() {
        // Set up
        Supplier supplier = new Supplier();
        supplier.setSupplierId(1L);
        UpdateSupplierDTO dto = new UpdateSupplierDTO();
        dto.setUserId(1L);
        dto.setSupplierId(1L);
        dto.setSupplierName("Nguyễn Văn A");
        dto.setFacilityName("Nguyễn Văn A");
        dto.setSupplierPhone("0987654321");
        dto.setSupplierAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}"	);
        dto.setSupplierMail("BNV71@gmail.com");
        dto.setStatus(0);
        String oldPhone = "0987654320";
        // Define behaviour of repository
        when(supplierRepository.findSupplierPhoneById(dto.getSupplierId())).thenReturn(oldPhone);
        when(supplierRepository.existsBySupplierPhoneAndUserId(dto.getSupplierPhone(), dto.getUserId()))
                .thenReturn(false);
        when(supplierRepository.findBySupplierId(1L)).thenReturn(Optional.of(supplier));
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.updateSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cập nhật thông tin nhà cung cấp thành công.", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateSupplierUTCID03")
    void updateSupplierUTCID03() {
        // Set up
        Supplier supplier = new Supplier();
        supplier.setSupplierId(1L);
        UpdateSupplierDTO dto = new UpdateSupplierDTO();
        dto.setUserId(1L);
        dto.setSupplierId(1L);
        dto.setSupplierName("");
        dto.setFacilityName("Nguyễn Văn A");
        dto.setSupplierPhone("0987654321");
        dto.setSupplierAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}"	);
        dto.setSupplierMail("BNV71@gmail.com");
        dto.setStatus(0);
        String oldPhone = "0987654320";
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.updateSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên nhà cung cấp không được để trống.", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateSupplierUTCID04")
    void updateSupplierUTCID04() {
        // Set up
        Supplier supplier = new Supplier();
        supplier.setSupplierId(1L);
        UpdateSupplierDTO dto = new UpdateSupplierDTO();
        dto.setUserId(1L);
        dto.setSupplierId(1L);
        dto.setSupplierName(null);
        dto.setFacilityName("Nguyễn Văn A");
        dto.setSupplierPhone("0987654321");
        dto.setSupplierAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}"	);
        dto.setSupplierMail("BNV71@gmail.com");
        dto.setStatus(0);
        String oldPhone = "0987654320";
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.updateSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên nhà cung cấp không được để trống.", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateSupplierUTCID05")
    void updateSupplierUTCID05() {
        // Set up
        Supplier supplier = new Supplier();
        supplier.setSupplierId(1L);
        UpdateSupplierDTO dto = new UpdateSupplierDTO();
        dto.setUserId(1L);
        dto.setSupplierId(1L);
        dto.setSupplierName("Nguyễn Văn A");
        dto.setFacilityName("");
        dto.setSupplierPhone("0987654321");
        dto.setSupplierAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}"	);
        dto.setSupplierMail("BNV71@gmail.com");
        dto.setStatus(0);
        String oldPhone = "0987654320";
        // Define behaviour of repository
        when(supplierRepository.findSupplierPhoneById(dto.getSupplierId())).thenReturn(oldPhone);
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.updateSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên cơ sở không được để trống.", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateSupplierUTCID06")
    void updateSupplierUTCID06() {
        // Set up
        Supplier supplier = new Supplier();
        supplier.setSupplierId(1L);
        UpdateSupplierDTO dto = new UpdateSupplierDTO();
        dto.setUserId(1L);
        dto.setSupplierId(1L);
        dto.setSupplierName("Nguyễn Văn A");
        dto.setFacilityName(null);
        dto.setSupplierPhone("0987654321");
        dto.setSupplierAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}"	);
        dto.setSupplierMail("BNV71@gmail.com");
        dto.setStatus(0);
        String oldPhone = "0987654320";
        // Define behaviour of repository
        when(supplierRepository.findSupplierPhoneById(dto.getSupplierId())).thenReturn(oldPhone);
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.updateSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên cơ sở không được để trống.", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateSupplierUTCID07")
    void updateSupplierUTCID07() {
        // Set up
        Supplier supplier = new Supplier();
        supplier.setSupplierId(1L);
        UpdateSupplierDTO dto = new UpdateSupplierDTO();
        dto.setUserId(1L);
        dto.setSupplierId(1L);
        dto.setSupplierName("Nguyễn Văn A");
        dto.setFacilityName("Nguyễn Văn A");
        dto.setSupplierPhone("0987654321");
        dto.setSupplierAddress("");
        dto.setSupplierMail("BNV71@gmail.com");
        dto.setStatus(0);
        String oldPhone = "0987654320";
        // Define behaviour of repository
        when(supplierRepository.findSupplierPhoneById(dto.getSupplierId())).thenReturn(oldPhone);
        when(supplierRepository.existsBySupplierPhoneAndUserId(dto.getSupplierPhone(), dto.getUserId()))
                .thenReturn(false);
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.updateSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Địa chỉ không được để trống.", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateSupplierUTCID08")
    void updateSupplierUTCID08() {
        // Set up
        Supplier supplier = new Supplier();
        supplier.setSupplierId(1L);
        UpdateSupplierDTO dto = new UpdateSupplierDTO();
        dto.setUserId(1L);
        dto.setSupplierId(1L);
        dto.setSupplierName("Nguyễn Văn A");
        dto.setFacilityName("Nguyễn Văn A");
        dto.setSupplierPhone("0987654321");
        dto.setSupplierAddress(null);
        dto.setSupplierMail("BNV71@gmail.com");
        dto.setStatus(0);
        String oldPhone = "0987654320";
        // Define behaviour of repository
        when(supplierRepository.findSupplierPhoneById(dto.getSupplierId())).thenReturn(oldPhone);
        when(supplierRepository.existsBySupplierPhoneAndUserId(dto.getSupplierPhone(), dto.getUserId()))
                .thenReturn(false);
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.updateSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Địa chỉ không được để trống.", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateSupplierUTCID09")
    void updateSupplierUTCID09() {
        // Set up
        Supplier supplier = new Supplier();
        supplier.setSupplierId(1L);
        UpdateSupplierDTO dto = new UpdateSupplierDTO();
        dto.setUserId(1L);
        dto.setSupplierId(1L);
        dto.setSupplierName("Nguyễn Văn A");
        dto.setFacilityName("Nguyễn Văn A");
        dto.setSupplierPhone("abcdefgh");
        dto.setSupplierAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}"	);
        dto.setSupplierMail("BNV71@gmail.com");
        dto.setStatus(0);
        String oldPhone = "0987654320";
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.updateSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Số điện thoại không hợp lệ.", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateSupplierUTCID10")
    void updateSupplierUTCID10() {
        // Set up
        Supplier supplier = new Supplier();
        supplier.setSupplierId(1L);
        UpdateSupplierDTO dto = new UpdateSupplierDTO();
        dto.setUserId(1L);
        dto.setSupplierId(1L);
        dto.setSupplierName("Nguyễn Văn A");
        dto.setFacilityName("Nguyễn Văn A");
        dto.setSupplierPhone("098765432");
        dto.setSupplierAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}"	);
        dto.setSupplierMail("BNV71@gmail.com");
        dto.setStatus(0);
        String oldPhone = "0987654320";
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.updateSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Số điện thoại không hợp lệ.", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateSupplierUTCID11")
    void updateSupplierUTCID11() {
        // Set up
        Supplier supplier = new Supplier();
        supplier.setSupplierId(1L);
        UpdateSupplierDTO dto = new UpdateSupplierDTO();
        dto.setUserId(1L);
        dto.setSupplierId(1L);
        dto.setSupplierName("Nguyễn Văn A");
        dto.setFacilityName("Nguyễn Văn A");
        dto.setSupplierPhone("");
        dto.setSupplierAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}"	);
        dto.setSupplierMail("BNV71@gmail.com");
        dto.setStatus(0);
        String oldPhone = "0987654320";
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.updateSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Số điện thoại không được để trống.", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateSupplierUTCID12")
    void updateSupplierUTCID12() {
        // Set up
        Supplier supplier = new Supplier();
        supplier.setSupplierId(1L);
        UpdateSupplierDTO dto = new UpdateSupplierDTO();
        dto.setUserId(1L);
        dto.setSupplierId(1L);
        dto.setSupplierName("Nguyễn Văn A");
        dto.setFacilityName("Nguyễn Văn A");
        dto.setSupplierPhone(null);
        dto.setSupplierAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}"	);
        dto.setSupplierMail("BNV71@gmail.com");
        dto.setStatus(0);
        String oldPhone = "0987654320";
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.updateSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Số điện thoại không được để trống.", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateSupplierUTCID13")
    void updateSupplierUTCID13() {
        // Set up
        Supplier supplier = new Supplier();
        supplier.setSupplierId(1L);
        UpdateSupplierDTO dto = new UpdateSupplierDTO();
        dto.setUserId(1L);
        dto.setSupplierId(1L);
        dto.setSupplierName("Nguyễn Văn A");
        dto.setFacilityName("Nguyễn Văn A");
        dto.setSupplierPhone("0987654321");
        dto.setSupplierAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}"	);
        dto.setSupplierMail("a_b_c@gmail.com");
        dto.setStatus(0);
        String oldPhone = "0987654320";
        // Define behaviour of repository
        when(supplierRepository.findSupplierPhoneById(dto.getSupplierId())).thenReturn(oldPhone);
        when(supplierRepository.existsBySupplierPhoneAndUserId(dto.getSupplierPhone(), dto.getUserId()))
                .thenReturn(false);
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.updateSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Email không hợp lệ.", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateSupplierUTCID14")
    void updateSupplierUTCID14() {
        // Set up
        Supplier supplier = new Supplier();
        supplier.setSupplierId(1L);
        UpdateSupplierDTO dto = new UpdateSupplierDTO();
        dto.setUserId(1L);
        dto.setSupplierId(1L);
        dto.setSupplierName("Nguyễn Văn A");
        dto.setFacilityName("Nguyễn Văn A");
        dto.setSupplierPhone("0987654321");
        dto.setSupplierAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}"	);
        dto.setSupplierMail("12@3tungdt@gmail.com");
        dto.setStatus(0);
        String oldPhone = "0987654320";
        // Define behaviour of repository
        when(supplierRepository.findSupplierPhoneById(dto.getSupplierId())).thenReturn(oldPhone);
        when(supplierRepository.existsBySupplierPhoneAndUserId(dto.getSupplierPhone(), dto.getUserId()))
                .thenReturn(false);
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.updateSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Email không hợp lệ.", responseEntity.getBody());
    }

    @Test
    @DisplayName("searchSupplierUTCID01")
    void searchSupplierUTCID01() {
        // Set up
        Long userId = 1L;
        String key1 = "Nguyễn Văn A";
        Supplier supplier1 = new Supplier();
        Supplier supplier2 = new Supplier();
        List<Supplier> supplierList = new ArrayList<>();
        supplierList.add(supplier1);
        supplierList.add(supplier2);
        // Define behaviour of repository
        when(supplierRepository.searchByUsernameOrPhone(1L, key1)).thenReturn(Optional.of(supplierList));
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.searchSupplier(userId, key1);
        List<Customer> resultList = (List<Customer>) responseEntity.getBody();
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(supplierList, resultList);
    }

    @Test
    @DisplayName("searchSupplierUTCID02")
    void searchSupplierUTCID02() {
        // Set up
        Long userId = 1L;
        String key1 = "09876";
        Supplier supplier1 = new Supplier();
        Supplier supplier2 = new Supplier();
        List<Supplier> supplierList = new ArrayList<>();
        supplierList.add(supplier1);
        supplierList.add(supplier2);
        // Define behaviour of repository
        when(supplierRepository.searchByUsernameOrPhone(1L, key1)).thenReturn(Optional.of(supplierList));
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.searchSupplier(userId, key1);
        List<Customer> resultList = (List<Customer>) responseEntity.getBody();
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(supplierList, resultList);
    }

    @Test
    @DisplayName("searchSupplierUTCID04")
    void searchSupplierUTCID04() {
        // Set up
        Long userId = 1L;
        String key1 = "";
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.searchSupplier(userId, key1);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    @DisplayName("searchSupplierUTCID03")
    void searchSupplierUTCID03() {
        // Set up
        Long userId = 1L;
        String key1 = "@ucnxnfr";
        // Define behaviour of repository
        when(supplierRepository.searchByUsernameOrPhone(1L, key1)).thenReturn(Optional.empty());
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.searchSupplier(userId, key1);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    @DisplayName("searchSupplierUTCID05")
    void searchSupplierUTCID05() {
        // Set up
        Long userId = 1L;
        String key1 = "";
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.searchSupplier(userId, key1);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    void viewImports() {
        // Set up
        ImportReceipt importReceipt1 = new ImportReceipt();
        ImportReceipt importReceipt2 = new ImportReceipt();
        List<ImportReceipt> importList = new ArrayList<>();
        importList.add(importReceipt1);
        importList.add(importReceipt2);
        // Define behaviour of repository
        when(importReceiptRepository.findBySupplierId(1L)).thenReturn(Optional.of(importList));

        // Run service method
        ResponseEntity<?> responseEntity = supplierService.viewImports(1L);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(importList, responseEntity.getBody());
    }

    @Test
    @DisplayName("getAllSupplierPagingUTCID01")
    void getAllSupplierPagingUTCID01() {
        // Set up
        Long userId = 1L;
        Supplier supplier1 = new Supplier();
        Supplier supplier2 = new Supplier();
        List<Supplier> supplierList = new ArrayList<>();
        supplierList.add(supplier1);
        supplierList.add(supplier2);
        int page = 1;
        int size = 2;
        String sort = "ASC";
        Sort sortable = Sort.by("supplierId").ascending();
        Pageable pageable = PageRequest.of(page, size, sortable);
        // Define behaviour of repository
        when(supplierRepository.findAllByUserId(1L, pageable)).thenReturn(supplierPage);
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.getAllSupplierPaging(userId, page, size, sort);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(supplierPage, responseEntity.getBody());
    }
}