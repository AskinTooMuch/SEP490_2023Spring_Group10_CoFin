package com.example.eims.service.impl;

import com.example.eims.entity.Subscription;
import com.example.eims.repository.SubscriptionRepository;
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

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public ResponseEntity<?> getAllSubscription() {
        Optional<List<Subscription>> subscriptionListOpt = subscriptionRepository.findByStatus(true);
        if (subscriptionListOpt.isEmpty()) {
            return new ResponseEntity<>("Không có gói đăng ký khả dụng", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(subscriptionListOpt.get(), HttpStatus.OK);
    }
}
