/*
 * Copyright (C) 2023, FPT University<br>
 * SEP490 - SEP490_G10<br>
 * EIMS<br>
 * Eggs Incubating Management System<br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 07/03/2023    1.0        ChucNV           First Deploy<br>
 */
package com.example.eims.controller;

import com.example.eims.dto.breed.EditBreedDTO;
import com.example.eims.dto.breed.NewBreedDTO;
import com.example.eims.service.impl.FileStorageServiceImpl;
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

    @Autowired
    private FileStorageServiceImpl fileStorageServiceImpl;

    public final Validator validator = new Validator();

    /**
     * Create new breed
     *
     * @param newBreedDTO contains the breed information
     * @return response message or new breed
     */
    @PostMapping("/new")
    public ResponseEntity<?> createNewBreed(@ModelAttribute NewBreedDTO newBreedDTO) {
        System.out.println(newBreedDTO.getBreedName());
        //Check data and trim
        if ((newBreedDTO.getAverageWeightFemale() <= 0) ||
                (newBreedDTO.getAverageWeightMale() <= 0) ||
                (newBreedDTO.getGrowthTime() <= 0)) {
            return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);
        }
        try {
            newBreedDTO.setBreedName(validator.advanceTrim(newBreedDTO.getBreedName(), true));
            newBreedDTO.setCommonDisease(validator.advanceTrim(newBreedDTO.getCommonDisease(), false));
        } catch (RuntimeException runtimeException) {
            return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);
        }
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
        return breedService.viewBreedDetailBySpecie(specieId);
    }

    /**
     * Get breed list by user id
     *
     * @param userId contains the users id
     * @return response message or new breed
     */
    @GetMapping("/detail/userId")
    public ResponseEntity<?> getBreedDetailByUserId(@RequestParam Long userId){
        return breedService.viewBreedDetailByUser(userId);
    }

    /**
     * Deactivate breed by breed id
     *
     * @param breedId contains the breed's id
     * @return response message or new breed
     */
    @PostMapping("/delete")
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
    public ResponseEntity<?> editBreed(@RequestBody EditBreedDTO editBreedDTO){
        System.out.println(editBreedDTO);
        //Check data and trim
        if ((editBreedDTO.getAverageWeightFemale() <= 0) ||
                (editBreedDTO.getAverageWeightMale() <= 0) ||
                (editBreedDTO.getGrowthTime() <= 0)) {
            return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);
        }
        try {
            editBreedDTO.setBreedName(validator.advanceTrim(editBreedDTO.getBreedName(), true));
            editBreedDTO.setCommonDisease(validator.advanceTrim(editBreedDTO.getCommonDisease(), false));
        } catch (RuntimeException runtimeException) {
            return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);
        }
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
