/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 29/03/2023   1.0         DuongVV     First Deploy<br>
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
import com.example.eims.service.interfaces.INotificationService;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements INotificationService {
    @Autowired
    private final NotificationRepository notificationRepository;
    @Autowired
    private final EggBatchRepository eggBatchRepository;
    @Autowired
    private final EggProductRepository eggProductRepository;
    @Autowired
    private final IncubationPhaseRepository incubationPhaseRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   EggBatchRepository eggBatchRepository, EggProductRepository eggProductRepository,
                                   IncubationPhaseRepository incubationPhaseRepository) {
        this.notificationRepository = notificationRepository;
        this.eggBatchRepository = eggBatchRepository;
        this.eggProductRepository = eggProductRepository;
        this.incubationPhaseRepository = incubationPhaseRepository;
    }

    /**
     * Get new Notifications.
     *
     * @param facilityId the id of current logged-in user's facility.
     * @return list of Notifications
     */
    @Override
    public ResponseEntity<?> getNewNotification(Long facilityId) {
        // Get today's notification
        Date date = Date.valueOf(LocalDate.now());
        Optional<List<Notification>> notificationListOptional = notificationRepository
                .findAllByFacilityIdAndDate(facilityId, date);

        if (notificationListOptional.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        List<Notification> notificationList = notificationListOptional.get();
        for (Notification notification : notificationList) {
            String brief = "";
            EggBatch eggBatch = eggBatchRepository.findByEggBatchId(notification.getEggBatchId()).get();
            Optional<EggProduct> eggProductOptional = eggProductRepository
                    .findEggProductByPhaseNumber(eggBatch.getEggBatchId(), 1);

            // Get latest phase
            int progress = 0;
            String phaseDescription = "";
            Optional<EggProduct> eggProductLastPhaseOptional = eggProductRepository.
                    findEggProductLastPhase(eggBatch.getEggBatchId());
            if (eggProductLastPhaseOptional.isPresent()) {
                Long incubationPhaseId = eggProductLastPhaseOptional.get().getIncubationPhaseId();
                IncubationPhase phase = incubationPhaseRepository.findByIncubationPhaseId(incubationPhaseId).get();
                progress = phase.getPhaseNumber();
                phaseDescription = phase.getPhaseDescription();
            }
            if (progress == 0) {
                brief = "Lô trứng mã " + eggBatch.getEggBatchId() + " chưa bắt đầu ấp";
            } else {
                brief = "Lô trứng mã " + eggBatch.getEggBatchId() + " ,giai đoạn " +
                        progress + " (" + phaseDescription + ") cần cập nhật";
            }
            // Set notification's brief
            notification.setNotificationBrief(brief);
        }
        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }

    /**
     * Get old Notifications.
     *
     * @param facilityId the id of current logged-in user's facility.
     * @return list of Notifications
     */
    @Override
    public ResponseEntity<?> getOldNotification(Long facilityId) {
        // Get before today's notification
        Date date = Date.valueOf(LocalDate.now());
        Optional<List<Notification>> notificationListOptional = notificationRepository
                .findAllByFacilityIdAndDateBefore(facilityId, date);
        if (notificationListOptional.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        List<Notification> notificationList = notificationListOptional.get();
        for (Notification notification : notificationList) {
            String brief = "";
            EggBatch eggBatch = eggBatchRepository.findByEggBatchId(notification.getEggBatchId()).get();
            Optional<EggProduct> eggProductOptional = eggProductRepository
                    .findEggProductByPhaseNumber(eggBatch.getEggBatchId(), 1);

            // Get latest phase
            int progress = 0;
            String phaseDescription = "";
            Optional<EggProduct> eggProductLastPhaseOptional = eggProductRepository.
                    findEggProductLastPhase(eggBatch.getEggBatchId());
            if (eggProductLastPhaseOptional.isPresent()) {
                Long incubationPhaseId = eggProductLastPhaseOptional.get().getIncubationPhaseId();
                IncubationPhase phase = incubationPhaseRepository.findByIncubationPhaseId(incubationPhaseId).get();
                progress = phase.getPhaseNumber();
                phaseDescription = phase.getPhaseDescription();
            }
            if (progress == 0) {
                brief = "Lô trứng mã " + eggBatch.getEggBatchId() + " chưa bắt đầu ấp";
            } else {
                brief = "Lô trứng mã " + eggBatch.getEggBatchId() + " ,giai đoạn " +
                        progress + " (" + phaseDescription + ") cần cập nhật";
            }
            // Set notification's brief
            notification.setNotificationBrief(brief);
        }
        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }

    /**
     * Get 5 newest Notifications.
     *
     * @param facilityId the id of current logged-in user's facility.
     * @return list of Notifications
     */
    @Override
    public ResponseEntity<?> getTopNewNotification(Long facilityId) {
        // Get top 5 newest notification
        Date date = Date.valueOf(LocalDate.now());
        Optional<List<Notification>> notificationListOptional = notificationRepository
                .getTopNotification(facilityId);

        if (notificationListOptional.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        List<Notification> notificationList = notificationListOptional.get();
        for (Notification notification : notificationList) {
            // Skip old notification
            if (!notification.getNotificationBrief().equals("")){
                continue;
            }
            // Set new notification's brief
            String brief = "";
            EggBatch eggBatch = eggBatchRepository.findByEggBatchId(notification.getEggBatchId()).get();
            Optional<EggProduct> eggProductOptional = eggProductRepository
                    .findEggProductByPhaseNumber(eggBatch.getEggBatchId(), 1);

            // Get latest phase
            int progress = 0;
            String phaseDescription = "";
            Optional<EggProduct> eggProductLastPhaseOptional = eggProductRepository.
                    findEggProductLastPhase(eggBatch.getEggBatchId());
            if (eggProductLastPhaseOptional.isPresent()) {
                Long incubationPhaseId = eggProductLastPhaseOptional.get().getIncubationPhaseId();
                IncubationPhase phase = incubationPhaseRepository.findByIncubationPhaseId(incubationPhaseId).get();
                progress = phase.getPhaseNumber();
                phaseDescription = phase.getPhaseDescription();
            }
            if (progress == 0) {
                brief = "Lô trứng mã " + eggBatch.getEggBatchId() + " chưa bắt đầu ấp";
            } else {
                brief = "Lô trứng mã " + eggBatch.getEggBatchId() + " ,giai đoạn " +
                        progress + " (" + phaseDescription + ") cần cập nhật";
            }
            // Set notification's brief
            notification.setNotificationBrief(brief);
        }
        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }

    /**
     * Delete a notification.
     *
     * @param notificationId the id of the notification
     * @return
     */
    @Override
    public ResponseEntity<?> deleteNotification(Long notificationId) {
        Optional<Notification> notificationOptional = notificationRepository.findById(notificationId);
        if (notificationOptional.isEmpty()) { // Null
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            notificationRepository.delete(notificationOptional.get());
            return new ResponseEntity<>("Hoàn thành", HttpStatus.OK);
        }
    }
}
