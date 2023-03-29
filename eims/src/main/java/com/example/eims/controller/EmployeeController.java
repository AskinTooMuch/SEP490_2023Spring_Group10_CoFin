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

package com.example.eims.controller;

import com.example.eims.dto.employee.CreateEmployeeDTO;
import com.example.eims.dto.employee.UpdateEmployeeDTO;
import com.example.eims.service.interfaces.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    /**
     * Get all employee of a facility
     * @param facilityId the id of the facility
     * @return list of employee
     */
    @GetMapping("/all")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> getAllEmployee(@RequestParam Long facilityId){
        return employeeService.viewAllEmployee(facilityId);
    }

    /**
     * function to search for employee
     * @param facilityId faccility id
     * @param searchKey keyword for search
     * @return list of employee
     */
    @GetMapping("/search")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> searchEmployeeByPhoneOrName(@RequestParam Long facilityId, @RequestParam String searchKey){
        return employeeService.searchEmployeeByNameOrPhone(facilityId,searchKey);
    }

    /**
     * Create new employee
     * @param createEmployeeDTO contain all employee's information
     * @return response message
     */
    @PostMapping("/create")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> createNewEmployee(@RequestBody CreateEmployeeDTO createEmployeeDTO){
        return employeeService.createNewEmployee(createEmployeeDTO);
    }

    /**
     * View an employee information
     * @param employeeId the id of employee
     * @return employee information
     */
    @GetMapping("/get")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> getEmployeeById(@RequestParam Long employeeId){
        return employeeService.viewEmployeeById(employeeId);
    }

    @GetMapping("/update/get")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> getUpdateEmployeeById(@RequestParam Long employeeId){
        return employeeService.viewEmployeeById(employeeId);
    }

    @PostMapping("/update/save")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> updateEmployee(@RequestBody UpdateEmployeeDTO updateEmployeeDTO){
        return employeeService.updateEmployee(updateEmployeeDTO);
    }

    @DeleteMapping("/delete")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> deleteEmployee(@RequestParam Long employeeId){
        return employeeService.deleteEmployee(employeeId);
    }
}
