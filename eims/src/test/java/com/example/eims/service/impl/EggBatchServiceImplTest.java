 /*
  * Copyright (C) 2023, FPT University <br>
  * SEP490 - SEP490_G10 <br>
  * EIMS <br>
  * Eggs Incubating Management System <br>
  *
  * Record of change:<br>
  * DATE         Version     Author      DESCRIPTION<br>
  * 06/04/2023   1.0         DuongVV     First Deploy<br>
  * 09/04/2023   1.1         DuongVV     Update tests<br>
  */

 package com.example.eims.service.impl;

 import com.example.eims.dto.eggBatch.EggBatchDetailDTO;
 import com.example.eims.dto.eggBatch.EggBatchViewListItemDTO;
 import com.example.eims.dto.eggBatch.UpdateEggBatchDTO;
 import com.example.eims.dto.eggLocation.EggLocationUpdateEggBatchDTO;
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

 import java.time.LocalDateTime;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.List;
 import java.util.Optional;

 import static org.junit.jupiter.api.Assertions.*;
 import static org.mockito.Mockito.when;

 @ExtendWith(MockitoExtension.class)
 class EggBatchServiceImplTest {
     @Mock
     EggBatchRepository eggBatchRepository;
     @Mock
     ImportReceiptRepository importReceiptRepository;
     @Mock
     SupplierRepository supplierRepository;
     @Mock
     SpecieRepository specieRepository;
     @Mock
     BreedRepository breedRepository;
     @Mock
     EggProductRepository eggProductRepository;
     @Mock
     EggLocationRepository eggLocationRepository;
     @Mock
     MachineRepository machineRepository;
     @Mock
     IncubationPhaseRepository incubationPhaseRepository;
     @Mock
     MachineTypeRepository machineTypeRepository;
     @InjectMocks
     EggBatchServiceImpl eggBatchService;

     @Test
     @DisplayName("getEggBatchUTCID01")
     void getEggBatchUTCID01() {
         // Set up
         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setImportId(1L);
         eggBatch.setBreedId(1L);
         eggBatch.setDateAction(LocalDateTime.now());
         ImportReceipt importReceipt = new ImportReceipt();
         importReceipt.setFacilityId(1L);
         importReceipt.setSupplierId(1L);
         Supplier supplier = new Supplier();
         Breed breed = new Breed();
         breed.setBreedId(1L);
         Specie specie = new Specie();
         List<EggProduct> eggProductList = new ArrayList<>();
         EggProduct eggProduct1 = new EggProduct();
         eggProduct1.setProductId(1L);
         eggProduct1.setIncubationPhaseId(1L);
         eggProductList.add(eggProduct1);
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setPhaseNumber(0);
         List<IncubationPhase> incubationPhaseList = new ArrayList<>();
         incubationPhaseList.add(incubationPhase);
         incubationPhaseList.add(incubationPhase);
         List<EggLocation> eggLocationList = new ArrayList<>();
         EggLocation eggLocation = new EggLocation();
         eggLocation.setMachineId(1L);
         eggLocationList.add(eggLocation);
         Machine machine = new Machine();
         List<Machine> notFullList = new ArrayList<>();
         notFullList.add(machine);
         MachineType machineType = new MachineType();

         EggBatchDetailDTO dto = new EggBatchDetailDTO();
         dto.setEggBatchId(1L);
         // Define behaviour of repository
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(importReceiptRepository.findByImportId(eggBatch.getImportId())).thenReturn(Optional.of(importReceipt));
         when(supplierRepository.findBySupplierId(importReceipt.getSupplierId())).thenReturn(Optional.of(supplier));
         when(breedRepository.findByBreedId(eggBatch.getEggBatchId())).thenReturn(Optional.of(breed));
         when(specieRepository.findById(breed.getSpecieId())).thenReturn(Optional.of(specie));
         when(eggProductRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProductList));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct1.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(machineRepository.findAllNotFull(importReceipt.getFacilityId())).thenReturn(Optional.of(notFullList));
         when(machineTypeRepository.findByMachineTypeId(machine.getMachineTypeId())).thenReturn(Optional.of(machineType));
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct1));
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(eggBatch.getEggBatchId())).thenReturn(incubationPhaseList);

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.getEggBatch(1L);
         EggBatchDetailDTO result = (EggBatchDetailDTO) responseEntity.getBody();
         // Assert
         assertEquals(1L, result.getEggBatchId());
     }

     @Test
     @DisplayName("getEggBatchUTCID02")
     void getEggBatchUTCID02() {
         // Set up
         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(-1L);
         // Define behaviour of repository
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.empty());
         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.getEggBatch(-1L);
         // Assert
         assertEquals(null, responseEntity.getBody());
     }

     @Test
     @DisplayName("viewListEggBatchUTCID01")
     void viewListEggBatchUTCID01() {
         // Set up
         Long facilityId = 1L;
         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setImportId(1L);
         eggBatch.setBreedId(1L);
         ImportReceipt importReceipt = new ImportReceipt();
         importReceipt.setFacilityId(1L);
         importReceipt.setSupplierId(1L);
         List<ImportReceipt> importReceiptList = new ArrayList<>();
         importReceiptList.add(importReceipt);
         List<EggBatch> eggBatchList = new ArrayList<>();
         eggBatchList.add(eggBatch);
         Supplier supplier = new Supplier();
         Breed breed = new Breed();
         breed.setBreedId(1L);
         Specie specie = new Specie();
         List<EggProduct> eggProductList = new ArrayList<>();
         EggProduct eggProduct1 = new EggProduct();
         eggProduct1.setProductId(1L);
         eggProduct1.setIncubationPhaseId(1L);
         eggProductList.add(eggProduct1);
         IncubationPhase incubationPhase = new IncubationPhase();
         List<EggLocation> eggLocationList = new ArrayList<>();
         EggLocation eggLocation = new EggLocation();
         eggLocation.setMachineId(1L);
         eggLocationList.add(eggLocation);
         Machine machine = new Machine();
         List<Machine> notFullList = new ArrayList<>();
         notFullList.add(machine);
         MachineType machineType = new MachineType();

         List<EggBatchViewListItemDTO> dtoList = new ArrayList<>();

         // Define behaviour of repository
         when(importReceiptRepository.findByFacilityIdOrderByImportDateDesc(facilityId)).thenReturn(Optional.of(importReceiptList));
         when(eggBatchRepository.findByImportIdOrderByStatusDesc(importReceipt.getImportId())).thenReturn(Optional.of(eggBatchList));
         when(supplierRepository.findBySupplierId(importReceipt.getSupplierId())).thenReturn(Optional.of(supplier));
         when(breedRepository.findByBreedId(eggBatch.getEggBatchId())).thenReturn(Optional.of(breed));
         when(specieRepository.findById(breed.getSpecieId())).thenReturn(Optional.of(specie));
         when(eggProductRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProductList));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct1.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.viewListEggBatch(facilityId);
         List<EggBatchViewListItemDTO> result = (List<EggBatchViewListItemDTO>) responseEntity.getBody();
         // Assert
         assertEquals(1, result.size());
     }

     @Test
     @DisplayName("viewListEggBatchUTCID02")
     void viewListEggBatchUTCID02() {
         // Set up
         Long facilityId = -1L;

         // Define behaviour of repository
         when(importReceiptRepository.findByFacilityIdOrderByImportDateDesc(facilityId)).thenReturn(Optional.empty());

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.viewListEggBatch(facilityId);
         // Assert
         assertEquals(new ArrayList<>(), responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID01")
     void updateEggBatchUTCID01() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(0);

         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(-1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         List<IncubationPhase> incubationPhaseList = Arrays.asList(incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(-1L);
         dto.setPhaseNumber(0);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(new ArrayList<>());

         // Define behaviour of repository
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.empty());


         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Không tìm thấy lô trứng", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID02")
     void updateEggBatchUTCID02() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(1);

         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(0);
         eggBatch.setDateAction(LocalDateTime.now().plusHours(24));

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         List<IncubationPhase> incubationPhaseList = Arrays.asList(incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(2);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(new ArrayList<>());

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(eggBatch.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Chưa đến giai đoạn cần cập nhật", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID03")
     void updateEggBatchUTCID03() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(1);

         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         List<IncubationPhase> incubationPhaseList = Arrays.asList(incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(1000);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(1L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(1000);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);
         eggLocations.add(eggLocation2);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(2);
         dto.setEggWasted(-1);
         dto.setAmount(500);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(new ArrayList<>());


         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(eggBatch.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Số trứng hao hụt phải lớn hơn hoặc bằng 0", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID04")
     void updateEggBatchUTCID04() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(1);

         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         List<IncubationPhase> incubationPhaseList = Arrays.asList(incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(1000);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(1L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(1000);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);
         eggLocations.add(eggLocation2);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(0);
         dto.setEggWasted(0);
         dto.setAmount(-1);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(new ArrayList<>());


         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(eggBatch.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Số trứng cập nhật phải lớn hơn hoặc bằng 0", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID05")
     void updateEggBatchUTCID05() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(1);

         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         List<IncubationPhase> incubationPhaseList = Arrays.asList(incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(1000);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(1L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(1000);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);
         eggLocations.add(eggLocation2);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(-1);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(new ArrayList<>());


         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(eggBatch.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Giai đoạn cập nhật không hợp lệ", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID06")
     void updateEggBatchUTCID06() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(0);
         incubationPhase.setPhaseDescription("phase");

         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);


         List<IncubationPhase> incubationPhaseList = Arrays.asList(incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(1000);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(1L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(1000);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);
         eggLocations.add(eggLocation2);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(2);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(new ArrayList<>());


         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(eggBatch.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Lô trứng chưa được ấp, không thể cập nhật phase", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID07")
     void updateEggBatchUTCID07() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(0);
         incubationPhase.setPhaseDescription("phase");

         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         List<IncubationPhase> incubationPhaseList = Arrays.asList(incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(1000);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(1L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(1000);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);
         eggLocations.add(eggLocation2);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(0);
         dto.setEggWasted(1000);
         dto.setAmount(500);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(new ArrayList<>());


         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(eggBatch.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Số trứng không hợp lệ. Tổng số Trứng hư tổn, hao hụt(" + dto.getEggWasted() + ") và Trứng " +
                 "vỡ/dập(" + dto.getAmount() + ") phải nhỏ hơn hoặc bằng số trứng của lô(" + eggBatch.getAmount() + ")", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID08")
     void updateEggBatchUTCID08() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(0);
         incubationPhase.setPhaseDescription("phase");

         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         List<IncubationPhase> incubationPhaseList = Arrays.asList(incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(1000);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(1L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(1000);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);
         eggLocations.add(eggLocation2);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(0);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(new ArrayList<>());


         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(eggBatch.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Chưa cập nhật vị trí trứng", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID09")
     void updateEggBatchUTCID09() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(0);
         incubationPhase.setPhaseDescription("phase");

         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         List<IncubationPhase> incubationPhaseList = Arrays.asList(incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(500);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(1L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(1000);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);
         eggLocations.add(eggLocation1);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(0);
         dto.setEggWasted(0);
         dto.setAmount(1000);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);


         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(eggBatch.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Cập nhật lô trứng thành công", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID10")
     void updateEggBatchUTCID10() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(0);
         incubationPhase.setPhaseDescription("phase");

         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         List<IncubationPhase> incubationPhaseList = Arrays.asList(incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(500);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(1000);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation2);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(0);
         dto.setEggWasted(0);
         dto.setAmount(1000);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);


         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(eggBatch.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Cập nhật lô trứng thành công", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID11")
     void updateEggBatchUTCID11() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(0);
         incubationPhase.setPhaseDescription("phase");

         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         List<IncubationPhase> incubationPhaseList = Arrays.asList(incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(-100);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(1000);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(0);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);


         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(eggBatch.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Số lượng trứng trong danh sách máy không hợp lệ", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID12")
     void updateEggBatchUTCID12() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(0);
         incubationPhase.setPhaseDescription("phase");

         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         List<IncubationPhase> incubationPhaseList = Arrays.asList(incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(500);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(1L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(500);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);
         eggLocations.add(eggLocation2);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(0);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);


         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(eggBatch.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Số lượng trứng trong danh sách máy không hợp lệ. Tổng số trứng trong danh sách máy(1000) " +
                 "phải bằng số trứng của lô(1000) trừ đi tổng số Trứng hư tổn, hao hụt(0) và Trứng vỡ/dập(500)", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID13")
     void updateEggBatchUTCID13() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(0);
         incubationPhase.setPhaseDescription("phase");

         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         List<IncubationPhase> incubationPhaseList = Arrays.asList(incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(500);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(1L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(500);
         Machine machine1 = new Machine();

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(0);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         EggProduct egg1Inserted = new EggProduct();
         egg1Inserted.setProductId(1L);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(eggBatch.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.save(Mockito.any(EggProduct.class))).thenReturn(egg1Inserted);
         when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine1));
         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Cập nhật lô trứng thành công", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID14")
     void updateEggBatchUTCID14() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(1);
         incubationPhase.setPhaseDescription("phase");

         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         List<IncubationPhase> incubationPhaseList = Arrays.asList(incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(500);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(1L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(500);
         Machine machine1 = new Machine();

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(3);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         EggProduct egg1Inserted = new EggProduct();
         egg1Inserted.setProductId(1L);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(eggBatch.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Chưa đến giai đoạn cập nhật phase", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID15")
     void updateEggBatchUTCID15() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(1);
         incubationPhase.setPhaseDescription("phase");

         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);

         List<IncubationPhase> incubationPhaseList = Arrays.asList(incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(500);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(1L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(500);
         Machine machine1 = new Machine();

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(0);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         EggProduct egg1Inserted = new EggProduct();
         egg1Inserted.setProductId(1L);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(eggBatch.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Đã qua giai đoạn cập nhật phase", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID16")
     void updateEggBatchUTCID16() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(1);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(500);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(1L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(500);
         Machine machine1 = new Machine();

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(2);
         dto.setEggWasted(0);
         dto.setAmount(2000);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         EggProduct egg1Inserted = new EggProduct();
         egg1Inserted.setProductId(1L);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(new EggProduct()));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Số phase cập nhật không được lớn hơn số trứng đang ấp", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID17")
     void updateEggBatchUTCID17() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(1);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(500);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(1L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(500);
         Machine machine1 = new Machine();

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(2);
         dto.setEggWasted(1000);
         dto.setAmount(500);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         EggProduct egg1Inserted = new EggProduct();
         egg1Inserted.setProductId(1L);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(new EggProduct()));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Số lượng trứng không hợp lệ. Tổng số Trứng hư tổn, hao hụt mới(1000) và phase(500) phải nhỏ hơn hoặc bằng số Trứng đang ấp(1000)", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID18")
     void updateEggBatchUTCID18() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(1);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(500);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(1L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(500);
         Machine machine1 = new Machine();

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         //eggLocations.add(eggLocation1);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(2);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         EggProduct egg1Inserted = new EggProduct();
         egg1Inserted.setProductId(1L);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(new EggProduct()));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Chưa cập nhật vị trí trứng", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID19")
     void updateEggBatchUTCID19() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(1);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(500);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(1L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(500);
         Machine machine1 = new Machine();

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);
         eggLocations.add(eggLocation1);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(2);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         EggProduct egg1Inserted = new EggProduct();
         egg1Inserted.setProductId(1L);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(new EggProduct()));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Có máy bị trùng lặp", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID20")
     void updateEggBatchUTCID20() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(1);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(500);
         Machine machine1 = new Machine();
         machine1.setMachineTypeId(1L);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(500);
         Machine machine2 = new Machine();
         machine2.setMachineTypeId(2L);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);
         eggLocations.add(eggLocation2);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(2);
         dto.setEggWasted(0);
         dto.setAmount(0);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         EggProduct egg1Inserted = new EggProduct();
         egg1Inserted.setProductId(1L);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(new EggProduct()));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));
         when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine1));
         when(machineRepository.findByMachineId(2L)).thenReturn(Optional.of(machine2));
         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Chỉ được chọn máy ấp trong giai đoạn này", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID21")
     void updateEggBatchUTCID21() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(1);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(-100);
         Machine machine1 = new Machine();
         machine1.setMachineTypeId(1L);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(500);
         Machine machine2 = new Machine();
         machine2.setMachineTypeId(2L);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(2);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         EggProduct egg1Inserted = new EggProduct();
         egg1Inserted.setProductId(1L);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(new EggProduct()));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));
         when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine1));
         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Số lượng trứng trong danh sách máy không hợp lệ", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID22")
     void updateEggBatchUTCID22() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(1);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(500);
         Machine machine1 = new Machine();
         machine1.setMachineTypeId(1L);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(500);
         Machine machine2 = new Machine();
         machine2.setMachineTypeId(1L);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);
         eggLocations.add(eggLocation2);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(2);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         EggProduct egg1Inserted = new EggProduct();
         egg1Inserted.setProductId(1L);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(new EggProduct()));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));
         when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine1));
         when(machineRepository.findByMachineId(2L)).thenReturn(Optional.of(machine2));
         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Số lượng trứng trong danh sách máy không hợp lệ. Tổng số trứng trong danh sách máy(1000) phải bằng số Trứng đang ấp trước đó(1000) trừ đi số Trứng hư tổn, hao hụt(0) và số phase(500)", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID23")
     void updateEggBatchUTCID23() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(1);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(500);
         Machine machine1 = new Machine();
         machine1.setMachineTypeId(1L);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(500);
         Machine machine2 = new Machine();
         machine2.setMachineTypeId(1L);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);
         eggLocations.add(eggLocation2);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(2);
         dto.setEggWasted(0);
         dto.setAmount(1000);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         EggProduct egg1Inserted = new EggProduct();
         egg1Inserted.setEggBatchId(1L);
         egg1Inserted.setIncubationPhaseId(incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId());
         egg1Inserted.setIncubationDate(LocalDateTime.now());
         egg1Inserted.setAmount(dto.getAmount());
         egg1Inserted.setCurAmount(dto.getAmount());
         egg1Inserted.setStatus(true);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(new EggProduct()));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));
         when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine1));
         when(machineRepository.findByMachineId(2L)).thenReturn(Optional.of(machine2));
         when(eggProductRepository.save(Mockito.any(EggProduct.class))).thenReturn(egg1Inserted);
         when(eggLocationRepository.findByProductId(1L)).thenReturn(Optional.of(new ArrayList<>()));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Cập nhật lô trứng thành công", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID24")
     void updateEggBatchUTCID24() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(1);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(250);
         Machine machine1 = new Machine();
         machine1.setMachineTypeId(1L);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(250);
         Machine machine2 = new Machine();
         machine2.setMachineTypeId(1L);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);
         eggLocations.add(eggLocation2);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(2);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         EggProduct egg1Inserted = new EggProduct();
         egg1Inserted.setEggBatchId(1L);
         egg1Inserted.setIncubationPhaseId(incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId());
         egg1Inserted.setIncubationDate(LocalDateTime.now());
         egg1Inserted.setAmount(dto.getAmount());
         egg1Inserted.setCurAmount(dto.getAmount());
         egg1Inserted.setStatus(true);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(new EggProduct()));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));
         when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine1));
         when(machineRepository.findByMachineId(2L)).thenReturn(Optional.of(machine2));
         when(eggProductRepository.save(Mockito.any(EggProduct.class))).thenReturn(egg1Inserted);
         when(eggLocationRepository.findByProductId(1L)).thenReturn(Optional.of(new ArrayList<>()));


         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Cập nhật lô trứng thành công", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID25")
     void updateEggBatchUTCID25() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(2);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(250);
         Machine machine1 = new Machine();
         machine1.setMachineTypeId(1L);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(250);
         Machine machine2 = new Machine();
         machine2.setMachineTypeId(1L);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);
         eggLocations.add(eggLocation2);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(2);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(1);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         EggProduct egg1Inserted = new EggProduct();
         egg1Inserted.setEggBatchId(1L);
         egg1Inserted.setIncubationPhaseId(incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId());
         egg1Inserted.setIncubationDate(LocalDateTime.now());
         egg1Inserted.setAmount(dto.getAmount());
         egg1Inserted.setCurAmount(dto.getAmount());
         egg1Inserted.setStatus(true);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(new EggProduct()));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));
         when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine1));
         when(machineRepository.findByMachineId(2L)).thenReturn(Optional.of(machine2));
         when(eggProductRepository.save(Mockito.any(EggProduct.class))).thenReturn(egg1Inserted);
         when(eggLocationRepository.findByProductId(1L)).thenReturn(Optional.of(new ArrayList<>()));


         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Cập nhật lô trứng thành công", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID26")
     void updateEggBatchUTCID26() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(3);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(500);
         Machine machine1 = new Machine();
         machine1.setMachineTypeId(1L);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(250);
         Machine machine2 = new Machine();
         machine2.setMachineTypeId(1L);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(5);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(new EggProduct()));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Chưa đến giai đoạn cập nhật Trứng đang nở", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID27")
     void updateEggBatchUTCID27() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(7);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(500);
         Machine machine1 = new Machine();
         machine1.setMachineTypeId(1L);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(250);
         Machine machine2 = new Machine();
         machine2.setMachineTypeId(1L);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(5);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(new EggProduct()));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Đã qua giai đoạn cập nhật Trứng đang nở", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID28")
     void updateEggBatchUTCID28() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(4);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(500);
         Machine machine1 = new Machine();
         machine1.setMachineTypeId(1L);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(500);
         Machine machine2 = new Machine();
         machine2.setMachineTypeId(2L);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);
         eggLocations.add(eggLocation2);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(5);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(new EggProduct()));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Không được xác nhận đã hoàn thành giai đoạn, vẫn còn trứng chưa cho sang máy nở", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID29")
     void updateEggBatchUTCID29() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(4);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(500);
         Machine machine1 = new Machine();
         machine1.setMachineTypeId(1L);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(500);
         Machine machine2 = new Machine();
         machine2.setMachineTypeId(2L);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(5);
         dto.setEggWasted(0);
         dto.setAmount(2000);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(new EggProduct()));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Số phase cập nhật không được lớn hơn số trứng đang ấp", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID30")
     void updateEggBatchUTCID30() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(4);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(500);
         Machine machine1 = new Machine();
         machine1.setMachineTypeId(1L);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(500);
         Machine machine2 = new Machine();
         machine2.setMachineTypeId(2L);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(5);
         dto.setEggWasted(1000);
         dto.setAmount(500);
         dto.setNeedAction(1);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(new EggProduct()));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Số trứng không hợp lệ. Tổng số Trứng hư tổn, hao hụt mới(1000) và phase(500) phải nhỏ hơn hoặc bằng số " +
                 "Trứng đang ấp cũ (1000)", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID31")
     void updateEggBatchUTCID31() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(4);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(500);
         Machine machine1 = new Machine();
         machine1.setMachineTypeId(1L);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(500);
         Machine machine2 = new Machine();
         machine2.setMachineTypeId(2L);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         //eggLocations.add(eggLocation1);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(5);
         dto.setEggWasted(0);
         dto.setAmount(1000);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(new EggProduct()));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));
         when(eggLocationRepository.findByProductId(eggIncubating.getProductId())).thenReturn(Optional.of(new ArrayList<>()));
         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Cập nhật lô trứng thành công", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID32")
     void updateEggBatchUTCID32() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(4);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(500);
         Machine machine1 = new Machine();
         machine1.setMachineTypeId(1L);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(500);
         Machine machine2 = new Machine();
         machine2.setMachineTypeId(2L);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);
         eggLocations.add(eggLocation1);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(5);
         dto.setEggWasted(0);
         dto.setAmount(1000);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(new EggProduct()));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));
         when(eggLocationRepository.findByProductId(eggIncubating.getProductId())).thenReturn(Optional.of(new ArrayList<>()));
         when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine1));
         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Cập nhật lô trứng thành công", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID33")
     void updateEggBatchUTCID33() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(4);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(500);
         Machine machine1 = new Machine();
         machine1.setMachineTypeId(1L);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(500);
         Machine machine2 = new Machine();
         machine2.setMachineTypeId(2L);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation2);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(5);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(1);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(new EggProduct()));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));
         //when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine1));
         when(machineRepository.findByMachineId(2L)).thenReturn(Optional.of(machine2));
         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Chưa chuyển hết sang nở, phải chọn cả máy ấp và máy nở", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID34")
     void updateEggBatchUTCID34() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(4);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(1000);
         Machine machine1 = new Machine();
         machine1.setMachineTypeId(1L);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(500);
         Machine machine2 = new Machine();
         machine2.setMachineTypeId(2L);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(5);
         dto.setEggWasted(0);
         dto.setAmount(1000);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(new EggProduct()));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));
         when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine1));
         //when(machineRepository.findByMachineId(2L)).thenReturn(Optional.of(machine2));
         when(eggLocationRepository.findByProductId(eggIncubating.getProductId())).thenReturn(Optional.of(new ArrayList<>()));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Cập nhật lô trứng thành công", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID35")
     void updateEggBatchUTCID35() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(4);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(10000);
         Machine machine1 = new Machine();
         machine1.setMachineTypeId(1L);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(500);
         Machine machine2 = new Machine();
         machine2.setMachineTypeId(2L);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);
         eggLocations.add(eggLocation2);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(5);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(1);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(new EggProduct()));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));
         when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine1));
         //when(machineRepository.findByMachineId(2L)).thenReturn(Optional.of(machine2));
         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Số lượng trứng trong danh sách máy không hợp lệ", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID36")
     void updateEggBatchUTCID36() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(5);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(500);
         eggIncubating.setCurAmount(500);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggHatching = new EggProduct();
         eggIncubating.setProductId(2L);
         eggIncubating.setAmount(500);
         eggIncubating.setCurAmount(500);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(-100);
         Machine machine1 = new Machine();
         machine1.setMachineTypeId(1L);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(-100);
         Machine machine2 = new Machine();
         machine2.setMachineTypeId(2L);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         //eggLocations.add(eggLocation1);
         eggLocations.add(eggLocation2);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(5);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(new EggProduct()));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(6).getIncubationPhaseId())).thenReturn(Optional.of(eggHatching));
         //when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine1));
         when(machineRepository.findByMachineId(2L)).thenReturn(Optional.of(machine2));
         when(eggLocationRepository.findByProductId(eggIncubating.getProductId())).thenReturn(Optional.of(new ArrayList<>()));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Cập nhật lô trứng thành công", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID37")
     void updateEggBatchUTCID37() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(4);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggHatching = new EggProduct();
         eggHatching.setProductId(2L);
         eggHatching.setAmount(1000);
         eggHatching.setCurAmount(1000);
         eggHatching.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(500);
         Machine machine1 = new Machine();
         machine1.setMachineTypeId(1L);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(250);
         Machine machine2 = new Machine();
         machine2.setMachineTypeId(2L);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);
         eggLocations.add(eggLocation2);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(5);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(1);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.ofNullable(null));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));
         //when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
         //incubationPhaseList.get(6).getIncubationPhaseId())).thenReturn(Optional.of(eggHatching));
         when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine1));
         when(machineRepository.findByMachineId(2L)).thenReturn(Optional.of(machine2));
         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Số lượng trứng trong danh sách máy không hợp lệ. Tổng số trứng trong danh sách máy nở(250) phải bằng số trứng chuyển sang nở(500)", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID38")
     void updateEggBatchUTCID38() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(4);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggHatching = new EggProduct();
         eggHatching.setProductId(2L);
         eggHatching.setAmount(1000);
         eggHatching.setCurAmount(1000);
         eggHatching.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(250);
         Machine machine1 = new Machine();
         machine1.setMachineTypeId(1L);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(500);
         Machine machine2 = new Machine();
         machine2.setMachineTypeId(2L);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);
         eggLocations.add(eggLocation2);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(5);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(1);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.ofNullable(null));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));
         //when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
         //incubationPhaseList.get(6).getIncubationPhaseId())).thenReturn(Optional.of(eggHatching));
         when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine1));
         when(machineRepository.findByMachineId(2L)).thenReturn(Optional.of(machine2));
         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Số lượng trứng trong danh sách máy không hợp lệ. Tổng số trứng trong danh sách máy (đang ấp và nở) (750) phải bằng số trứng đang ấp trước đó(1000) trừ đi tổng số Trứng hư tổn, hao hụt cập nhật thêm(0)", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID39")
     void updateEggBatchUTCID39() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(4);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggHatching = new EggProduct();
         eggHatching.setProductId(2L);
         eggHatching.setAmount(1000);
         eggHatching.setCurAmount(1000);
         eggHatching.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(2L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(250);
         Machine machine1 = new Machine();
         machine1.setMachineTypeId(2L);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(500);
         Machine machine2 = new Machine();
         machine2.setMachineTypeId(2L);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);
         eggLocations.add(eggLocation2);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(5);
         dto.setEggWasted(0);
         dto.setAmount(1000);
         dto.setNeedAction(1);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.ofNullable(null));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));
         //when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
         //incubationPhaseList.get(6).getIncubationPhaseId())).thenReturn(Optional.of(eggHatching));
         when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine1));
         when(machineRepository.findByMachineId(2L)).thenReturn(Optional.of(machine2));
         when(eggLocationRepository.findByProductId(eggIncubating.getProductId())).thenReturn(Optional.of(new ArrayList<>()));
         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Cập nhật lô trứng thành công", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID40")
     void updateEggBatchUTCID40() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(5);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(500);
         eggIncubating.setCurAmount(500);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggHatching = new EggProduct();
         eggHatching.setProductId(2L);
         eggHatching.setAmount(500);
         eggHatching.setCurAmount(500);
         eggHatching.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(250);
         Machine machine1 = new Machine();
         machine1.setMachineTypeId(1L);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(100);
         Machine machine2 = new Machine();
         machine2.setMachineTypeId(2L);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);
         eggLocations.add(eggLocation2);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(5);
         dto.setEggWasted(0);
         dto.setAmount(250);
         dto.setNeedAction(1);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(eggHatching));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));
         //when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
         //incubationPhaseList.get(6).getIncubationPhaseId())).thenReturn(Optional.of(eggHatching));
         when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine1));
         when(machineRepository.findByMachineId(2L)).thenReturn(Optional.of(machine2));
         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Số lượng trứng trong danh sách máy không hợp lệ. Tổng số trứng trong danh sách máy nở(100) phải bằng số Trứng đang nở trước đó(500) và số Trứng chuyển sang nở mới(250)", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID41")
     void updateEggBatchUTCID41() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(5);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(500);
         eggIncubating.setCurAmount(500);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggHatching = new EggProduct();
         eggHatching.setProductId(2L);
         eggHatching.setAmount(500);
         eggHatching.setCurAmount(500);
         eggHatching.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(250);
         Machine machine1 = new Machine();
         machine1.setMachineTypeId(1L);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(250);
         Machine machine2 = new Machine();
         machine2.setMachineTypeId(2L);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);
         eggLocations.add(eggLocation2);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(5);
         dto.setEggWasted(0);
         dto.setAmount(250);
         dto.setNeedAction(1);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(eggHatching));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));
         //when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
         //incubationPhaseList.get(6).getIncubationPhaseId())).thenReturn(Optional.of(eggHatching));
         when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine1));
         when(machineRepository.findByMachineId(2L)).thenReturn(Optional.of(machine2));
         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Số lượng trứng trong danh sách máy không hợp lệ. Tổng số trứng trong danh sách máy nở(250) phải bằng số Trứng đang nở trước đó(500) và số Trứng chuyển sang nở mới(250)", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID42")
     void updateEggBatchUTCID42() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(4);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggHatching = new EggProduct();
         eggHatching.setProductId(2L);
         eggHatching.setAmount(1000);
         eggHatching.setCurAmount(1000);
         eggHatching.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(1L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(250);
         Machine machine1 = new Machine();
         machine1.setMachineTypeId(1L);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(1000);
         Machine machine2 = new Machine();
         machine2.setMachineTypeId(2L);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         //eggLocations.add(eggLocation1);
         eggLocations.add(eggLocation2);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(5);
         dto.setEggWasted(0);
         dto.setAmount(1000);
         dto.setNeedAction(1);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.empty());
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));
         //when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
         //incubationPhaseList.get(6).getIncubationPhaseId())).thenReturn(Optional.of(eggHatching));
         //when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine1));
         when(eggProductRepository.save(Mockito.any(EggProduct.class))).thenReturn(eggHatching);
         when(machineRepository.findByMachineId(2L)).thenReturn(Optional.of(machine2));
         when(eggLocationRepository.findByProductId(eggIncubating.getProductId())).thenReturn(Optional.of(new ArrayList<>()));
         when(eggLocationRepository.findByProductId(eggIncubating.getProductId())).thenReturn(Optional.of(new ArrayList<>()));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Cập nhật lô trứng thành công", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID43")
     void updateEggBatchUTCID43() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(5);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(500);
         eggIncubating.setCurAmount(500);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggHatching = new EggProduct();
         eggHatching.setProductId(2L);
         eggHatching.setAmount(500);
         eggHatching.setCurAmount(500);
         eggHatching.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         EggLocationUpdateEggBatchDTO eggLocation1 = new EggLocationUpdateEggBatchDTO();
         eggLocation1.setMachineId(1L);
         eggLocation1.setMachineTypeId(2L);
         eggLocation1.setAmountCurrent(0);
         eggLocation1.setCapacity(1000);
         eggLocation1.setAmountUpdate(250);
         Machine machine1 = new Machine();
         machine1.setMachineTypeId(2L);

         EggLocationUpdateEggBatchDTO eggLocation2 = new EggLocationUpdateEggBatchDTO();
         eggLocation2.setMachineId(2L);
         eggLocation2.setMachineTypeId(2L);
         eggLocation2.setAmountCurrent(0);
         eggLocation2.setCapacity(1000);
         eggLocation2.setAmountUpdate(750);
         Machine machine2 = new Machine();
         machine2.setMachineTypeId(2L);

         List<EggLocationUpdateEggBatchDTO> eggLocations = new ArrayList<>();
         eggLocations.add(eggLocation1);
         eggLocations.add(eggLocation2);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(5);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(1);
         dto.setEggLocationUpdateEggBatchDTOS(eggLocations);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(eggHatching));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         //when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
         //incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));
         //when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
         //incubationPhaseList.get(6).getIncubationPhaseId())).thenReturn(Optional.of(eggHatching));
         when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine1));
         when(eggProductRepository.save(Mockito.any(EggProduct.class))).thenReturn(eggHatching);
         when(machineRepository.findByMachineId(2L)).thenReturn(Optional.of(machine2));
         when(eggLocationRepository.findByProductId(eggIncubating.getProductId())).thenReturn(Optional.of(new ArrayList<>()));
        when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(1L,3L)).thenReturn(Optional.of(eggIncubating));
         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Cập nhật lô trứng thành công", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID44")
     void updateEggBatchUTCID44() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(7);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(0);
         eggIncubating.setCurAmount(0);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggHatching = new EggProduct();
         eggHatching.setProductId(2L);
         eggHatching.setAmount(1000);
         eggHatching.setCurAmount(1000);
         eggHatching.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(6);
         dto.setEggWasted(0);
         dto.setAmount(250);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(new ArrayList<>());

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(eggHatching));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Đã qua giai đoạn cập nhật Trứng tắc", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID45")
     void updateEggBatchUTCID45() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(4);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(1000);
         eggIncubating.setCurAmount(1000);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggHatching = new EggProduct();
         eggHatching.setProductId(2L);
         eggHatching.setAmount(1000);
         eggHatching.setCurAmount(1000);
         eggHatching.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(6);
         dto.setEggWasted(0);
         dto.setAmount(250);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(new ArrayList<>());

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.ofNullable(null));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Chưa đến giai đoạn cập nhật Trứng tắc", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID46")
     void updateEggBatchUTCID46() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(5);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(500);
         eggIncubating.setCurAmount(500);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggHatching = new EggProduct();
         eggHatching.setProductId(2L);
         eggHatching.setAmount(500);
         eggHatching.setCurAmount(500);
         eggHatching.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(6);
         dto.setEggWasted(0);
         dto.setAmount(250);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(new ArrayList<>());

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.ofNullable(null));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(6).getIncubationPhaseId())).thenReturn(Optional.of(eggHatching));
         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Vẫn còn trứng trong máy ấp, không thể cập nhật trứng Tắc", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID47")
     void updateEggBatchUTCID47() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(5);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(0);
         eggIncubating.setCurAmount(0);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggHatching = new EggProduct();
         eggHatching.setProductId(2L);
         eggHatching.setAmount(1000);
         eggHatching.setCurAmount(1000);
         eggHatching.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(6);
         dto.setEggWasted(0);
         dto.setAmount(2000);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(new ArrayList<>());

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.ofNullable(null));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(6).getIncubationPhaseId())).thenReturn(Optional.of(eggHatching));
         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Số phase cập nhật không được lớn hơn số Trứng đang nở", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID48")
     void updateEggBatchUTCID48() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(5);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(0);
         eggIncubating.setCurAmount(0);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggHatching = new EggProduct();
         eggHatching.setProductId(2L);
         eggHatching.setAmount(1000);
         eggHatching.setCurAmount(1000);
         eggHatching.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(6);
         dto.setEggWasted(500);
         dto.setAmount(1000);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(new ArrayList<>());

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.ofNullable(null));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(6).getIncubationPhaseId())).thenReturn(Optional.of(eggHatching));
         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Số trứng không hợp lệ. Tổng số Trứng hư tổn, hao hụt mới(500) và số phase(1000) phải nhỏ hơn hoặc bằng số Trứng đang nở(1000)", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID49")
     void updateEggBatchUTCID49() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(5);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");


         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(0);
         eggIncubating.setCurAmount(0);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggHatching = new EggProduct();
         eggHatching.setProductId(2L);
         eggHatching.setAmount(1000);
         eggHatching.setCurAmount(1000);
         eggHatching.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(6);
         dto.setEggWasted(0);
         dto.setAmount(0);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(new ArrayList<>());

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.ofNullable(null));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggIncubating));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(6).getIncubationPhaseId())).thenReturn(Optional.of(eggHatching));
         when(eggLocationRepository.findByProductId(eggHatching.getProductId())).thenReturn(Optional.of(new ArrayList<>()));
         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Cập nhật lô trứng thành công", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID50")
     void updateEggBatchUTCID50() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(5);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");

         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(0);
         eggIncubating.setCurAmount(0);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggHatching = new EggProduct();
         eggHatching.setProductId(2L);
         eggHatching.setAmount(1000);
         eggHatching.setCurAmount(1000);
         eggHatching.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhase,
                         incubationPhase, incubationPhase);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(8);
         dto.setEggWasted(0);
         dto.setAmount(250);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(new ArrayList<>());

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.ofNullable(null));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Chưa đến giai đoạn cập nhật phase", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID51")
     void updateEggBatchUTCID51() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(8);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatched = new IncubationPhase();
         incubationPhaseHatched.setIncubationPhaseId(4L);
         incubationPhaseHatched.setPhaseNumber(7);
         incubationPhaseHatched.setPhaseDescription("phase");

         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(0);
         eggIncubating.setCurAmount(0);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggHatching = new EggProduct();
         eggHatching.setProductId(2L);
         eggHatching.setAmount(0);
         eggHatching.setCurAmount(0);
         eggHatching.setIncubationDate(LocalDateTime.now());

         EggProduct eggHatched = new EggProduct();
         eggHatched.setProductId(3L);
         eggHatched.setAmount(0);
         eggHatched.setCurAmount(0);
         eggHatched.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhaseHatched,
                         incubationPhase, incubationPhase);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(9);
         dto.setEggWasted(0);
         dto.setAmount(250);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(new ArrayList<>());

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.ofNullable(null));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(8).getIncubationPhaseId())).thenReturn(Optional.of(eggHatched));
         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Đã phân biệt đực/cái hết số con nở", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID52")
     void updateEggBatchUTCID52() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(8);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatched = new IncubationPhase();
         incubationPhaseHatched.setIncubationPhaseId(4L);
         incubationPhaseHatched.setPhaseNumber(7);
         incubationPhaseHatched.setPhaseDescription("phase");

         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(0);
         eggIncubating.setCurAmount(0);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggHatching = new EggProduct();
         eggHatching.setProductId(2L);
         eggHatching.setAmount(0);
         eggHatching.setCurAmount(0);
         eggHatching.setIncubationDate(LocalDateTime.now());

         EggProduct eggHatched = new EggProduct();
         eggHatched.setProductId(3L);
         eggHatched.setAmount(1000);
         eggHatched.setCurAmount(1000);
         eggHatched.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhaseHatched,
                         incubationPhase, incubationPhase);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(9);
         dto.setEggWasted(0);
         dto.setAmount(2000);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(new ArrayList<>());

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.ofNullable(null));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(8).getIncubationPhaseId())).thenReturn(Optional.of(eggHatched));
         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Số phase không được vượt quá số lượng Con nở", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID53")
     void updateEggBatchUTCID53() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(8);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatched = new IncubationPhase();
         incubationPhaseHatched.setIncubationPhaseId(4L);
         incubationPhaseHatched.setPhaseNumber(7);
         incubationPhaseHatched.setPhaseDescription("phase");

         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(0);
         eggIncubating.setCurAmount(0);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggHatching = new EggProduct();
         eggHatching.setProductId(2L);
         eggHatching.setAmount(0);
         eggHatching.setCurAmount(0);
         eggHatching.setIncubationDate(LocalDateTime.now());

         EggProduct eggHatched = new EggProduct();
         eggHatched.setProductId(3L);
         eggHatched.setAmount(1000);
         eggHatched.setCurAmount(1000);
         eggHatched.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhaseHatched,
                         incubationPhase, incubationPhase);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(9);
         dto.setEggWasted(1000);
         dto.setAmount(500);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(new ArrayList<>());

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.ofNullable(null));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(8).getIncubationPhaseId())).thenReturn(Optional.of(eggHatched));
         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Tổng số phase (500) và Trứng hao hụt mới (1000) không được vượt quá " +
                 "số lượng Con nở (1000)", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID54")
     void updateEggBatchUTCID54() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(8);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatched = new IncubationPhase();
         incubationPhaseHatched.setIncubationPhaseId(4L);
         incubationPhaseHatched.setPhaseNumber(7);
         incubationPhaseHatched.setPhaseDescription("phase");

         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(0);
         eggIncubating.setCurAmount(0);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggHatching = new EggProduct();
         eggHatching.setProductId(2L);
         eggHatching.setAmount(0);
         eggHatching.setCurAmount(0);
         eggHatching.setIncubationDate(LocalDateTime.now());

         EggProduct eggHatched = new EggProduct();
         eggHatched.setProductId(3L);
         eggHatched.setAmount(1000);
         eggHatched.setCurAmount(1000);
         eggHatched.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhaseHatched,
                         incubationPhase, incubationPhase);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(8);
         dto.setEggWasted(0);
         dto.setAmount(1000);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(new ArrayList<>());

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.ofNullable(null));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(8).getIncubationPhaseId())).thenReturn(Optional.of(eggHatched));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Cập nhật lô trứng thành công", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateEggBatchUTCID55")
     void updateEggBatchUTCID55() {
         // Set up
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(8);
         incubationPhase.setPhaseDescription("phase");

         IncubationPhase incubationPhaseWasted = new IncubationPhase();
         incubationPhaseWasted.setIncubationPhaseId(2L);
         incubationPhaseWasted.setPhaseNumber(-1);
         incubationPhaseWasted.setPhaseDescription("phase");

         IncubationPhase incubationPhaseIncubating = new IncubationPhase();
         incubationPhaseIncubating.setIncubationPhaseId(3L);
         incubationPhaseIncubating.setPhaseNumber(1);
         incubationPhaseIncubating.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatching = new IncubationPhase();
         incubationPhaseHatching.setIncubationPhaseId(4L);
         incubationPhaseHatching.setPhaseNumber(5);
         incubationPhaseHatching.setPhaseDescription("phase");

         IncubationPhase incubationPhaseHatched = new IncubationPhase();
         incubationPhaseHatched.setIncubationPhaseId(4L);
         incubationPhaseHatched.setPhaseNumber(7);
         incubationPhaseHatched.setPhaseDescription("phase");

         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setAmount(1000);
         eggBatch.setStatus(1);
         eggBatch.setNeedAction(1);
         eggBatch.setDateAction(LocalDateTime.now());

         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);

         EggProduct eggIncubating = new EggProduct();
         eggIncubating.setProductId(1L);
         eggIncubating.setAmount(0);
         eggIncubating.setCurAmount(0);
         eggIncubating.setIncubationDate(LocalDateTime.now());

         EggProduct eggHatching = new EggProduct();
         eggHatching.setProductId(2L);
         eggHatching.setAmount(0);
         eggHatching.setCurAmount(0);
         eggHatching.setIncubationDate(LocalDateTime.now());

         EggProduct eggHatched = new EggProduct();
         eggHatched.setProductId(3L);
         eggHatched.setAmount(500);
         eggHatched.setCurAmount(500);
         eggHatched.setIncubationDate(LocalDateTime.now());

         EggProduct male = new EggProduct();
         male.setProductId(3L);
         male.setAmount(500);
         male.setCurAmount(500);
         male.setIncubationDate(LocalDateTime.now());

         EggProduct eggWasted = new EggProduct();
         eggWasted.setAmount(0);
         eggWasted.setCurAmount(0);

         List<IncubationPhase> incubationPhaseList = Arrays.asList
                 (incubationPhaseWasted, incubationPhase, incubationPhaseIncubating,
                         incubationPhase, incubationPhase, incubationPhase,
                         incubationPhaseHatching, incubationPhase, incubationPhaseHatched,
                         incubationPhase, incubationPhase);

         UpdateEggBatchDTO dto = new UpdateEggBatchDTO();
         dto.setEggBatchId(1L);
         dto.setPhaseNumber(8);
         dto.setEggWasted(0);
         dto.setAmount(500);
         dto.setNeedAction(0);
         dto.setEggLocationUpdateEggBatchDTOS(new ArrayList<>());

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(dto.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(dto.getPhaseNumber() + 1).getIncubationPhaseId())).thenReturn(Optional.of(male));
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(0).getIncubationPhaseId())).thenReturn(Optional.of(eggWasted));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                 incubationPhaseList.get(8).getIncubationPhaseId())).thenReturn(Optional.of(eggHatched));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.updateEggBatch(dto);
         // Assert
         assertEquals("Cập nhật lô trứng thành công", responseEntity.getBody());
     }

     @Test
     @DisplayName("setDoneEggBatchUTCID01")
     void setDoneEggBatchUTCID01() {
         // Set up
         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setStatus(1);
         EggProduct eggProduct = new EggProduct();
         eggProduct.setIncubationPhaseId(1L);
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(0);
         List<IncubationPhase> incubationPhaseList = Arrays.asList(incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(eggBatch.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.setDoneEggBatch(eggBatch.getEggBatchId(), true);
         // Assert
         assertEquals("Lô trứng chưa được ấp, không thể cập nhật hoàn thành cho lô trứng", responseEntity.getBody());
     }

     @Test
     @DisplayName("setDoneEggBatchUTCID02")
     void setDoneEggBatchUTCID02() {
         // Set up
         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setStatus(1);
         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(3);
         List<IncubationPhase> incubationPhaseList = Arrays.asList(incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(eggBatch.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(), incubationPhaseList.get(2).getIncubationPhaseId())).thenReturn(Optional.of(eggProduct));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.setDoneEggBatch(eggBatch.getEggBatchId(), true);
         // Assert
         assertEquals("Vẫn còn trứng đang ấp, không thể cập nhật hoàn thành cho lô trứng", responseEntity.getBody());
     }

     @Test
     @DisplayName("setDoneEggBatchUTCID03")
     void setDoneEggBatchUTCID03() {
         // Set up
         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setStatus(1);
         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(1000);
         eggProduct.setIncubationPhaseId(1L);
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(5);
         List<IncubationPhase> incubationPhaseList = Arrays.asList(incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(eggBatch.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(), incubationPhaseList.get(6).getIncubationPhaseId())).thenReturn(Optional.of(eggProduct));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.setDoneEggBatch(eggBatch.getEggBatchId(), true);
         // Assert
         assertEquals("Vẫn còn trứng đang nở, không thể cập nhật hoàn thành cho lô trứng", responseEntity.getBody());
     }

     @Test
     @DisplayName("setDoneEggBatchUTCID04")
     void setDoneEggBatchUTCID04() {
         // Set up
         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setStatus(0);

         // Define behaviour of repository
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.setDoneEggBatch(eggBatch.getEggBatchId(), true);
         // Assert
         assertEquals("Lô trứng đã hoàn thành, không thể cập nhật", responseEntity.getBody());
     }

     @Test
     @DisplayName("setDoneEggBatchUTCID05")
     void setDoneEggBatchUTCID05() {
         // Set up
         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(-1L);
         eggBatch.setStatus(0);

         // Define behaviour of repository
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.empty());

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.setDoneEggBatch(eggBatch.getEggBatchId(), true);
         // Assert
         assertEquals("Không tìm thấy lô trứng", responseEntity.getBody());
     }

     @Test
     @DisplayName("setDoneEggBatchUTCID06")
     void setDoneEggBatchUTCID06() {
         // Set up
         EggBatch eggBatch = new EggBatch();
         eggBatch.setEggBatchId(1L);
         eggBatch.setStatus(1);
         EggProduct eggProduct = new EggProduct();
         eggProduct.setAmount(0);
         eggProduct.setIncubationPhaseId(1L);
         IncubationPhase incubationPhase = new IncubationPhase();
         incubationPhase.setIncubationPhaseId(1L);
         incubationPhase.setPhaseNumber(5);
         List<IncubationPhase> incubationPhaseList = Arrays.asList(incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase, incubationPhase);

         // Define behaviour of repository
         when(incubationPhaseRepository.getListIncubationPhaseForEggBatch(eggBatch.getEggBatchId())).thenReturn(incubationPhaseList);
         when(eggBatchRepository.findByEggBatchId(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggBatch));
         when(eggProductRepository.findEggProductLastPhase(eggBatch.getEggBatchId())).thenReturn(Optional.of(eggProduct));
         when(incubationPhaseRepository.findByIncubationPhaseId(eggProduct.getIncubationPhaseId())).thenReturn(Optional.of(incubationPhase));
         when(eggProductRepository.findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(), incubationPhaseList.get(6).getIncubationPhaseId())).thenReturn(Optional.of(eggProduct));

         // Run service method
         ResponseEntity<?> responseEntity = eggBatchService.setDoneEggBatch(eggBatch.getEggBatchId(), true);
         // Assert
         assertEquals("Đã hoàn thành lô trứng", responseEntity.getBody());
     }
 }