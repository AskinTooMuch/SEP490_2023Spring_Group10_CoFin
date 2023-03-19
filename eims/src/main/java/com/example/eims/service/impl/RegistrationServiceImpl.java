/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 04/03/2023   1.0         DuongVV     First Deploy<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.registration.RegistrationInforDTO;
import com.example.eims.dto.registration.RegistrationListItemDTO;
import com.example.eims.entity.Facility;
import com.example.eims.entity.Registration;
import com.example.eims.entity.User;
import com.example.eims.repository.FacilityRepository;
import com.example.eims.repository.RegistrationRepository;
import com.example.eims.repository.UserRepository;
import com.example.eims.service.interfaces.IRegistrationService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationServiceImpl implements IRegistrationService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RegistrationRepository registrationRepository;
    @Autowired
    private final FacilityRepository facilityRepository;
    @PersistenceContext
    private EntityManager em;

    public RegistrationServiceImpl(UserRepository userRepository, RegistrationRepository registrationRepository, FacilityRepository facilityRepository, EntityManager em) {
        this.userRepository = userRepository;
        this.registrationRepository = registrationRepository;
        this.facilityRepository = facilityRepository;
        this.em = em;
    }

    /**
     * View list registration of owners.
     *
     * @return
     */
    @Override
    public ResponseEntity<?> viewListRegistration() {
        Query query = em.createNamedQuery("getRegistrationListByStatus");
        query.setParameter(1, 0); /* status = 0 (considering list)*/
        List<RegistrationListItemDTO> registrationListItemDTOList = query.getResultList();
        return new ResponseEntity<>(registrationListItemDTOList, HttpStatus.OK);
    }

    /**
     * View a registration of owner.
     *
     * @param userId the id of the owner
     * @return
     */
    @Override
    public ResponseEntity<?> viewRegistration(Long userId) {
        Query query = em.createNamedQuery("getRegistrationInforForUser");
        query.setParameter(1, userId);
        RegistrationInforDTO registrationInforDTO = (RegistrationInforDTO) query.getSingleResult();
        return new ResponseEntity<>(registrationInforDTO, HttpStatus.OK);
    }

    /**
     * Approve or Decline owner's registration.
     *
     * @param userId   id of the user
     * @param approval is the decision of approval
     * @return
     */
    @Override
    public ResponseEntity<?> registrationApproval(Long userId, Long facilityId, boolean approval) {
        int status = (approval ? 2:1); /*1-rejected 2-approved */
        Optional<Registration> registrationOptional = registrationRepository.findByUserId(userId);
        if (approval) {  /* Approve registration */
            // Change status of registration
            if (registrationOptional.isPresent()){
                Registration registration = registrationOptional.get();
                registration.setStatus(status);
                registrationRepository.save(registration);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            // Change status of Owner's account
            Optional<User> userOptional = userRepository.findByUserId(userId);
            if (userOptional.isPresent()){
                User user = userOptional.get();
                user.setStatus(1);
                userRepository.save(user);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            // Change status of Facility
            Optional<Facility> facilityOptional = facilityRepository.findByUserId(userId);
            if (facilityOptional.isPresent()){
                Facility facility = facilityOptional.get();
                facility.setStatus(1);
                facilityRepository.save(facility);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            //  Send message to Owner
            String mess = "Đơn đăng ký của bạn đã được chấp thuận! Chào mừng đến với EIMS.";
            //
            return new ResponseEntity<>("Đã chấp thuận đơn đăng ký", HttpStatus.OK);
        } else { /* Decline registration */
            // Change status of registration
            if (registrationOptional.isPresent()){
                Registration registration = registrationOptional.get();
                registration.setStatus(status);
                registrationRepository.save(registration);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            //  Send mess to Owner
            String mess = "Đơn đăng ký của bạn đã bị từ chối! Vui lòng liên hệ eims.contact để biết thêm thông tin chi tiết.";
            //
            return new ResponseEntity<>("Đã từ chối đơn đăng ký", HttpStatus.OK);
        }
    }
}
