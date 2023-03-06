/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author      DESCRIPTION<br>
 * 22/02/2023    1.0        ChucNV      First Deploy<br>
 * 22/02/2023    1.1        ChucNV      Add new specie API<br>
 * 23/02/2023    2.0        ChucNV      Add edit, list, delete specie API<br>
 * 26/02/2023    2.1        ChucNV      Fix API to work with userId<br>
 * 02/03/2023    3.0        DuongVV     New code structure<br>
 */

package com.example.eims.controller;

import com.example.eims.dto.specie.DetailSpecieDTO;
import com.example.eims.dto.specie.EditSpecieDTO;
import com.example.eims.dto.specie.NewSpecieDTO;
import com.example.eims.entity.Specie;
import com.example.eims.service.interfaces.ISpecieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/specie")
public class SpecieController {
    @Autowired
    private ISpecieService specieService;

    /**
     * Create a new specie after checking the phone number (session) is valid
     *
     * @param newSpecieDTO contains user's id, specie's name and incubation period
     * @return
     */
    @PostMapping("/new")
    //@Secured({"ROLE_OWNER"})
    public ResponseEntity<String> newSpecie(@RequestBody NewSpecieDTO newSpecieDTO){
        return specieService.newSpecie(newSpecieDTO);
    }

    /**
     * Get list species of a user
     *
     * @param userId the id of current logged-in user
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<DetailSpecieDTO>> listSpecie(@RequestParam Long userId){
        return specieService.listSpecie(userId);
    }

    /**
     * Edit specie's information : Getting the original values
     *
     * @param specieId the id of the specie
     * @return
     */
    @PostMapping("/edit/get")
    public ResponseEntity<Specie> getSpecie(@RequestParam Long specieId){
        return specieService.getSpecie(specieId);
    }

    /**
     * Edit specie's information : Saving the data
     *
     * @param editSpecieDTO contains user's id, specie's name and incubation period
     * @return
     */
    @PostMapping("/edit/save")
    public ResponseEntity<?> saveSpecie(@RequestBody EditSpecieDTO editSpecieDTO){
        return specieService.saveSpecie(editSpecieDTO);
    }

    /**
     * Delete one user's specie
     *
     * @param specieId the id of the specie
     * @return
     */
    @GetMapping("/delete")
    public ResponseEntity<String> deleteSpecie(@RequestParam(value = "specieId") Long specieId){
        return specieService.deleteSpecie(specieId);
    }
}
