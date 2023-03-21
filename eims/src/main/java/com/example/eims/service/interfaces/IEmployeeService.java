/*
 * Copyright (C) 2023, FPT University<br>
 * SEP490 - SEP490_G10<br>
 * EIMS<br>
 * Eggs Incubating Management System<br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 15/03/2023   1.0         DuongVV     First Deploy<br>
 */
package com.example.eims.service.interfaces;

import com.example.eims.dto.employee.CreateEmployeeDTO;
import com.example.eims.dto.employee.UpdateEmployeeDTO;
import org.springframework.http.ResponseEntity;

public interface IEmployeeService {

    /**
     * Service to create new employee
     * Newly added employee will have the status of 1
     * @param createEmployeeDTO Payload
     * @return New employee or response message
     */
    ResponseEntity<?> createNewEmployee(CreateEmployeeDTO createEmployeeDTO);

    /**
     * Service to update existing employee in the system
     * @param updateEmployeeDTO Payload
     * @return Updated employee information or message
     */
    ResponseEntity<?> updateEmployee(UpdateEmployeeDTO updateEmployeeDTO);

    /**
     * Service to delete employee
     * @param employeeId employee id to be deleted
     * @return response message
     */
    ResponseEntity<?> deleteEmployee(Long employeeId);

    /**
     * Service to get all information of an employee
     * @param employeeId Employee id
     * @return Employee information
     */
    ResponseEntity<?> viewEmployeeById(Long employeeId);

    /**
     * Service to view all employee of a facility
     * @param facilityId Facility id
     * @return List employee
     */
    ResponseEntity<?> viewAllEmployee(Long facilityId);

    /**
     * Find employee by name or phone number
     * @param facilityId facility id
     * @param employeePhone employee phone number
     * @return employee list
     */
    ResponseEntity<?> searchEmployeeByNameOrPhone(Long facilityId, String employeePhone);
}
