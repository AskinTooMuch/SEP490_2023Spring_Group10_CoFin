/*
 * Copyright (C) 2023, FPT University<br>
 * SEP490 - SEP490_G10<br>
 * EIMS<br>
 * Eggs Incubating Management System<br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 15/03/2023   1.0         DuongVV     First Deploy<br>
 * 23/03/2023   1.1         ChucNV      Update code according to new relations<br>
 */
package com.example.eims.service.impl;

import com.example.eims.dto.employee.CreateEmployeeDTO;
import com.example.eims.dto.employee.EmployeeDetailDTO;
import com.example.eims.dto.employee.EmployeeListItemDTO;
import com.example.eims.dto.employee.UpdateEmployeeDTO;
import com.example.eims.entity.*;
import com.example.eims.repository.FacilityRepository;
import com.example.eims.repository.SalaryHistoryRepository;
import com.example.eims.repository.UserRepository;
import com.example.eims.repository.WorkInRepository;
import com.example.eims.service.interfaces.IEmployeeService;
import com.example.eims.utils.StringDealer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private final StringDealer stringDealer = new StringDealer();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkInRepository workInRepository;
    @Autowired
    private SalaryHistoryRepository salaryHistoryRepository;

    @Autowired
    FacilityRepository facilityRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public EmployeeServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, WorkInRepository workInRepository,
                               FacilityRepository facilityRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.workInRepository = workInRepository;
        this.facilityRepository = facilityRepository;
    }

    /**
     * Service to create new employee
     * Newly added employee will have the status of 1
     *
     * @param createEmployeeDTO Payload
     * @return New employee or response message
     */
    @Override
    public ResponseEntity<?> createNewEmployee(CreateEmployeeDTO createEmployeeDTO) {
        Optional<Facility> facility = facilityRepository.findByFacilityId(createEmployeeDTO.getFacilityId());
        if(!facility.isPresent()){
            return new ResponseEntity<>("Cơ sở hiện không được hoạt động", HttpStatus.BAD_REQUEST);
        }
        if (createEmployeeDTO.getEmployeeName() == null || stringDealer.trimMax(createEmployeeDTO.getEmployeeName()).equals("")) { /* Username is empty */
            return new ResponseEntity<>("Tên không được để trống", HttpStatus.BAD_REQUEST);
        }
        String employeeName = stringDealer.trimMax(createEmployeeDTO.getEmployeeName());
        // Date of birth
        if (createEmployeeDTO.getEmployeeDob() == null || stringDealer.trimMax(createEmployeeDTO.getEmployeeDob()).equals("")) { /* Date of birth is empty */
            return new ResponseEntity<>("Ngày sinh không được để trống", HttpStatus.BAD_REQUEST);
        }
        String sDate = stringDealer.trimMax(createEmployeeDTO.getEmployeeDob());
        // Phone number
        if (createEmployeeDTO.getEmployeePhone() == null || stringDealer.trimMax(createEmployeeDTO.getEmployeePhone()).equals("")) { /* Phone number is empty */
            return new ResponseEntity<>("Số điện thoại không được để trống", HttpStatus.BAD_REQUEST);
        }
        String phone = stringDealer.trimMax(createEmployeeDTO.getEmployeePhone());
        if (!stringDealer.checkPhoneRegex(phone)) { /* Phone number is not valid*/
            return new ResponseEntity<>("Số điện thoại không đúng đinh dạng", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByPhone(phone)) {/* Phone number is existed*/
            return new ResponseEntity<>("Số điện thoại đã được sử dụng", HttpStatus.BAD_REQUEST);
        }
        // Email
        String email = "";
        if (createEmployeeDTO.getEmail() != null){
            email = stringDealer.trimMax(createEmployeeDTO.getEmail());
            if ((!email.equals("")) && !stringDealer.checkEmailRegex(email)) { /* Email is not valid */
                return new ResponseEntity<>("Email không đúng định dạng", HttpStatus.BAD_REQUEST);
            }
        }
        // Address
        if (createEmployeeDTO.getEmployeeAddress() == null || stringDealer.trimMax(createEmployeeDTO.getEmployeeAddress()).equals("")) { /* Address is empty */
            return new ResponseEntity<>("Địa chỉ không được để trống", HttpStatus.BAD_REQUEST);
        }
        String address = stringDealer.trimMax(createEmployeeDTO.getEmployeeAddress());
        // Password
        if (createEmployeeDTO.getEmployeePassword() == null || stringDealer.trimMax(createEmployeeDTO.getEmployeePassword()).equals("")) { /* Password is empty */
            return new ResponseEntity<>("Mật khẩu không được để trống", HttpStatus.BAD_REQUEST);
        }
        String password = stringDealer.trimMax(createEmployeeDTO.getEmployeePassword());
        if (!stringDealer.checkPasswordRegex(password)) { /* Password is not valid */
            return new ResponseEntity<>("Mật khẩu không đúng định dạng", HttpStatus.BAD_REQUEST);
        }
        Float salary = createEmployeeDTO.getSalary();

        //set employee information
        User employeeInformation = new User();
        employeeInformation.setUsername(employeeName);
        employeeInformation.setEmail(email);
        Date date = stringDealer.convertToDateAndFormat(sDate);
        employeeInformation.setDob(date);
        employeeInformation.setPhone(phone);
        employeeInformation.setAddress(address);
        List roleList = new ArrayList<Role>();
        roleList.add(new Role(3, "ROLE_EMPLOYEE", true));
        employeeInformation.setRoles(roleList);     /* Role EMPLOYEE */
        employeeInformation.setStatus(1);
        employeeInformation.setPassword(passwordEncoder.encode(password));
        employeeInformation.setSalary(salary);

        //create new salary history
        SalaryHistory salaryHistory = new SalaryHistory();
        salaryHistory.setSalary(salary);
        Date issueDate = Date.valueOf(LocalDate.now());
        salaryHistory.setIssueDate(issueDate);

        try {
            User responseUser = userRepository.save(employeeInformation);
            //save work in
            WorkIn workIn = new WorkIn();
            workIn.setUserId(responseUser.getUserId());
            workIn.setFacilityId(createEmployeeDTO.getFacilityId());
            workIn.setStatus(1);
            workInRepository.save(workIn);

            //save salary history
            salaryHistory.setUserId(responseUser.getUserId());
            salaryHistoryRepository.save(salaryHistory);
            return new ResponseEntity<>("Thêm nhân viên thành công", HttpStatus.OK);
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Service to update existing employee in the system
     *
     * @param updateEmployeeDTO Payload
     * @return Updated employee information or message
     */
    @Override
    public ResponseEntity<?> updateEmployee(UpdateEmployeeDTO updateEmployeeDTO) {
        //facility check
        Optional<Facility> facility = facilityRepository.findByFacilityId(updateEmployeeDTO.getFacilityId());
        if(!facility.isPresent()){
            return new ResponseEntity<>("Cơ sở hiện không được hoạt động", HttpStatus.BAD_REQUEST);
        }

        Long employeeId = updateEmployeeDTO.getEmployeeId();
        Optional<User> oldEmployeeInfo = userRepository.findByUserId(employeeId);
        //check if employee id exist
        if(!oldEmployeeInfo.isPresent()){
            return new ResponseEntity<>("Nhân viên không còn tồn tại trên hệ thống", HttpStatus.BAD_REQUEST);
        }
        //check if employee still working
        Optional<WorkIn> workInOtp = workInRepository.findByUserId(employeeId);
        if (workInOtp.isPresent()){
            WorkIn workIn = workInOtp.get();
            if(!workIn.getFacilityId().equals(updateEmployeeDTO.getFacilityId())){
                return new ResponseEntity<>("Nhân viên không còn tồn tại trên hệ thống", HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>("Nhân viên không còn tồn tại trên hệ thống", HttpStatus.BAD_REQUEST);
        }

        if (updateEmployeeDTO.getEmployeeName() == null || stringDealer.trimMax(updateEmployeeDTO.getEmployeeName()).equals("")) { /* Username is empty */
            return new ResponseEntity<>("Tên không được để trống", HttpStatus.BAD_REQUEST);
        }
        String employeeName = stringDealer.trimMax(updateEmployeeDTO.getEmployeeName());
        // Date of birth
        if (updateEmployeeDTO.getEmployeeDob() == null || stringDealer.trimMax(updateEmployeeDTO.getEmployeeDob()).equals("")) { /* Date of birth is empty */
            return new ResponseEntity<>("Ngày sinh không được để trống", HttpStatus.BAD_REQUEST);
        }
        String sDate = stringDealer.trimMax(updateEmployeeDTO.getEmployeeDob());
        // Phone number
        if (updateEmployeeDTO.getEmployeePhone() == null || stringDealer.trimMax(updateEmployeeDTO.getEmployeePhone()).equals("")) { /* Phone number is empty */
            return new ResponseEntity<>("Số điện thoại không được để trống", HttpStatus.BAD_REQUEST);
        }
        String phone = stringDealer.trimMax(updateEmployeeDTO.getEmployeePhone());
        if (!stringDealer.checkPhoneRegex(phone)) { /* Phone number is not valid*/
            return new ResponseEntity<>("Số điện thoại không đúng đinh dạng", HttpStatus.BAD_REQUEST);
        }
        // Email
        String email = "";
        if(updateEmployeeDTO.getEmail() != null){
            email = stringDealer.trimMax(updateEmployeeDTO.getEmail());
            if ((!email.equals("")) && !stringDealer.checkEmailRegex(email)) { /* Email is not valid */
                return new ResponseEntity<>("Email không đúng định dạng", HttpStatus.BAD_REQUEST);
            }
        }
        // Address
        if (updateEmployeeDTO.getEmployeeAddress() == null || stringDealer.trimMax(updateEmployeeDTO.getEmployeeAddress()).equals("")) { /* Address is empty */
            return new ResponseEntity<>("Địa chỉ không được để trống", HttpStatus.BAD_REQUEST);
        }
        String address = stringDealer.trimMax(updateEmployeeDTO.getEmployeeAddress());

        Float salary = updateEmployeeDTO.getSalary();
        if (oldEmployeeInfo.isPresent()){
            User oldEmployeInfor = oldEmployeeInfo.get();

            //set employee new information
            User employeeNewInformation = new User();
            employeeNewInformation.setUserId(employeeId);
            employeeNewInformation.setUsername(employeeName);
            employeeNewInformation.setEmail(email);
            Date date = stringDealer.convertToDateAndFormat(sDate);
            employeeNewInformation.setDob(date);
            employeeNewInformation.setPhone(phone);
            employeeNewInformation.setAddress(address);
            List roleList = new ArrayList<Role>();
            roleList.add(new Role(3, "ROLE_EMPLOYEE", true));
            employeeNewInformation.setRoles(roleList);     /* Role EMPLOYEE */
            employeeNewInformation.setStatus(updateEmployeeDTO.getStatus());
            employeeNewInformation.setPassword(oldEmployeInfor.getPassword());
            employeeNewInformation.setSalary(salary);
            try {
                User responseUser = userRepository.save(employeeNewInformation);
                //if owner change employee salary
                if (!oldEmployeInfor.getSalary().equals(salary)){
                    //create new salary history
                    SalaryHistory salaryHistory = new SalaryHistory();
                    salaryHistory.setSalary(salary);
                    Date issueDate = Date.valueOf(LocalDate.now());
                    salaryHistory.setIssueDate(issueDate);
                    salaryHistory.setUserId(employeeId);
                    salaryHistoryRepository.save(salaryHistory);
                }
                return new ResponseEntity<>("Cập nhật thánh công", HttpStatus.OK);
            } catch (IllegalArgumentException iae) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    /**
     * Service to delete employee
     *
     * @param employeeId employee id to be deleted
     * @return response message
     */
    @Override
    public ResponseEntity<?> deleteEmployee(Long employeeId) {
        Optional<User> oldEmployeeInfo = userRepository.findByUserId(employeeId);
        //check if employee id exist
        if(!oldEmployeeInfo.isPresent()){
            return new ResponseEntity<>("Nhân viên không còn tồn tại trên hệ thống", HttpStatus.BAD_REQUEST);
        }
        //check if employee still working
        Optional<WorkIn> workInOtp = workInRepository.findByUserId(employeeId);
        if (!workInOtp.isPresent()){
            return new ResponseEntity<>("Nhân viên không còn tồn tại trên hệ thống", HttpStatus.BAD_REQUEST);
        }
        WorkIn workIn = workInOtp.get();
        workInRepository.delete(workIn);
        Optional<User> employeeOtp = userRepository.findByUserId(workIn.getUserId());
        if(employeeOtp.isPresent()){
            User employee = employeeOtp.get();
            employee.setPhone("Không");
            employee.setStatus(0);
            userRepository.save(employee);
        }
        return new ResponseEntity<>("Xoá nhân viên thành công", HttpStatus.OK);
    }

    /**
     * Service to get all information of an employee
     *
     * @param employeeId Employee id
     * @return Employee information
     */
    @Override
    public ResponseEntity<?> viewEmployeeById(Long employeeId) {
        Optional<User> employeeOtp= userRepository.findByUserId(employeeId);
        //if employee exist
        if(employeeOtp.isPresent()){
            User employee = employeeOtp.get();
            EmployeeDetailDTO employeeDetailDTO = new EmployeeDetailDTO();
            employeeDetailDTO.getFromEntity(employee);
            return new ResponseEntity<>(employeeDetailDTO, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Service to view all employee of a facility
     *
     * @param facilityId Facility id
     * @return List employee
     */
    @Override
    public ResponseEntity<?> viewAllEmployee(Long facilityId) {
        //get id of all employee work in facility
        Optional<List<WorkIn>> workInListOpt = workInRepository.findAllByFacilityId(facilityId);
        if(workInListOpt.isPresent()){
            List<WorkIn> workInList = workInListOpt.get();
            List<EmployeeListItemDTO> listEmployee = new ArrayList<>();
            for(WorkIn workIn : workInList){
                //get employee information
                Optional<User> employee = userRepository.findByUserId(workIn.getUserId());
                //add employee information to list
                if (employee.isPresent()){
                    EmployeeListItemDTO employeeListItemDTO = new EmployeeListItemDTO();
                    employeeListItemDTO.getFromUser(employee.get());
                    listEmployee.add(employeeListItemDTO);
                }
            }
            return  new ResponseEntity<>(listEmployee, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Find employee by name or phone number
     *
     * @param facilityId  facility id
     * @param employeePhone employee phone number or name
     * @return employee list
     */
    @Override
    public ResponseEntity<?> searchEmployeeByNameOrPhone(Long facilityId, String employeePhone) {
        return null;
    }

}
