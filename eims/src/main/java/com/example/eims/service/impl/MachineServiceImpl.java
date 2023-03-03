/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 02/03/2023   1.0         DuongVV     First Deploy<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.machine.CreateMachineDTO;
import com.example.eims.dto.machine.UpdateMachineDTO;
import com.example.eims.entity.Machine;
import com.example.eims.repository.EggLocationRepository;
import com.example.eims.repository.FacilityRepository;
import com.example.eims.repository.MachineRepository;
import com.example.eims.service.interfaces.IMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MachineServiceImpl implements IMachineService {

    @Autowired
    private final MachineRepository machineRepository;
    @Autowired
    private final FacilityRepository facilityRepository;
    @Autowired
    private final EggLocationRepository eggLocationRepository;

    public MachineServiceImpl(MachineRepository machineRepository, FacilityRepository facilityRepository, EggLocationRepository eggLocationRepository) {
        this.machineRepository = machineRepository;
        this.facilityRepository = facilityRepository;
        this.eggLocationRepository = eggLocationRepository;
    }

    /**
     * Get all of their machines.
     *
     * @param facilityId the id of current logged-in user's selected facility.
     * @return list of Machines
     */
    @Override
    public ResponseEntity<?> getAllMachine(Long facilityId) {
        // Get all machines of the current facility
        Optional<List<Machine>> machineList = machineRepository.findByFacilityId(facilityId);
        if (machineList.isPresent()) {
            return new ResponseEntity<>(machineList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Get a machine.
     *
     * @param machineId the id of the machine
     * @return
     */
    @Override
    public ResponseEntity<?> getMachine(Long machineId) {
        // Get a machine of the current facility
        Optional<Machine> machine = machineRepository.findById(machineId);
        if (machine.isPresent()) {
            return new ResponseEntity<>(machine.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Create a machine of a facility.
     *
     * @param createMachineDTO contains the facility id, machine type id, name, max and current capacity
     * @return
     */
    @Override
    public ResponseEntity<?> createMachine(CreateMachineDTO createMachineDTO) {
        if (facilityRepository.getStatusById(createMachineDTO.getFacilityId()) == 0) { /*Facility stopped running*/
            return new ResponseEntity<>("Facility stopped running", HttpStatus.BAD_REQUEST);
        } else {
            // Retrieve machine information and create new machine
            Machine machine = new Machine();
            machine.setMachineTypeId(createMachineDTO.getMachineTypeId());
            machine.setFacilityId(createMachineDTO.getFacilityId());
            machine.setMachineName(createMachineDTO.getName());
            machine.setMaxCapacity(createMachineDTO.getMaxCapacity());
            machine.setCurCapacity(0);
            Date date = Date.valueOf(LocalDate.now());/* format yyyy-MM-dd*/
            machine.setAddedDate(date);
            machine.setActive(0);
            // Save
            machineRepository.save(machine);
            return new ResponseEntity<>("Machine created!", HttpStatus.OK);
        }
    }

    /**
     * Show form update a machine.
     *
     * @param machineId the id of the machine
     * @return
     */
    @Override
    public ResponseEntity<?> showFormUpdate(Long machineId) {
        // Get a machine of the current facility
        Optional<Machine> machine = machineRepository.findById(machineId);
        if (machine.isPresent()) {
            return new ResponseEntity<>(machine.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update a machine of a facility.
     *
     * @param updateMachineDTO contains the facility id, machine type id, name, max and current capacity, status
     * @return
     */
    @Override
    public ResponseEntity<?> updateMachine(UpdateMachineDTO updateMachineDTO) {
        // Retrieve machine's new information
        Optional<Machine> machineOptional = machineRepository.findByMachineId(updateMachineDTO.getMachineId());
        if (machineOptional.isPresent()) {
            Machine machine = machineOptional.get();
            machine.setMachineName(updateMachineDTO.getName());
            machine.setMaxCapacity(updateMachineDTO.getMaxCapacity());
            machine.setCurCapacity(updateMachineDTO.getCurCapacity());
            machine.setActive(updateMachineDTO.getActive());
            // Save
            machineRepository.save(machine);
            return new ResponseEntity<>("Machine updated!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Delete a machine of a facility.
     *
     * @param machineId the id of the machine
     * @return
     */
    @Override
    public ResponseEntity<?> deleteMachine(Long machineId) {
        if (machineRepository.existsByMachineId(machineId)) {
            // Check if Machine is running
            if (eggLocationRepository.existsByMachineId(machineId)) { /*Machine is running (have eggs in it)*/
                return new ResponseEntity<>("Machine is still running, can not delete", HttpStatus.BAD_REQUEST);
            } else {
                // Delete
                machineRepository.deleteById(machineId);
                return new ResponseEntity<>("Machine deleted!", HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get all of facility's machines with Paging.
     * facilityId is the id of current logged-in user's facility.
     *
     * @param facilityId
     * @param page       the page number
     * @param size       the size of page
     * @param sort       sorting type
     * @return page of machines
     */
    @Override
    public ResponseEntity<?> getAllMachinePaging(Long facilityId, Integer page, Integer size, String sort) {
        // Get sorting type
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("machineId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("machineId").descending();
        }
        // Get all customers of the current User with Paging
        Page<Machine> machinePage = machineRepository.findAllByFacilityId(facilityId, PageRequest.of(page, size, sortable));
        return new ResponseEntity<>(machinePage, HttpStatus.OK);
    }
}
