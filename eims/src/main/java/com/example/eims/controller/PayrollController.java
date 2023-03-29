package com.example.eims.controller;

import com.example.eims.dto.payroll.CreatePayrollDTO;
import com.example.eims.dto.payroll.UpdatePayrollDTO;
import com.example.eims.service.interfaces.IPayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/payroll")
public class PayrollController {
    @Autowired
    IPayrollService payrollService;

    /**
     * Get all payroll of an owner
     * @param ownerId owner's id
     * @return list payroll
     */
    @GetMapping("/all")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> getAllPayroll(@RequestParam Long ownerId){
        return payrollService.getAllPayroll(ownerId);
    }

    /**
     * get all information of a payroll
     * @param payrollId payroll's id
     * @return return payroll detail
     */
    @Secured({"ROLE_OWNER"})
    @GetMapping("/get")
    public ResponseEntity<?> getPayrollById(@RequestParam Long payrollId){
        return payrollService.getPayrollByID(payrollId);
    }

    /**
     * save new payroll information to the database
     * @param createPayrollDTO information of new payroll
     * @return message
     */
    @PostMapping("/create")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> createPayroll(@RequestBody CreatePayrollDTO createPayrollDTO){
        return payrollService.createPayroll(createPayrollDTO);
    }


    /**
     * Update new payroll information to the database
     * @param updatePayrollDTO new information of the payroll
     * @return message
     */
    @Secured({"ROLE_OWNER"})
    @PostMapping("/update/save")
    public ResponseEntity<?> updatePayroll(@RequestBody UpdatePayrollDTO updatePayrollDTO){
        return payrollService.updatePayroll(updatePayrollDTO);
    }

    /**
     * Return payroll list base on search keyword
     * @param ownerId ownerId
     * @param searchKey search keyword
     * @return list payroll
     */
    @GetMapping("/search")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> searchCost(@RequestParam Long ownerId,@RequestParam String searchKey){
        return payrollService.searchPayroll(ownerId,searchKey);
    }
}
