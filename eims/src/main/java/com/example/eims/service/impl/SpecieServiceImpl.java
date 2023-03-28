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
import com.example.eims.utils.StringDealer;
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
    private final StringDealer stringDealer = new StringDealer();

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
        Optional<User> userOpt = userRepository.findById(newSpecieDTO.getUserId());
        //Get attributes
        String specieName = stringDealer.trimMax(newSpecieDTO.getSpecieName());
        int incubationPeriod = newSpecieDTO.getIncubationPeriod();
        int embryolessDate = newSpecieDTO.getEmbryolessDate();
        int diedEmbryoDate = newSpecieDTO.getDiedEmbryoDate();
        int balutDate = newSpecieDTO.getBalutDate();
        int hatchingDate = newSpecieDTO.getHatchingDate();
        //Check and return false conditions
        if (specieName.equals("")) { return new ResponseEntity<>("Tên loài không được để trống", HttpStatus.BAD_REQUEST); }
        if (specieName.length() > 32) { return new ResponseEntity<>("Tên loài không được dài hơn 32 ký tự", HttpStatus.BAD_REQUEST); }
        if ((incubationPeriod < 0) || (incubationPeriod > 1000)) { return new ResponseEntity<>("Tổng thời gian ấp không được nhỏ hơn 0 và lớn hơn 1000 ngày", HttpStatus.BAD_REQUEST); }
        if ((embryolessDate < 0) || (embryolessDate > incubationPeriod)) { return new ResponseEntity<>("Mốc xác định trứng trắng không được nhỏ hơn 0 và lớn hơn tổng thời gian ấp", HttpStatus.BAD_REQUEST); }
        if ((diedEmbryoDate < embryolessDate) || (diedEmbryoDate > incubationPeriod)) { return new ResponseEntity<>("Mốc xác định trứng loãng không được nhỏ hơn mốc xác định trứng trắng và lớn hơn tổng thời gian ấp", HttpStatus.BAD_REQUEST); }
        if ((balutDate < diedEmbryoDate) || (balutDate > incubationPeriod)) { return new ResponseEntity<>("Mốc xác định trứng lộn không được nhỏ hơn mốc xác định trứng loãng và lớn hơn tổng thời gian ấp", HttpStatus.BAD_REQUEST); }
        if ((hatchingDate < balutDate) || (hatchingDate > incubationPeriod)) { return new ResponseEntity<>("Mốc chuyển trứng sang máy nở không được nhỏ hơn mốc xác định trứng lộn và lớn hơn tổng thời gian ấp", HttpStatus.BAD_REQUEST); }
        //Done; start adding the specie
        User user = null;
        if (userOpt.isPresent()) { user = userOpt.get(); }
        if ((user == null) || (user.getStatus() != 2)) {
            return new ResponseEntity<>("Không tìm thấy tài khoản hoặc tài khoản đã bị vô hiệu hóa", HttpStatus.BAD_REQUEST);
        }
        try {
            specieRepository.createNewSpecie(
                    user.getUserId(),
                    specieName,
                    incubationPeriod,
                    embryolessDate,
                    diedEmbryoDate,
                    balutDate,
                    hatchingDate);
            return new ResponseEntity<>("Tạo loài mới thành công", HttpStatus.OK);
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>("Có lỗi xảy ra, vui lòng thử lại", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get list species of a user
     *
     * @param userId the id of current logged-in user
     * @return
     */
    @Override
    public ResponseEntity<?> listSpecie(Long userId) {
        //Prevent getting null species
        User user = null;
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) { user = userOpt.get(); }
        if ((user == null) || (user.getStatus() != 2)) {
            return new ResponseEntity<>("Không tìm thấy tài khoản hoặc tài khoản đã bị vô hiệu hóa", HttpStatus.BAD_REQUEST);
        }
        Optional<List<Specie>> speciesOpt = specieRepository.findByUserId(userId);
        //Get species
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
        return new ResponseEntity<>("Tài khoản chưa thêm loài", HttpStatus.BAD_REQUEST);
    }

    /**
     * Edit specie's information : Getting the original values
     *
     * @param specieId the id of the specie
     * @return
     */
    @Override
    public ResponseEntity<?> getSpecie(Long specieId) {
        //Prevent getting null specie
        Optional<Specie> specieOpt = specieRepository.findById(specieId);
        Specie specie = null;
        EditSpecieDTO editSpecieDTO;
        if (specieOpt.isPresent()) { specie = specieOpt.get(); }
        if ((specie == null) || (!specie.isStatus())) {
            return new ResponseEntity<>("Không tìm thấy loài hoặc loài đã bị vô hiệu hóa", HttpStatus.BAD_REQUEST);
        }
        //Get specie
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
    }

    /**
     * Edit specie's information : Saving the data
     *
     * @param editSpecieDTO contains user's id, specie's name and incubation period
     * @return
     */
    @Override
    public ResponseEntity<?> saveSpecie(EditSpecieDTO editSpecieDTO) {
        //Get attributes
        String specieName = stringDealer.trimMax(editSpecieDTO.getSpecieName());
        int incubationPeriod = editSpecieDTO.getIncubationPeriod();
        int embryolessDate = editSpecieDTO.getEmbryolessDate();
        int diedEmbryoDate = editSpecieDTO.getDiedEmbryoDate();
        int balutDate = editSpecieDTO.getBalutDate();
        int hatchingDate = editSpecieDTO.getHatchingDate();
        //Check and return false conditions
        if (specieName.equals("")) { return new ResponseEntity<>("Tên loài không được để trống", HttpStatus.BAD_REQUEST); }
        if (specieName.length() > 32) { return new ResponseEntity<>("Tên loài không được dài hơn 32 ký tự", HttpStatus.BAD_REQUEST); }
        if ((incubationPeriod < 0) || (incubationPeriod > 1000)) { return new ResponseEntity<>("Tổng thời gian ấp không được nhỏ hơn 0 và lớn hơn 1000 ngày", HttpStatus.BAD_REQUEST); }
        if ((embryolessDate < 0) || (embryolessDate > incubationPeriod)) { return new ResponseEntity<>("Mốc xác định trứng trắng không được nhỏ hơn 0 và lớn hơn tổng thời gian ấp", HttpStatus.BAD_REQUEST); }
        if ((diedEmbryoDate < embryolessDate) || (diedEmbryoDate > incubationPeriod)) { return new ResponseEntity<>("Mốc xác định trứng loãng không được nhỏ hơn mốc xác định trứng trắng và lớn hơn tổng thời gian ấp", HttpStatus.BAD_REQUEST); }
        if ((balutDate < diedEmbryoDate) || (balutDate > incubationPeriod)) { return new ResponseEntity<>("Mốc xác định trứng lộn không được nhỏ hơn mốc xác định trứng loãng và lớn hơn tổng thời gian ấp", HttpStatus.BAD_REQUEST); }
        if ((hatchingDate < balutDate) || (hatchingDate > incubationPeriod)) { return new ResponseEntity<>("Mốc chuyển trứng sang máy nở không được nhỏ hơn mốc xác định trứng lộn và lớn hơn tổng thời gian ấp", HttpStatus.BAD_REQUEST); }
        //Prevent getting and saving null specie
        Optional<Specie> specieOpt = specieRepository.findById(editSpecieDTO.getSpecieId());
        Specie specie = null;
        if (specieOpt.isPresent()) { specie = specieOpt.get(); }
        if ((specie == null) || (!specie.isStatus())) {
            return new ResponseEntity<>("Không tìm thấy loài hoặc loài đã bị vô hiệu hóa", HttpStatus.BAD_REQUEST);
        }
        specieRepository.updateSpecie(
                editSpecieDTO.getSpecieId(),
                specieName,
                incubationPeriod,
                embryolessDate,
                diedEmbryoDate,
                balutDate,
                hatchingDate);
        return new ResponseEntity<>("Lưu thông tin loài thành công", HttpStatus.OK);
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
            return new ResponseEntity<>("Xóa loài thành công", HttpStatus.OK);
        }
        return new ResponseEntity<>("Không tìm thấy loài", HttpStatus.BAD_REQUEST);
    }
}
