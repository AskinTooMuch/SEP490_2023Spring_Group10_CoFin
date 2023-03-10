/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 10/03/2023   1.0         DuongVV     First Deploy<br>
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
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    RegistrationRepository registrationRepository;
    @Mock
    FacilityRepository facilityRepository;
    @Mock
    EntityManager em;
    @InjectMocks
    RegistrationServiceImpl registrationService;
    @Test
    void viewListRegistration() {
        // Set up
        RegistrationListItemDTO itemDTO1 = new RegistrationListItemDTO();
        RegistrationListItemDTO itemDTO2 = new RegistrationListItemDTO();
        List<RegistrationListItemDTO> listDTO = new ArrayList<>();
        listDTO.add(itemDTO1);
        listDTO.add(itemDTO2);

        Query q = mock(Query.class);
        // Define behaviour of repository
        when(em.createNamedQuery("getRegistrationListByStatus")).thenReturn(q);
        when(q.getResultList()).thenReturn(listDTO);
        // Run service method
        ResponseEntity<?> responseEntity = registrationService.viewListRegistration();
        List<RegistrationListItemDTO> resultList = (List<RegistrationListItemDTO>) responseEntity.getBody();
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(2, resultList.size());
    }

    @Test
    void viewRegistration() {
        // Set up
        RegistrationInforDTO dto = new RegistrationInforDTO();

        Query q = mock(Query.class);
        // Define behaviour of repository
        when(em.createNamedQuery("getRegistrationInforForUser")).thenReturn(q);
        when(q.getSingleResult()).thenReturn(dto);
        // Run service method
        ResponseEntity<?> responseEntity = registrationService.viewRegistration(1L);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(dto, responseEntity.getBody());
    }

    @Test
    void registrationApproval() {
        // Set up
        Long userId = 1L;
        Long facilityId = 1L;
        boolean approval = true;

        Registration registration = new Registration();
        User user = new User();
        Facility facility = new Facility();
        // Define behaviour of repository
        when(registrationRepository.findByUserId(userId)).thenReturn(Optional.of(registration));
        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(user));
        when(facilityRepository.findByUserId(userId)).thenReturn(Optional.of(facility));

        // Run service method
        ResponseEntity<?> responseEntity = registrationService.registrationApproval(userId, facilityId, approval);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Owner's registration approved", responseEntity.getBody());
    }
}