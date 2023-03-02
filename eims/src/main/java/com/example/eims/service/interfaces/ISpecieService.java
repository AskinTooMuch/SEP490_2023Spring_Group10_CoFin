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

package com.example.eims.service.interfaces;

import com.example.eims.dto.specie.EditSpecieDTO;
import com.example.eims.dto.specie.NewSpecieDTO;
import com.example.eims.entity.Specie;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ISpecieService {
    /**
     * Create a new specie after checking the phone number (session) is valid
     *
     * @param newSpecieDTO contains user's id, specie's name and incubation period
     * @return
     */
    public ResponseEntity<String> newSpecie(NewSpecieDTO newSpecieDTO);

    /**
     * Get list species of a user
     *
     * @param userId the id of current logged-in user
     * @return
     */
    public ResponseEntity<List<Specie>> listSpecie(Long userId);

    /**
     * Edit specie's information : Getting the original values
     *
     * @param specieId the id of the specie
     * @return
     */
    public ResponseEntity<Specie> getSpecie(Long specieId);

    /**
     * Edit specie's information : Saving the data
     *
     * @param editSpecieDTO contains user's id, specie's name and incubation period
     * @return
     */
    public ResponseEntity<Specie> saveSpecie(EditSpecieDTO editSpecieDTO);

    /**
     * Delete one user's specie
     *
     * @param specieId the id of the specie
     * @return
     */
    public ResponseEntity<String> deleteSpecie(Long specieId);
}
