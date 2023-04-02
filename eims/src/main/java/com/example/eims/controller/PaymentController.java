/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 02/04/2023   1.0         ChucNV     First Deploy<br>
 */

package com.example.eims.controller;

import com.example.eims.dto.payment.ChargeDTO;
import com.example.eims.service.impl.StripeServiceImpl;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.stripe.net.StripeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/subscribe")
public class PaymentController {
    @Autowired
    StripeServiceImpl stripeService;
    @PostMapping("/charge")
    public ResponseEntity<?> chargeCard(@RequestBody ChargeDTO chargeDTO) throws Exception {
        PaymentIntent intent = stripeService.createCharge(chargeDTO);
        StripeResponse lastResponse = intent.getLastResponse();
        if (Objects.equals(intent.getStatus(), "succeeded")) {
            return new ResponseEntity<>("succeeded", HttpStatus.OK);
        }
        return new ResponseEntity<>("succeeded", HttpStatus.BAD_REQUEST);
    }
}
