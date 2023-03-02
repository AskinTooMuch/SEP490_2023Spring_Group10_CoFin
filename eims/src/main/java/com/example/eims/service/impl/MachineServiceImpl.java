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
import com.example.eims.service.interfaces.IMachineService;
import org.springframework.http.ResponseEntity;

public class MachineServiceImpl implements IMachineService {
    /**
     * Get all of their machines.
     *
     * @param facilityId the id of current logged-in user's selected facility.
     * @return list of Machines
     */
    @Override
    public ResponseEntity<?> getAllMachine(Long facilityId) {
        return null;
    }

    /**
     * Get a machine.
     *
     * @param machineId the id of the machine
     * @return
     */
    @Override
    public ResponseEntity<?> getMachine(Long machineId) {
        return null;
    }

    /**
     * Create a machine of a facility.
     *
     * @param createMachineDTO contains the facility id, machine type id, name, max and current capacity
     * @return
     */
    @Override
    public ResponseEntity<?> createMachine(CreateMachineDTO createMachineDTO) {
        return null;
    }

    /**
     * Show form update a machine.
     *
     * @param machineId the id of the machine
     * @return
     */
    @Override
    public ResponseEntity<?> showFormUpdate(Long machineId) {
        return null;
    }

    /**
     * Update a machine of a facility.
     *
     * @param machineId        the id of the machine
     * @param updateMachineDTO contains the facility id, machine type id, name, max and current capacity, status
     * @return
     */
    @Override
    public ResponseEntity<?> updateMachine(Long machineId, UpdateMachineDTO updateMachineDTO) {
        return null;
    }

    /**
     * Delete a machine of a facility.
     *
     * @param machineId the id of the machine
     * @return
     */
    @Override
    public ResponseEntity<?> deleteCustomer(Long machineId) {
        return null;
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
    public ResponseEntity<?> getAllSupplierPaging(Long facilityId, Integer page, Integer size, String sort) {
        return null;
    }
}
