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
 * 06/04/2023    3.0        ChucNV      Modify getDiscountByFacilityId to get other attributes<br>
 */
package com.example.eims.controller;

import com.example.eims.dto.payment.ChargeDTO;
import com.example.eims.service.impl.SubscriptionServiceImpl;
import com.stripe.model.PaymentIntent;
import com.stripe.net.StripeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("api/subscription")
public class SubscriptionController {
    @Autowired
    private SubscriptionServiceImpl subscriptionService;

    /**
     * Get all active subscriptions
     * @return
     * @throws Exception
     */
    @GetMapping("/getActive")
    public ResponseEntity<?> getAll() throws Exception {
        return subscriptionService.getAllSubscription();
    }

    /**
     * Get 1 subscription by id
     * @return
     * @throws Exception
     */
    @GetMapping("/getById")
    public ResponseEntity<?> getSubscriptionById(@RequestParam Long subscriptionId) throws Exception {
        return subscriptionService.getSubscriptionById(subscriptionId);
    }

    /**
     * Get 1 subscription discount and machine information by id
     * @return
     * @throws Exception
     */
    @GetMapping("/getDiscount")
    public ResponseEntity<?> getDiscountByFacilityId(@RequestParam Long subscriptionId, @RequestParam Long facilityId) throws Exception {
        return subscriptionService.getDiscountByFacilityId(subscriptionId, facilityId);
    }

    /**
     * Get all subscription by facility
     * @return
     * @throws Exception
     */
    @GetMapping("/getHistory")
    public ResponseEntity<?> getAllSubscriptionByFacilityId(@RequestParam Long facilityId) throws Exception {
        return subscriptionService.getAllSubscriptionByFacilityId(facilityId);
    }
}
