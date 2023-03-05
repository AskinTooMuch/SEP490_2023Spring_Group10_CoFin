/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 04/03/2023   1.0         DuongVV     First Deploy<br>
 */

package com.example.eims.service.interfaces;

import org.springframework.http.ResponseEntity;

public interface IRegistrationService {

    /**
     * View list registration of owners.
     *
     * @return
     */
    public ResponseEntity<?> viewListRegistration();

    /**
     * View a registration of owner.
     *
     * @param userId the id of the owner
     * @return
     */
    public ResponseEntity<?> viewRegistration(Long userId);

    /**
     * Approve or Decline owner's registration.
     *
     * @param userId     id of the user
     * @param facilityId id of the user's facility
     * @param approval   is the decision of approval
     * @return
     */
    public ResponseEntity<?> registrationApproval(Long userId, Long facilityId, boolean approval);
}
