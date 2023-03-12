/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 09/03/2023   1.0         DuongVV     First Deploy<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.breed.EditBreedDTO;
import com.example.eims.dto.breed.NewBreedDTO;
import com.example.eims.entity.Breed;
import com.example.eims.entity.Specie;
import com.example.eims.repository.BreedRepository;
import com.example.eims.repository.SpecieRepository;
import jakarta.servlet.http.HttpServlet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BreedServiceImplTest {
    @Mock
    SpecieRepository specieRepository;
    @Mock
    BreedRepository breedRepository;
    //@Mock
    //FileStorageService fileStorageService;
    @InjectMocks
    BreedServiceImpl breedService;

    @Test
    void createNewBreed() {
        // Set up
        NewBreedDTO dto = new NewBreedDTO();
        dto.setSpecieId(1L);
        Breed breed = new Breed();
        breed.setBreedId(1L);
        Specie specie = new Specie();
        specie.setUserId(1L);
        specie.setSpecieId(1L);
        // Define behaviour of repository
        when(specieRepository.findById(dto.getSpecieId())).thenReturn(Optional.of(specie));

        // Run service method
        ResponseEntity<?> responseEntity = breedService.createNewBreed(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Breed added successfully", responseEntity.getBody());
    }

    @Test
    void updateBreed() {
        // Set up
        EditBreedDTO dto = new EditBreedDTO();
        dto.setSpecieId(1L);
        Breed breed = new Breed();
        // Define behaviour of repository
        when(breedRepository.findById(dto.getBreedId())).thenReturn(Optional.of(breed));

        // Run service method
        ResponseEntity<?> responseEntity = breedService.updateBreed(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Breed saved successfully", responseEntity.getBody());
    }

    @Test
    void deleteBreed() {
        // Set up
        Breed breed = new Breed();
        breed.setBreedId(1L);
        // Define behaviour of repository
        when(breedRepository.findById(1L)).thenReturn(Optional.of(breed));

        // Run service method
        ResponseEntity<?> responseEntity = breedService.deleteBreed(1L);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Delete breed successfully", responseEntity.getBody());
    }

/*    @Test
    void viewBreedDetailById() {
        // Set up
        Breed breed = new Breed();
        breed.setBreedId(1L);
        breed.setImageSrc("source");

        Resource resource = mock(Resource.class);
        HttpServlet request = mock(HttpServlet.class);
        // Define behaviour of repository
        when(breedRepository.findById(1L)).thenReturn(Optional.of(breed));
        when(fileStorageService.loadFileAsResource(breed.getImageSrc())).thenReturn(resource);


        // Run service method
        ResponseEntity<?> responseEntity = breedService.viewBreedDetailById(1L, request);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(breed, responseEntity.getBody());
    }*/

    @Test
    void viewBreedDetailBySpecie() {
        // Set up
        Breed breed1 = new Breed();
        Breed breed2 = new Breed();
        List<Breed> breedList = new ArrayList<>();
        breedList.add(breed1);
        breedList.add(breed2);
        // Define behaviour of repository
        when(breedRepository.findBySpecieId(1L)).thenReturn(Optional.of(breedList));

        // Run service method
        ResponseEntity<?> responseEntity = breedService.viewBreedDetailBySpecie(1L);
        List<Breed> listResult = (List<Breed>) responseEntity.getBody();
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(2, listResult.size());
    }

    @Test
    void viewBreedDetailByUser() {
        // Set up
        Breed breed1 = new Breed();
        Breed breed2 = new Breed();
        List<Breed> breedList = new ArrayList<>();
        breedList.add(breed1);
        breedList.add(breed2);
        // Define behaviour of repository
        when(breedRepository.findByUserId(1L)).thenReturn(Optional.of(breedList));

        // Run service method
        ResponseEntity<?> responseEntity = breedService.viewBreedDetailByUser(1L);
        List<Breed> listResult = (List<Breed>) responseEntity.getBody();
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(2, listResult.size());
    }
}