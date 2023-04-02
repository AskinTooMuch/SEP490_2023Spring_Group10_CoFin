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

package com.example.eims.service.impl;

import com.example.eims.dto.payment.ChargeDTO;
import com.example.eims.service.interfaces.IStripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class StripeServiceImpl implements IStripeService {
    @Value("${stripe.keys.secret}")
    private String API_SECRET_KEY;

    public StripeServiceImpl() {
    }

    public PaymentIntent createCharge(ChargeDTO chargeDTO) throws StripeException {
        Stripe.apiKey = API_SECRET_KEY;
        System.out.println(chargeDTO);

        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setAmount(chargeDTO.getAmount())
                .setCurrency(chargeDTO.getCurrency())
                .setDescription("chargeDTO.getDescription()")
                .setPaymentMethod(chargeDTO.getMethod())
                .setConfirmationMethod(PaymentIntentCreateParams.ConfirmationMethod.MANUAL)
                .setConfirm(true)
                .build();



        return PaymentIntent.create(createParams);
    }
}
