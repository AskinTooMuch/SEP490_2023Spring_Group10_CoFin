/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 10/04/2023   1.0         DuongVV     First Deploy<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.eggProduct.EggProductCreateExportDTO;
import com.example.eims.dto.exportReceipt.CreateExportDTO;
import com.example.eims.dto.exportReceipt.CreateExportDataItemDTO;
import com.example.eims.dto.exportReceipt.ExportReceiptDetailDTO;
import com.example.eims.entity.*;
import com.example.eims.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class ExportReceiptServiceImplTest {
    @Mock
    ExportReceiptRepository exportReceiptRepository;
    @Mock
    ExportDetailRepository exportDetailRepository;
    @Mock
    ImportReceiptRepository importReceiptRepository;
    @Mock
    EggBatchRepository eggBatchRepository;
    @Mock
    EggProductRepository eggProductRepository;
    @Mock
    BreedRepository breedRepository;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    IncubationPhaseRepository incubationPhaseRepository;
    @InjectMocks
    ExportReceiptServiceImpl exportReceiptService;
    @Test
    @DisplayName("viewExportsByFacilityUTCID01")
    void viewExportsByFacilityUTCID01() {
        // Set up
        Long facilityId = 1L;
        List<ExportReceipt> exportReceiptList = new ArrayList<>();
        ExportReceipt exportReceipt = new ExportReceipt();
        exportReceipt.setCustomerId(1L);
        exportReceiptList.add(exportReceipt);
        Customer customer = new Customer();
        // Define behaviour of repository
        when(exportReceiptRepository.findByFacilityIdOrderByExportDateDesc(facilityId)).thenReturn(Optional.of(exportReceiptList));
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));

        // Run service method
        ResponseEntity<?> responseEntity = exportReceiptService.viewExportsByFacility(facilityId);
        List<ExportReceipt> result = (List<ExportReceipt>) responseEntity.getBody();
        // Assert
        assertEquals(1, result.size());
    }
    @Test
    @DisplayName("viewExportsByFacilityUTCID02")
    void viewExportsByFacilityUTCID02() {
        // Set up
        Long facilityId = 1L;
        // Define behaviour of repository
        when(exportReceiptRepository.findByFacilityIdOrderByExportDateDesc(facilityId)).thenReturn(Optional.empty());

        // Run service method
        ResponseEntity<?> responseEntity = exportReceiptService.viewExportsByFacility(facilityId);
        List<ExportReceipt> result = (List<ExportReceipt>) responseEntity.getBody();
        // Assert
        assertEquals(0, result.size());
    }
    @Test
    @DisplayName("getExportDataUTCID01")
    void getExportDataUTCID01() {
        // Set up
        Long facilityId = -1L;

        // Define behaviour of repository
        when(importReceiptRepository.findByFacilityId(facilityId)).thenReturn(Optional.empty());

        // Run service method
        ResponseEntity<?> responseEntity = exportReceiptService.getExportData(facilityId);
        // Assert
        assertEquals(new ArrayList<>(), responseEntity.getBody());
    }
    @Test
    @DisplayName("getExportDataUTCID02")
    void getExportDataUTCID02() {
        // Set up
        Long facilityId = 1L;
        ImportReceipt importReceipt = new ImportReceipt();
        importReceipt.setImportId(1L);

        List<ImportReceipt> importReceiptList = new ArrayList<>();
        importReceiptList.add(importReceipt);

        EggBatch eggBatch = new EggBatch();
        eggBatch.setEggBatchId(1L);
        eggBatch.setBreedId(1L);
        List<EggBatch> eggBatchList = new ArrayList<>();
        eggBatchList.add(eggBatch);

        EggProduct eggProduct = new EggProduct();
        eggProduct.setIncubationPhaseId(1L);
        IncubationPhase incubationPhase = new IncubationPhase();
        incubationPhase.setPhaseNumber(5);

        Breed breed = new Breed();

        EggProduct egg2 = new EggProduct();
        egg2.setProductId(2L);
        egg2.setEggBatchId(2L);
        egg2.setIncubationPhaseId(2L);
        egg2.setCurAmount(1000);
        IncubationPhase incubationPhase2 = new IncubationPhase();
        incubationPhase2.setPhaseNumber(2);

        EggProduct egg3 = new EggProduct();
        egg2.setProductId(3L);
        egg3.setEggBatchId(3L);
        egg3.setIncubationPhaseId(3L);
        egg3.setCurAmount(1000);
        IncubationPhase incubationPhase3 = new IncubationPhase();
        incubationPhase3.setPhaseNumber(3);

        List<EggProduct> eggProductList = new ArrayList<>();
        eggProductList.add(egg2);
        eggProductList.add(egg3);

        // Define behaviour of repository
        when(importReceiptRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(importReceiptList));
        when(eggBatchRepository.findByImportId(importReceipt.getImportId())).thenReturn(Optional.of(eggBatchList));
        when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
        when(incubationPhaseRepository.findByIncubationPhaseId(1L)).thenReturn(Optional.of(incubationPhase));
        when(breedRepository.findByBreedId(eggBatch.getBreedId())).thenReturn(Optional.of(breed));
        when(eggProductRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProductList));
        when(incubationPhaseRepository.findByIncubationPhaseId(egg2.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase2));
        when(incubationPhaseRepository.findByIncubationPhaseId(egg3.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase3));

        // Run service method
        ResponseEntity<?> responseEntity = exportReceiptService.getExportData(facilityId);
        List<CreateExportDataItemDTO> result = (List<CreateExportDataItemDTO>) responseEntity.getBody();
        // Assert
        assertEquals(2, result.get(0).getEggProductList().size());
    }
    @Test
    @DisplayName("getExportDataUTCID03")
    void getExportDataUTCID03() {
        // Set up
        Long facilityId = 1L;
        ImportReceipt importReceipt = new ImportReceipt();
        importReceipt.setImportId(1L);

        List<ImportReceipt> importReceiptList = new ArrayList<>();
        importReceiptList.add(importReceipt);

        EggBatch eggBatch = new EggBatch();
        eggBatch.setEggBatchId(1L);
        eggBatch.setBreedId(1L);
        List<EggBatch> eggBatchList = new ArrayList<>();
        eggBatchList.add(eggBatch);

        EggProduct eggProduct = new EggProduct();
        eggProduct.setIncubationPhaseId(1L);
        IncubationPhase incubationPhase = new IncubationPhase();
        incubationPhase.setPhaseNumber(5);

        Breed breed = new Breed();

        EggProduct egg2 = new EggProduct();
        egg2.setProductId(2L);
        egg2.setEggBatchId(2L);
        egg2.setIncubationPhaseId(2L);
        egg2.setCurAmount(1000);
        IncubationPhase incubationPhase2 = new IncubationPhase();
        incubationPhase2.setPhaseNumber(2);

        EggProduct egg3 = new EggProduct();
        egg2.setProductId(3L);
        egg3.setEggBatchId(3L);
        egg3.setIncubationPhaseId(3L);
        egg3.setCurAmount(0);
        IncubationPhase incubationPhase3 = new IncubationPhase();
        incubationPhase3.setPhaseNumber(3);

        List<EggProduct> eggProductList = new ArrayList<>();
        eggProductList.add(egg2);
        eggProductList.add(egg3);

        // Define behaviour of repository
        when(importReceiptRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(importReceiptList));
        when(eggBatchRepository.findByImportId(importReceipt.getImportId())).thenReturn(Optional.of(eggBatchList));
        when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
        when(incubationPhaseRepository.findByIncubationPhaseId(1L)).thenReturn(Optional.of(incubationPhase));
        when(breedRepository.findByBreedId(eggBatch.getBreedId())).thenReturn(Optional.of(breed));
        when(eggProductRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProductList));
        when(incubationPhaseRepository.findByIncubationPhaseId(egg2.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase2));
        when(incubationPhaseRepository.findByIncubationPhaseId(egg3.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase3));

        // Run service method
        ResponseEntity<?> responseEntity = exportReceiptService.getExportData(facilityId);
        List<CreateExportDataItemDTO> result = (List<CreateExportDataItemDTO>) responseEntity.getBody();
        // Assert
        assertEquals(1, result.get(0).getEggProductList().size());
    }

    @Test
    @DisplayName("createExportUTCID01")
    void createExportUTCID01() {
        // Set up
        EggProductCreateExportDTO eggProduct1 = new EggProductCreateExportDTO();
        eggProduct1.setEggProductId(1L);
        eggProduct1.setCurAmount(100);
        eggProduct1.setExportAmount(100);
        eggProduct1.setPrice(new BigDecimal(1));
        eggProduct1.setVaccine(new BigDecimal(1));

        List<EggProductCreateExportDTO> eggProductList = new ArrayList<>();
        //eggProductList.add(eggProduct1);

        CreateExportDTO dto = new CreateExportDTO();
        dto.setCustomerId(null);
        dto.setEggProductList(eggProductList);

        EggProduct eggProduct = new EggProduct();
        // Define behaviour of repository

        // Run service method
        ResponseEntity<?> responseEntity = exportReceiptService.createExport(dto);
        // Assert
        assertEquals("Hãy chọn khách hàng", responseEntity.getBody());
    }

    @Test
    @DisplayName("createExportUTCID02")
    void createExportUTCID02() {
        // Set up
        EggProductCreateExportDTO eggProduct1 = new EggProductCreateExportDTO();
        eggProduct1.setEggProductId(1L);
        eggProduct1.setCurAmount(100);
        eggProduct1.setExportAmount(100);
        eggProduct1.setPrice(new BigDecimal(1));
        eggProduct1.setVaccine(new BigDecimal(1));

        List<EggProductCreateExportDTO> eggProductList = new ArrayList<>();
        //eggProductList.add(eggProduct1);

        CreateExportDTO dto = new CreateExportDTO();
        dto.setCustomerId(1L);
        dto.setEggProductList(eggProductList);

        EggProduct eggProduct = new EggProduct();
        // Define behaviour of repository

        // Run service method
        ResponseEntity<?> responseEntity = exportReceiptService.createExport(dto);
        // Assert
        assertEquals("Hãy chọn ít nhất 1 sản phẩm để bán", responseEntity.getBody());
    }

    @Test
    @DisplayName("createExportUTCID03")
    void createExportUTCID03() {
        // Set up
        EggProductCreateExportDTO eggProduct1 = new EggProductCreateExportDTO();
        eggProduct1.setEggProductId(1L);
        eggProduct1.setCurAmount(100);
        eggProduct1.setExportAmount(100);
        eggProduct1.setPrice(new BigDecimal(1));
        eggProduct1.setVaccine(new BigDecimal(1));

        List<EggProductCreateExportDTO> eggProductList = new ArrayList<>();
        eggProductList.add(eggProduct1);
        eggProductList.add(eggProduct1);

        CreateExportDTO dto = new CreateExportDTO();
        dto.setCustomerId(1L);
        dto.setEggProductList(eggProductList);

        EggProduct eggProduct = new EggProduct();
        // Define behaviour of repository

        // Run service method
        ResponseEntity<?> responseEntity = exportReceiptService.createExport(dto);
        // Assert
        assertEquals("Có sản phẩm bị trùng lặp", responseEntity.getBody());
    }
    @Test
    @DisplayName("createExportUTCID04")
    void createExportUTCID04() {
        // Set up
        EggProductCreateExportDTO eggProduct1 = new EggProductCreateExportDTO();
        eggProduct1.setEggProductId(null);
        eggProduct1.setCurAmount(100);
        eggProduct1.setExportAmount(100);
        eggProduct1.setPrice(new BigDecimal(1));
        eggProduct1.setVaccine(new BigDecimal(1));

        List<EggProductCreateExportDTO> eggProductList = new ArrayList<>();
        eggProductList.add(eggProduct1);

        CreateExportDTO dto = new CreateExportDTO();
        dto.setCustomerId(1L);
        dto.setEggProductList(eggProductList);

        EggProduct eggProduct = new EggProduct();
        // Define behaviour of repository

        // Run service method
        ResponseEntity<?> responseEntity = exportReceiptService.createExport(dto);
        // Assert
        assertEquals("Chưa chọn loại", responseEntity.getBody());
    }
    @Test
    @DisplayName("createExportUTCID05")
    void createExportUTCID05() {
        // Set up
        EggProductCreateExportDTO eggProduct1 = new EggProductCreateExportDTO();
        eggProduct1.setEggProductId(1L);
        eggProduct1.setCurAmount(100);
        eggProduct1.setExportAmount(-100);
        eggProduct1.setPrice(new BigDecimal(1));
        eggProduct1.setVaccine(new BigDecimal(1));

        List<EggProductCreateExportDTO> eggProductList = new ArrayList<>();
        eggProductList.add(eggProduct1);

        CreateExportDTO dto = new CreateExportDTO();
        dto.setCustomerId(1L);
        dto.setEggProductList(eggProductList);

        EggProduct eggProduct = new EggProduct();
        // Define behaviour of repository

        // Run service method
        ResponseEntity<?> responseEntity = exportReceiptService.createExport(dto);
        // Assert
        assertEquals("Số lượng xuất không hợp lệ", responseEntity.getBody());
    }

    @Test
    @DisplayName("createExportUTCID06")
    void createExportUTCID06() {
        // Set up
        EggProductCreateExportDTO eggProduct1 = new EggProductCreateExportDTO();
        eggProduct1.setEggProductId(1L);
        eggProduct1.setCurAmount(100);
        eggProduct1.setExportAmount(200);
        eggProduct1.setPrice(new BigDecimal(1));
        eggProduct1.setVaccine(new BigDecimal(1));

        List<EggProductCreateExportDTO> eggProductList = new ArrayList<>();
        eggProductList.add(eggProduct1);

        CreateExportDTO dto = new CreateExportDTO();
        dto.setCustomerId(1L);
        dto.setEggProductList(eggProductList);

        EggProduct eggProduct = new EggProduct();
        // Define behaviour of repository

        // Run service method
        ResponseEntity<?> responseEntity = exportReceiptService.createExport(dto);
        // Assert
        assertEquals("Số lượng xuất không hợp lệ", responseEntity.getBody());
    }
    @Test
    @DisplayName("createExportUTCID07")
    void createExportUTCID07() {
        // Set up
        EggProductCreateExportDTO eggProduct1 = new EggProductCreateExportDTO();
        eggProduct1.setEggProductId(1L);
        eggProduct1.setCurAmount(100);
        eggProduct1.setExportAmount(100);
        eggProduct1.setPrice(new BigDecimal(1));
        eggProduct1.setVaccine(new BigDecimal(-1));

        List<EggProductCreateExportDTO> eggProductList = new ArrayList<>();
        eggProductList.add(eggProduct1);

        CreateExportDTO dto = new CreateExportDTO();
        dto.setCustomerId(1L);
        dto.setEggProductList(eggProductList);

        EggProduct eggProduct = new EggProduct();
        // Define behaviour of repository

        // Run service method
        ResponseEntity<?> responseEntity = exportReceiptService.createExport(dto);
        // Assert
        assertEquals("Đơn giá vaccine không hợp lệ", responseEntity.getBody());
    }

    @Test
    @DisplayName("createExportUTCID08")
    void createExportUTCID08() {
        // Set up
        EggProductCreateExportDTO eggProduct1 = new EggProductCreateExportDTO();
        eggProduct1.setEggProductId(1L);
        eggProduct1.setCurAmount(100);
        eggProduct1.setExportAmount(100);
        eggProduct1.setPrice(new BigDecimal(-1));
        eggProduct1.setVaccine(new BigDecimal(1));

        List<EggProductCreateExportDTO> eggProductList = new ArrayList<>();
        eggProductList.add(eggProduct1);

        CreateExportDTO dto = new CreateExportDTO();
        dto.setCustomerId(1L);
        dto.setEggProductList(eggProductList);

        EggProduct eggProduct = new EggProduct();
        // Define behaviour of repository

        // Run service method
        ResponseEntity<?> responseEntity = exportReceiptService.createExport(dto);
        // Assert
        assertEquals("Đơn giá không hợp lệ", responseEntity.getBody());
    }
    @Test
    @DisplayName("createExportUTCID09")
    void createExportUTCID09() {
        // Set up
        EggProductCreateExportDTO eggProduct1 = new EggProductCreateExportDTO();
        eggProduct1.setEggProductId(1L);
        eggProduct1.setCurAmount(100);
        eggProduct1.setExportAmount(100);
        eggProduct1.setPrice(new BigDecimal(9999999999999.99));
        eggProduct1.setVaccine(new BigDecimal(1));

        List<EggProductCreateExportDTO> eggProductList = new ArrayList<>();
        eggProductList.add(eggProduct1);

        CreateExportDTO dto = new CreateExportDTO();
        dto.setCustomerId(1L);
        dto.setEggProductList(eggProductList);

        EggProduct eggProduct = new EggProduct();
        // Define behaviour of repository

        // Run service method
        ResponseEntity<?> responseEntity = exportReceiptService.createExport(dto);
        // Assert
        assertEquals("Tổng giá trị đơn hàng quá lớn", responseEntity.getBody());
    }
    @Test
    @DisplayName("createExportUTCID10")
    void createExportUTCID10() {
        // Set up
        EggProductCreateExportDTO eggProduct1 = new EggProductCreateExportDTO();
        eggProduct1.setEggProductId(1L);
        eggProduct1.setCurAmount(100);
        eggProduct1.setExportAmount(100);
        eggProduct1.setPrice(new BigDecimal(1));
        eggProduct1.setVaccine(new BigDecimal(1));

        List<EggProductCreateExportDTO> eggProductList = new ArrayList<>();
        eggProductList.add(eggProduct1);

        CreateExportDTO dto = new CreateExportDTO();
        dto.setCustomerId(1L);
        dto.setEggProductList(eggProductList);

        ExportReceipt exportReceipt = new ExportReceipt();
        exportReceipt.setExportId(1L);

        EggProduct eggProduct = new EggProduct();
        // Define behaviour of repository
        when(exportReceiptRepository.save(Mockito.any(ExportReceipt.class))).thenReturn(exportReceipt);
        when(eggProductRepository.getByProductId(1L)).thenReturn(Optional.of(eggProduct));

        // Run service method
        ResponseEntity<?> responseEntity = exportReceiptService.createExport(dto);
        // Assert
        assertEquals("Tạo hóa đơn xuất thành công", responseEntity.getBody());
    }
    @Test
    @DisplayName("getExportUTCID01")
    void getExportUTCID01() {
        // Set up
        Long exportId = 1L;

        // Define behaviour of repository
        when(exportReceiptRepository.findById(exportId)).thenReturn(Optional.empty());

        // Run service method
        ResponseEntity<?> responseEntity = exportReceiptService.getExport(exportId);
        // Assert
        assertEquals("Không tìm thấy hóa đơn xuất", responseEntity.getBody());
    }
    @Test
    @DisplayName("getExportUTCID02")
    void getExportUTCID02() {
        // Set up
        Long exportId = 1L;

        ExportReceipt exportReceipt = new ExportReceipt();
        exportReceipt.setExportId(1L);
        exportReceipt.setCustomerId(1L);

        Customer customer = new Customer();

        ExportDetail exportDetail = new ExportDetail();
        exportDetail.setProductId(1L);

        List<ExportDetail> exportDetailList = new ArrayList<>();
        exportDetailList.add(exportDetail);

        EggProduct eggProduct = new EggProduct();
        eggProduct.setEggBatchId(1L);
        eggProduct.setIncubationPhaseId(1L);

        IncubationPhase incubationPhase = new IncubationPhase();
        EggBatch eggBatch = new EggBatch();
        eggBatch.setEggBatchId(1L);
        eggBatch.setBreedId(1L);
        Breed breed = new Breed();

        // Define behaviour of repository
        when(exportReceiptRepository.findById(exportId)).thenReturn(Optional.of(exportReceipt));
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));
        when(exportDetailRepository.findByExportId(1L)).thenReturn(Optional.of(exportDetailList));
        when(eggProductRepository.findById(1L)).thenReturn(Optional.of(eggProduct));
        when(incubationPhaseRepository.findByIncubationPhaseId(1L)).thenReturn(Optional.of(incubationPhase));
        when(eggBatchRepository.findByEggBatchId(1L)).thenReturn(Optional.of(eggBatch));
        when(breedRepository.findByBreedId(1L)).thenReturn(Optional.of(breed));

        // Run service method
        ResponseEntity<?> responseEntity = exportReceiptService.getExport(exportId);
        ExportReceiptDetailDTO result = (ExportReceiptDetailDTO) responseEntity.getBody();
        // Assert
        assertEquals(1L, result.getExportId());
    }

    @Test
    @DisplayName("updatePaidOfExportUTCID01")
    void updatePaidOfExportUTCID01() {
        //Set up
        ExportReceipt exportReceipt = new ExportReceipt();
        exportReceipt.setExportId(-1L);
        exportReceipt.setTotal(new BigDecimal(1000));

        BigDecimal paid = new BigDecimal(1000);
        // Define behaviour of repository
        when(exportReceiptRepository.findById(-1L)).thenReturn(Optional.empty());

        // Run service method
        ResponseEntity<?> responseEntity = exportReceiptService.updatePaidOfExport(-1L, paid);
        // Assert
        assertEquals("Không tìm thấy hóa đơn xuất", responseEntity.getBody());
    }
    @Test
    @DisplayName("updatePaidOfExportUTCID02")
    void updatePaidOfExportUTCID02() {
        //Set up
        ExportReceipt exportReceipt = new ExportReceipt();
        exportReceipt.setExportId(1L);
        exportReceipt.setTotal(new BigDecimal(1000));

        BigDecimal paid = new BigDecimal(-1);
        // Define behaviour of repository
        when(exportReceiptRepository.findById(1L)).thenReturn(Optional.of(exportReceipt));

        // Run service method
        ResponseEntity<?> responseEntity = exportReceiptService.updatePaidOfExport(1L, paid);
        // Assert
        assertEquals("Số tiền đã trả không hợp lệ", responseEntity.getBody());
    }

    @Test
    @DisplayName("updatePaidOfExportUTCID03")
    void updatePaidOfExportUTCID03() {
        //Set up
        ExportReceipt exportReceipt = new ExportReceipt();
        exportReceipt.setExportId(1L);
        exportReceipt.setTotal(new BigDecimal(1000));

        BigDecimal paid = new BigDecimal(2000);
        // Define behaviour of repository
        when(exportReceiptRepository.findById(1L)).thenReturn(Optional.of(exportReceipt));

        // Run service method
        ResponseEntity<?> responseEntity = exportReceiptService.updatePaidOfExport(1L, paid);
        // Assert
        assertEquals("Số tiền đã trả không hợp lệ", responseEntity.getBody());
    }
    @Test
    @DisplayName("updatePaidOfExportUTCID04")
    void updatePaidOfExportUTCID04() {
        //Set up
        ExportReceipt exportReceipt = new ExportReceipt();
        exportReceipt.setExportId(1L);
        exportReceipt.setTotal(new BigDecimal(1000));

        BigDecimal paid = BigDecimal.valueOf(10000000000000.0);
        // Define behaviour of repository
        when(exportReceiptRepository.findById(1L)).thenReturn(Optional.of(exportReceipt));

        // Run service method
        ResponseEntity<?> responseEntity = exportReceiptService.updatePaidOfExport(1L, paid);
        // Assert
        assertEquals("Số tiền đã trả không hợp lệ", responseEntity.getBody());
    }
    @Test
    @DisplayName("updatePaidOfExportUTCID05")
    void updatePaidOfExportUTCID05() {
        //Set up
        ExportReceipt exportReceipt = new ExportReceipt();
        exportReceipt.setExportId(1L);
        exportReceipt.setTotal(new BigDecimal(1000));

        BigDecimal paid = new BigDecimal(1000);
        // Define behaviour of repository
        when(exportReceiptRepository.findById(1L)).thenReturn(Optional.of(exportReceipt));

        // Run service method
        ResponseEntity<?> responseEntity = exportReceiptService.updatePaidOfExport(1L, paid);
        // Assert
        assertEquals("Cập nhật số tiền đã trả thành công", responseEntity.getBody());
    }
}