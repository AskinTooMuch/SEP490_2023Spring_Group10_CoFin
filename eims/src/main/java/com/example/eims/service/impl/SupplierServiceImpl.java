/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 02/03/2023   1.0         DuongVV     First Deploy<br>
 * 09/03/2023   1.1         ChucNV      Hot Fix
 */

package com.example.eims.service.impl;

import com.example.eims.dto.supplier.CreateSupplierDTO;
import com.example.eims.dto.supplier.SupplierDetailDTO;
import com.example.eims.dto.supplier.UpdateSupplierDTO;
import com.example.eims.entity.ImportReceipt;
import com.example.eims.entity.Supplier;
import com.example.eims.repository.ImportReceiptRepository;
import com.example.eims.repository.SupplierRepository;
import com.example.eims.repository.UserRepository;
import com.example.eims.service.interfaces.ISupplierService;
import com.example.eims.utils.StringDealer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    private final StringDealer stringDealer;
    public SupplierServiceImpl(UserRepository userRepository, SupplierRepository supplierRepository,
                               ImportReceiptRepository importReceiptRepository) {
        this.userRepository = userRepository;
        this.supplierRepository = supplierRepository;
        this.importReceiptRepository = importReceiptRepository;
        this.stringDealer = new StringDealer();
    }

    /**
     * Get all of their suppliers.
     *
     * @param userId the id of current logged-in user
     * @return list of Suppliers
     */
    @Override
    public ResponseEntity<?> getAllSupplier(Long userId) {
        // Get all suppliers of the current User
        Optional<List<Supplier>> supplierListOptional = supplierRepository.findByUserId(userId);
        if (supplierListOptional.isEmpty()) {
            return new ResponseEntity<>("No supplier found", HttpStatus.NO_CONTENT); // 204
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
        int accountStatus = (userRepository.getStatusByUserId(userId)? 1:0);
        if (accountStatus == 0) { /* status = 0 (deactivated) */
            return new ResponseEntity<>("Tài khoản đã bị vô hiệu hóa", HttpStatus.BAD_REQUEST);
        }
        System.out.println(createSupplierDTO);
        // Retrieve supplier information and create new supplier
        Supplier supplier = new Supplier();
        // Check input
        // Name
        String name = stringDealer.trimMax(createSupplierDTO.getSupplierName());
        if (name.equals("")) { /* Supplier name is empty */
            return new ResponseEntity<>("Tên không được để trống", HttpStatus.BAD_REQUEST);
        }
        // Phone number
        String phone = stringDealer.trimMax(createSupplierDTO.getSupplierPhone());
        if (phone.equals("")) { /* Phone number is empty */
            return new ResponseEntity<>("Số điện thoại không được để trống", HttpStatus.BAD_REQUEST);
        }
        if (!stringDealer.checkPhoneRegex(phone)) { /* Phone number is not valid */
            return new ResponseEntity<>("Số điện thoại không hợp lệ", HttpStatus.BAD_REQUEST);
        }
        // Check phone number existed or not
        boolean existed = supplierRepository.existsBySupplierPhone(createSupplierDTO.getSupplierPhone());
        if (existed) { /* if phone number existed */
            return new ResponseEntity<>("Số điện thoại đã được sử dụng", HttpStatus.BAD_REQUEST);
        }
        // Address
        String address = stringDealer.trimMax(createSupplierDTO.getSupplierAddress());
        if (address.equals("")) { /* Address is empty */
            return new ResponseEntity<>("Địa chỉ không được để trống", HttpStatus.BAD_REQUEST);
        }
        // Facility name
        String fName = stringDealer.trimMax(createSupplierDTO.getFacilityName());
        if (fName.equals("")) { /* Facility name is empty */
            return new ResponseEntity<>("Tên cơ sở không được để trống", HttpStatus.BAD_REQUEST);
        }
        // Email
        String email = stringDealer.trimMax(createSupplierDTO.getSupplierMail());
        if ((!email.equals("")) && (!stringDealer.checkEmailRegex(email))) { /* Email is not valid */
            return new ResponseEntity<>("Email không hợp lệ", HttpStatus.BAD_REQUEST);
        }
        // Set attribute
        supplier.setUserId(userId);
        supplier.setSupplierName(name);
        supplier.setSupplierPhone(phone);
        supplier.setSupplierAddress(address);
        supplier.setFacilityName(fName);
        supplier.setSupplierMail(email);
        supplier.setStatus(1);
        // Save
        supplierRepository.save(supplier);
        return new ResponseEntity<>("Supplier created!", HttpStatus.OK);

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
        // Check blank input
        // Name
        String name = stringDealer.trimMax(updateSupplierDTO.getSupplierName());
        if (name.equals("")) { /* Supplier name is empty */
            return new ResponseEntity<>("Tên không được để trống", HttpStatus.BAD_REQUEST);
        }
        // Phone number
        String newPhone = stringDealer.trimMax(updateSupplierDTO.getSupplierPhone());
        if (newPhone.equals("")) { /* Phone number is empty */
            return new ResponseEntity<>("Số điện thoại không được để trống", HttpStatus.BAD_REQUEST);
        }
        if (!stringDealer.checkPhoneRegex(newPhone)) { /* Phone number is not valid */
            return new ResponseEntity<>("Số điện thoại không hợp lệ", HttpStatus.BAD_REQUEST);
        }
        String oldPhone = supplierRepository.findSupplierPhoneById(updateSupplierDTO.getSupplierId());
        if (!oldPhone.equals(newPhone)){
            Optional<Supplier> supplierOptional = supplierRepository.findBySupplierPhone(newPhone);
            if (supplierOptional.isPresent()) {
                return new ResponseEntity<>("Số điện thoại đã được sử dụng", HttpStatus.BAD_REQUEST);
            }
        }
        // Address
        String address = stringDealer.trimMax(updateSupplierDTO.getSupplierAddress());
        if (address.equals("")) { /* Address is empty */
            return new ResponseEntity<>("Địa chỉ không được để trống", HttpStatus.BAD_REQUEST);
        }
        // Facility name
        String fName = stringDealer.trimMax(updateSupplierDTO.getFacilityName());
        if (fName.equals("")) { /* Facility name is empty */
            return new ResponseEntity<>("Tên cơ sở không được để trống", HttpStatus.BAD_REQUEST);
        }
        // Email
        String email = stringDealer.trimMax(updateSupplierDTO.getSupplierMail());
        if (!(email.equals("")) && !stringDealer.checkEmailRegex(email)) { /* Email is not valid */
            return new ResponseEntity<>("Email không hợp lệ", HttpStatus.BAD_REQUEST);
        }
        // Status
        int status = updateSupplierDTO.getStatus();

        // Retrieve supplier's new information
        Optional<Supplier> supplierOptional = supplierRepository.findBySupplierId(updateSupplierDTO.getSupplierId());
        if (supplierOptional.isPresent()){
            Supplier supplier = supplierOptional.get();
            supplier.setSupplierName(name);
            supplier.setSupplierPhone(newPhone);
            supplier.setSupplierAddress(address);
            supplier.setSupplierMail(email);
            supplier.setStatus(status);
            // Save
            supplierRepository.save(supplier);
            return new ResponseEntity<>("Cập nhật thông tin nhà cung cấp thành công", HttpStatus.OK);
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
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
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
     * @param userId the id of current logged-in user.
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
