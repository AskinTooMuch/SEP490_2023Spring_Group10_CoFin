/*
 * Copyright (C) 2023, FPT University<br>
 * SEP490 - SEP490_G10<br>
 * EIMS<br>
 * Eggs Incubating Management System<br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 07/03/2023    1.0        ChucNV           First Deploy<br>
 * 12/03/2023    1.1        ChucNV           Fix notation<br>
 * 29/03/2023    2.0        ChucNV           Remove pre-service checks<br>
 */
package com.example.eims.controller;

import com.example.eims.dto.breed.EditBreedDTO;
import com.example.eims.dto.breed.NewBreedDTO;
import com.example.eims.service.interfaces.IBreedService;
import com.example.eims.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/breed")
public class BreedController {

    @Autowired
    private IBreedService breedService;

    public final Validator validator = new Validator();

    /**
     * Create new breed
     *
     * @param newBreedDTO contains the breed information
     * @return response message or new breed
     */
    @PostMapping("/new")
    public ResponseEntity<?> createNewBreed(@ModelAttribute NewBreedDTO newBreedDTO) {
        //Create new breed
        return breedService. createNewBreed(newBreedDTO);
    }

    /**
     * Get breed by breed id
     *
     * @param breedId contains the breed's id
     * @return response message or new breed
     */
    @GetMapping("/detail/breedId")
    public ResponseEntity<?> getBreedDetailByBreedId(@RequestParam Long breedId){
        System.out.println("Get by breed id " + breedId);
        return breedService.viewBreedDetailById(breedId);
    }

    /**
     * Get breed list by specie id
     *
     * @param specieId contains the breed's id
     * @return response message or new breed
     */
    @GetMapping("/detail/specieId")
    public ResponseEntity<?> getBreedDetailBySpecieId(@RequestParam Long specieId){
        return breedService.viewListBreedBySpecie(specieId);
    }

    /**
     * Get breed list by user id
     *
     * @param userId contains the users id
     * @return response message or new breed
     */
    @GetMapping("/detail/userId")
    public ResponseEntity<?> getBreedDetailByUserId(@RequestParam Long userId){
        return breedService.viewListBreedByUser(userId);
    }

    /**
     * Deactivate breed by breed id
     *
     * @param breedId contains the breed's id
     * @return response message or new breed
     */
    @GetMapping("/delete")
    public ResponseEntity<?> deactivateBreed(@RequestParam Long breedId){
        return breedService.deleteBreed(breedId);
    }

    /**
     * Edit breed by breed id
     *
     * @param editBreedDTO contains the breed's id
     * @return response message or new breed
     */
    @PostMapping("/edit")
    public ResponseEntity<?> editBreed(@ModelAttribute EditBreedDTO editBreedDTO){
        //Update breed info
        return breedService.updateBreed(editBreedDTO);
    }

    /**
     * Load breeds image by breed id
     *
     * @param breedId contains the breed's id
     * @return response message or breed image base 64 string
     */
    @GetMapping("/detail/breedId/image")
    public ResponseEntity<?> loadBreedImage(@RequestParam Long breedId){
        return breedService.loadBreedImage(breedId);
    }
}
