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

package com.example.eims.service.interfaces;

import org.springframework.http.ResponseEntity;

public interface INotificationService {

    /**
     * Get new Notifications.
     *
     * @param facilityId the id of current logged-in user's facility.
     * @return list of Notifications
     */
    public ResponseEntity<?> getNewNotification(Long facilityId);

    /**
     * Get old Notifications.
     *
     * @param facilityId the id of current logged-in user's facility.
     * @return list of Notifications
     */
    public ResponseEntity<?> getOldNotification(Long facilityId);

    /**
     * Get 5 newest Notifications.
     *
     * @param facilityId the id of current logged-in user's facility.
     * @return list of Notifications
     */
    public ResponseEntity<?> getTopNewNotification(Long facilityId);

    /**
     * Delete a notification.
     *
     * @param notificationId the id of the notification
     * @return
     */
    public ResponseEntity<?> deleteNotification(Long notificationId);

}
