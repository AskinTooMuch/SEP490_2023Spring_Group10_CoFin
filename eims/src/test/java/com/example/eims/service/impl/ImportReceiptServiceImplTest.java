/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 10/03/2023   1.0         DuongVV     First Deploy<br>
 * 10/04/2023   1.1         DuongVV     Update test<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.eggBatch.CreateEggBatchDTO;
import com.example.eims.dto.importReceipt.CreateImportDTO;
import com.example.eims.dto.importReceipt.ImportReceiptDetailDTO;
import com.example.eims.dto.importReceipt.ImportReceiptListItemDTO;
import com.example.eims.entity.*;
import com.example.eims.repository.BreedRepository;
import com.example.eims.repository.EggBatchRepository;
import com.example.eims.repository.ImportReceiptRepository;
import com.example.eims.repository.SupplierRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImportReceiptServiceImplTest {
    @Mock
    ImportReceiptRepository importReceiptRepository;
    @Mock
    SupplierRepository supplierRepository;
    @Mock
    EggBatchRepository eggBatchRepository;
    @Mock
    BreedRepository breedRepository;
    @InjectMocks
    ImportReceiptServiceImpl importReceiptService;

    @Test
    @DisplayName("viewImportsByFacilityUTCID01")
    void viewImportsByFacilityUTCID01() {
        //Set up
        Long facilityId = 1L;

        ImportReceipt importReceipt1 = new ImportReceipt();
        importReceipt1.setSupplierId(1L);

        ImportReceipt importReceipt2 = new ImportReceipt();
        importReceipt2.setSupplierId(1L);

        List<ImportReceipt> importReportList = new ArrayList<>();
        importReportList.add(importReceipt1);
        importReportList.add(importReceipt2);

        Supplier supplier = new Supplier();
        supplier.setSupplierId(1L);
        // Define behaviour of repository
        when(importReceiptRepository.findByFacilityIdOrderByImportDateDesc(facilityId)).thenReturn(Optional.of(importReportList));
        when(supplierRepository.findBySupplierId(1L)).thenReturn(Optional.of(supplier));
        // Run service method
        ResponseEntity<?> responseEntity = importReceiptService.viewImportsByFacility(facilityId);
        List<ImportReceiptListItemDTO> result = (List<ImportReceiptListItemDTO>) responseEntity.getBody();
        // Assert
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("viewImportsByFacilityUTCID02")
    void viewImportsByFacilityUTCID02() {
        //Set up
        Long facilityId = -1L;

        ImportReceipt importReceipt1 = new ImportReceipt();
        importReceipt1.setSupplierId(1L);

        ImportReceipt importReceipt2 = new ImportReceipt();
        importReceipt2.setSupplierId(1L);

        List<ImportReceipt> importReportList = new ArrayList<>();
        importReportList.add(importReceipt1);
        importReportList.add(importReceipt2);

        Supplier supplier = new Supplier();
        supplier.setSupplierId(1L);
        // Define behaviour of repository
        when(importReceiptRepository.findByFacilityIdOrderByImportDateDesc(facilityId)).thenReturn(Optional.empty());
        // Run service method
        ResponseEntity<?> responseEntity = importReceiptService.viewImportsByFacility(facilityId);
        // Assert
        assertEquals(new ArrayList<>(), responseEntity.getBody());
    }

    @Test
    @DisplayName("createImportUTCID01")
    void createImportUTCID01() {
        //Set up
        CreateEggBatchDTO eggBatch1 = new CreateEggBatchDTO();
        eggBatch1.setBreedId(1L);
        eggBatch1.setAmount(2);
        eggBatch1.setPrice(new BigDecimal(2));

        List<CreateEggBatchDTO> eggBatchList = new ArrayList<>();
        //eggBatchList.add(eggBatch1);

        CreateImportDTO dto = new CreateImportDTO();
        dto.setSupplierId(null);
        dto.setImportDate(LocalDateTime.now());
        dto.setUserId(1L);
        dto.setEggBatchList(eggBatchList);

        // Define behaviour of repository

        // Run service method
        ResponseEntity<?> responseEntity = importReceiptService.createImport(dto);
        // Assert
        assertEquals("Hãy chọn nhà cung cấp", responseEntity.getBody());
    }

    @Test
    @DisplayName("createImportUTCID02")
    void createImportUTCID02() {
        //Set up
        CreateEggBatchDTO eggBatch1 = new CreateEggBatchDTO();
        eggBatch1.setBreedId(1L);
        eggBatch1.setAmount(2);
        eggBatch1.setPrice(new BigDecimal(2));

        List<CreateEggBatchDTO> eggBatchList = new ArrayList<>();
        //eggBatchList.add(eggBatch1);

        CreateImportDTO dto = new CreateImportDTO();
        dto.setSupplierId(1L);
        dto.setImportDate(null);
        dto.setUserId(1L);
        dto.setEggBatchList(eggBatchList);

        // Define behaviour of repository

        // Run service method
        ResponseEntity<?> responseEntity = importReceiptService.createImport(dto);
        // Assert
        assertEquals("Hãy nhập ngày nhập", responseEntity.getBody());
    }

    @Test
    @DisplayName("createImportUTCID03")
    void createImportUTCID03() {
        //Set up
        CreateEggBatchDTO eggBatch1 = new CreateEggBatchDTO();
        eggBatch1.setBreedId(1L);
        eggBatch1.setAmount(2);
        eggBatch1.setPrice(new BigDecimal(2));

        List<CreateEggBatchDTO> eggBatchList = new ArrayList<>();
        //eggBatchList.add(eggBatch1);

        CreateImportDTO dto = new CreateImportDTO();
        dto.setSupplierId(1L);
        dto.setImportDate(LocalDateTime.now().plusHours(3));
        dto.setUserId(1L);
        dto.setEggBatchList(eggBatchList);
        String time = LocalDateTime.now().toString().replace("T", " ");
        String now = time.subSequence(0, 18).toString();
        // Define behaviour of repository

        // Run service method
        ResponseEntity<?> responseEntity = importReceiptService.createImport(dto);
        // Assert
        assertEquals("Ngày nhập hóa đơn phải trước bây giờ (" + now + ")", responseEntity.getBody());
    }

    @Test
    @DisplayName("createImportUTCID04")
    void createImportUTCID04() {
        //Set up
        CreateEggBatchDTO eggBatch1 = new CreateEggBatchDTO();
        eggBatch1.setBreedId(1L);
        eggBatch1.setAmount(2);
        eggBatch1.setPrice(new BigDecimal(2));

        List<CreateEggBatchDTO> eggBatchList = new ArrayList<>();
        //eggBatchList.add(eggBatch1);

        CreateImportDTO dto = new CreateImportDTO();
        dto.setSupplierId(1L);
        dto.setImportDate(LocalDateTime.now().minusHours(96));
        dto.setUserId(1L);
        dto.setEggBatchList(eggBatchList);
        // Define behaviour of repository

        // Run service method
        ResponseEntity<?> responseEntity = importReceiptService.createImport(dto);
        // Assert
        assertEquals("Hãy nhập ngày nhập hóa đơn không cách hiện tại quá 3 ngày", responseEntity.getBody());
    }

    @Test
    @DisplayName("createImportUTCID05")
    void createImportUTCID05() {
        //Set up
        CreateEggBatchDTO eggBatch1 = new CreateEggBatchDTO();
        eggBatch1.setBreedId(1L);
        eggBatch1.setAmount(2);
        eggBatch1.setPrice(new BigDecimal(2));

        List<CreateEggBatchDTO> eggBatchList = new ArrayList<>();
        //eggBatchList.add(eggBatch1);

        CreateImportDTO dto = new CreateImportDTO();
        dto.setSupplierId(1L);
        dto.setImportDate(LocalDateTime.now().minusHours(1));
        dto.setUserId(1L);
        dto.setEggBatchList(eggBatchList);
        // Define behaviour of repository

        // Run service method
        ResponseEntity<?> responseEntity = importReceiptService.createImport(dto);
        // Assert
        assertEquals("Hãy tạo ít nhất 1 lô trứng", responseEntity.getBody());
    }

    @Test
    @DisplayName("createImportUTCID06")
    void createImportUTCID06() {
        //Set up
        CreateEggBatchDTO eggBatch1 = new CreateEggBatchDTO();
        eggBatch1.setBreedId(null);
        eggBatch1.setAmount(2);
        eggBatch1.setPrice(new BigDecimal(2));

        List<CreateEggBatchDTO> eggBatchList = new ArrayList<>();
        eggBatchList.add(eggBatch1);

        CreateImportDTO dto = new CreateImportDTO();
        dto.setSupplierId(1L);
        dto.setImportDate(LocalDateTime.now().minusHours(1));
        dto.setUserId(1L);
        dto.setEggBatchList(eggBatchList);
        // Define behaviour of repository

        // Run service method
        ResponseEntity<?> responseEntity = importReceiptService.createImport(dto);
        // Assert
        assertEquals("Chưa chọn loại", responseEntity.getBody());
    }
    @Test
    @DisplayName("createImportUTCID07")
    void createImportUTCID07() {
        //Set up
        CreateEggBatchDTO eggBatch1 = new CreateEggBatchDTO();
        eggBatch1.setBreedId(1L);
        eggBatch1.setAmount(-2);
        eggBatch1.setPrice(new BigDecimal(2));

        List<CreateEggBatchDTO> eggBatchList = new ArrayList<>();
        eggBatchList.add(eggBatch1);

        CreateImportDTO dto = new CreateImportDTO();
        dto.setSupplierId(1L);
        dto.setImportDate(LocalDateTime.now().minusHours(1));
        dto.setUserId(1L);
        dto.setEggBatchList(eggBatchList);
        // Define behaviour of repository

        // Run service method
        ResponseEntity<?> responseEntity = importReceiptService.createImport(dto);
        // Assert
        assertEquals("Số lượng trứng phải lớn hơn 0", responseEntity.getBody());
    }
    @Test
    @DisplayName("createImportUTCID08")
    void createImportUTCID08() {
        //Set up
        CreateEggBatchDTO eggBatch1 = new CreateEggBatchDTO();
        eggBatch1.setBreedId(1L);
        eggBatch1.setAmount(2);
        eggBatch1.setPrice(new BigDecimal(-2));

        List<CreateEggBatchDTO> eggBatchList = new ArrayList<>();
        eggBatchList.add(eggBatch1);

        CreateImportDTO dto = new CreateImportDTO();
        dto.setSupplierId(1L);
        dto.setImportDate(LocalDateTime.now().minusHours(1));
        dto.setUserId(1L);
        dto.setEggBatchList(eggBatchList);
        // Define behaviour of repository

        // Run service method
        ResponseEntity<?> responseEntity = importReceiptService.createImport(dto);
        // Assert
        assertEquals("Đơn giá không hợp lệ", responseEntity.getBody());
    }
    @Test
    @DisplayName("createImportUTCID09")
    void createImportUTCID09() {
        //Set up
        CreateEggBatchDTO eggBatch1 = new CreateEggBatchDTO();
        eggBatch1.setBreedId(1L);
        eggBatch1.setAmount(2);
        eggBatch1.setPrice(BigDecimal.valueOf(9999999999999.99));

        List<CreateEggBatchDTO> eggBatchList = new ArrayList<>();
        eggBatchList.add(eggBatch1);

        CreateImportDTO dto = new CreateImportDTO();
        dto.setSupplierId(1L);
        dto.setImportDate(LocalDateTime.now().minusHours(1));
        dto.setUserId(1L);
        dto.setEggBatchList(eggBatchList);
        // Define behaviour of repository

        // Run service method
        ResponseEntity<?> responseEntity = importReceiptService.createImport(dto);
        // Assert
        assertEquals("Giá trị lô trứng không được vượt quá 9999999999999.99", responseEntity.getBody());
    }
    @Test
    @DisplayName("createImportUTCID10")
    void createImportUTCID10() {
        //Set up
        CreateEggBatchDTO eggBatch1 = new CreateEggBatchDTO();
        eggBatch1.setBreedId(1L);
        eggBatch1.setAmount(2);
        eggBatch1.setPrice(new BigDecimal(2));

        List<CreateEggBatchDTO> eggBatchList = new ArrayList<>();
        eggBatchList.add(eggBatch1);

        ImportReceipt importReceipt = new ImportReceipt();
        importReceipt.setImportId(1L);

        CreateImportDTO dto = new CreateImportDTO();
        dto.setSupplierId(1L);
        dto.setImportDate(LocalDateTime.now().minusHours(1));
        dto.setUserId(1L);
        dto.setEggBatchList(eggBatchList);
        // Define behaviour of repository
        when(importReceiptRepository.save(Mockito.any(ImportReceipt.class))).thenReturn(importReceipt);
        // Run service method
        ResponseEntity<?> responseEntity = importReceiptService.createImport(dto);
        // Assert
        assertEquals("Tạo hóa đơn nhập thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("getImportUTCID01")
    void getImportUTCID01() {
        //Set up
        ImportReceipt importReceipt = new ImportReceipt();
        importReceipt.setImportId(1L);
        importReceipt.setSupplierId(1L);

        Supplier supplier = new Supplier();

        EggBatch eggBatch = new EggBatch();
        eggBatch.setBreedId(1L);

        Breed breed = new Breed();
        List<EggBatch> eggBatchList = new ArrayList<>();
        eggBatchList.add(eggBatch);

        // Define behaviour of repository
        when(importReceiptRepository.findByImportId(1L)).thenReturn(Optional.of(importReceipt));
        when(supplierRepository.findBySupplierId(1L)).thenReturn(Optional.of(supplier));
        when(eggBatchRepository.findByImportId(1L)).thenReturn(Optional.of(eggBatchList));
        when(breedRepository.findByBreedId(1L)).thenReturn(Optional.of(breed));

        // Run service method
        ResponseEntity<?> responseEntity = importReceiptService.getImport(1L);
        ImportReceiptDetailDTO result = (ImportReceiptDetailDTO) responseEntity.getBody();
        // Assert
        assertEquals(1L, result.getImportId());
    }

    @Test
    @DisplayName("getImportUTCID02")
    void getImportUTCID02() {
        //Set up
        ImportReceipt importReceipt = new ImportReceipt();
        importReceipt.setImportId(-1L);

        // Define behaviour of repository
        when(importReceiptRepository.findByImportId(-1L)).thenReturn(Optional.empty());

        // Run service method
        ResponseEntity<?> responseEntity = importReceiptService.getImport(-1L);
        // Assert
        assertEquals("Không tìm thấy hóa đơn nhập", responseEntity.getBody());
    }

    @Test
    @DisplayName("updatePaidImportUTCID01")
    void updatePaidImportUTCID01() {
        //Set up
        ImportReceipt importReceipt = new ImportReceipt();
        importReceipt.setImportId(-1L);
        importReceipt.setTotal(new BigDecimal(1000));

        BigDecimal paid = new BigDecimal(1000);
        // Define behaviour of repository
        when(importReceiptRepository.findByImportId(-1L)).thenReturn(Optional.empty());

        // Run service method
        ResponseEntity<?> responseEntity = importReceiptService.updatePaidOfImport(-1L, paid);
        // Assert
        assertEquals("Không tìm thấy hóa đơn nhập", responseEntity.getBody());
    }

    @Test
    @DisplayName("updatePaidImportUTCID02")
    void updatePaidImportUTCID02() {
        //Set up
        ImportReceipt importReceipt = new ImportReceipt();
        importReceipt.setImportId(1L);
        importReceipt.setTotal(new BigDecimal(1000));

        BigDecimal paid = new BigDecimal(-1);
        // Define behaviour of repository
        when(importReceiptRepository.findByImportId(1L)).thenReturn(Optional.of(importReceipt));

        // Run service method
        ResponseEntity<?> responseEntity = importReceiptService.updatePaidOfImport(1L, paid);
        // Assert
        assertEquals("Số tiền đã trả không hợp lệ", responseEntity.getBody());
    }

    @Test
    @DisplayName("updatePaidImportUTCID03")
    void updatePaidImportUTCID03() {
        //Set up
        ImportReceipt importReceipt = new ImportReceipt();
        importReceipt.setImportId(1L);
        importReceipt.setTotal(new BigDecimal(1000));

        BigDecimal paid = new BigDecimal(2000);
        // Define behaviour of repository
        when(importReceiptRepository.findByImportId(1L)).thenReturn(Optional.of(importReceipt));

        // Run service method
        ResponseEntity<?> responseEntity = importReceiptService.updatePaidOfImport(1L, paid);
        // Assert
        assertEquals("Số tiền đã trả không hợp lệ", responseEntity.getBody());
    }

    @Test
    @DisplayName("updatePaidImportUTCID04")
    void updatePaidImportUTCID04() {
        //Set up
        ImportReceipt importReceipt = new ImportReceipt();
        importReceipt.setImportId(1L);
        importReceipt.setTotal(new BigDecimal(1000));

        BigDecimal paid = BigDecimal.valueOf(10000000000000.0);
        // Define behaviour of repository
        when(importReceiptRepository.findByImportId(1L)).thenReturn(Optional.of(importReceipt));

        // Run service method
        ResponseEntity<?> responseEntity = importReceiptService.updatePaidOfImport(1L, paid);
        // Assert
        assertEquals("Số tiền đã trả không hợp lệ", responseEntity.getBody());
    }
    @Test
    @DisplayName("updatePaidImportUTCID05")
    void updatePaidImportUTCID05() {
        //Set up
        ImportReceipt importReceipt = new ImportReceipt();
        importReceipt.setImportId(1L);
        importReceipt.setTotal(new BigDecimal(1000));

        BigDecimal paid = new BigDecimal(1000);
        // Define behaviour of repository
        when(importReceiptRepository.findByImportId(1L)).thenReturn(Optional.of(importReceipt));

        // Run service method
        ResponseEntity<?> responseEntity = importReceiptService.updatePaidOfImport(1L, paid);
        // Assert
        assertEquals("Cập nhật số tiền đã trả thành công", responseEntity.getBody());
    }
}