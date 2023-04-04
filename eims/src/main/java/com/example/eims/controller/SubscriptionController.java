/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author      DESCRIPTION<br>
 * 04/04/2023    1.0        ChucNV      First Deploy<br>
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
     * Make a payment through Stripe
     * @return
     * @throws Exception
     */
    @GetMapping("/getActive")
    public ResponseEntity<?> getAll() throws Exception {
        return subscriptionService.getAllSubscription();
    }

}
