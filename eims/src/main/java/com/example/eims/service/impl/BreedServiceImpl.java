/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 07/03/2023   1.0         ChucNV      First Deploy<br>
 * 12/03/2023   2.0         ChucNV      Fix edit breed<br>
 */
package com.example.eims.service.impl;

import com.example.eims.dto.breed.DetailBreedDTO;
import com.example.eims.dto.breed.NewBreedDTO;
import com.example.eims.dto.breed.EditBreedDTO;
import com.example.eims.entity.Breed;
import com.example.eims.entity.Specie;
import com.example.eims.repository.BreedRepository;
import com.example.eims.repository.SpecieRepository;
import com.example.eims.repository.UserRepository;
import com.example.eims.service.CustomFileNotFoundException;
import com.example.eims.service.interfaces.IBreedService;
import com.example.eims.utils.StringDealer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
@Service
public class BreedServiceImpl implements IBreedService {
    @Autowired
    private final SpecieRepository specieRepository;
    @Autowired
    private final BreedRepository breedRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private FileStorageServiceImpl fileStorageServiceImpl;
    private final StringDealer stringDealer = new StringDealer();

    public BreedServiceImpl(SpecieRepository specieRepository, BreedRepository breedRepository, UserRepository userRepository) {
        this.specieRepository = specieRepository;
        this.breedRepository = breedRepository;
        this.userRepository = userRepository;
    }

