package com.example.eims.controller;

import com.example.eims.dto.user.PhoneNumberDTO;
import com.example.eims.dto.user.UserDetailDTO;
import com.example.eims.entity.Facility;
import com.example.eims.entity.User;
import com.example.eims.entity.UserRole;
import com.example.eims.repository.FacilityRepisitory;
import com.example.eims.repository.UserRepository;
import com.example.eims.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FacilityRepisitory facilityRepisitory;
    @Autowired
    private UserRoleRepository userRoleRepository;

    /**
     * The API requesting user information and facility information with the requesting DTO consists of user phone number.
     * The API returns the details of a user and it's facility in the form of a UserDetailDTO
     * @param phoneNumberDTO
     * @return
     */
    @PostMapping("/details")
    @Secured({"ROLE_OWNER", "ROLE_EMPLOYEE"})
    public ResponseEntity<UserDetailDTO> sendUserDetail(@RequestBody PhoneNumberDTO phoneNumberDTO){
        User user = userRepository.findByPhone(phoneNumberDTO.getPhone()).get();
        Optional<Facility> facilityOpt = facilityRepisitory.findByUserId(user.getUserId());
        //User information
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setUserId(user.getUserId());
        UserRole userRole = new UserRole();
        if (userRoleRepository.findByRoleId(user.getRoleId()).isPresent()){
            userRole = userRoleRepository.findByRoleId(user.getRoleId()).get();
        }
        userDetailDTO.setUserRoleName(userRole.getRoleName());
        userDetailDTO.setUsername(user.getUsername());
        userDetailDTO.setUserDob(user.getDob());
        userDetailDTO.setUserEmail(user.getEmail());
        userDetailDTO.setUserSalary(user.getSalary());
        userDetailDTO.setUserAddress(user.getAddress());
        userDetailDTO.setUserStatus(user.isStatus());
        //Facility information
        if (facilityOpt.isPresent()) {
            Facility facility = facilityOpt.get();
            userDetailDTO.setFacilityId(facility.getFacilityId());
            userDetailDTO.setFacilityName(facility.getFacilityName());
            userDetailDTO.setFacilityAddress(facility.getFacilityAddress());
            userDetailDTO.setFacilityFoundDate(facility.getFacilityFoundDate());
            userDetailDTO.setFacilityId(facility.getFacilityId());
        }

        return new ResponseEntity<UserDetailDTO>(new UserDetailDTO(), HttpStatus.OK);
    }
}
