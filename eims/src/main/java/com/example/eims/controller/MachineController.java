/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 18/02/2023    1.0        DuongVV          First Deploy<br>
 * 28/02/2023    2.0        DuongVV          Add Paging<br>
 * 02/03/2023    3.0        DuongVV          New code structure<br>
 */

package com.example.eims.controller;

import com.example.eims.dto.machine.CreateMachineDTO;
import com.example.eims.dto.machine.UpdateMachineDTO;
import com.example.eims.service.interfaces.IMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/machine")
public class MachineController {
    @Autowired
    private IMachineService machineService;

    /**
     * Get all of their machines.
     *
     * @param facilityId the id of current logged-in user's selected facility.
     * @return list of Machines
     */
    @Secured({"ROLE_OWNER"})
    @GetMapping("/all")
    public ResponseEntity<?> getAllMachine(@RequestParam Long facilityId) {
        return machineService.getAllMachine(facilityId);
    }

    /**
     * Get a machine.
     *
     * @param machineId the id of the machine
     * @return
     */
    @Secured({"ROLE_OWNER"})
    @GetMapping("/get")
    public ResponseEntity<?> getMachine(@RequestParam Long machineId) {
        return machineService.getMachine(machineId);
    }

    /**
     * Create a machine of a facility.
     *
     * @param createMachineDTO contains the facility id, machine type id, name, max and current capacity
     * @return
     */
    @Secured({"ROLE_OWNER"})
    @PostMapping("/create")
    public ResponseEntity<?> createMachine(@RequestBody CreateMachineDTO createMachineDTO) {
        return machineService.createMachine(createMachineDTO);
    }

    /**
     * Show form update a machine.
     *
     * @param machineId the id of the machine
     * @return
     */
    @Secured({"ROLE_OWNER"})
    @GetMapping("/update/get")
    public ResponseEntity<?> showFormUpdate(@RequestParam Long machineId) {
        return machineService.showFormUpdate(machineId);
    }

    /**
     * Update a machine of a facility.
     *
     * @param updateMachineDTO contains the facility id, machine type id, name, max and current capacity, status
     * @return
     */
    @Secured({"ROLE_OWNER"})
    @PutMapping("/update/save")
    public ResponseEntity<?> updateMachine(@RequestBody UpdateMachineDTO updateMachineDTO) {
        return machineService.updateMachine(updateMachineDTO);
    }

    /**
     * Delete a machine of a facility.
     *
     * @param machineId the id of the machine
     * @return
     */
    @Secured({"ROLE_OWNER"})
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMachine(@RequestParam Long machineId) {
        return machineService.deleteMachine(machineId);
    }

    /**
     * Get all of facility's machines with Paging.
     * facilityId is the id of current logged-in user's facility.
     *
     * @param facilityId
     * @param page the page number
     * @param size the size of page
     * @param sort sorting type
     * @return page of machines
     */
    @GetMapping("/allPaging")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> getAllMachinePaging(@RequestParam(name = "facilityId") Long facilityId,
                                                  @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                                  @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
        return machineService.getAllMachinePaging(facilityId, page, size, sort);
    }
}
