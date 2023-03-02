/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 02/03/2023   1.0         DuongVV     First Deploy<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.specie.EditSpecieDTO;
import com.example.eims.dto.specie.NewSpecieDTO;
import com.example.eims.entity.Specie;
import com.example.eims.service.interfaces.ISpecieService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class SpecieServiceImpl implements ISpecieService {
    /**
     * Create a new specie after checking the phone number (session) is valid
     *
     * @param newSpecieDTO contains user's id, specie's name and incubation period
     * @return
     */
    @Override
    public ResponseEntity<String> newSpecie(NewSpecieDTO newSpecieDTO) {
        return null;
    }

    /**
     * Get list species of a user
     *
     * @param userId the id of current logged-in user
     * @return
     */
    @Override
    public ResponseEntity<List<Specie>> listSpecie(Long userId) {
        return null;
    }

    /**
     * Edit specie's information : Getting the original values
     *
     * @param specieId the id of the specie
     * @return
     */
    @Override
    public ResponseEntity<Specie> getSpecie(Long specieId) {
        return null;
    }

    /**
     * Edit specie's information : Saving the data
     *
     * @param editSpecieDTO contains user's id, specie's name and incubation period
     * @return
     */
    @Override
    public ResponseEntity<Specie> saveSpecie(EditSpecieDTO editSpecieDTO) {
        return null;
    }

    /**
     * Delete one user's specie
     *
     * @param specieId the id of the specie
     * @return
     */
    @Override
    public ResponseEntity<String> deleteSpecie(Long specieId) {
        return null;
    }
}
