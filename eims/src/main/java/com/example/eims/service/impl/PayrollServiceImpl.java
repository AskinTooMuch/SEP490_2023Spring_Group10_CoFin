/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 29/03/2023    1.0        DuongNH          First Deploy<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.payroll.CreatePayrollDTO;
import com.example.eims.dto.payroll.PayrollDetailDTO;
import com.example.eims.dto.payroll.UpdatePayrollDTO;
import com.example.eims.entity.Payroll;
import com.example.eims.entity.User;
import com.example.eims.entity.WorkIn;
import com.example.eims.repository.FacilityRepository;
import com.example.eims.repository.PayrollRepository;
import com.example.eims.repository.UserRepository;
import com.example.eims.repository.WorkInRepository;
import com.example.eims.service.interfaces.IPayrollService;
import com.example.eims.utils.StringDealer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PayrollServiceImpl implements IPayrollService {

    private final StringDealer stringDealer = new StringDealer();

    @Autowired
    PayrollRepository payrollRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    WorkInRepository workInRepository;
    @Autowired
    FacilityRepository facilityRepository;

    public PayrollServiceImpl(PayrollRepository payrollRepository, UserRepository userRepository, WorkInRepository workInRepository, FacilityRepository facilityRepository) {
        this.payrollRepository = payrollRepository;
        this.userRepository = userRepository;
        this.workInRepository = workInRepository;
        this.facilityRepository = facilityRepository;
    }

    /**
     * get all payroll create by owner with that owner id
     *
     * @param ownerId owner's id
     * @return list payroll
     */
    @Override
    public ResponseEntity<?> getAllPayroll(Long ownerId) {
        if(!userRepository.existsById(ownerId)){
            return new ResponseEntity<>("Tài khoản không hợp lệ", HttpStatus.BAD_REQUEST);
        }

        Optional<List<Payroll>> payrollListOtp = payrollRepository.findAllByOwnerId(ownerId);
        if(!payrollListOtp.isPresent()){
            return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        List<Payroll> payrollList = payrollListOtp.get();
        List<PayrollDetailDTO> payrollDetailDTOList = new ArrayList<>();
        for (Payroll payroll: payrollList ) {
            Long employeeId = payroll.getEmployeeId();
            Optional<User> employeeOpt = userRepository.findByUserId(employeeId);
            if(employeeOpt.isPresent()){
                User employee = employeeOpt.get();
                PayrollDetailDTO payrollDetailDTO = new PayrollDetailDTO();
                payrollDetailDTO.getFromEntity(payroll,employee.getUsername());
                payrollDetailDTOList.add(payrollDetailDTO);
            }
        }
        return new ResponseEntity<>(payrollDetailDTOList, HttpStatus.OK);
    }

    /**
     * return payroll with that id
     *
     * @param payrollId payroll's id
     * @return Payroll detail information
     */
    @Override
    public ResponseEntity<?> getPayrollByID(Long payrollId) {
        Optional<Payroll> payrollListOtp = payrollRepository.findByPayrollId(payrollId);
        if(!payrollListOtp.isPresent()){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Payroll payroll = payrollListOtp.get();
        PayrollDetailDTO payrollDetailDTO = new PayrollDetailDTO();
        Long employeeId = payroll.getEmployeeId();
        Optional<User> employeeOpt = userRepository.findByUserId(employeeId);
        if(employeeOpt.isPresent()){
            User employee = employeeOpt.get();
            payrollDetailDTO.getFromEntity(payroll,employee.getUsername());
            return new ResponseEntity<>(payrollDetailDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    /**
     * Save new payroll to the database
     *
     * @param createPayrollDTO information of payroll to be created
     * @return message
     */
    @Override
    public ResponseEntity<?> createPayroll(CreatePayrollDTO createPayrollDTO) {
        //check if employee exist in the system
        Optional<User> employee = userRepository.findByUserId(createPayrollDTO.getEmployeeId());
        if (!employee.isPresent()){
            return new ResponseEntity<>("Nhân viên không tồn tại",HttpStatus.BAD_REQUEST);
        }
        //check if employee still working
        Optional<WorkIn> workInOpt = workInRepository.findByUserId(createPayrollDTO.getEmployeeId());
        if(!workInOpt.isPresent()){
            return new ResponseEntity<>("Nhân viên đã nghỉ việc",HttpStatus.BAD_REQUEST);
        }
        //check if facility still working
        Long facilityId = workInOpt.get().getFacilityId();
        if(!facilityRepository.getStatusById(facilityId)){
            return new ResponseEntity<>("Cơ sở đã ngừng hoạt động", HttpStatus.BAD_REQUEST);
        }
        //check if owner id valid
        if(!userRepository.existsById(createPayrollDTO.getOwnerId())){
            return new ResponseEntity<>("Tài khoản không hợp lệ", HttpStatus.BAD_REQUEST);
        }
        //check payroll item
        if(createPayrollDTO.getPayrollItem() == null || stringDealer.trimMax(createPayrollDTO.getPayrollItem()).equals("")){
            return new ResponseEntity<>("Khoản tiền không được để trống", HttpStatus.BAD_REQUEST);
        }
        String payrollItem = stringDealer.trimMax(createPayrollDTO.getPayrollItem());
        if (payrollItem.length() > 50){
            return new ResponseEntity<>("Khoản tiền không được dài quá 50 ký tự", HttpStatus.BAD_REQUEST);
        }
        //check payroll amount
        Float payrollAmount = createPayrollDTO.getPayrollAmount();
        if(payrollAmount <= 0 ){
            return new ResponseEntity<>("Số tiền phải lớn hơn 0", HttpStatus.BAD_REQUEST);
        }
        // Paid date
        if (createPayrollDTO.getIssueDate() == null || stringDealer.trimMax(createPayrollDTO.getIssueDate()).equals("")) { /* Date of birth is empty */
            return new ResponseEntity<>("Ngày trả không được để trống", HttpStatus.BAD_REQUEST);
        }
        String sDate = stringDealer.trimMax(createPayrollDTO.getIssueDate());
        String note = "";
        if(createPayrollDTO.getNote() != null && !stringDealer.trimMax(createPayrollDTO.getNote()).equals("")){
            note = stringDealer.trimMax(createPayrollDTO.getNote());
        }
        if(note.length() > 255){
            return new ResponseEntity<>("Ghi chú không quá 255 ký tự", HttpStatus.BAD_REQUEST);
        }
        //set up payroll information
        Payroll newPayroll = new Payroll();
        newPayroll.setOwnerId(createPayrollDTO.getOwnerId());
        newPayroll.setEmployeeId(createPayrollDTO.getEmployeeId());
        newPayroll.setPhone(employee.get().getPhone());
        newPayroll.setPayrollItem(payrollItem);
        newPayroll.setPayrollAmount(payrollAmount);
        Date date = stringDealer.convertToDateAndFormat(sDate);
        newPayroll.setIssueDate(date);
        newPayroll.setNote(note);
        newPayroll.setStatus(true);

        //save new payroll
        payrollRepository.save(newPayroll);
        return new ResponseEntity<>("Thêm tiền lương thành công", HttpStatus.OK);
    }

    /**
     * Update payroll new information to the database
     *
     * @param updatePayrollDTO payroll's new information
     * @return message
     */
    @Override
    public ResponseEntity<?> updatePayroll(UpdatePayrollDTO updatePayrollDTO) {
        Optional<Payroll> oldPayrollOpt = payrollRepository.findByPayrollId(updatePayrollDTO.getPayrollId());
        if(!oldPayrollOpt.isPresent()){
            return new ResponseEntity<>("Khoản tiền lương không tồn tại", HttpStatus.BAD_REQUEST);
        }
        Payroll updatePayroll = oldPayrollOpt.get();

        //check payroll item
        if(updatePayrollDTO.getPayrollItem() == null || stringDealer.trimMax(updatePayrollDTO.getPayrollItem()).equals("")){
            return new ResponseEntity<>("Khoản tiền không được để trống", HttpStatus.BAD_REQUEST);
        }
        String payrollItem = stringDealer.trimMax(updatePayrollDTO.getPayrollItem());
        if (payrollItem.length() > 50){
            return new ResponseEntity<>("Khoản tiền không được dài quá 50 ký tự", HttpStatus.BAD_REQUEST);
        }
        //check payroll amount
        Float payrollAmount = updatePayrollDTO.getPayrollAmount();
        if(payrollAmount <= 0 ){
            return new ResponseEntity<>("Số tiền phải lớn hơn 0", HttpStatus.BAD_REQUEST);
        }
        // Paid date
        if (updatePayrollDTO.getIssueDate() == null || stringDealer.trimMax(updatePayrollDTO.getIssueDate()).equals("")) { /* Date of birth is empty */
            return new ResponseEntity<>("Ngày trả không được để trống", HttpStatus.BAD_REQUEST);
        }
        String sDate = stringDealer.trimMax(updatePayrollDTO.getIssueDate());
        Date date = stringDealer.convertToDateAndFormat(sDate);
        //check valid note
        String note = "";
        if(updatePayrollDTO.getNote() != null && !stringDealer.trimMax(updatePayrollDTO.getNote()).equals("")){
            note = stringDealer.trimMax(updatePayrollDTO.getNote());
        }
        if(note.length() > 255){
            return new ResponseEntity<>("Ghi chú không quá 255 ký tự", HttpStatus.BAD_REQUEST);
        }

        //set new information
        updatePayroll.setPayrollItem(payrollItem);
        updatePayroll.setPayrollAmount(payrollAmount);
        updatePayroll.setIssueDate(date);
        updatePayroll.setNote(note);

        //update payroll
        payrollRepository.save(updatePayroll);
        return new ResponseEntity<>("Cập nhật tiền lương thành công", HttpStatus.OK);
    }

    /**
     * return payroll match search keyword
     *
     * @param ownerId   owner's id
     * @param searchKey search keyword
     * @return list of payroll
     */
    @Override
    public ResponseEntity<?> searchPayroll(Long ownerId, String searchKey) {
        if(!userRepository.existsById(ownerId)){
            return new ResponseEntity<>("Tài khoản không hợp lệ", HttpStatus.BAD_REQUEST);
        }

        if(searchKey == null || stringDealer.trimMax(searchKey).equals("")){
            return new ResponseEntity<>("Nhập từ khóa để tìm kiếm", HttpStatus.BAD_REQUEST);
        }
        String searchKey2 = stringDealer.trimMax(searchKey);
        Optional<List<Payroll>> payrollListOtp = payrollRepository.searchPayroll(ownerId,searchKey2);
        if(!payrollListOtp.isPresent() || payrollListOtp.get().isEmpty()){
            return new ResponseEntity<>("Không tìm thấy khoản tiền lương phù hợp", HttpStatus.BAD_REQUEST);
        }

        List<Payroll> payrollList = payrollListOtp.get();
        List<PayrollDetailDTO> payrollDetailDTOList = new ArrayList<>();
        for (Payroll payroll: payrollList ) {
            Long employeeId = payroll.getEmployeeId();
            Optional<User> employeeOpt = userRepository.findByUserId(employeeId);
            if(employeeOpt.isPresent()){
                User employee = employeeOpt.get();
                PayrollDetailDTO payrollDetailDTO = new PayrollDetailDTO();
                payrollDetailDTO.getFromEntity(payroll,employee.getUsername());
                payrollDetailDTOList.add(payrollDetailDTO);
            }
        }
        return new ResponseEntity<>(payrollDetailDTOList, HttpStatus.OK);
    }

}
