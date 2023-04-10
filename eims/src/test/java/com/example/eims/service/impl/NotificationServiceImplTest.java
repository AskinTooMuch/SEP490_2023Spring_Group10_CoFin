/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 10/04/2023   1.0         DuongVV     First Deploy<br>
 */

package com.example.eims.service.impl;

import com.example.eims.entity.EggBatch;
import com.example.eims.entity.EggProduct;
import com.example.eims.entity.IncubationPhase;
import com.example.eims.entity.Notification;
import com.example.eims.repository.EggBatchRepository;
import com.example.eims.repository.EggProductRepository;
import com.example.eims.repository.IncubationPhaseRepository;
import com.example.eims.repository.NotificationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {
    @Mock
    NotificationRepository notificationRepository;
    @Mock
    EggBatchRepository eggBatchRepository;
    @Mock
    EggProductRepository eggProductRepository;
    @Mock
    IncubationPhaseRepository incubationPhaseRepository;
    @InjectMocks
    NotificationServiceImpl notificationService;
    @Test
    @DisplayName("getNewNotificationUTCID01")
    void getNewNotificationUTCID01() {
        //Set up
        Long facilityId = 1L;
        Date today = Date.valueOf(LocalDate.now());
        Notification notification = new Notification();
        List<Notification> notificationList = new ArrayList<>();
        notificationList.add(notification);

        // Define behaviour of repository
        when(notificationRepository.findAllByFacilityIdAndDate(facilityId, today)).thenReturn(Optional.of(notificationList));
        // Run service method
        ResponseEntity<?> responseEntity = notificationService.getNewNotification(facilityId);
        // Assert
        assertEquals(notificationList, responseEntity.getBody());
    }
    @Test
    @DisplayName("getNewNotificationUTCID02")
    void getNewNotificationUTCID02() {
        //Set up
        Long facilityId = -1L;
        Date today = Date.valueOf(LocalDate.now());
        Notification notification = new Notification();
        List<Notification> notificationList = new ArrayList<>();
        notificationList.add(notification);

        // Define behaviour of repository
        when(notificationRepository.findAllByFacilityIdAndDate(facilityId, today)).thenReturn(Optional.empty());
        // Run service method
        ResponseEntity<?> responseEntity = notificationService.getNewNotification(facilityId);
        // Assert
        assertEquals(new ArrayList<>(), responseEntity.getBody());
    }

    @Test
    @DisplayName("getOldNotificationUTCID01")
    void getOldNotificationUTCID01() {
        //Set up
        Long facilityId = 1L;
        Date today = Date.valueOf(LocalDate.now());
        Notification notification = new Notification();
        List<Notification> notificationList = new ArrayList<>();
        notificationList.add(notification);

        // Define behaviour of repository
        when(notificationRepository.findAllByFacilityIdAndDateBefore(facilityId, today)).thenReturn(Optional.of(notificationList));
        // Run service method
        ResponseEntity<?> responseEntity = notificationService.getOldNotification(facilityId);
        // Assert
        assertEquals(notificationList, responseEntity.getBody());
    }

    @Test
    @DisplayName("getOldNotificationUTCID02")
    void getOldNotificationUTCID02() {
        //Set up
        Long facilityId = -1L;
        Date today = Date.valueOf(LocalDate.now());

        // Define behaviour of repository
        when(notificationRepository.findAllByFacilityIdAndDateBefore(facilityId, today)).thenReturn(Optional.empty());
        // Run service method
        ResponseEntity<?> responseEntity = notificationService.getOldNotification(facilityId);
        // Assert
        assertEquals(new ArrayList<>(), responseEntity.getBody());
    }
    @Test
    @DisplayName("getTopNewNotificationUTCID01")
    void getTopNewNotificationUTCID01() {
        //Set up
        Long facilityId = 1L;
        Date today = Date.valueOf(LocalDate.now());

        Notification notification1 = new Notification();
        notification1.setNotificationBrief("a");
        Notification notification2 = new Notification();
        notification2.setNotificationBrief("");
        notification2.setEggBatchId(1L);

        EggBatch eggBatch = new EggBatch();
        eggBatch.setEggBatchId(1L);

        EggProduct eggProduct = new EggProduct();
        eggProduct.setIncubationPhaseId(1L);

        IncubationPhase incubationPhase = new IncubationPhase();
        incubationPhase.setPhaseNumber(1);
        incubationPhase.setPhaseDescription("phase");

        List<Notification> notificationList = new ArrayList<>();
        notificationList.add(notification1);
        notificationList.add(notification2);
        // Define behaviour of repository
        when(notificationRepository.findAllByFacilityIdAndDate(facilityId, today)).thenReturn(Optional.of(notificationList));
        when(eggBatchRepository.findByEggBatchId(1L)).thenReturn(Optional.of(eggBatch));
        when(eggProductRepository.findEggProductLastPhase(1L)).thenReturn(Optional.of(eggProduct));
        when(incubationPhaseRepository.findByIncubationPhaseId(1L)).thenReturn(Optional.of(incubationPhase));
        // Run service method
        ResponseEntity<?> responseEntity = notificationService.getTopNewNotification(facilityId);
        List<Notification> result = (List<Notification>) responseEntity.getBody();
        // Assert
        assertEquals(2, result.size());
    }
    @Test
    @DisplayName("getTopNewNotificationUTCID02")
    void getTopNewNotificationUTCID02() {
        //Set up
        Long facilityId = 1L;
        Date today = Date.valueOf(LocalDate.now());

        Notification notification1 = new Notification();
        notification1.setNotificationBrief("a");
        Notification notification2 = new Notification();
        notification2.setNotificationBrief("");
        notification2.setEggBatchId(1L);

        EggBatch eggBatch = new EggBatch();
        eggBatch.setEggBatchId(1L);

        EggProduct eggProduct = new EggProduct();
        eggProduct.setIncubationPhaseId(1L);

        IncubationPhase incubationPhase = new IncubationPhase();
        incubationPhase.setPhaseNumber(0);
        incubationPhase.setPhaseDescription("phase");

        List<Notification> notificationList = new ArrayList<>();
        notificationList.add(notification1);
        notificationList.add(notification2);
        // Define behaviour of repository
        when(notificationRepository.findAllByFacilityIdAndDate(facilityId, today)).thenReturn(Optional.of(notificationList));
        when(eggBatchRepository.findByEggBatchId(1L)).thenReturn(Optional.of(eggBatch));
        when(eggProductRepository.findEggProductLastPhase(1L)).thenReturn(Optional.of(eggProduct));
        when(incubationPhaseRepository.findByIncubationPhaseId(1L)).thenReturn(Optional.of(incubationPhase));
        // Run service method
        ResponseEntity<?> responseEntity = notificationService.getTopNewNotification(facilityId);
        List<Notification> result = (List<Notification>) responseEntity.getBody();
        // Assert
        assertEquals(2, result.size());
    }
    @Test
    @DisplayName("getTopNewNotificationUTCID03")
    void getTopNewNotificationUTCID03() {
        //Set up
        Long facilityId = -1L;
        Date today = Date.valueOf(LocalDate.now());

        // Define behaviour of repository
        when(notificationRepository.findAllByFacilityIdAndDate(facilityId, today)).thenReturn(Optional.empty());
        // Run service method
        ResponseEntity<?> responseEntity = notificationService.getTopNewNotification(facilityId);
        // Assert
        assertEquals(new ArrayList<>(), responseEntity.getBody());
    }

    @Test
    @DisplayName("deleteNotificationUTCID01")
    void deleteNotificationUTCID01() {
        //Set up
        Long notificationId = -1L;

        // Define behaviour of repository
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.empty());
        // Run service method
        ResponseEntity<?> responseEntity = notificationService.deleteNotification(notificationId);
        // Assert
        assertEquals("Không tìm thấy thông báo", responseEntity.getBody());
    }

    @Test
    @DisplayName("deleteNotificationUTCID02")
    void deleteNotificationUTCID02() {
        //Set up
        Long notificationId = 1L;
        Notification notification = new Notification();

        // Define behaviour of repository
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));
        // Run service method
        ResponseEntity<?> responseEntity = notificationService.deleteNotification(notificationId);
        // Assert
        assertEquals("Xác nhận hoàn thành", responseEntity.getBody());
    }
}