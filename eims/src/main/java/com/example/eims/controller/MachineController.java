/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 18/02/2023    1.0        DuongVV          First Deploy<br>
 */

package com.example.eims.controller;

import com.example.eims.dto.machine.CreateMachineDTO;
import com.example.eims.dto.machine.UpdateMachineDTO;
import com.example.eims.entity.Machine;
import com.example.eims.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/machine")
public class MachineController {
    @Autowired
    private MachineRepository machineRepository;

    /**
     * API to get all of their machines.
     * facilityId is the id of current logged-in user's selected facility.
     *
     * @param facilityId
     * @return list of Machines
     */
    @GetMapping("/all")
    public List<Machine> getAllMachine(Long facilityId) {
        // Get all machines of the current facility
        List<Machine> machineList = machineRepository.findByFacilityId(facilityId);
        return machineList;
    }

    /**
     * API to get a machine.
     * machineId is the id of the machine
     *
     * @param machineId
     * @return
     */
    @GetMapping("/{machineId}")
    public Machine getMachine(@PathVariable Long machineId) {
        // Get a machine of the current facility
        Machine machine = machineRepository.findById(machineId).get();
        return machine;
    }

    /**
     * API to create a machine of a facility.
     * createMachineDTO contains the facility id, machine type id, name, max and current capacity
     *
     * @param createMachineDTO
     *
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<?> createMachine(CreateMachineDTO createMachineDTO) {
        // Retrieve machine information and create new machine
        Machine machine = new Machine();
        machine.setMachineTypeId(createMachineDTO.getMachineTypeId());
        machine.setFacilityId(createMachineDTO.getFacilityId());
        machine.setMachineName(createMachineDTO.getName());
        machine.setMaxCapacity(createMachineDTO.getMaxCapacity());
        machine.setCurCapacity(0);
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        Date addedDate;
        try {
            addedDate = new Date(
                    (new SimpleDateFormat("yyyy-mm-dd").parse(date)).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        machine.setAddedDate(addedDate);
        machine.setActive(0);
        // Save
        machineRepository.save(machine);
        return new ResponseEntity<>("Machine created!", HttpStatus.OK);
    }

    /**
     * API to update a machine of a facility.
     * The DTO contains the facility id, machine type id, name, max and current capacity, status
     * id is the id of the machine
     *
     * @param updateMachineDTO
     * @param machineId
     * @return
     */
    @PutMapping("/update/{machineId}")
    public ResponseEntity<?> updateMachine(@PathVariable Long machineId, UpdateMachineDTO updateMachineDTO) {
        // Retrieve machine's new information
        Machine machine = machineRepository.findByMachineId(machineId);
        machine.setMachineName(updateMachineDTO.getName());
        machine.setMaxCapacity(updateMachineDTO.getMaxCapacity());
        machine.setCurCapacity(updateMachineDTO.getCurCapacity());
        machine.setActive(updateMachineDTO.getActive());
        // Save
        machineRepository.save(machine);
        return new ResponseEntity<>("Machine updated!", HttpStatus.OK);
    }

    /**
     * API to delete a machine of a facility.
     * id is the id of the machine
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        // Delete
        machineRepository.deleteById(id);
        return new ResponseEntity<>("Machine deleted!", HttpStatus.OK);
    }
}
