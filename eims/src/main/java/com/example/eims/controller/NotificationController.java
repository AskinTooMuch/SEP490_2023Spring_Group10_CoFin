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

package com.example.eims.controller;

import com.example.eims.service.interfaces.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    @Autowired
    private INotificationService notificationService;
    /**
     * Get new Notifications.
     *
     * @param facilityId the id of current logged-in user's facility.
     * @return list of Notifications
     */
    @GetMapping("/all/new")
    public ResponseEntity<?> getNewNotification(@RequestParam Long facilityId){
        return notificationService.getNewNotification(facilityId);
    }

    /**
     * Get old Notifications.
     *
     * @param facilityId the id of current logged-in user's facility.
     * @return list of Notifications
     */
    @GetMapping("/all/old")
    public ResponseEntity<?> getOldNotification(@RequestParam Long facilityId){
        return notificationService.getOldNotification(facilityId);
    }

    /**
     * Get 5 newest Notifications.
     *
     * @param facilityId the id of current logged-in user's facility.
     * @return list of Notifications
     */
    @GetMapping("/top")
    public ResponseEntity<?> getTopNewNotification(Long facilityId){
        return notificationService.getTopNewNotification(facilityId);
    }

    /**
     * Delete a notification.
     *
     * @param notificationId the id of the notification
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteNotification(Long notificationId){
        return notificationService.deleteNotification(notificationId);
    }
}
