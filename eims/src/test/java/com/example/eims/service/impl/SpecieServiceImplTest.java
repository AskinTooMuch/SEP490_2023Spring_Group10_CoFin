/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 10/03/2023   1.0         DuongVV     First Deploy<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.specie.DetailSpecieDTO;
import com.example.eims.dto.specie.EditSpecieDTO;
import com.example.eims.dto.specie.NewSpecieDTO;
import com.example.eims.entity.IncubationPhase;
import com.example.eims.entity.Specie;
import com.example.eims.entity.User;
import com.example.eims.repository.IncubationPhaseRepository;
import com.example.eims.repository.SpecieRepository;
import com.example.eims.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class SpecieServiceImplTest {
    @Mock
    SpecieRepository specieRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    IncubationPhaseRepository incubationPhaseRepository;
    @Mock
    EntityManager em;
    @InjectMocks
    SpecieServiceImpl specieService;

    @Test
    void newSpecie() {
        // Set up
        User user = new User();
        user.setUserId(1L);
        NewSpecieDTO dto = new NewSpecieDTO();
        dto.setUserId(1L);
        dto.setSpecieName("name");
        dto.setIncubationPeriod(10);
        dto.setEmbryolessDate(3);
        dto.setDiedEmbryoDate(5);
        dto.setHatchingDate(7);
        // Define behaviour of repository
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Run service method
        ResponseEntity<?> responseEntity = specieService.newSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Specie added successfully",responseEntity.getBody());
    }

    @Test
    void listSpecie() {
        // Set up
        User user = new User();
        user.setUserId(1L);

        Specie specie1 = new Specie();
        specie1.setSpecieId(1L);
        Specie specie2 = new Specie();
        specie2.setSpecieId(2L);

        List<Specie> specieList = new ArrayList<>();
        specieList.add(specie1);
        specieList.add(specie2);
/*

        IncubationPhase phase2 = new IncubationPhase();
        phase2.setSpecieId(1L);
        phase2.setPhaseNumber(2);
        IncubationPhase phase3 = new IncubationPhase();
        phase3.setSpecieId(1L);
        phase3.setPhaseNumber(3);
        IncubationPhase phase5 = new IncubationPhase();
        phase5.setSpecieId(2L);
        phase5.setPhaseNumber(5);
*/

        List<IncubationPhase> phaseList = new ArrayList<>();
/*        phaseList.add(phase2);
        phaseList.add(phase3);
        phaseList.add(phase5);*/

        List<DetailSpecieDTO> detailSpecieDTOList = new ArrayList<>();

        // Define behaviour of repository
        when(specieRepository.findByUserId(1L)).thenReturn(Optional.of(specieList));
        when(incubationPhaseRepository.findAll()).thenReturn(phaseList);

        // Run service method
        ResponseEntity<?> responseEntity = specieService.listSpecie(1L);
        List<DetailSpecieDTO> resultList = (List<DetailSpecieDTO>) responseEntity.getBody();
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(2,resultList.size());
    }

    @Test
    void getSpecie() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        // Define behaviour of repository
        when(specieRepository.findById(1L)).thenReturn(Optional.of(specie));
        // Run service method
        ResponseEntity<?> responseEntity = specieService.getSpecie(1L);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(specie,responseEntity.getBody());
    }

    @Test
    void saveSpecie() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        EditSpecieDTO dto = new EditSpecieDTO();
        dto.setSpecieId(1L);
        // Define behaviour of repository
        when(specieRepository.findById(1L)).thenReturn(Optional.of(specie));
        // Run service method
        ResponseEntity<?> responseEntity = specieService.saveSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Saved specie successfully",responseEntity.getBody());
    }

    @Test
    void deleteSpecie() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        // Define behaviour of repository
        when(specieRepository.findById(1L)).thenReturn(Optional.of(specie));
        // Run service method
        ResponseEntity<?> responseEntity = specieService.deleteSpecie(1L);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Specie delete successfully",responseEntity.getBody());
    }
}