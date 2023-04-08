/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 02/03/2023   1.0         DuongVV     First Deploy<br>
 * 09/03/2023   1.1         ChucNV      Hot Fix<br>
 * 26/03/2023   1.2         ChucNV      Refine Code<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.supplier.CreateSupplierDTO;
import com.example.eims.dto.supplier.SupplierDetailDTO;
import com.example.eims.dto.supplier.UpdateSupplierDTO;
import com.example.eims.entity.Facility;
import com.example.eims.entity.ImportReceipt;
import com.example.eims.entity.Supplier;
import com.example.eims.repository.FacilityRepository;
import com.example.eims.repository.ImportReceiptRepository;
import com.example.eims.repository.SupplierRepository;
import com.example.eims.repository.UserRepository;
import com.example.eims.service.interfaces.ISupplierService;
import com.example.eims.utils.AddressPojo;
import com.example.eims.utils.StringDealer;
import com.fasterxml.jackson.core.JsonProcessingException;
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
public class SupplierServiceImpl implements ISupplierService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final SupplierRepository supplierRepository;
    @Autowired
    private final ImportReceiptRepository importReceiptRepository;
    @Autowired
    private final FacilityRepository facilityRepository;
    private final StringDealer stringDealer = new StringDealer();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SupplierServiceImpl(UserRepository userRepository, SupplierRepository supplierRepository,
                               ImportReceiptRepository importReceiptRepository, FacilityRepository facilityRepository) {
        this.userRepository = userRepository;
        this.supplierRepository = supplierRepository;
        this.importReceiptRepository = importReceiptRepository;
        this.facilityRepository = facilityRepository;
    }

    /**
     * Get all of owner's suppliers.
     *
     * @param userId the id of the Owner
     * @return list of Suppliers
     */
    @Override
    public ResponseEntity<?> getAllSupplier(Long userId) {
        // Get all suppliers of the current User
        Optional<List<Supplier>> supplierListOptional = supplierRepository.findByUserId(userId);
        if (supplierListOptional.isEmpty()) {
            return new ResponseEntity<>("Không tìm thấy nhà cung cấp", HttpStatus.NOT_FOUND); // 404
        } else {
            return new ResponseEntity<>(supplierListOptional.get(), HttpStatus.OK);
        }
    }

    /**
     * Get all of owner's active suppliers.
     *
     * @param userId the id of the Owner
     * @return list of Suppliers
     */
    @Override
    public ResponseEntity<?> getActiveSupplier(Long userId) {
        // Get all active suppliers of the current User
        Optional<List<Supplier>> supplierListOptional = supplierRepository.
                findByUserIdAndStatus(userId, 1);
        if (supplierListOptional.isEmpty()) {
            return new ResponseEntity<>("Không tìm thấy nhà cung cấp", HttpStatus.NOT_FOUND); //404
        } else {
            return new ResponseEntity<>(supplierListOptional.get(), HttpStatus.OK);
        }
    }

    /**
     * Get a supplier.
     *
     * @param supplierId the id of the supplier
     * @return
     */
    @Override
    public ResponseEntity<?> getSupplier(Long supplierId) {
        // Get a supplier
        Optional<Supplier> supplierOptional = supplierRepository.findBySupplierId(supplierId);
        if (supplierOptional.isPresent()) {
            Supplier supplier = supplierOptional.get();
            System.out.println(supplier);
            // Get and add attribute to DTO
            // Get fertilized rate
            Float fertilizedRate = 9.0F;
            // Get male rate
            Float maleRate = 6.0F;
            SupplierDetailDTO supplierDetailDTO = new SupplierDetailDTO();
            supplierDetailDTO.getFromEntity(supplier);
            supplierDetailDTO.setFertilizedRate(fertilizedRate);
            supplierDetailDTO.setMaleRate(maleRate);
            System.out.println(supplierDetailDTO);
            return new ResponseEntity<>(supplierDetailDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Create a supplier of a user.
     *
     * @param createSupplierDTO contains the user's id, name, phone number and address of the supplier
     * @return
     */
    @Override
    public ResponseEntity<?> createSupplier(CreateSupplierDTO createSupplierDTO) {
        // Check if Owner's account is still activated
        Long userId = createSupplierDTO.getUserId();
        int accountStatus = userRepository.getStatusByUserId(userId);
        if (accountStatus != 2) { /* status = 0 (deactivated) */
            return new ResponseEntity<>("Tài khoản đã bị vô hiệu hóa.", HttpStatus.BAD_REQUEST);
        }
        // Retrieve supplier information and create new supplier
        Supplier supplier = new Supplier();
        //Trim inputs
        String supplierName = stringDealer.trimMax(createSupplierDTO.getSupplierName());
        String supplierPhone = stringDealer.trimMax(createSupplierDTO.getSupplierPhone());
        String supplierAddress = stringDealer.trimMax(createSupplierDTO.getSupplierAddress());
        String facilityName = stringDealer.trimMax(createSupplierDTO.getFacilityName());
        String supplierMail = stringDealer.trimMax(createSupplierDTO.getSupplierMail());
        // Check input
        // Name
        if (supplierName.equals("")) { /* Supplier name is empty */
            return new ResponseEntity<>("Tên nhà cung cấp không được để trống.", HttpStatus.BAD_REQUEST);
        }
        if (supplierName.length() > 32) { /* Supplier name is empty */
            return new ResponseEntity<>("Tên nhà cung cấp không được dài hơn 32 ký tự.", HttpStatus.BAD_REQUEST);
        }
        // Phone number
        if (supplierPhone.equals("")) { /* Phone number is empty */
            return new ResponseEntity<>("Số điện thoại không được để trống.", HttpStatus.BAD_REQUEST);
        }
        if (!stringDealer.checkPhoneRegex(supplierPhone)) { /* Phone number is not valid */
            return new ResponseEntity<>("Số điện thoại không hợp lệ.", HttpStatus.BAD_REQUEST);
        }
        // Check phone number existed or not
        if (supplierRepository.existsBySupplierPhoneAndUserId(supplierPhone, userId)) { /* if phone number existed */
            return new ResponseEntity<>("Số điện thoại đã được sử dụng.", HttpStatus.BAD_REQUEST);
        }
        // Facility name
        if (facilityName.equals("")) { /* Facility name is empty */
            return new ResponseEntity<>("Tên cơ sở không được để trống.", HttpStatus.BAD_REQUEST);
        }
        if (facilityName.length() > 50) { /* Facility name is empty */
            return new ResponseEntity<>("Tên cơ sở không được dài hơn 50 ký tự.", HttpStatus.BAD_REQUEST);
        }
        // Email
        if ((!createSupplierDTO.getSupplierMail().equals("")) && (!stringDealer.checkEmailRegex(supplierMail))) { /* Email is not valid */
            return new ResponseEntity<>("Email không hợp lệ.", HttpStatus.BAD_REQUEST);
        }
        // Address
        if (supplierAddress.equals("")) { /* Address is empty */
            return new ResponseEntity<>("Địa chỉ không được để trống.", HttpStatus.BAD_REQUEST);
        }
        System.out.println(supplierAddress);
        try {
            AddressPojo address = objectMapper.readValue(supplierAddress, AddressPojo.class);
            address.city = stringDealer.trimMax(address.city);
            address.district = stringDealer.trimMax(address.district);
            address.ward = stringDealer.trimMax(address.ward);
            address.street = stringDealer.trimMax(address.street);
            if (address.street.equals("")) {
                return new ResponseEntity<>("Số nhà không được để trống", HttpStatus.BAD_REQUEST);
            }
            if (address.street.length() > 30) {
                return new ResponseEntity<>("Số nhà không được dài hơn 30 ký tự", HttpStatus.BAD_REQUEST);
            }
            if (address.city.equals("")) {
                return new ResponseEntity<>("Thành phố không được để trống", HttpStatus.BAD_REQUEST);
            }
            if (address.district.equals("")) {
                return new ResponseEntity<>("Huyện không được để trống", HttpStatus.BAD_REQUEST);
            }
            if (address.ward.equals("")) {
                return new ResponseEntity<>("Xã không được để trống", HttpStatus.BAD_REQUEST);
            }
            supplierAddress = objectMapper.writeValueAsString(address);
            System.out.println(supplierAddress);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // Set attribute
        supplier.setUserId(userId);
        supplier.setSupplierName(supplierName);
        supplier.setSupplierPhone(supplierPhone);
        supplier.setSupplierAddress(supplierAddress);
        supplier.setFacilityName(facilityName);
        supplier.setSupplierMail(supplierMail);
        supplier.setStatus(1);
        // Save
        supplierRepository.save(supplier);
        return new ResponseEntity<>("Thêm nhà cung cấp thành công.", HttpStatus.OK);
    }

    /**
     * Show form to update a supplier.
     *
     * @param supplierId the id of the supplier
     * @return
     */
    @Override
    public ResponseEntity<?> showFormUpdate(Long supplierId) {
        // Get a supplier
        Optional<Supplier> supplier = supplierRepository.findBySupplierId(supplierId);
        if (supplier.isPresent()) {
            return new ResponseEntity<>(supplier.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update a supplier of a user.
     *
     * @param updateSupplierDTO contains the user's id, new name, phone number and address of the supplier
     * @return
     */
    @Override
    public ResponseEntity<?> updateSupplier(UpdateSupplierDTO updateSupplierDTO) {
        Long userId = updateSupplierDTO.getUserId();
        //Trim inputs
        String supplierName = stringDealer.trimMax(updateSupplierDTO.getSupplierName());
        String supplierPhone = stringDealer.trimMax(updateSupplierDTO.getSupplierPhone());
        String supplierAddress = stringDealer.trimMax(updateSupplierDTO.getSupplierAddress());
        String facilityName = stringDealer.trimMax(updateSupplierDTO.getFacilityName());
        String supplierMail = stringDealer.trimMax(updateSupplierDTO.getSupplierMail());
        // Check input
        // Name
        if (supplierName.equals("")) { /* Supplier name is empty */
            return new ResponseEntity<>("Tên nhà cung cấp không được để trống.", HttpStatus.BAD_REQUEST);
        }
        if (supplierName.length() > 32) { /* Supplier name is empty */
            return new ResponseEntity<>("Tên nhà cung cấp không được dài hơn 32 ký tự.", HttpStatus.BAD_REQUEST);
        }
        // Phone number
        if (supplierPhone.equals("")) { /* Phone number is empty */
            return new ResponseEntity<>("Số điện thoại không được để trống.", HttpStatus.BAD_REQUEST);
        }
        if (!stringDealer.checkPhoneRegex(supplierPhone)) { /* Phone number is not valid */
            return new ResponseEntity<>("Số điện thoại không hợp lệ.", HttpStatus.BAD_REQUEST);
        }
        String oldPhone = supplierRepository.findSupplierPhoneById(updateSupplierDTO.getSupplierId());
        if ((!oldPhone.equals(updateSupplierDTO.getSupplierPhone())) &&
                (supplierRepository.existsBySupplierPhoneAndUserId(updateSupplierDTO.getSupplierPhone(), userId))) {
            return new ResponseEntity<>("Số điện thoại đã được sử dụng.", HttpStatus.BAD_REQUEST);
        }
        // Facility name
        if (facilityName.equals("")) { /* Facility name is empty */
            return new ResponseEntity<>("Tên cơ sở không được để trống.", HttpStatus.BAD_REQUEST);
        }
        if (facilityName.length() > 50) { /* Facility name is empty */
            return new ResponseEntity<>("Tên cơ sở không được dài hơn 50 ký tự.", HttpStatus.BAD_REQUEST);
        }
        // Email
        if ((!supplierMail.equals("")) && (!stringDealer.checkEmailRegex(supplierMail))) { /* Email is not valid */
            return new ResponseEntity<>("Email không hợp lệ.", HttpStatus.BAD_REQUEST);
        }
        // Address
        if (supplierAddress.equals("")) { /* Address is empty */
            return new ResponseEntity<>("Địa chỉ không được để trống.", HttpStatus.BAD_REQUEST);
        }
        System.out.println(supplierAddress);
        try {
            AddressPojo address = objectMapper.readValue(supplierAddress, AddressPojo.class);
            address.city = stringDealer.trimMax(address.city);
            address.district = stringDealer.trimMax(address.district);
            address.ward = stringDealer.trimMax(address.ward);
            address.street = stringDealer.trimMax(address.street);
            if (address.street.equals("")) {
                return new ResponseEntity<>("Số nhà không được để trống", HttpStatus.BAD_REQUEST);
            }
            if (address.street.length() > 30) {
                return new ResponseEntity<>("Số nhà không được dài hơn 30 ký tự", HttpStatus.BAD_REQUEST);
            }
            if (address.city.equals("")) {
                return new ResponseEntity<>("Thành phố không được để trống", HttpStatus.BAD_REQUEST);
            }
            if (address.district.equals("")) {
                return new ResponseEntity<>("Huyện không được để trống", HttpStatus.BAD_REQUEST);
            }
            if (address.ward.equals("")) {
                return new ResponseEntity<>("Xã không được để trống", HttpStatus.BAD_REQUEST);
            }
            supplierAddress = objectMapper.writeValueAsString(address);
            System.out.println(supplierAddress);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // Status
        int status = updateSupplierDTO.getStatus();

        // Retrieve supplier's new information
        Optional<Supplier> supplierOptional = supplierRepository.findBySupplierId(updateSupplierDTO.getSupplierId());
        if (supplierOptional.isPresent()) {
            Supplier supplier = supplierOptional.get();
            supplier.setSupplierName(supplierName);
            supplier.setSupplierPhone(supplierPhone);
            supplier.setSupplierAddress(supplierAddress);
            supplier.setSupplierMail(supplierMail);
            supplier.setFacilityName(facilityName);
            supplier.setStatus(status);
            // Save
            supplierRepository.save(supplier);
            return new ResponseEntity<>("Cập nhật thông tin nhà cung cấp thành công.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Search supplier of the user by their name or phone number.
     *
     * @param key    the search key (name or phone number)
     * @param userId the id of current logged-in user
     * @return list of suppliers
     */
    @Override
    public ResponseEntity<?> searchSupplier(Long userId, String key) {
        // Trim spaces
        StringDealer stringDealer = new StringDealer();
        key = stringDealer.trimMax(key);
        // Search
        Optional<List<Supplier>> supplierList = supplierRepository.searchByUsernameOrPhone(userId, key);
        if (supplierList.isPresent()) {
            return new ResponseEntity<>(supplierList.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    /**
     * Get all import bill from supplier.
     *
     * @param supplierId the id of supplier
     * @return list of import receipts
     */
    @Override
    public ResponseEntity<?> viewImports(Long supplierId) {
        Optional<List<ImportReceipt>> importReceiptList = importReceiptRepository.findBySupplierId(supplierId);
        if (importReceiptList.isPresent()) {
            return new ResponseEntity<>(importReceiptList.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get all of user's Suppliers with Paging.
     *
     * @param userId the id of the Owner
     * @param page   the page number
     * @param size   the size of page
     * @param sort   sorting type
     * @return list of Suppliers
     */
    @Override
    public ResponseEntity<?> getAllSupplierPaging(Long userId, Integer page, Integer size, String sort) {
        // Get sorting type
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("supplierId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("supplierId").descending();
        }
        // Get all customers of the current User with Paging
        Page<Supplier> supplierPage = supplierRepository.findAllByUserId(userId, PageRequest.of(page, size, sortable));
        return new ResponseEntity<>(supplierPage, HttpStatus.OK);
    }
}
