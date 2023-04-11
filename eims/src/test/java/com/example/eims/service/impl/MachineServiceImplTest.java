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
 * 05/04/2023   2.1         DuongNH     Update test <br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.machine.CreateMachineDTO;
import com.example.eims.dto.machine.MachineDetailDTO;
import com.example.eims.dto.machine.MachineListItemDTO;
import com.example.eims.dto.machine.UpdateMachineDTO;
import com.example.eims.entity.*;
import com.example.eims.repository.*;
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

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MachineServiceImplTest {

    @Mock
    MachineRepository machineRepository;
    @Mock
    FacilityRepository facilityRepository;
    @Mock
    EggLocationRepository eggLocationRepository;
    @Mock
    MachineTypeRepository machineTypeRepository;
    @Mock
    EggProductRepository eggProductRepository;
    @Mock
    Page machinePage;
    @Mock
    BreedRepository breedRepository;
    @Mock
    IncubationPhaseRepository incubationPhaseRepository;
    @Mock
    SpecieRepository specieRepository;
    @Mock
    SubscriptionRepository subscriptionRepository;
    @Mock
    UserSubscriptionRepository userSubscriptionRepository;
    @InjectMocks
    MachineServiceImpl machineService;

    @Test
    @DisplayName("getAllMachineUTCID01")
    void getAllMachineUTCID01() {
        // Set up
        Long facilityId = 1L;
        Machine machine1 = new Machine();
        machine1.setMachineTypeId(1L);
        Machine machine2 = new Machine();
        machine2.setMachineTypeId(1L);
        List<Machine> machineList = new ArrayList<>();
        machineList.add(machine1);
        machineList.add(machine2);
        MachineType machineType = new MachineType();
        machineType.setMachineTypeName("type1");
        // Define behaviour of repository
        when(machineRepository.findByFacilityId(1L)).thenReturn(Optional.of(machineList));
        when(machineTypeRepository.findByMachineTypeId(1L)).thenReturn(Optional.of(machineType));

        // Run service method
        ResponseEntity<?> responseEntity = machineService.getAllMachine(facilityId);
        List<MachineListItemDTO> resultList = (List<MachineListItemDTO>) responseEntity.getBody();
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(2, resultList.size());
    }

    @Test
    @DisplayName("getAllMachineUTCID02")
    void getAllMachineUTCID02() {
        // Set up
        Long facilityId = 1L;
        List<Machine> machineList = new ArrayList<>();
        // Define behaviour of repository
        when(machineRepository.findByFacilityId(1L)).thenReturn(Optional.of(machineList));

        // Run service method
        ResponseEntity<?> responseEntity = machineService.getAllMachine(facilityId);
        List<MachineListItemDTO> resultList = (List<MachineListItemDTO>) responseEntity.getBody();
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(0, resultList.size());
    }

    @Test
    @DisplayName("getAllMachineUTCID03")
    void getAllMachineUTCID03() {
        // Set up
        Long facilityId = 1L;
        // Define behaviour of repository
        when(machineRepository.findByFacilityId(1L)).thenReturn(Optional.empty());

        // Run service method
        ResponseEntity<?> responseEntity = machineService.getAllMachine(facilityId);
        System.out.println(responseEntity.getBody());
        // Assert
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    @DisplayName("getMachineUTCID01")
    void getMachineUTCID01() {
        // Set up
        Long machineId = 0L;
        EggProduct eggProduct = new EggProduct();
        // Define behaviour of repository
        when(machineRepository.findById(0L)).thenReturn(Optional.empty());

        // Run service method
        ResponseEntity<?> responseEntity = machineService.getMachine(machineId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    @DisplayName("getMachineUTCID02")
    void getMachineUTCID02() {
        // Set up
        Long machineId = 1L;
        MachineType machineType = new MachineType();
        machineType.setMachineTypeId(1L);
        machineType.setMachineTypeName("Máy ấp");

        Machine machine = new Machine();
        machine.setMachineId(machineId);
        machine.setMachineTypeId(1L);
        machine.setCurCapacity(0);
        machine.setMaxCapacity(6000);
        machine.setAddedDate(Date.valueOf("2019-02-17"));
        machine.setActive(1);

        EggLocation eggLocation1 = new EggLocation();
        eggLocation1.setProductId(1L);
        EggLocation eggLocation2 = new EggLocation();
        eggLocation2.setProductId(1L);
        List<EggLocation> eggLocationList = new ArrayList<>();
        eggLocationList.add(eggLocation1);
        eggLocationList.add(eggLocation2);

        EggProduct eggProduct = new EggProduct();
        eggProduct.setProductId(1L);
        eggProduct.setIncubationDate(LocalDateTime.now());
        eggProduct.setEggBatchId(1L);
        eggProduct.setIncubationPhaseId(1L);

        Breed breed = new Breed();
        breed.setSpecieId(1L);

        Specie specie = new Specie();
        specie.setSpecieId(1L);
        specie.setIncubationPeriod(10);

        List<EggProduct> listEggProduct = new ArrayList<>();
        listEggProduct.add(eggProduct);

        IncubationPhase incubationPhase = new IncubationPhase();
        incubationPhase.setIncubationPhaseId(1l);
        incubationPhase.setPhaseNumber(1);

        // Define behaviour of repository
        when(machineRepository.findById(1L)).thenReturn(Optional.of(machine));
        when(machineTypeRepository.findByMachineTypeId(1L)).thenReturn(Optional.of(machineType));
        when(eggLocationRepository.getAllByMachineId(1L)).thenReturn(Optional.of(eggLocationList));
        when(eggProductRepository.getByProductId(1L)).thenReturn(Optional.of(eggProduct));
        when(breedRepository.getBreedOfProduct(1L)).thenReturn(breed);
        when(eggProductRepository.findByEggBatchId(1L)).thenReturn(Optional.of(listEggProduct));
        when(incubationPhaseRepository.findByIncubationPhaseId(1L)).thenReturn(Optional.of(incubationPhase));
        when(specieRepository.findById(1L)).thenReturn(Optional.of(specie));
        // Run service method
        ResponseEntity<?> responseEntity = machineService.getMachine(machineId);
        System.out.println(responseEntity.getBody());
        // Assert
        assertNotEquals(null,responseEntity.getBody());
    }

    @Test
    @DisplayName("createMachineUTCID02")
    void createMachineUTCID02() {
        // Set up
        Long facilityId = 1L;

        CreateMachineDTO dto = new CreateMachineDTO();
        dto.setFacilityId(facilityId);
        dto.setMachineName("");
        dto.setMachineTypeId(1L);
        dto.setMaxCapacity(2000);

        Facility facility = new Facility();
        facility.setFacilityId(facilityId);

        MachineType machineType = new MachineType();
        machineType.setMachineTypeId(1L);
        machineType.setStatus(true);
        // Define behaviour of repository
        when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
        when(facilityRepository.getStatusById(1L)).thenReturn(true);
        // Run service method
        ResponseEntity<?> responseEntity = machineService.createMachine(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên máy không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("createMachineUTCID01")
    void createMachineUTCID01() {
        // Set up
        Long facilityId = 1L;

        CreateMachineDTO dto = new CreateMachineDTO();
        dto.setFacilityId(facilityId);
        dto.setMachineName("Máy nở nhỏ 2");
        dto.setMachineTypeId(1L);
        dto.setMaxCapacity(2000);

        Facility facility = new Facility();
        facility.setFacilityId(facilityId);

        MachineType machineType = new MachineType();
        machineType.setMachineTypeId(1L);
        machineType.setStatus(true);
        // Define behaviour of repository
        when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
        when(facilityRepository.getStatusById(1L)).thenReturn(true);
        when(machineTypeRepository.findById(1L)).thenReturn(Optional.of(machineType));
        // Run service method
        ResponseEntity<?> responseEntity = machineService.createMachine(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tạo máy mới thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("createMachineUTCID03")
    void createMachineUTCID03() {
        // Set up
        Long facilityId = 1L;

        CreateMachineDTO dto = new CreateMachineDTO();
        dto.setFacilityId(facilityId);
        dto.setMachineName("abcdefgh");
        dto.setMachineTypeId(1L);
        dto.setMaxCapacity(2000);

        Facility facility = new Facility();
        facility.setFacilityId(facilityId);

        MachineType machineType = new MachineType();
        machineType.setMachineTypeId(1L);
        machineType.setStatus(true);
        // Define behaviour of repository
        when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
        when(facilityRepository.getStatusById(1L)).thenReturn(true);
        when(machineTypeRepository.findById(1L)).thenReturn(Optional.of(machineType));
        // Run service method
        ResponseEntity<?> responseEntity = machineService.createMachine(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tạo máy mới thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("createMachineUTCID04")
    void createMachineUTCID04() {
        // Set up
        Long facilityId = 1L;

        CreateMachineDTO dto = new CreateMachineDTO();
        dto.setFacilityId(facilityId);
        dto.setMachineName(null);
        dto.setMachineTypeId(1L);
        dto.setMaxCapacity(2000);

        Facility facility = new Facility();
        facility.setFacilityId(facilityId);

        MachineType machineType = new MachineType();
        machineType.setMachineTypeId(1L);
        machineType.setStatus(true);
        // Define behaviour of repository
        when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
        when(facilityRepository.getStatusById(1L)).thenReturn(true);
        // Run service method
        ResponseEntity<?> responseEntity = machineService.createMachine(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên máy không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("createMachineUTCID05")
    void createMachineUTCID05() {
        // Set up
        Long facilityId = 1L;

        Facility facility = new Facility();
        facility.setFacilityId(facilityId);

        MachineType machineType = new MachineType();
        machineType.setMachineTypeId(-1L);
        machineType.setStatus(true);

        CreateMachineDTO dto = new CreateMachineDTO();
        dto.setFacilityId(facilityId);
        dto.setMachineName("Máy nở nhỏ 2");
        dto.setMachineTypeId(machineType.getMachineTypeId());
        dto.setMaxCapacity(2000);
        // Define behaviour of repository
        when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
        when(facilityRepository.getStatusById(1L)).thenReturn(true);
        when(machineTypeRepository.findById(-1L)).thenReturn(Optional.empty());
        // Run service method
        ResponseEntity<?> responseEntity = machineService.createMachine(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Loại máy không tồn tại", responseEntity.getBody());
    }

    @Test
    @DisplayName("createMachineUTCID06")
    void createMachineUTCID06() {
        // Set up
        Long facilityId = 1L;

        Facility facility = new Facility();
        facility.setFacilityId(facilityId);

        MachineType machineType = new MachineType();
        machineType.setMachineTypeId(1L);
        machineType.setStatus(true);

        CreateMachineDTO dto = new CreateMachineDTO();
        dto.setFacilityId(facilityId);
        dto.setMachineName("12345678");
        dto.setMachineTypeId(machineType.getMachineTypeId());
        dto.setMaxCapacity(2000);
        // Define behaviour of repository
        when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
        when(facilityRepository.getStatusById(1L)).thenReturn(true);
        when(machineTypeRepository.findById(1L)).thenReturn(Optional.of(machineType));
        // Run service method
        ResponseEntity<?> responseEntity = machineService.createMachine(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tạo máy mới thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("createMachineUTCID07")
    void createMachineUTCID07() {
        // Set up
        Long facilityId = 1L;

        Facility facility = new Facility();
        facility.setFacilityId(facilityId);

        MachineType machineType = new MachineType();
        machineType.setMachineTypeId(1L);
        machineType.setStatus(true);

        CreateMachineDTO dto = new CreateMachineDTO();
        dto.setFacilityId(facilityId);
        dto.setMachineName("Máy nở nhỏ 2");
        dto.setMachineTypeId(machineType.getMachineTypeId());
        dto.setMaxCapacity(0);
        // Define behaviour of repository
        when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
        when(facilityRepository.getStatusById(1L)).thenReturn(true);
        when(machineTypeRepository.findById(1L)).thenReturn(Optional.of(machineType));
        // Run service method
        ResponseEntity<?> responseEntity = machineService.createMachine(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Sức chứa của máy phải trong khoảng 1 - 20.000", responseEntity.getBody());
    }

    @Test
    @DisplayName("createMachineUTCID08")
    void createMachineUTCID08() {
        // Set up
        Long facilityId = 1L;

        Facility facility = new Facility();
        facility.setFacilityId(facilityId);

        MachineType machineType = new MachineType();
        machineType.setMachineTypeId(1L);
        machineType.setStatus(true);

        CreateMachineDTO dto = new CreateMachineDTO();
        dto.setFacilityId(facilityId);
        dto.setMachineName("Máy nở nhỏ 2");
        dto.setMachineTypeId(machineType.getMachineTypeId());
        dto.setMaxCapacity(-1);
        // Define behaviour of repository
        when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
        when(facilityRepository.getStatusById(1L)).thenReturn(true);
        when(machineTypeRepository.findById(1L)).thenReturn(Optional.of(machineType));
        // Run service method
        ResponseEntity<?> responseEntity = machineService.createMachine(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Sức chứa của máy phải trong khoảng 1 - 20.000", responseEntity.getBody());
    }

    @Test
    @DisplayName("createMachineUTCID09")
    void createMachineUTCID09() {
        // Set up
        Long facilityId = 15L;

        Facility facility = new Facility();
        facility.setFacilityId(facilityId);

        MachineType machineType = new MachineType();
        machineType.setMachineTypeId(1L);
        machineType.setStatus(true);

        CreateMachineDTO dto = new CreateMachineDTO();
        dto.setFacilityId(facilityId);
        dto.setMachineName("Máy nở nhỏ 2");
        dto.setMachineTypeId(machineType.getMachineTypeId());
        dto.setMaxCapacity(-1);
        // Define behaviour of repository
        when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.empty());
        // Run service method
        ResponseEntity<?> responseEntity = machineService.createMachine(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cơ sở không tồn tại", responseEntity.getBody());
    }

    @Test
    @DisplayName("showFormUpdateUTCID01")
    void showFormUpdateUTCID01() {
        // Set up
        Long machineId = 1L;
        Machine machine = new Machine();
        machine.setMachineId(machineId);
        machine.setMachineName("name");
        machine.setActive(1);
        UpdateMachineDTO dto = new UpdateMachineDTO();
        dto.getFromEntity(machine);
        // Define behaviour of repository
        when(machineRepository.findById(1L)).thenReturn(Optional.of(machine));
        // Run service method
        ResponseEntity<?> responseEntity = machineService.showFormUpdate(machineId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(dto, responseEntity.getBody());
    }

    @Test
    @DisplayName("showFormUpdateUTCID02")
    void showFormUpdateUTCID02() {
        // Set up
        Long machineId = 0L;
        // Define behaviour of repository
        when(machineRepository.findById(0L)).thenReturn(Optional.empty());
        // Run service method
        ResponseEntity<?> responseEntity = machineService.showFormUpdate(machineId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    @DisplayName("updateMachineUTCID01")
    void updateMachineUTCID01() {
        // Set up
        Machine machine = new Machine();
        UpdateMachineDTO dto = new UpdateMachineDTO();
        dto.setMachineId(1L);
        dto.setMachineName("Máy nở nhỏ 3");
        dto.setActive(0);
        dto.setFacilityId(1L);
        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setSubscriptionId(1L);
        // Define behaviour of repository
        when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine));
        when(machineRepository.countActiveMachine(1L)).thenReturn(10);
        when(userSubscriptionRepository.getUserSubscriptionByFacilityIdAndStatus(1L,true))
                .thenReturn(Optional.of(userSubscription));
        when(subscriptionRepository.findBySubscriptionId(1L)).thenReturn(Optional.of(new Subscription()));
        when(eggLocationRepository.existsByMachineId(1L)).thenReturn(false);
        // Run service method
        ResponseEntity<?> responseEntity = machineService.updateMachine(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cập nhật thông tin máy thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateMachineUTCID02")
    void updateMachineUTCID02() {
        // Set up
        Machine machine = new Machine();
        UpdateMachineDTO dto = new UpdateMachineDTO();
        dto.setMachineId(1L);
        dto.setMachineName("abcdefgh");
        dto.setActive(1);
        dto.setFacilityId(1L);
        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setSubscriptionId(1L);
        // Define behaviour of repository
        when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine));
        when(machineRepository.countActiveMachine(1L)).thenReturn(10);
        when(userSubscriptionRepository.getUserSubscriptionByFacilityIdAndStatus(1L,true))
                .thenReturn(Optional.of(userSubscription));
        when(subscriptionRepository.findBySubscriptionId(1L)).thenReturn(Optional.of(new Subscription()));
        // Run service method
        ResponseEntity<?> responseEntity = machineService.updateMachine(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cập nhật thông tin máy thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateMachineUTCID03")
    void updateMachineUTCID03() {
        // Set up
        Machine machine = new Machine();
        UpdateMachineDTO dto = new UpdateMachineDTO();
        dto.setMachineId(1L);
        dto.setMachineName("12345678");
        dto.setActive(0);
        dto.setFacilityId(1L);
        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setSubscriptionId(1L);
        // Define behaviour of repository
        when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine));
        when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine));
        when(machineRepository.countActiveMachine(1L)).thenReturn(10);
        when(userSubscriptionRepository.getUserSubscriptionByFacilityIdAndStatus(1L,true))
                .thenReturn(Optional.of(userSubscription));
        when(subscriptionRepository.findBySubscriptionId(1L)).thenReturn(Optional.of(new Subscription()));
        when(eggLocationRepository.existsByMachineId(1L)).thenReturn(false);
        // Run service method
        ResponseEntity<?> responseEntity = machineService.updateMachine(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cập nhật thông tin máy thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateMachineUTCID04")
    void updateMachineUTCID04() {
        // Set up
        Machine machine = new Machine();
        UpdateMachineDTO dto = new UpdateMachineDTO();
        dto.setMachineId(1L);
        dto.setMachineName("        ");
        dto.setActive(0);
        dto.setFacilityId(1L);
        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setSubscriptionId(1L);
        // Define behaviour of repository
        when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine));
        when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine));
        when(machineRepository.countActiveMachine(1L)).thenReturn(10);
        when(userSubscriptionRepository.getUserSubscriptionByFacilityIdAndStatus(1L,true))
                .thenReturn(Optional.of(userSubscription));
        when(subscriptionRepository.findBySubscriptionId(1L)).thenReturn(Optional.of(new Subscription()));
        // Run service method
        ResponseEntity<?> responseEntity = machineService.updateMachine(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên máy không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateMachineUTCID05")
    void updateMachineUTCID05() {
        // Set up
        Machine machine = new Machine();
        UpdateMachineDTO dto = new UpdateMachineDTO();
        dto.setMachineId(1L);
        dto.setMachineName(null);
        dto.setActive(1);
        dto.setFacilityId(1L);
        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setSubscriptionId(1L);
        // Define behaviour of repository
        when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine));
        when(machineRepository.countActiveMachine(1L)).thenReturn(10);
        when(userSubscriptionRepository.getUserSubscriptionByFacilityIdAndStatus(1L,true))
                .thenReturn(Optional.of(userSubscription));
        when(subscriptionRepository.findBySubscriptionId(1L)).thenReturn(Optional.of(new Subscription()));

        // Run service method
        ResponseEntity<?> responseEntity = machineService.updateMachine(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên máy không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateMachineUTCID06")
    void updateMachineUTCID06() {
        // Set up
        Machine machine = new Machine();
        UpdateMachineDTO dto = new UpdateMachineDTO();
        dto.setMachineId(1L);
        dto.setMachineName("Máy nở nhỏ 3");
        dto.setActive(2);
        dto.setFacilityId(1L);
        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setSubscriptionId(1L);
        // Define behaviour of repository
        when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine));
        when(machineRepository.countActiveMachine(1L)).thenReturn(10);
        when(userSubscriptionRepository.getUserSubscriptionByFacilityIdAndStatus(1L,true))
                .thenReturn(Optional.of(userSubscription));
        when(subscriptionRepository.findBySubscriptionId(1L)).thenReturn(Optional.of(new Subscription()));
        // Run service method
        ResponseEntity<?> responseEntity = machineService.updateMachine(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Trạng thái không hợp lệ", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateMachineUTCID07")
    void updateMachineUTCID07() {
        // Set up
        UpdateMachineDTO dto = new UpdateMachineDTO();
        dto.setMachineId(0L);
        dto.setMachineName("Máy nở nhỏ 3");
        dto.setActive(2);
        // Define behaviour of repository
        when(machineRepository.findByMachineId(0L)).thenReturn(Optional.empty());
        // Run service method
        ResponseEntity<?> responseEntity = machineService.updateMachine(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    @DisplayName("deleteMachineUTCID01")
    void deleteMachineUTCID01() {
        // Set up
        Machine machine = new Machine();
        machine.setMachineId(1L);
        machine.setMachineName("name");
        machine.setActive(1);
        // Define behaviour of repository
        when(machineRepository.existsByMachineId(1L)).thenReturn(true);
        when(eggLocationRepository.existsByMachineId(1L)).thenReturn(false);
        // Run service method
        ResponseEntity<?> responseEntity = machineService.deleteMachine(machine.getMachineId());
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Xóa máy thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("deleteMachineUTCID02")
    void deleteMachineUTCID02() {
        // Set up
        Machine machine = new Machine();
        machine.setMachineId(0L);
        machine.setMachineName("name");
        machine.setActive(1);
        // Define behaviour of repository
        when(machineRepository.existsByMachineId(0L)).thenReturn(false);
        // Run service method
        ResponseEntity<?> responseEntity = machineService.deleteMachine(machine.getMachineId());
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Máy không tồn tại", responseEntity.getBody());
    }

    @Test
    @DisplayName("deleteMachineUTCID03")
    void deleteMachineUTCID03() {
        // Set up
        Machine machine = new Machine();
        machine.setMachineId(1L);
        machine.setMachineName("name");
        machine.setActive(1);
        // Define behaviour of repository
        when(machineRepository.existsByMachineId(1L)).thenReturn(true);
        when(eggLocationRepository.existsByMachineId(1L)).thenReturn(true);
        // Run service method
        ResponseEntity<?> responseEntity = machineService.deleteMachine(machine.getMachineId());
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Chuyển trứng sang máy khác trước khi xóa", responseEntity.getBody());
    }

    @Test
    @DisplayName("getAllMachinePagingUTCID01")
    void getAllMachinePagingUTCID01() {
        // Set up
        Long facilityId = 1L;
        Machine machine1 = new Machine();
        Machine machine2 = new Machine();
        List<Machine> machineList = new ArrayList<>();
        machineList.add(machine1);
        machineList.add(machine2);
        int page = 1;
        int size = 2;
        String sort = "ASC";
        Sort sortable = Sort.by("machineId").ascending();
        Pageable pageable = PageRequest.of(page, size, sortable);
        // Define behaviour of repository
        when(facilityRepository.existsByFacilityId(1L)).thenReturn(true);
        when(machineRepository.findAllByFacilityId(facilityId, pageable)).thenReturn(machinePage);
        // Run service method
        ResponseEntity<?> responseEntity = machineService.getAllMachinePaging(facilityId, page, size, sort);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(machinePage, responseEntity.getBody());
    }

    @Test
    @DisplayName("getAllMachinePagingUTCID02")
    void getAllMachinePagingUTCID02() {
        // Set up
        Long facilityId = 0L;
        Machine machine1 = new Machine();
        Machine machine2 = new Machine();
        List<Machine> machineList = new ArrayList<>();
        machineList.add(machine1);
        machineList.add(machine2);
        int page = 1;
        int size = 2;
        String sort = "ASC";
        Sort sortable = Sort.by("machineId").ascending();
        Pageable pageable = PageRequest.of(page, size, sortable);
        // Define behaviour of repository
        when(facilityRepository.existsByFacilityId(0L)).thenReturn(false);
        // Run service method
        ResponseEntity<?> responseEntity = machineService.getAllMachinePaging(facilityId, page, size, sort);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cơ sở không tồn tại", responseEntity.getBody());
    }
}