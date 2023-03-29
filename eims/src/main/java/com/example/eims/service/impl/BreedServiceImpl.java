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
 * 29/03/2023   3.0         ChucNV      Refine code<br>
 */
package com.example.eims.service.impl;

import com.example.eims.dto.breed.BreedListDTO;
import com.example.eims.dto.breed.DetailBreedDTO;
import com.example.eims.dto.breed.NewBreedDTO;
import com.example.eims.dto.breed.EditBreedDTO;
import com.example.eims.dto.user.UserDetailDTO;
import com.example.eims.entity.Breed;
import com.example.eims.entity.Specie;
import com.example.eims.repository.BreedRepository;
import com.example.eims.repository.SpecieRepository;
import com.example.eims.repository.UserRepository;
import com.example.eims.service.CustomFileNotFoundException;
import com.example.eims.service.interfaces.IBreedService;
import com.example.eims.utils.StringDealer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

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
    @PersistenceContext
    private final EntityManager em;
    private final StringDealer stringDealer = new StringDealer();

    public BreedServiceImpl(SpecieRepository specieRepository, BreedRepository breedRepository, UserRepository userRepository, EntityManager em) {
        this.specieRepository = specieRepository;
        this.breedRepository = breedRepository;
        this.userRepository = userRepository;
        this.em = em;
    }

    /**
     * Service to create a new Breed.
     * The newly added breed will have the status of 1 (true)
     * @param newBreedDTO Payload
     * @return New breed created or response message
     */
    @Override
    public ResponseEntity<?> createNewBreed(@ModelAttribute NewBreedDTO newBreedDTO) {
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
                return new ResponseEntity<>("Không tìm thấy loài.", HttpStatus.BAD_REQUEST);
            }
            if ((userRepository.findById(specie.getUserId()).isPresent()) && (userRepository.findById(specie.getUserId()).get().getStatus()==2)) {
                breed.setUserId(specie.getUserId());
            } else {
                return new ResponseEntity<>("Không tìm thấy người dùng hoặc tài khoản không được kích hoạt.", HttpStatus.BAD_REQUEST);
            }
            // Check requirements
            String breedName = stringDealer.trimMax(newBreedDTO.getBreedName());
            if (breedName.equals("")) {  return new ResponseEntity<>("Tên loại không được để trống", HttpStatus.BAD_REQUEST); }
            if (breedName.length() > 32) {  return new ResponseEntity<>("Tên loại không được dài hơn 32 ký tự", HttpStatus.BAD_REQUEST); }
            String commonDisease = stringDealer.trimMax(newBreedDTO.getCommonDisease());
            if (commonDisease.length() > 1000) {  return new ResponseEntity<>("Bệnh thường gặp không được dài hơn 1000 ký tự", HttpStatus.BAD_REQUEST); }
            // Number fields: DTO has automatically caught any error/exceptions
            if(newBreedDTO.getAverageWeightMale() < 0.01 || newBreedDTO.getAverageWeightFemale() < 0.01){
                return new ResponseEntity<>("Cân nặng trung bình phải lớn hơn 0.01", HttpStatus.BAD_REQUEST);
            }
            if(newBreedDTO.getAverageWeightMale() > 1000 || newBreedDTO.getAverageWeightFemale() > 1000){
                return new ResponseEntity<>("Cân nặng trung bình không được lớn hơn 1000kg", HttpStatus.BAD_REQUEST);
            }
            breed.setSpecieId(specie.getSpecieId());
            breed.setBreedName(breedName);
            breed.setAverageWeightMale(newBreedDTO.getAverageWeightMale());
            breed.setAverageWeightFemale(newBreedDTO.getAverageWeightFemale());
            breed.setCommonDisease(commonDisease);

            //growth time
            if (newBreedDTO.getGrowthTime() <= 0){
                return new ResponseEntity<>("Thời gian lớn lên phải lớn hơn 0", HttpStatus.BAD_REQUEST);
            }
            breed.setGrowthTime(newBreedDTO.getGrowthTime());

            // Saved files name and new breed added as true status for default
            breed.setImageSrc(filename);
            breed.setStatus(true);
            try {
                breedRepository.save(breed);
            } catch (IllegalArgumentException iae) {
                return new ResponseEntity<>("Thêm loại thất bại", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("Thêm loại thành công", HttpStatus.OK);
        }
        return new ResponseEntity<>("Không tìm thấy loài", HttpStatus.BAD_REQUEST);
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
        if (specieOpt.isEmpty()) { return new ResponseEntity<>("Không tìm thấy loài" , HttpStatus.BAD_REQUEST); }
        Optional<Breed> breedOpt = breedRepository.findById(editBreedDTO.getBreedId());
        if (breedOpt.isEmpty()) { return new ResponseEntity<>("Không tìm thấy loại" , HttpStatus.BAD_REQUEST); }
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
        if (name.equals("")) {  return new ResponseEntity<>("Tên loại không được để trống", HttpStatus.BAD_REQUEST); }
        if (name.length() > 32) {  return new ResponseEntity<>("Tên loại không được dài hơn 32 ký tự", HttpStatus.BAD_REQUEST); }

        // Number fields: DTO has automatically caught any error/exceptions
        if(editBreedDTO.getAverageWeightMale() <= 0.01 || editBreedDTO.getAverageWeightFemale() <= 0.01){ return new ResponseEntity<>("Cân nặng trung bình phải lớn hơn hoặc bằng 0.01 kg", HttpStatus.BAD_REQUEST); }
        if(editBreedDTO.getAverageWeightMale() > 1000 || editBreedDTO.getAverageWeightFemale() > 1000){ return new ResponseEntity<>("Cân nặng trung bình phải nhỏ hơn hoặc bằng 1000 kg", HttpStatus.BAD_REQUEST); }
        breed.setAverageWeightMale(editBreedDTO.getAverageWeightMale());
        breed.setAverageWeightFemale(editBreedDTO.getAverageWeightFemale());
        //growth time
        if (editBreedDTO.getGrowthTime() <= 0){ return new ResponseEntity<>("Thời gian lớn lên phải lớn hơn 0", HttpStatus.BAD_REQUEST); }
        breed.setGrowthTime(editBreedDTO.getGrowthTime());
        breed.setCommonDisease(stringDealer.trimMax(editBreedDTO.getCommonDisease()));
        //The user not allowed to deactivate a breed in this form, only through delete tab
        breed.setStatus(true);
        try {
            breedRepository.save(breed);
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>("Thêm loại thất bại", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Thêm loại thành công", HttpStatus.OK);
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
            return new ResponseEntity<>("Xóa loại thành công", HttpStatus.OK);
        }
        return new ResponseEntity<>("Không tìm thấy loại", HttpStatus.BAD_REQUEST);
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
        return new ResponseEntity<>("Không tìm thấy loại", HttpStatus.BAD_REQUEST);
    }

    /**
     * Service to view breed detail information of 1 specie
     * @param specieId Specie id to be queried
     * @return Breed information or response message
     */
    @Override
    public ResponseEntity<?> viewListBreedBySpecie(Long specieId) {
        Query q = em.createNamedQuery("GetListBreedBySpecie");
        q.setParameter(1, specieId);
        List<BreedListDTO> breedListDTOS = (List<BreedListDTO>) q.getResultList();
        if (!breedListDTOS.isEmpty()) {
            return new ResponseEntity<>(breedListDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>("Không tìm thấy loại thuộc loài này", HttpStatus.BAD_REQUEST);
    }

    /**
     * Service to view breed detail information of 1 specie
     * @param userId User id to be queried
     * @return Breed information or response message
     */
    @Override
    public ResponseEntity<?> viewListBreedByUser(Long userId) {
        Query q = em.createNamedQuery("GetListBreedByUser");
        q.setParameter(1, userId);
        List<BreedListDTO> breedListDTOS = (List<BreedListDTO>) q.getResultList();
        if (!breedListDTOS.isEmpty()) {
            return new ResponseEntity<>(breedListDTOS, HttpStatus.OK);
        }
//        Optional<List<Breed>> breedOpt = breedRepository.findByUserId(userId);
//        if (breedOpt.isPresent()) {
//            return new ResponseEntity<>(breedOpt.get(), HttpStatus.OK);
//        }
        return new ResponseEntity<>("Không tìm thấy loại", HttpStatus.BAD_REQUEST);
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
                System.out.println("Tải file lỗi");
            }
        }
        return new ResponseEntity<>("Không tìm thấy loại", HttpStatus.BAD_REQUEST);
    }

}
