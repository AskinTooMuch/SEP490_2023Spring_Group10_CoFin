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
package com.example.eims.service.impl;

import com.example.eims.dto.payment.ChargeDTO;
import com.example.eims.entity.UserSubscription;
import com.example.eims.repository.FacilityRepository;
import com.example.eims.repository.UserRepository;
import com.example.eims.repository.UserSubscriptionRepository;
import com.example.eims.service.interfaces.IPaymentService;
import com.stripe.model.PaymentIntent;
import com.stripe.net.StripeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PaymentServiceImpl implements IPaymentService {
    @Autowired
    StripeServiceImpl stripeService;

    @Autowired
    UserSubscriptionRepository userSubscriptionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FacilityRepository facilityRepository;
    @Override
    public ResponseEntity<?> chargeCard(ChargeDTO chargeDTO) throws Exception {
        if (!facilityRepository.existsById(chargeDTO.getFacilityId())) {
            return new ResponseEntity<>("Không tìm thấy cơ sở hoặc cơ sở đã bị vô hiệu hóa", HttpStatus.BAD_REQUEST);
        }
        PaymentIntent intent = stripeService.createCharge(chargeDTO);
        StripeResponse lastResponse = intent.getLastResponse();
        if (Objects.equals(intent.getStatus(), "succeeded")) {
            UserSubscription us = new UserSubscription();
            us.setFacilityId(chargeDTO.getFacilityId());
            us.setSubscriptionId(chargeDTO.getSubscriptionId());
            long millis=System.currentTimeMillis();
            java.sql.Date date=new java.sql.Date(millis);
            us.setSubscribeDate(date);
            userSubscriptionRepository.save(us);
            return new ResponseEntity<>("Thanh toán thành công", HttpStatus.OK);

        }
        return new ResponseEntity<>("Thanh toán thất bại", HttpStatus.BAD_REQUEST);

    }
}
