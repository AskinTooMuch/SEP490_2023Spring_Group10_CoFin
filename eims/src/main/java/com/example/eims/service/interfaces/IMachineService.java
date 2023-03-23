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

package com.example.eims.service.interfaces;

import com.example.eims.dto.machine.CreateMachineDTO;
import com.example.eims.dto.machine.UpdateMachineDTO;
import org.springframework.http.ResponseEntity;

public interface IMachineService {
    /**
     * Get all of their machines.
     *
     * @param facilityId the id of current logged-in user's selected facility.
     * @return list of Machines
     */
    public ResponseEntity<?> getAllMachine(Long facilityId);

    /**
     * Get a machine.
     *
     * @param machineId the id of the machine
     * @return
     */
    public ResponseEntity<?> getMachine(Long machineId);

    /**
     * Get machines not full.
     *
     * @param facilityId the id of current logged-in user's selected facility.
     * @return
     */
    public ResponseEntity<?> getMachinesNotFull(Long facilityId);

    /**
     * Create a machine of a facility.
     *
     * @param createMachineDTO contains the facility id, machine type id, name, max and current capacity
     * @return
     */
    public ResponseEntity<?> createMachine(CreateMachineDTO createMachineDTO);

    /**
     * Show form update a machine.
     *
     * @param machineId the id of the machine
     * @return
     */
    public ResponseEntity<?> showFormUpdate(Long machineId);

    /**
     * Update a machine of a facility.
     *
     * @param updateMachineDTO contains the facility id, machine type id, name, max and current capacity, status
     * @return
     */
    public ResponseEntity<?> updateMachine(UpdateMachineDTO updateMachineDTO);

    /**
     * Delete a machine of a facility.
     *
     * @param machineId the id of the machine
     * @return
     */
    public ResponseEntity<?> deleteMachine(Long machineId);

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
    public ResponseEntity<?> getAllMachinePaging(Long facilityId, Integer page, Integer size, String sort);
}
