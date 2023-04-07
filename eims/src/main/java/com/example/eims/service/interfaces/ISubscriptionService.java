/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author      DESCRIPTION<br>
 * 04/04/2023    1.0        ChucNV      First Deploy<br>
 * 05/04/2023    2.0        ChucNV      Add get discount<br>
 * 06/04/2023    3.0        ChucNV      Modify get discount<br>
 * 07/04/2023    4.0        ChucNV      Add getAllSubscriptionByFacilityId<br>
 */
package com.example.eims.service.interfaces;

import org.springframework.http.ResponseEntity;

public interface ISubscriptionService {
    /**
     * Get all subscriptions.
     *
     * @return list of Subscription list or error message
     */
    public ResponseEntity<?> getAllSubscription();

    /**
     * Get 1 subscription by id
     * @return Subscription or error message
     */
    public ResponseEntity<?> getSubscriptionById(Long subscriptionId);

    /**
     * Get 1 subscription by id
     * @return Discount of the pack or error message
     */
    public ResponseEntity<?> getDiscountByFacilityId(Long subscriptionId, Long facilityId);

    /**
     * Get all subscriptions of a facility
     * @param facilityId
     * @return
     */
    public ResponseEntity<?> getAllSubscriptionByFacilityId(Long facilityId);
}
