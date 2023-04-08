/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 02/03/2023   1.0         DuongVV     First Deploy<br>
 * 26/03/2023   1.1         ChucNV      Refine Code<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.customer.CreateCustomerDTO;
import com.example.eims.dto.customer.UpdateCustomerDTO;
import com.example.eims.entity.Customer;
import com.example.eims.entity.Supplier;
import com.example.eims.repository.CustomerRepository;
import com.example.eims.repository.FacilityRepository;
import com.example.eims.repository.UserRepository;
import com.example.eims.service.interfaces.ICustomerService;
import com.example.eims.utils.AddressPojo;
import com.example.eims.utils.StringDealer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final FacilityRepository facilityRepository;
    private final StringDealer stringDealer = new StringDealer();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomerServiceImpl(CustomerRepository customerRepository, UserRepository userRepository,
                               FacilityRepository facilityRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.facilityRepository = facilityRepository;
    }

    /**
     * Get all of user's customers.
     *
     * @param userId the id of the Owner
     * @return list of Customers
     */
    @Override
    public ResponseEntity<?> getAllCustomer(Long userId) {
        // Get all customers of the current User
        Optional<List<Customer>> customers = customerRepository.findByUserId(userId);
        if (customers.isPresent()) {
            return new ResponseEntity<>(customers.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Không tìm thấy khách hàng", HttpStatus.NOT_FOUND);
    }

    /**
     * Get all of user's active customers.
     *
     * @param userId the id of the owner
     * @return list of Customers
     */
    @Override
    public ResponseEntity<?> getActiveCustomer(Long userId) {
        // Get all active customers of the current User
        Optional<List<Customer>> customerListOptional = customerRepository.
                findByUserIdAndStatus(userId, 1);
        if (customerListOptional.isEmpty()) {
            return new ResponseEntity<>("Không tìm thấy khách hàng", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(customerListOptional.get(), HttpStatus.OK);
        }
    }

    /**
     * Get a customer.
     *
     * @param customerId the id of the customer
     * @return a customer
     */
    @Override
    public ResponseEntity<?> getCustomer(Long customerId) {
        // Get a customer of the current User
        Optional<Customer> customer = customerRepository.findByCustomerId(customerId);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Không tìm thấy khách hàng", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Create a customer of a user.
     *
     * @param createCustomerDTO contains the user's id, name, phone number and address of the customer
     * @return
     */
    @Override
    public ResponseEntity<?> createCustomer(CreateCustomerDTO createCustomerDTO) {
        // Check if Owner's account is still activated
        Long userId = createCustomerDTO.getUserId();
        int accountStatus =userRepository.getStatusByUserId(userId);
        if (accountStatus != 2) { /* status = 0 (deactivated) */
            return new ResponseEntity<>("Tài khoản đã bị vô hiệu hóa", HttpStatus.BAD_REQUEST);
        }
        // Check blank input
        // Name
        String name = stringDealer.trimMax(createCustomerDTO.getCustomerName());
        String phone = stringDealer.trimMax(createCustomerDTO.getCustomerPhone());
        String mail = stringDealer.trimMax(createCustomerDTO.getCustomerMail());
        String customerAddress = stringDealer.trimMax(createCustomerDTO.getCustomerAddress());

        if (name == null || name.equals("")) { /* Customer name is empty */
            return new ResponseEntity<>("Tên không được để trống", HttpStatus.BAD_REQUEST);
        }
        if (name.length() > 32) { /* Supplier name is empty */
            return new ResponseEntity<>("Tên khách hàng không được dài hơn 32 ký tự.", HttpStatus.BAD_REQUEST);
        }
        // Phone number
        if (phone.equals("")) { /* Phone number is empty */
            return new ResponseEntity<>("Số điện thoại không được để trống", HttpStatus.BAD_REQUEST);
        }
        if (!stringDealer.checkPhoneRegex(phone)) { /* Phone number is not valid */
            return new ResponseEntity<>("Số điện thoại không hợp lệ", HttpStatus.BAD_REQUEST);
        }
        // Check phone number existed or not
        if (customerRepository.existsByCustomerPhoneAndUserId(phone, userId)) { /* if phone number existed */
            return new ResponseEntity<>("Số điện thoại đã được sử dụng", HttpStatus.BAD_REQUEST);
        }
        // Email
        if ((!mail.equals("")) && !stringDealer.checkEmailRegex(mail)) { /* Customer email is not valid */
            return new ResponseEntity<>("Email không hợp lệ", HttpStatus.BAD_REQUEST);
        }
        // Address
        if (createCustomerDTO.getCustomerAddress() == null || customerAddress.equals("")) { /* Address is empty */
            return new ResponseEntity<>("Địa chỉ không được để trống", HttpStatus.BAD_REQUEST);
        }

        JSONObject addressObj;
        System.out.println(customerAddress);
        try {
            addressObj = new JSONObject(customerAddress);
            String city = stringDealer.trimMax((String) addressObj.get("city"));
            String district = stringDealer.trimMax((String) addressObj.get("district"));
            String ward = stringDealer.trimMax((String) addressObj.get("ward"));
            String street = stringDealer.trimMax((String) addressObj.get("street"));
            if (city == null || city.equals("")) {
                return new ResponseEntity<>("Thành phố không được để trống", HttpStatus.BAD_REQUEST);
            }
            if (district == null || district.equals("")) {
                return new ResponseEntity<>("Quận/Huyện không được để trống", HttpStatus.BAD_REQUEST);
            }
            if (ward == null || ward.equals("")) {
                return new ResponseEntity<>("Phường xã không được để trống", HttpStatus.BAD_REQUEST);
            }
            if (street == null || street.equals("")) {
                return new ResponseEntity<>("Số nhà không được để trống", HttpStatus.BAD_REQUEST);
            }
            if (street.length() > 30) {
                return new ResponseEntity<>("Số nhà không được quá 30 kí tự", HttpStatus.BAD_REQUEST);
            }
            addressObj = new JSONObject();
            addressObj.put("city", city);
            addressObj.put("district", district);
            addressObj.put("ward", ward);
            addressObj.put("street", street);
            customerAddress = addressObj.toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        // Retrieve customer information and create new customer
        Customer customer = new Customer();
        customer.setUserId(createCustomerDTO.getUserId());
        customer.setCustomerName(name);
        customer.setCustomerPhone(phone);
        customer.setCustomerAddress(customerAddress);
        customer.setCustomerMail(mail);
        customer.setStatus(1);
        // Save
        customerRepository.save(customer);
        return new ResponseEntity<>("Thêm khách hàng mới thành công", HttpStatus.OK);
    }

    /**
     * Show form to update a customer of a user.
     *
     * @param customerId the id of the customer
     * @return a customer
     */
    @Override
    public ResponseEntity<?> showFormUpdate(Long customerId) {
        // Get a customer of the current User
        Optional<Customer> customer = customerRepository.findByCustomerId(customerId);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update a customer of a user.
     *
     * @param updateCustomerDTO contains the new name, phone number and address, email and status of the customer
     * @return
     */
    @Override
    public ResponseEntity<?> updateCustomer(UpdateCustomerDTO updateCustomerDTO) {
        Long userId = updateCustomerDTO.getUserId();
        // Trim inputs
        String name = stringDealer.trimMax(updateCustomerDTO.getCustomerName());
        String newPhone = stringDealer.trimMax(updateCustomerDTO.getCustomerPhone());
        String mail = stringDealer.trimMax(updateCustomerDTO.getCustomerMail());
        String customerAddress = stringDealer.trimMax(updateCustomerDTO.getCustomerAddress());

        // Check inputs
        // Name
        if (name.equals("")) { /* Customer name is empty */
            return new ResponseEntity<>("Tên khách hàng không được để trống", HttpStatus.BAD_REQUEST);
        }
        if (name.length() > 32) { /* Supplier name is empty */
            return new ResponseEntity<>("Tên khách hàng không được dài hơn 32 ký tự.", HttpStatus.BAD_REQUEST);
        }
        // Phone number
        if (newPhone.equals("")) { /* Phone number is empty */
            return new ResponseEntity<>("Số điện thoại không được để trống", HttpStatus.BAD_REQUEST);
        }
        if (!stringDealer.checkPhoneRegex(newPhone)) { /* Phone number is not valid */
            return new ResponseEntity<>("Số điện thoại không hợp lệ", HttpStatus.BAD_REQUEST);
        }
        String oldPhone = customerRepository.findCustomerPhoneById(updateCustomerDTO.getCustomerId());
        if (!newPhone.equals(oldPhone) && (customerRepository.existsByCustomerPhoneAndUserId(newPhone, userId))) {
            return new ResponseEntity<>("Số điện thoại đã được sử dụng", HttpStatus.BAD_REQUEST);
        }
        // Email
        if ((!mail.equals("")) && !stringDealer.checkEmailRegex(mail)) { /* Customer email is not valid */
            return new ResponseEntity<>("Email không hợp lệ", HttpStatus.BAD_REQUEST);
        }
        // Address
        if (customerAddress.equals("")) { /* Address is empty */
            return new ResponseEntity<>("Địa chỉ không được để trống", HttpStatus.BAD_REQUEST);
        }
        JSONObject addressObj;
        System.out.println(customerAddress);
        try {
            addressObj = new JSONObject(customerAddress);
            String city = stringDealer.trimMax((String) addressObj.get("city"));
            String district = stringDealer.trimMax((String) addressObj.get("district"));
            String ward = stringDealer.trimMax((String) addressObj.get("ward"));
            String street = stringDealer.trimMax((String) addressObj.get("street"));
            if (city == null || city.equals("")) {
                return new ResponseEntity<>("Thành phố không được để trống", HttpStatus.BAD_REQUEST);
            }
            if (district == null || district.equals("")) {
                return new ResponseEntity<>("Quận/Huyện không được để trống", HttpStatus.BAD_REQUEST);
            }
            if (ward == null || ward.equals("")) {
                return new ResponseEntity<>("Phường xã không được để trống", HttpStatus.BAD_REQUEST);
            }
            if (street == null || street.equals("")) {
                return new ResponseEntity<>("Số nhà không được để trống", HttpStatus.BAD_REQUEST);
            }
            if (street.length() > 30) {
                return new ResponseEntity<>("Số nhà không được quá 30 kí tự", HttpStatus.BAD_REQUEST);
            }
            addressObj = new JSONObject();
            addressObj.put("city", city);
            addressObj.put("district", district);
            addressObj.put("ward", ward);
            addressObj.put("street", street);
            customerAddress = addressObj.toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        // Status
        int status = updateCustomerDTO.getStatus();
        if (!(status == 1 || status == 0)) {
            return new ResponseEntity<>("Trạng thái không hợp lệ", HttpStatus.BAD_REQUEST);
        }
        Optional<Customer> customerOptional = customerRepository.findByCustomerId(updateCustomerDTO.getCustomerId());
        if (customerOptional.isPresent()) {
            // Retrieve customer's new information
            Customer customer = customerOptional.get();
            customer.setCustomerName(name);
            customer.setCustomerPhone(newPhone);
            customer.setCustomerAddress(customerAddress);
            customer.setCustomerMail(mail);
            customer.setStatus(status);
            // Save
            customerRepository.save(customer);
            return new ResponseEntity<>("Cập nhật thông tin khách hàng thành công", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Có lỗi xảy ra, vui lòng thử lại sau.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Search customer of the user by their name or phone number.
     *
     * @param userId the id of the Owner
     * @param key    the search key (name or phone number)
     * @return list of customers match the key search item.
     */
    @Override
    public ResponseEntity<?> searchCustomer(Long userId, String key) {
        if (key == null || stringDealer.trimMax(key).equals("")) {
            return new ResponseEntity<>("Nhập tên hoặc số điện thoại để tìm kiếm", HttpStatus.BAD_REQUEST);
        }
        // Trim spaces
        key = stringDealer.trimMax(key);
        // Search
        Optional<List<Customer>> customerList = customerRepository.searchByUsernameOrPhone(userId, key);
        if (customerList.isPresent()) {
            return new ResponseEntity<>(customerList.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    /**
     * Get all of user's customers with Paging.
     *
     * @param userId the id of the Owner
     * @param page   the page number
     * @param size   the size of page
     * @param sort   sorting type
     * @return list of Customers
     */
    @Override
    public ResponseEntity<?> getAllCustomerPaging(Long userId, Integer page, Integer size, String sort) {
        // Get sorting type
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("customerId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("customerId").descending();
        }
        // Get all customers of the current User with Paging
        Page<Customer> customerPage = customerRepository.findAllByUserId(userId, PageRequest.of(page, size, sortable));
        return new ResponseEntity<>(customerPage, HttpStatus.OK);
    }
}
