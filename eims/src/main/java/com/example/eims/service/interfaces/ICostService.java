/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 27/03/2023    1.0        DuongNH          First Deploy<br>
 */

package com.example.eims.service.interfaces;

import com.example.eims.dto.cost.CreateCostDTO;
import com.example.eims.dto.cost.UpdateCostDTO;
import org.springframework.http.ResponseEntity;

public interface ICostService {
    /**
     * Get all cost of a user
     * @param userId owner's id
     * @return List of cost
     */
    public ResponseEntity<?> getAllCost(Long userId);

    /**
     * Get all information of a cost
     * @param costId Cost's id
     * @return message
     */
    public ResponseEntity<?> getCostById(Long costId);

    /**
     * Save new cost information to the database
     * @param createCostDTO contain information of cost to be added to the database
     * @return message
     */
    public ResponseEntity<?> createCost(CreateCostDTO createCostDTO);

    /**
     * Save new cost information to the database
     * @param updateCostDTO contain cost's new information
     * @return message
     */
    public ResponseEntity<?> updateCost(UpdateCostDTO updateCostDTO);

    /**
     * Find cost by name
     * @param userId owner's id
     * @param costName cost name to find
     * @return list of Cost
     */
    public ResponseEntity<?> searchCostByName(Long userId, String costName);

    /**
     * Get all of user's cost with Paging.
     *
     * @param userId the id of the Owner
     * @param page   the page number
     * @param size   the size of page
     * @param sort   sorting type
     * @return list of Cost
     */
    public ResponseEntity<?> getAllCostPaging(Long userId, Integer page, Integer size, String sort);
}
