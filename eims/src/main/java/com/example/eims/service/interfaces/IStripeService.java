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

package com.example.eims.service.interfaces;

import com.example.eims.dto.payment.ChargeDTO;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Coupon;
import com.stripe.model.PaymentIntent;

public interface IStripeService {

    /**
     * Create one-time payments
     * @param chargeDTO
     * @return
     */
    public PaymentIntent createCharge(ChargeDTO chargeDTO)  throws StripeException;

}
