/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 02/03/2023   1.0         DuongVV     First Deploy<br>
 * 03/03/2023   3.1         DuongVV     Add view/approve registration<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.user.UpdateUserDTO;
import com.example.eims.dto.user.UserDetailDTO;
import com.example.eims.dto.user.UserListItemDTO;
import com.example.eims.entity.Facility;
import com.example.eims.entity.Role;
import com.example.eims.entity.User;
import com.example.eims.entity.WorkIn;
import com.example.eims.repository.FacilityRepository;
import com.example.eims.repository.UserRepository;
import com.example.eims.repository.UserRoleRepository;
import com.example.eims.repository.WorkInRepository;
import com.example.eims.service.interfaces.IUserService;
import com.example.eims.utils.AddressPojo;
import com.example.eims.utils.StringDealer;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserRoleRepository userRoleRepository;
    @Autowired
    private final FacilityRepository facilityRepository;
    @Autowired
    private final WorkInRepository workInRepository;
    @PersistenceContext
    private final EntityManager em;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final StringDealer stringDealer;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository,
                           FacilityRepository facilityRepository, WorkInRepository workInRepository, EntityManager em) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.facilityRepository = facilityRepository;
        this.workInRepository = workInRepository;
        this.em = em;
        this.stringDealer = new StringDealer();
    }


    /**
     * Requesting user information and facility information with the requesting DTO consists of user phone number.
     *
     * @param userId
     * @return the details of a user and it's facility in the form of a UserDetailDTO
     */
    @Override
    public ResponseEntity<?> sendUserDetail(Long userId) {
        User user = userRepository.findByUserId(userId).get();
        List<Role> roles = user.getRoles();
        // get first role
        if (roles.get(0).getRoleId() == 2 || roles.get(0).getRoleId() == 3) {
            Query q = em.createNamedQuery("GetUserDetail");
            q.setParameter(1, userId);
            try {
                UserDetailDTO userDetailDTO = (UserDetailDTO) q.getSingleResult();
                System.out.println(userDetailDTO);
                return new ResponseEntity<>(userDetailDTO, HttpStatus.OK);
            } catch (NoResultException e) {
                return new ResponseEntity<>("Người dùng không hợp lệ", HttpStatus.BAD_REQUEST);
            }
        }
        if (roles.get(0).getRoleId() == 4 || roles.get(0).getRoleId() == 5) {
            return new ResponseEntity<>(new UserDetailDTO(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Người dùng không hợp lệ", HttpStatus.BAD_REQUEST);
    }

    /**
     * Get all user.
     *
     * @return
     */
    @Override
    public ResponseEntity<?> getAllUser() {
        // Retrieve users
        List<User> userList = userRepository.findAll();
        // Return
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    /**
     * Get all user for a role.
     *
     * @param roleId role id of the user
     * @return
     */
    @Override
    public ResponseEntity<?> getAllUserByRole(Long roleId) {
        List<UserListItemDTO> dtoList = new ArrayList<>();
        Optional<Role> roleOptional = userRoleRepository.findByRoleId(roleId);
        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            List<User> users = role.getAccounts();
            if (role.getRoleId() == 2) { // owner
                for (User user : users) {
                    if (user.getStatus() == 2) { // active
                        UserListItemDTO dto = new UserListItemDTO();
                        dto.setUserId(user.getUserId());
                        dto.setUserName(user.getUsername());
                        dto.setPhone(user.getPhone());
                        dto.setDob(user.getDob());
                        dto.setStatus(user.getStatus());
                        Facility facility = facilityRepository.findByUserId(user.getUserId()).get();
                        dto.setFacilityName(facility.getFacilityName());
                        dto.setBusinessLicenseNumber(facility.getBusinessLicenseNumber());
                        dtoList.add(dto);
                    }
                }
            }
            if (role.getRoleId() == 3) { // employee
                for (User user : users) {
                    if (user.getStatus() == 2) { // active
                        UserListItemDTO dto = new UserListItemDTO();
                        dto.setUserId(user.getUserId());
                        dto.setUserName(user.getUsername());
                        dto.setPhone(user.getPhone());
                        dto.setDob(user.getDob());
                        dto.setStatus(user.getStatus());
                        WorkIn workIn = workInRepository.findByUserId(user.getUserId()).get();
                        Facility facility = facilityRepository.findByFacilityId(workIn.getFacilityId()).get();
                        dto.setFacilityName(facility.getFacilityName());
                        dto.setBusinessLicenseNumber(facility.getBusinessLicenseNumber());
                        dtoList.add(dto);
                    }
                }
            }
            if (role.getRoleId() == 4) { // moderator
                for (User user : users) {
                    UserListItemDTO dto = new UserListItemDTO();
                    dto.setUserId(user.getUserId());
                    dto.setUserName(user.getUsername());
                    dto.setPhone(user.getPhone());
                    dto.setDob(user.getDob());
                    dto.setStatus(user.getStatus());
                    dtoList.add(dto);
                }
            }
        }
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    /**
     * Get all user with Paging.
     *
     * @param page the page number
     * @param size the size of page
     * @param sort sorting type
     * @return
     */
    @Override
    public ResponseEntity<?> getAllUserPaging(Integer page, Integer size, String sort) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("userId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("userId").descending();
        }
        // Get all users with Paging
        Page<User> userPage = userRepository.findAll(PageRequest.of(page, size, sortable));
        return new ResponseEntity<>(userPage, HttpStatus.OK);
    }

    /**
     * Show form to update user's information.
     *
     * @param userId the id of the user
     * @return
     */
    @Override
    public ResponseEntity<?> showFormUpdate(Long userId) {
        // Retrieve user
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            UpdateUserDTO updateUserDTO = new UpdateUserDTO();
            updateUserDTO.getFromEntity(user.get());
            // Return
            return new ResponseEntity<>(updateUserDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update a user.
     *
     * @param updateUserDTO contains the user's new name,date of birth, phone number, address, email, salary and status
     * @return
     */
    @Override
    public ResponseEntity<?> updateUser(UpdateUserDTO updateUserDTO) {
        // Check if Owner's account is still activated
        Long userId = updateUserDTO.getUserId();
        int accountStatus = userRepository.getStatusByUserId(userId);
        if (accountStatus != 2) { /* status = 0 (deactivated) */
            return new ResponseEntity<>("Tài khoản đã bị vô hiệu hóa", HttpStatus.BAD_REQUEST);
        }
        // Retrieve user's new information
        Optional<User> userOptional = userRepository.findById(updateUserDTO.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Check input
            // Name
            String name = stringDealer.trimMax(updateUserDTO.getUsername());
            if (name.equals("")) { /* Name is empty */
                return new ResponseEntity<>("Tên không được để trống", HttpStatus.BAD_REQUEST);
            }
            if (name.length() > 32) { /* Name is empty */
                return new ResponseEntity<>("Tên không được dài hơn 32 kí tự", HttpStatus.BAD_REQUEST);
            }
            // Date of birth
            String sDate = stringDealer.trimMax(updateUserDTO.getDob());
            if (sDate == null || sDate.equals("")) { /* Date of birth is empty */
                return new ResponseEntity<>("Ngày sinh không được để trống", HttpStatus.BAD_REQUEST);
            }
            // Email
            String email = stringDealer.trimMax(updateUserDTO.getEmail());
            if (!email.equals("") && !stringDealer.checkEmailRegex(email)) { /* Email is not valid*/
                return new ResponseEntity<>("Email không hợp lệ", HttpStatus.BAD_REQUEST);
            }
            if (email.length() > 64) { /* Email is not valid*/
                return new ResponseEntity<>("Email không được dài hơn 64 kí tự", HttpStatus.BAD_REQUEST);
            }
            // Address
            String userAddress = stringDealer.trimMax(updateUserDTO.getAddress());
            if (userAddress.equals("")) {
                return new ResponseEntity<>("Địa chỉ không được để trống", HttpStatus.BAD_REQUEST);
            }
            try {
                AddressPojo address = objectMapper.readValue(userAddress, AddressPojo.class);
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
                userAddress = objectMapper.writeValueAsString(address);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            // Set attribute
            user.setUsername(name);
            Date dob = stringDealer.convertToDateAndFormat(sDate);
            user.setDob(dob);
            user.setAddress(userAddress);
            user.setEmail(email);
            // Save
            userRepository.save(user);
            return new ResponseEntity<>("Cập nhật thông tin thành công", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    /*
     *//**
     * View list of users with same role.
     *
     * @param roleId the id of user's role
     * @return
     *//*
    @Override
    public ResponseEntity<?> getAllUserByRole(Long roleId) {
        Optional<List<User>> userList = userRepository.findAllByRoleId(roleId);
        if (userList.isPresent()) {
            return new ResponseEntity<>(userList.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    *//**
     * View list of users with same role and status.
     *
     * @param roleId the id of user's role
     * @param status the status of user
     * @return
     *//*
    @Override
    public ResponseEntity<?> getAllUserByRoleAndStatus(Long roleId, int status) {
        Optional<List<User>> userList = userRepository.findAllByRoleIdAndStatus(roleId, status);
        if (userList.isPresent()) {
            return new ResponseEntity<>(userList.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }*/
}
