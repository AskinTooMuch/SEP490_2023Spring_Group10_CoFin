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

import com.example.eims.dto.machine.CreateMachineDTO;
import com.example.eims.dto.machine.MachineDetailDTO;
import com.example.eims.dto.machine.MachineListItemDTO;
import com.example.eims.dto.machine.UpdateMachineDTO;
import com.example.eims.entity.*;
import com.example.eims.repository.*;
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
    @InjectMocks
    MachineServiceImpl machineService;

    @Test
    void getAllMachine() {
        // Set up
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
        when(machineTypeRepository.findByMachineTypeId(1L)).thenReturn(machineType);

        // Run service method
        ResponseEntity<?> responseEntity = machineService.getAllMachine(1L);
        List<MachineListItemDTO> resultList = (List<MachineListItemDTO>) responseEntity.getBody();
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(2, resultList.size());
    }

    @Test
    void getMachine() {
        // Set up
        Machine machine = new Machine();
        machine.setMachineTypeId(1L);
        MachineType machineType = new MachineType();
        machineType.setMachineTypeName("type1");
        EggLocation eggLocation1 = new EggLocation();
        eggLocation1.setProductId(1L);
        EggLocation eggLocation2 = new EggLocation();
        eggLocation2.setProductId(1L);
        List<EggLocation> eggLocationList = new ArrayList<>();
        eggLocationList.add(eggLocation1);
        eggLocationList.add(eggLocation2);
        EggProduct eggProduct = new EggProduct();
        // Define behaviour of repository
        when(machineRepository.findById(1L)).thenReturn(Optional.of(machine));
        when(machineTypeRepository.findByMachineTypeId(1L)).thenReturn(machineType);
        when(eggLocationRepository.getAllByMachineId(1L)).thenReturn(Optional.of(eggLocationList));
        when(eggProductRepository.getByProductId(1L)).thenReturn(Optional.of(eggProduct));

        // Run service method
        ResponseEntity<?> responseEntity = machineService.getMachine(1L);
        MachineDetailDTO result = (MachineDetailDTO) responseEntity.getBody();
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(2, result.getEggs().size());
    }

    @Test
    void createMachine() {
        // Set up
        Machine machine = new Machine();
        CreateMachineDTO dto = new CreateMachineDTO();
        dto.setFacilityId(1L);
        dto.setMachineTypeId(1L);
        dto.setMachineName("name");
        dto.setMaxCapacity(999);
        // Define behaviour of repository
        when(facilityRepository.getStatusById(1L)).thenReturn(true);
        // Run service method
        ResponseEntity<?> responseEntity = machineService.createMachine(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tạo máy mới thành công", responseEntity.getBody());
    }

    @Test
    void showFormUpdate() {
        // Set up
        Machine machine = new Machine();
        machine.setMachineId(1L);
        machine.setMachineName("name");
        machine.setActive(1);
        UpdateMachineDTO dto = new UpdateMachineDTO();
        dto.getFromEntity(machine);
        // Define behaviour of repository
        when(machineRepository.findById(1L)).thenReturn(Optional.of(machine));
        // Run service method
        ResponseEntity<?> responseEntity = machineService.showFormUpdate(1L);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(dto, responseEntity.getBody());
    }

    @Test
    void updateMachine() {
        // Set up
        Machine machine = new Machine();
        UpdateMachineDTO dto = new UpdateMachineDTO();
        dto.setMachineId(1L);
        dto.setMachineName("name");
        dto.setStatus(1);
        // Define behaviour of repository
        when(machineRepository.findByMachineId(1L)).thenReturn(Optional.of(machine));
        // Run service method
        ResponseEntity<?> responseEntity = machineService.updateMachine(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cập nhật thông tin máy thành công", responseEntity.getBody());
    }

    @Test
    void deleteMachine() {
        // Set up
        Machine machine = new Machine();
        UpdateMachineDTO dto = new UpdateMachineDTO();
        dto.setMachineId(1L);
        dto.setMachineName("name");
        dto.setStatus(1);
        // Define behaviour of repository
        when(machineRepository.existsByMachineId(1L)).thenReturn(true);
        when(eggLocationRepository.existsByMachineId(1L)).thenReturn(false);
        // Run service method
        ResponseEntity<?> responseEntity = machineService.deleteMachine(1L);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Xóa máy thành công", responseEntity.getBody());
    }

    @Test
    void getAllMachinePaging() {
        // Set up
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
        when(machineRepository.findAllByFacilityId(1L, pageable)).thenReturn(machinePage);
        // Run service method
        ResponseEntity<?> responseEntity = machineService.getAllMachinePaging(1L, 1, 2, "ASC");
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(machinePage, responseEntity.getBody());
    }
}