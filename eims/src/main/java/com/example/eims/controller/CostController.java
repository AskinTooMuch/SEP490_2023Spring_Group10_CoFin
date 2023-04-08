/*
 * Copyright (C) 2023, FPT University<br>
 * SEP490 - SEP490_G10<br>
 * EIMS<br>
 * Eggs Incubating Management System<br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 27/03/2023   1.0         DuongNH     First Deploy<br>
 * 27/03/2023   2.0         DuongNH     Add function<br>
 */
package com.example.eims.controller;

import com.example.eims.dto.cost.CreateCostDTO;
import com.example.eims.dto.cost.UpdateCostDTO;
import com.example.eims.service.interfaces.ICostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/cost")
public class CostController {
    @Autowired
    ICostService costService;

    /**
     * Get all cost of a user
     * @param userId owner's id
     * @return List of cost
     */
    @GetMapping("/all")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> getAllCost(@RequestParam Long userId){
        return costService.getAllCost(userId);
    }

    /**
     * Get all information of a cost
     * @param costId Cost's id
     * @return message
     */
    @Secured({"ROLE_OWNER"})
    @GetMapping("/get")
    public ResponseEntity<?> getCostById(@RequestParam Long costId){
        return costService.getCostById(costId);
    }

    /**
     * Save new cost information to the database
     * @param createCostDTO contain information of cost to be added to the database
     * @return message
     */
    @PostMapping("/create")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> createCost(@RequestBody CreateCostDTO createCostDTO){
        return costService.createCost(createCostDTO);
    }

    /**
     * Get all information of a cost
     * @param costId cost's id
     * @return CostDTO
     */
    @Secured({"ROLE_OWNER"})
    @GetMapping("/update/get")
    public ResponseEntity<?> getUpdateCostById(@RequestParam Long costId){
        return costService.getCostById(costId);
    }

    /**
     * Save new cost information to the database
     * @param updateCostDTO contain cost's new information
     * @return message
     */
    @Secured({"ROLE_OWNER"})
    @PostMapping("/update/save")
    public ResponseEntity<?> updateCost(@RequestBody UpdateCostDTO updateCostDTO){
        return costService.updateCost(updateCostDTO);
    }

    /**
     * Find cost by name
     * @param userId owner's id
     * @param costName cost name to find
     * @return list of Cost
     */
    @GetMapping("/search")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> searchCost(@RequestParam Long userId,@RequestParam String costName){
        return costService.searchCostByName(userId,costName);
    }
}
