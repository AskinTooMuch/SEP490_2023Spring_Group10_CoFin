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
 */
package com.example.eims.service.impl;

import com.example.eims.entity.Subscription;
import com.example.eims.repository.FacilityRepository;
import com.example.eims.repository.SubscriptionRepository;
import com.example.eims.repository.UserSubscriptionRepository;
import com.example.eims.service.interfaces.ISubscriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements ISubscriptionService {
    @Autowired
    private final SubscriptionRepository subscriptionRepository;
    @Autowired
    private final FacilityRepository facilityRepository;
    @Autowired
    private final UserSubscriptionRepository userSubscriptionRepository;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, FacilityRepository facilityRepository, UserSubscriptionRepository userSubscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.facilityRepository = facilityRepository;
        this.userSubscriptionRepository = userSubscriptionRepository;
    }

    @Override
    public ResponseEntity<?> getAllSubscription() {
        Optional<List<Subscription>> subscriptionListOpt = subscriptionRepository.findByStatus(true);
        if (subscriptionListOpt.isEmpty()) {
            return new ResponseEntity<>("Không có gói đăng ký khả dụng", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(subscriptionListOpt.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getSubscriptionById(Long subscriptionId) {
        Optional<Subscription> subscriptionOpt = subscriptionRepository.findBySubscriptionId(subscriptionId);
        if (subscriptionOpt.isEmpty()) {
            return new ResponseEntity<>("Không tìm thấy gói đăng ký khả dụng", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(subscriptionOpt.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getDiscountByFacilityId(Long facilityId) {
        if (!facilityRepository.existsByFacilityId(facilityId)) {
            return new ResponseEntity<>("Không tìm thấy cơ sở khả dụng", HttpStatus.BAD_REQUEST);
        }
        float discount = userSubscriptionRepository.getDiscountByFacility(facilityId);
        System.out.println(discount);
        return new ResponseEntity<>(discount, HttpStatus.OK);
    }
}
