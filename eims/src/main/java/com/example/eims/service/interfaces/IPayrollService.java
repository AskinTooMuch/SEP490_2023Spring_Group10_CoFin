package com.example.eims.service.interfaces;

import com.example.eims.dto.payroll.CreatePayrollDTO;
import com.example.eims.dto.payroll.UpdatePayrollDTO;
import org.springframework.http.ResponseEntity;

public interface IPayrollService {

    /**
     * get all payroll create by owner with that owner id
     * @param ownerId owner's id
     * @return list payroll
     */
    public ResponseEntity<?> getAllPayroll(Long ownerId);

    /**
     * return payroll with that id
     * @param payrollId payroll's id
     * @return Payroll detail information
     */
    public ResponseEntity<?> getPayrollByID(Long payrollId);

    /**
     * Save new payroll to the database
     * @param createPayrollDTO information of payroll to be created
     * @return message
     */
    public ResponseEntity<?> createPayroll(CreatePayrollDTO createPayrollDTO);

    /**
     * Update payroll new information to the database
     * @param updatePayrollDTO payroll's new information
     * @return message
     */
    public ResponseEntity<?> updatePayroll(UpdatePayrollDTO updatePayrollDTO);

    /**
     * return payroll match search keyword
     * @param ownerId owner's id
     * @param searchKey search keyword
     * @return list of payroll
     */
    public ResponseEntity<?> searchPayroll(Long ownerId, String searchKey);

}
