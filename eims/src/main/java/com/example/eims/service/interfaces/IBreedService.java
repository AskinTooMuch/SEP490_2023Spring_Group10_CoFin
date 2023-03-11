/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 05/03/2023   1.0         ChucNV      First Deploy<br>
 */
package com.example.eims.service.interfaces;

import com.example.eims.dto.breed.NewBreedDTO;
import com.example.eims.dto.breed.EditBreedDTO;
import jakarta.servlet.http.HttpServlet;
import org.springframework.http.ResponseEntity;

public interface IBreedService {
    /**
     * Service to create a new Breed.
     * The newly added breed will have the status of 1 (true)
     * @param newBreedDTO Payload
     * @return New breed created or response message
     */
    ResponseEntity<?> createNewBreed (NewBreedDTO newBreedDTO);

    /**
     * Service to update existing breed.
     * The new information will be updated through the breed id
     * @param editBreedDTO Payload
     * @return Breed updated or response message
     */
    ResponseEntity<?> updateBreed (EditBreedDTO editBreedDTO);

    /**
     * Service to delete (disable) existing breed.
     * The new information will be updated through the breed id
     * @param breedId Breed id to be disabled
     * @return Response message
     */
    ResponseEntity<?> deleteBreed (Long breedId);

    /**
     * Service to view detail information of 1 breed
     * @param breedId Breed id to be queried
     * @return Breed information or response message
     */
    ResponseEntity<?> viewBreedDetailById (Long breedId);

    /**
     * Service to view breed detail information of 1 specie
     * @param specieId Specie id to be queried
     * @return Breed information or response message
     */
    ResponseEntity<?> viewBreedDetailBySpecie (Long specieId);

    /**
     * Service to view breed detail information of 1 specie
     * @param userId User id to be queried
     * @return Breed information or response message
     */
    ResponseEntity<?> viewBreedDetailByUser (Long userId);

    /**
     * Service to view breed image
     * @param breedId Breed id to be queried
     * @return Breed's image or response message
     */
    ResponseEntity<?> loadBreedImage (Long breedId);
}
