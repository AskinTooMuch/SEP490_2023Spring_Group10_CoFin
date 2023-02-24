/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author      DESCRIPTION<br>
 * 22/02/2023    1.0        ChucNV      First Deploy<br>
 * 22/02/2023    1.0        ChucNV      Add new specie API
 * 23/02/2023    2.0        ChucNV      Add edit, list, delete specie API
 */

package com.example.eims.controller;

import com.example.eims.dto.specie.EditSpecieDTO;
import com.example.eims.dto.specie.NewSpecieDTO;
import com.example.eims.entity.Specie;
import com.example.eims.entity.User;
import com.example.eims.repository.SpecieRepository;
import com.example.eims.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/specie")
public class SpecieController {
    @Autowired
    private SpecieRepository specieRepository;

    @Autowired
    UserRepository userRepository;
    @PersistenceContext
    private EntityManager em;

    /**
     * Create a new specie after checking the phone number (session) is valid
     * @param newSpecieDTO
     * @return
     */
    @PostMapping("/new")
    //@Secured({"ROLE_OWNER"})
    public ResponseEntity<String> newSpecie(@RequestBody NewSpecieDTO newSpecieDTO){
        System.out.println(newSpecieDTO);
        Optional<User> userOpt = userRepository.findByPhone(newSpecieDTO.getPhone());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Specie specie = new Specie();
            specie.setSpecieId(100L);
            specie.setUserId(user.getUserId());
            specie.setSpecieName(newSpecieDTO.getSpecieName());
            specie.setIncubationPeriod(newSpecieDTO.getIncubationPeriod());
            specie.setStatus(true);
            specieRepository.save(specie);
            return new ResponseEntity<>("Specie added successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Account not found with phone number "+newSpecieDTO.getPhone(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Get list species of a user
     * @param userId
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<Specie>> listSpecie(@RequestParam Long userId){
        System.out.println("Requesting user id: " + userId);
        Optional<List<Specie>> speciesOpt = specieRepository.findByUserId(userId);
        if (speciesOpt.isPresent()){
            List<Specie> specieList = speciesOpt.get();
            System.out.println(specieList);
            return new ResponseEntity<>(specieList, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    /**
     * Edit specie's information : Getting the original values
     * @param specieId
     * @return
     */
    @PostMapping("/edit/get")
    public ResponseEntity<Specie> getSpecie(@RequestParam Long specieId){
        System.out.println("Requesting specie id: " + specieId);
        Optional<Specie> specieOpt = specieRepository.findById(specieId);
        if (specieOpt.isPresent()){
            Specie specie = specieOpt.get();
            return new ResponseEntity<>(specie, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    /**
     * Edit specie's information : Saving the data
     * @param editSpecieDTO
     * @return
     */
    @PostMapping("/edit/save")
    public ResponseEntity<Specie> saveSpecie(@RequestBody EditSpecieDTO editSpecieDTO){
        System.out.println("Requesting specie id: " + editSpecieDTO.getSpecieId());
        Optional<Specie> specieOpt = specieRepository.findById(editSpecieDTO.getSpecieId());
        if (specieOpt.isPresent()){
            Specie specie = specieOpt.get();
            specie.setSpecieName(editSpecieDTO.getSpecieName());
            specie.setIncubationPeriod(editSpecieDTO.getIncubationPeriod());
            specieRepository.save(specie);
            return new ResponseEntity<>(specie, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    /**
     * Delete one user's specie
     * @param specieId
     * @return
     */
    @PostMapping("/delete")
    public ResponseEntity<String> deleteSpecie(@RequestParam Long specieId){
        if (specieRepository.findById(specieId).isPresent()){
            specieRepository.deleteById(specieId);
            return new ResponseEntity<>("Specie delete successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