    /**
     * Service to create a new Breed.
     * The newly added breed will have the status of 1 (true)
     * @param newBreedDTO Payload
     * @return New breed created or response message
     */
    @Override
    public ResponseEntity<?> createNewBreed(@ModelAttribute NewBreedDTO newBreedDTO) {
        System.out.println(newBreedDTO);
        Optional<Specie> specieOpt = specieRepository.findById(newBreedDTO.getSpecieId());
        if (specieOpt.isPresent()) {
            Specie specie = specieOpt.get();
            //Create file attributes
            //Save image
            String filename = "";
            if (newBreedDTO.getImage() != null && !newBreedDTO.getImage().isEmpty()) {
                filename = fileStorageServiceImpl.storeFile(newBreedDTO.getImage()).trim();
            }
            //Set other attributes
            Breed breed = new Breed();
            breed.setUserId(specie.getUserId());
            //Check
            if (specieRepository.findById(specie.getSpecieId()).isPresent()) {
                breed.setSpecieId(newBreedDTO.getSpecieId());
            } else {
                return new ResponseEntity<>("Không tìm thấy tên loài.", HttpStatus.NOT_FOUND);
            }
            if (userRepository.findById(specie.getUserId()).isPresent()) {
                breed.setUserId(specie.getUserId());
            } else {
                return new ResponseEntity<>("Không tìm thấy người dùng.", HttpStatus.NOT_FOUND);
            }
            // Name
            String name = stringDealer.trimMax(newBreedDTO.getBreedName());
            if (name.equals("")) { /* Supplier name is empty */
                return new ResponseEntity<>("Tên không được để trống", HttpStatus.BAD_REQUEST);
            } else {
                breed.setBreedName(name);
            }
            String commonDisease = stringDealer.trimMax(newBreedDTO.getCommonDisease());
            breed.setCommonDisease(commonDisease);
            // Number fields: DTO has automatically caught any error/exceptions
            breed.setAverageWeightMale(newBreedDTO.getAverageWeightMale());
            breed.setAverageWeightFemale(newBreedDTO.getAverageWeightFemale());
            breed.setGrowthTime(newBreedDTO.getGrowthTime());
            // Saved files name and new breed added as true status for default
            breed.setImageSrc(filename);
            breed.setStatus(true);
            try {
                breedRepository.save(breed);
            } catch (IllegalArgumentException iae) {
                return null;
            }
            return new ResponseEntity<>("Breed added successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Specie not found with Id number "+newBreedDTO.getSpecieId(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Service to update existing breed.
     * The new information will be updated through the breed id
     * @param editBreedDTO Payload
     * @return Breed updated or response message
     */
    @Override
    public ResponseEntity<?> updateBreed(@ModelAttribute EditBreedDTO editBreedDTO) {
        System.out.println(editBreedDTO);
        // Check if the breed or specie exist or not
        Optional<Specie> specieOpt = specieRepository.findById(editBreedDTO.getSpecieId());
        if (specieOpt.isEmpty()) {
            return new ResponseEntity<>("Không tìm thấy tên loài với id " + editBreedDTO.getSpecieId(), HttpStatus.BAD_REQUEST);
        }
        Optional<Breed> breedOpt = breedRepository.findById(editBreedDTO.getBreedId());
        if (breedOpt.isEmpty()) {
            return new ResponseEntity<>("Không tìm thấy tên loại với id " + editBreedDTO.getBreedId(), HttpStatus.BAD_REQUEST);
        }
        Breed breed = breedOpt.get();
        //Create file attributes
        String filename = "";
        if (editBreedDTO.getImage() != null && !editBreedDTO.getImage().isEmpty()) {
            filename = fileStorageServiceImpl.storeFile(editBreedDTO.getImage()).trim();
            breed.setImageSrc(filename);
        }
        //Set other attributes
        breed.setSpecieId(editBreedDTO.getSpecieId());
        // Name
        String name = stringDealer.trimMax(editBreedDTO.getBreedName());
        if (name.equals("")) { /* Supplier name is empty */
            return new ResponseEntity<>("Tên không được để trống", HttpStatus.BAD_REQUEST);
        } else {
            breed.setBreedName(name);
        }
        // Number fields: DTO has automatically caught any error/exceptions
        breed.setAverageWeightMale(editBreedDTO.getAverageWeightMale());
        breed.setAverageWeightFemale(editBreedDTO.getAverageWeightFemale());
        breed.setGrowthTime(editBreedDTO.getGrowthTime());

        breed.setCommonDisease(stringDealer.trimMax(editBreedDTO.getCommonDisease()));
        //The user not allowed to deactivate a breed in this form, only through delete tab
        breed.setStatus(true);
        try {
            breedRepository.save(breed);
        } catch (IllegalArgumentException iae) {
            return null;
        }
        return new ResponseEntity<>("Breed saved successfully", HttpStatus.OK);
    }

    /**
     * Service to delete (disable) existing breed.
     * The new information will be updated through the breed id
     * @param breedId Breed id to be disabled
     * @return Response message
     */
    @Override
    public ResponseEntity<?> deleteBreed(Long breedId) {
        Optional<Breed> breedOpt = breedRepository.findById(breedId);
        if (breedOpt.isPresent()) {
            Breed breed = breedOpt.get();
            breed.setStatus(false);
            breedRepository.save(breed);
            return new ResponseEntity<>("Delete breed successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Breed not found", HttpStatus.BAD_REQUEST);
    }

    /**
     * Service to view detail information of 1 breed
     * @param breedId Breed id to be queried
     * @return Breed information or response message
     */
    @Override
    public ResponseEntity<?> viewBreedDetailById(Long breedId) {
        Optional<Breed> breedOpt = breedRepository.findById(breedId);
        //If breed is available by id
        if (breedOpt.isPresent()) {
            Breed breed = breedOpt.get();
            DetailBreedDTO detailBreedDTO = new DetailBreedDTO(breed);
            Optional<Specie> specieOpt = specieRepository.findById(detailBreedDTO.getSpecieId());
            if (specieOpt.isPresent()) {
                Specie specie = specieOpt.get();
                detailBreedDTO.setSpecieName(specie.getSpecieName());
                return new ResponseEntity<>(detailBreedDTO, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Breed not found", HttpStatus.BAD_REQUEST);
    }

    /**
     * Service to view breed detail information of 1 specie
     * @param specieId Specie id to be queried
     * @return Breed information or response message
     */
    @Override
    public ResponseEntity<?> viewBreedDetailBySpecie(Long specieId) {
        Optional<List<Breed>> breedOpt = breedRepository.findBySpecieId(specieId);
        if (breedOpt.isPresent()) {
            return new ResponseEntity<>(breedOpt.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Breed not found", HttpStatus.BAD_REQUEST);
    }

    /**
     * Service to view breed detail information of 1 specie
     * @param userId User id to be queried
     * @return Breed information or response message
     */
    @Override
    public ResponseEntity<?> viewBreedDetailByUser(Long userId) {
        Optional<List<Breed>> breedOpt = breedRepository.findByUserId(userId);
        if (breedOpt.isPresent()) {
            return new ResponseEntity<>(breedOpt.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Breed not found", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> loadBreedImage(Long breedId) {
        Optional<Breed> breedOpt = breedRepository.findById(breedId);
        //If breed is available by id
        if (breedOpt.isPresent()) {
            Breed breed = breedOpt.get();
            Resource resource = fileStorageServiceImpl.loadFileAsResource(breed.getImageSrc());
            try {
                String x64 = fileStorageServiceImpl.resourceToX64(resource);
                return new ResponseEntity<>(x64, HttpStatus.OK);
            } catch (CustomFileNotFoundException ce) {
                System.out.println("Could not get file");
            }
        }
        return new ResponseEntity<>("Breed not found", HttpStatus.BAD_REQUEST);
    }

}
