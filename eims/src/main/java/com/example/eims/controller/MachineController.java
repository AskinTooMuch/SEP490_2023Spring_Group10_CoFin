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

import java.sql.Date;
import java.time.LocalDate;
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
    public ResponseEntity<?> getAllMachine(@RequestParam Long facilityId) {
        // Get all machines of the current facility
        List<Machine> machineList = machineRepository.findByFacilityId(facilityId);
        return new ResponseEntity<>(machineList, HttpStatus.OK);
    }

    /**
     * API to get a machine.
     * machineId is the id of the machine
     *
     * @param machineId
     * @return
     */
    @GetMapping("/get")
    public ResponseEntity<?> getMachine(@RequestParam Long machineId) {
        // Get a machine of the current facility
        Machine machine = machineRepository.findById(machineId).orElse(null);
        if (machine != null) {
            return new ResponseEntity<>(machine, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No machine", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * API to create a machine of a facility.
     * createMachineDTO contains the facility id, machine type id, name, max and current capacity
     *
     * @param createMachineDTO
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<?> createMachine(@RequestBody CreateMachineDTO createMachineDTO) {
        // Retrieve machine information and create new machine
        Machine machine = new Machine();
        machine.setMachineTypeId(createMachineDTO.getMachineTypeId());
        machine.setFacilityId(createMachineDTO.getFacilityId());
        machine.setMachineName(createMachineDTO.getName());
        machine.setMaxCapacity(createMachineDTO.getMaxCapacity());
        machine.setCurCapacity(0);
        Date date = Date.valueOf(LocalDate.now());/* format yyyy-MM-dd*/

        /*
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(date);
        Date addedDate;
        try {
            addedDate = new Date(format.parse(dateString).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        machine.setAddedDate(addedDate);
        */

        machine.setAddedDate(date);
        machine.setActive(0);
        // Save
        machineRepository.save(machine);
        return new ResponseEntity<>("Machine created!", HttpStatus.OK);
    }

    /**
     * API to show form update a machine.
     * machineId is the id of the machine
     *
     * @param machineId
     * @return
     */
    @GetMapping("/update/get")
    public ResponseEntity<?> showFormUpdate(@RequestParam Long machineId) {
        // Get a machine of the current facility
        Machine machine = machineRepository.findById(machineId).orElse(null);
        if (machine != null) {
            return new ResponseEntity<>(machine, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No machine", HttpStatus.BAD_REQUEST);
        }
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
    @PutMapping("/update/save")
    public ResponseEntity<?> updateMachine(@RequestParam Long machineId, @RequestBody UpdateMachineDTO updateMachineDTO) {
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
     * machineId is the id of the machine
     *
     * @param machineId
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCustomer(@RequestParam Long machineId) {
        // Delete
        machineRepository.deleteById(machineId);
        return new ResponseEntity<>("Machine deleted!", HttpStatus.OK);
    }
}
