/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 02/03/2023   1.0         DuongVV     First Deploy<br>
 * 28/03/2023   2.0         ChucNV      Modify code for edit/get<br>
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
import com.example.eims.service.interfaces.ISpecieService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SpecieServiceImpl implements ISpecieService {
    public class servicePayload {
        private Long specieId;
        private String specieName;
        private int incubationPeriod;
        private int phaseNumber;
        private int phasePeriod;
    }

    @Autowired
    private final SpecieRepository specieRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final IncubationPhaseRepository incubationPhaseRepository;
    @PersistenceContext
    private final EntityManager em;

    public SpecieServiceImpl(SpecieRepository specieRepository, UserRepository userRepository, IncubationPhaseRepository incubationPhaseRepository, EntityManager em) {
        this.specieRepository = specieRepository;
        this.userRepository = userRepository;
        this.incubationPhaseRepository = incubationPhaseRepository;
        this.em = em;
    }

    /**
     * Create a new specie after checking the phone number (session) is valid
     *
     * @param newSpecieDTO contains user's id, specie's name and incubation period
     * @return
     */
    @Override
    public ResponseEntity<String> newSpecie(NewSpecieDTO newSpecieDTO) {
        System.out.println(newSpecieDTO);
        Optional<User> userOpt = userRepository.findById(newSpecieDTO.getUserId());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Specie specie = new Specie();
            try {
                specieRepository.createNewSpecie(
                        user.getUserId(),
                        newSpecieDTO.getSpecieName(),
                        newSpecieDTO.getIncubationPeriod(),
                        newSpecieDTO.getEmbryolessDate(),
                        newSpecieDTO.getDiedEmbryoDate(),
                        newSpecieDTO.getHatchingDate(),
                        newSpecieDTO.getBalutDate());
            } catch (IllegalArgumentException iae) {
                return null;
            }
            return new ResponseEntity<>("Specie added successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Account not found with Id number " + newSpecieDTO.getUserId(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Get list species of a user
     *
     * @param userId the id of current logged-in user
     * @return
     */
    @Override
    public ResponseEntity<List<DetailSpecieDTO>> listSpecie(Long userId) {
        System.out.println("Requesting user id: " + userId);
        Optional<List<Specie>> speciesOpt = specieRepository.findByUserId(userId);
        if (speciesOpt.isPresent()) {
            List<Specie> specieList = speciesOpt.get();
            List<IncubationPhase> incubationPhases = incubationPhaseRepository.findAll();
            List<DetailSpecieDTO> detailSpecieDTOList = new ArrayList<>();
            for (Specie s : specieList) {
                DetailSpecieDTO detailSpecieDTO = new DetailSpecieDTO();
                detailSpecieDTO.setSpecieId(s.getSpecieId());
                detailSpecieDTO.setSpecieName(s.getSpecieName());
                detailSpecieDTO.setIncubationPeriod(s.getIncubationPeriod());
                detailSpecieDTO.setStatus(s.isStatus());
                detailSpecieDTOList.add(detailSpecieDTO);
            }
            return new ResponseEntity<>(detailSpecieDTOList, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    /**
     * Edit specie's information : Getting the original values
     *
     * @param specieId the id of the specie
     * @return
     */
    @Override
    public ResponseEntity<?> getSpecie(Long specieId) {
        Optional<Specie> specieOpt = specieRepository.findById(specieId);
        EditSpecieDTO editSpecieDTO;
        if (specieOpt.isPresent()) {
            Specie specie = specieOpt.get();
            editSpecieDTO = new EditSpecieDTO();
            editSpecieDTO.setSpecieId(specie.getSpecieId());
            editSpecieDTO.setSpecieName(specie.getSpecieName());
            editSpecieDTO.setIncubationPeriod(specie.getIncubationPeriod());
            Optional<List<IncubationPhase>> incubationPhasesOpt = incubationPhaseRepository.findIncubationPhasesBySpecieId(specieId);
            if (incubationPhasesOpt.isPresent()) {
                List<IncubationPhase> incubationPhases = incubationPhasesOpt.get();
                for (IncubationPhase phase : incubationPhases) {
                    switch (phase.getPhaseNumber()) {
                        case 2 -> editSpecieDTO.setEmbryolessDate(phase.getPhasePeriod());
                        case 3 -> editSpecieDTO.setDiedEmbryoDate(phase.getPhasePeriod());
                        case 4 -> editSpecieDTO.setBalutDate(phase.getPhasePeriod());
                        case 5 -> editSpecieDTO.setHatchingDate(phase.getPhasePeriod());
                    }
                }
            }
            return new ResponseEntity<>(editSpecieDTO, HttpStatus.OK);
        } else return new ResponseEntity<>("Không tìm thấy loài", HttpStatus.BAD_REQUEST);
    }

    /**
     * Edit specie's information : Saving the data
     *
     * @param editSpecieDTO contains user's id, specie's name and incubation period
     * @return
     */
    @Override
    public ResponseEntity<?> saveSpecie(EditSpecieDTO editSpecieDTO) {
        System.out.println("Requesting specie id: " + editSpecieDTO.getSpecieId());
        Optional<Specie> specieOpt = specieRepository.findById(editSpecieDTO.getSpecieId());
        if (specieOpt.isPresent()) {
            specieRepository.updateSpecie(
                    editSpecieDTO.getSpecieId(),
                    editSpecieDTO.getSpecieName(),
                    editSpecieDTO.getIncubationPeriod(),
                    editSpecieDTO.getEmbryolessDate(),
                    editSpecieDTO.getDiedEmbryoDate(),
                    editSpecieDTO.getHatchingDate(),
                    editSpecieDTO.getBalutDate());
            return new ResponseEntity<>("Saved specie successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    /**
     * Delete one user's specie
     *
     * @param specieId the id of the specie
     * @return
     */
    @Override
    public ResponseEntity<String> deleteSpecie(Long specieId) {
        if (specieRepository.findById(specieId).isPresent()) {
            specieRepository.deactivateById(specieId);
            return new ResponseEntity<>("Specie delete successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
