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
package com.example.eims.service.interfaces;

import com.example.eims.dto.payment.ChargeDTO;
import org.springframework.http.ResponseEntity;

public interface IPaymentService {

    /**
     * Charge the card and add the information into db
     * @param chargeDTO dto for making charges to card
     * @return list payroll
     */
    public ResponseEntity<?> chargeCard(ChargeDTO chargeDTO) throws Exception;
}
