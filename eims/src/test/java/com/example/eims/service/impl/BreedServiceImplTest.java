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
import com.example.eims.entity.User;
import com.example.eims.repository.BreedRepository;
import com.example.eims.repository.SpecieRepository;
import com.example.eims.repository.UserRepository;
import jakarta.servlet.http.HttpServlet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.nio.file.Path;
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
    @Mock
    UserRepository userRepository;
    @Mock
    FileStorageServiceImpl fileStorageServiceImpl;
    @InjectMocks
    BreedServiceImpl breedService;

    @Test
    @DisplayName("createNewBreedUTCID01")
    void createNewBreedUTCID01() {
        // Set up
        User user = new User();
        user.setUserId(1L);
        Specie specie = new Specie();
        specie.setUserId(user.getUserId());
        specie.setSpecieId(123456789L);
        NewBreedDTO dto = new NewBreedDTO();
        dto.setSpecieId(specie.getSpecieId());
        dto.setBreedName("");
        dto.setAverageWeightFemale(12.3F);
        dto.setAverageWeightMale(12.3F);
        dto.setCommonDisease("");
        dto.setGrowthTime(15);
        dto.setImage(null);
        Breed breed = new Breed();
        // Define behaviour of repository
        when(specieRepository.findById(specie.getSpecieId())).thenReturn(Optional.empty());
       //when(userRepository.findById(specie.getUserId())).thenReturn(Optional.of(user));

        // Run service method
        ResponseEntity<?> responseEntity = breedService.createNewBreed(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Không tìm thấy loài", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewBreedUTCID02")
    void createNewBreedUTCID02() {
        // Set up
        User user = new User();
        user.setUserId(1L);
        Specie specie = new Specie();
        specie.setUserId(user.getUserId());
        specie.setSpecieId(1L);
        NewBreedDTO dto = new NewBreedDTO();
        dto.setSpecieId(specie.getSpecieId());
        dto.setBreedName("Gà ri");
        dto.setAverageWeightFemale(0F);
        dto.setAverageWeightMale(12.3F);
        dto.setCommonDisease("CRD_Hen gà");
        dto.setGrowthTime(15);
        dto.setImage(null);
        Breed breed = new Breed();
        // Define behaviour of repository
        when(specieRepository.findById(specie.getSpecieId())).thenReturn(Optional.of(specie));
        when(userRepository.findById(specie.getUserId())).thenReturn(Optional.of(user));

        // Run service method
        ResponseEntity<?> responseEntity = breedService.createNewBreed(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cân nặng trung bình phải lớn hơn 0", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewBreedUTCID03")
    void createNewBreedUTCID03() {
        // Set up
        User user = new User();
        user.setUserId(1L);
        Specie specie = new Specie();
        specie.setUserId(user.getUserId());
        specie.setSpecieId(1L);
        NewBreedDTO dto = new NewBreedDTO();
        dto.setSpecieId(specie.getSpecieId());
        dto.setBreedName("");
        dto.setAverageWeightFemale(11F);
        dto.setAverageWeightMale(11F);
        dto.setCommonDisease("");
        dto.setGrowthTime(15);
        dto.setImage(null);
        Breed breed = new Breed();
        // Define behaviour of repository
        when(specieRepository.findById(specie.getSpecieId())).thenReturn(Optional.of(specie));
        when(userRepository.findById(specie.getUserId())).thenReturn(Optional.of(user));

        // Run service method
        ResponseEntity<?> responseEntity = breedService.createNewBreed(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewBreedUTCID04")
    void createNewBreedUTCID04() {
        // Set up
        User user = new User();
        user.setUserId(1L);
        Specie specie = new Specie();
        specie.setUserId(user.getUserId());
        specie.setSpecieId(1L);
        NewBreedDTO dto = new NewBreedDTO();
        dto.setSpecieId(specie.getSpecieId());
        dto.setBreedName(null);
        dto.setAverageWeightFemale(11F);
        dto.setAverageWeightMale(11F);
        dto.setCommonDisease("");
        dto.setGrowthTime(15);
        dto.setImage(null);
        Breed breed = new Breed();
        // Define behaviour of repository
        when(specieRepository.findById(specie.getSpecieId())).thenReturn(Optional.of(specie));
        when(userRepository.findById(specie.getUserId())).thenReturn(Optional.of(user));

        // Run service method
        ResponseEntity<?> responseEntity = breedService.createNewBreed(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewBreedUTCID05")
    void createNewBreedUTCID05() {
        // Set up
        User user = new User();
        user.setUserId(1L);
        Specie specie = new Specie();
        specie.setUserId(user.getUserId());
        specie.setSpecieId(1L);
        NewBreedDTO dto = new NewBreedDTO();
        dto.setSpecieId(specie.getSpecieId());
        dto.setBreedName("Gà ri");
        dto.setAverageWeightFemale(12.3F);
        dto.setAverageWeightMale(12.3F);
        dto.setCommonDisease("");
        dto.setGrowthTime(0);
        dto.setImage(null);
        Breed breed = new Breed();
        // Define behaviour of repository
        when(specieRepository.findById(specie.getSpecieId())).thenReturn(Optional.of(specie));
        when(userRepository.findById(specie.getUserId())).thenReturn(Optional.of(user));

        // Run service method
        ResponseEntity<?> responseEntity = breedService.createNewBreed(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Thời gian lớn lên phải lớn hơn 0", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewBreedUTCID06")
    void createNewBreedUTCID06() {
        // Set up
        User user = new User();
        user.setUserId(1L);
        Specie specie = new Specie();
        specie.setUserId(user.getUserId());
        specie.setSpecieId(1L);
        NewBreedDTO dto = new NewBreedDTO();
        dto.setSpecieId(specie.getSpecieId());
        dto.setBreedName("Gà ri");
        dto.setAverageWeightFemale(12.3F);
        dto.setAverageWeightMale(12.3F);
        dto.setCommonDisease("");
        dto.setGrowthTime(15);
        dto.setImage(null);
        Breed breed = new Breed();
        // Define behaviour of repository
        when(specieRepository.findById(specie.getSpecieId())).thenReturn(Optional.of(specie));
        when(userRepository.findById(specie.getUserId())).thenReturn(Optional.of(user));

        // Run service method
        ResponseEntity<?> responseEntity = breedService.createNewBreed(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Thêm loại thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewBreedUTCID07")
    void createNewBreedUTCID07() {
        // Set up
        User user = new User();
        user.setUserId(1L);
        Specie specie = new Specie();
        specie.setUserId(user.getUserId());
        specie.setSpecieId(1L);
        NewBreedDTO dto = new NewBreedDTO();
        dto.setSpecieId(specie.getSpecieId());
        dto.setBreedName("Gà ri");
        dto.setAverageWeightFemale(11F);
        dto.setAverageWeightMale(11F);
        dto.setCommonDisease("CRD_Hen gà");
        dto.setGrowthTime(15);
        dto.setImage(null);
        Breed breed = new Breed();
        // Define behaviour of repository
        when(specieRepository.findById(specie.getSpecieId())).thenReturn(Optional.of(specie));
        when(userRepository.findById(specie.getUserId())).thenReturn(Optional.of(user));

        // Run service method
        ResponseEntity<?> responseEntity = breedService.createNewBreed(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Thêm loại thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewBreedUTCID08")
    void createNewBreedUTCID08() {
        // Set up
        User user = new User();
        user.setUserId(1L);
        Specie specie = new Specie();
        specie.setUserId(user.getUserId());
        specie.setSpecieId(1L);
        NewBreedDTO dto = new NewBreedDTO();
        dto.setSpecieId(specie.getSpecieId());
        dto.setBreedName("Gà ri");
        dto.setAverageWeightFemale(-11F);
        dto.setAverageWeightMale(-11F);
        dto.setCommonDisease("CRD_Hen gà");
        dto.setGrowthTime(15);
        dto.setImage(null);
        Breed breed = new Breed();
        // Define behaviour of repository
        when(specieRepository.findById(specie.getSpecieId())).thenReturn(Optional.of(specie));
        when(userRepository.findById(specie.getUserId())).thenReturn(Optional.of(user));

        // Run service method
        ResponseEntity<?> responseEntity = breedService.createNewBreed(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cân nặng trung bình phải lớn hơn 0", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateBreedUTCID01")
    void updateBreedUTCID01() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(123456789L);
        EditBreedDTO dto = new EditBreedDTO();
        dto.setSpecieId(specie.getSpecieId());
        dto.setBreedName("Gà tre");
        dto.setAverageWeightFemale(11F);
        dto.setAverageWeightMale(11F);
        dto.setCommonDisease("");
        dto.setGrowthTime(15);
        dto.setImage(null);
        dto.setStatus(true);
        Breed breed = new Breed();
        // Define behaviour of repository
        when(specieRepository.findById(123456789L)).thenReturn(Optional.empty());
        //when(breedRepository.findById(dto.getBreedId())).thenReturn(Optional.of(breed));

        // Run service method
        ResponseEntity<?> responseEntity = breedService.updateBreed(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Không tìm thấy loài", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateBreedUTCID02")
    void updateBreedUTCID02() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        Breed breed = new Breed();
        breed.setBreedId(1L);
        EditBreedDTO dto = new EditBreedDTO();
        dto.setSpecieId(specie.getSpecieId());
        dto.setBreedId(1L);
        dto.setBreedName("Gà tre");
        dto.setAverageWeightFemale(0F);
        dto.setAverageWeightMale(0F);
        dto.setCommonDisease("CRD_Hen gà");
        dto.setGrowthTime(15);
        dto.setImage(null);
        dto.setStatus(true);
        // Define behaviour of repository
        when(specieRepository.findById(1L)).thenReturn(Optional.of(specie));
        when(breedRepository.findById(dto.getBreedId())).thenReturn(Optional.of(breed));

        // Run service method
        ResponseEntity<?> responseEntity = breedService.updateBreed(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cân nặng trung bình phải lớn hơn 0", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateBreedUTCID03")
    void updateBreedUTCID03() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        Breed breed = new Breed();
        breed.setBreedId(1L);
        EditBreedDTO dto = new EditBreedDTO();
        dto.setSpecieId(specie.getSpecieId());
        dto.setBreedId(1L);
        dto.setBreedName("Gà tre");
        dto.setAverageWeightFemale(12.3F);
        dto.setAverageWeightMale(12.3F);
        dto.setCommonDisease("");
        dto.setGrowthTime(0);
        dto.setImage(null);
        dto.setStatus(false);
        // Define behaviour of repository
        when(specieRepository.findById(1L)).thenReturn(Optional.of(specie));
        when(breedRepository.findById(dto.getBreedId())).thenReturn(Optional.of(breed));

        // Run service method
        ResponseEntity<?> responseEntity = breedService.updateBreed(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Thời gian lớn lên phải lớn hơn 0", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateBreedUTCID04")
    void updateBreedUTCID04() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        Breed breed = new Breed();
        breed.setBreedId(1L);
        EditBreedDTO dto = new EditBreedDTO();
        dto.setSpecieId(specie.getSpecieId());
        dto.setBreedId(1L);
        dto.setBreedName("Gà tre");
        dto.setAverageWeightFemale(12.3F);
        dto.setAverageWeightMale(12.3F);
        dto.setCommonDisease("");
        dto.setGrowthTime(-11);
        dto.setImage(null);
        dto.setStatus(false);
        // Define behaviour of repository
        when(specieRepository.findById(1L)).thenReturn(Optional.of(specie));
        when(breedRepository.findById(dto.getBreedId())).thenReturn(Optional.of(breed));

        // Run service method
        ResponseEntity<?> responseEntity = breedService.updateBreed(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Thời gian lớn lên phải lớn hơn 0", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateBreedUTCID05")
    void updateBreedUTCID05() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        Breed breed = new Breed();
        breed.setBreedId(1L);
        EditBreedDTO dto = new EditBreedDTO();
        dto.setSpecieId(specie.getSpecieId());
        dto.setBreedId(1L);
        dto.setBreedName("Gà tre");
        dto.setAverageWeightFemale(12.3F);
        dto.setAverageWeightMale(12.3F);
        dto.setCommonDisease("");
        dto.setGrowthTime(15);
        dto.setImage(null);
        dto.setStatus(true);
        // Define behaviour of repository
        when(specieRepository.findById(1L)).thenReturn(Optional.of(specie));
        when(breedRepository.findById(dto.getBreedId())).thenReturn(Optional.of(breed));

        // Run service method
        ResponseEntity<?> responseEntity = breedService.updateBreed(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Thêm loại thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateBreedUTCID06")
    void updateBreedUTCID06() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        Breed breed = new Breed();
        breed.setBreedId(1L);
        EditBreedDTO dto = new EditBreedDTO();
        dto.setSpecieId(specie.getSpecieId());
        dto.setBreedId(1L);
        dto.setBreedName("Gà tre");
        dto.setAverageWeightFemale(11F);
        dto.setAverageWeightMale(11F);
        dto.setCommonDisease("CRD_Hen gà");
        dto.setGrowthTime(15);
        dto.setImage(null);
        dto.setStatus(false);
        // Define behaviour of repository
        when(specieRepository.findById(1L)).thenReturn(Optional.of(specie));
        when(breedRepository.findById(dto.getBreedId())).thenReturn(Optional.of(breed));

        // Run service method
        ResponseEntity<?> responseEntity = breedService.updateBreed(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Thêm loại thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateBreedUTCID07")
    void updateBreedUTCID07() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        Breed breed = new Breed();
        breed.setBreedId(1L);
        EditBreedDTO dto = new EditBreedDTO();
        dto.setSpecieId(specie.getSpecieId());
        dto.setBreedId(1L);
        dto.setBreedName("Gà tre");
        dto.setAverageWeightFemale(-11F);
        dto.setAverageWeightMale(-11F);
        dto.setCommonDisease("CRD_Hen gà");
        dto.setGrowthTime(15);
        dto.setImage(null);
        dto.setStatus(false);
        // Define behaviour of repository
        when(specieRepository.findById(1L)).thenReturn(Optional.of(specie));
        when(breedRepository.findById(dto.getBreedId())).thenReturn(Optional.of(breed));

        // Run service method
        ResponseEntity<?> responseEntity = breedService.updateBreed(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cân nặng trung bình phải lớn hơn 0", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateBreedUTCID08")
    void updateBreedUTCID08() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        Breed breed = new Breed();
        breed.setBreedId(1L);
        EditBreedDTO dto = new EditBreedDTO();
        dto.setSpecieId(specie.getSpecieId());
        dto.setBreedId(1L);
        dto.setBreedName("");
        dto.setAverageWeightFemale(12.3F);
        dto.setAverageWeightMale(12.3F);
        dto.setCommonDisease("CRD_Hen gà");
        dto.setGrowthTime(15);
        dto.setImage(null);
        dto.setStatus(true);
        // Define behaviour of repository
        when(specieRepository.findById(1L)).thenReturn(Optional.of(specie));
        when(breedRepository.findById(dto.getBreedId())).thenReturn(Optional.of(breed));

        // Run service method
        ResponseEntity<?> responseEntity = breedService.updateBreed(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("deleteBreedUTCID01")
    void deleteBreedUTCID01() {
        // Set up
        Breed breed = new Breed();
        breed.setBreedId(0L);
        // Define behaviour of repository
        when(breedRepository.findById(0L)).thenReturn(Optional.empty());

        // Run service method
        ResponseEntity<?> responseEntity = breedService.deleteBreed(breed.getBreedId());
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Không tìm thấy loại", responseEntity.getBody());
    }

    @Test
    @DisplayName("deleteBreedUTCID02")
    void deleteBreedUTCID02() {
        // Set up
        Breed breed = new Breed();
        breed.setBreedId(1L);
        // Define behaviour of repository
        when(breedRepository.findById(1L)).thenReturn(Optional.of(breed));

        // Run service method
        ResponseEntity<?> responseEntity = breedService.deleteBreed(breed.getBreedId());
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Xóa loại thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("viewBreedDetailByIdUTCID01")
    void viewBreedDetailByIdUTCID01() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        Breed breed = new Breed();
        breed.setBreedId(1L);
        breed.setSpecieId(specie.getSpecieId());
        breed.setBreedName("Gà ri");
        breed.setAverageWeightFemale(12.3F);
        breed.setAverageWeightMale(12.3F);
        breed.setCommonDisease(null);
        breed.setGrowthTime(15);
        breed.setImageSrc(null);
        // Define behaviour of repository
        when(breedRepository.findById(breed.getBreedId())).thenReturn(Optional.of(breed));
        when(specieRepository.findById(breed.getSpecieId())).thenReturn(Optional.of(specie));
        // Run service method
        ResponseEntity<?> responseEntity = breedService.viewBreedDetailById(breed.getBreedId());
        System.out.println(responseEntity.toString());
        // Assert
        assertNotEquals(null, responseEntity.getBody());
    }

    @Test
    @DisplayName("viewBreedDetailByIdUTCID02")
    void viewBreedDetailByIdUTCID02() {
        // Set up
        Breed breed = new Breed();
        breed.setBreedId(0L);
        breed.setSpecieId(1L);
        breed.setBreedName("Gà ri");
        breed.setAverageWeightFemale(12.3F);
        breed.setAverageWeightMale(12.3F);
        breed.setCommonDisease(null);
        breed.setGrowthTime(15);
        breed.setImageSrc(null);
        // Define behaviour of repository
        when(breedRepository.findById(0L)).thenReturn(Optional.empty());

        // Run service method
        ResponseEntity<?> responseEntity = breedService.viewBreedDetailById(breed.getBreedId());
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Không tìm thấy loại", responseEntity.getBody());
    }

    @Test
    @DisplayName("viewBreedDetailBySpecieUTCID01")
    void viewBreedDetailBySpecieUTCID01() {
        // Set up
        Long specieId = 1L;
        Breed breed1 = new Breed();
        breed1.setBreedId(specieId);
        Breed breed2 = new Breed();
        breed2.setBreedId(specieId);
        List<Breed> breedList = new ArrayList<>();
        breedList.add(breed1);
        breedList.add(breed2);
        // Define behaviour of repository
        when(breedRepository.findBySpecieId(specieId)).thenReturn(Optional.of(breedList));

        // Run service method
        ResponseEntity<?> responseEntity = breedService.viewListBreedBySpecie(specieId);
        System.out.println(responseEntity.getBody());
        // Assert
        assertNotEquals(null, responseEntity.getBody());
    }

    @Test
    @DisplayName("viewBreedDetailBySpecieUTCID02")
    void viewBreedDetailBySpecieUTCID02() {
        // Set up
        Long specieId = 15L;
        Breed breed1 = new Breed();
        breed1.setBreedId(specieId);
        Breed breed2 = new Breed();
        breed2.setBreedId(specieId);
        List<Breed> breedList = new ArrayList<>();
        // Define behaviour of repository
        when(breedRepository.findBySpecieId(15L)).thenReturn(Optional.of(breedList));

        // Run service method
        ResponseEntity<?> responseEntity = breedService.viewListBreedBySpecie(specieId);
        List<Breed> result = (ArrayList<Breed>) responseEntity.getBody();
        System.out.println(responseEntity.getBody());
        // Assert
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("viewBreedDetailBySpecieUTCID03")
    void viewBreedDetailBySpecieUTCID03() {
        // Set up
        Long specieId = 1L;
        Breed breed1 = new Breed();
        breed1.setBreedId(specieId);
        Breed breed2 = new Breed();
        breed2.setBreedId(specieId);
        List<Breed> breedList = new ArrayList<>();
        breedList.add(breed1);
        breedList.add(breed2);
        // Define behaviour of repository
        when(breedRepository.findBySpecieId(specieId)).thenReturn(Optional.empty());

        // Run service method
        ResponseEntity<?> responseEntity = breedService.viewListBreedBySpecie(specieId);
        System.out.println(responseEntity.getBody());
        // Assert
        assertEquals("Không tìm thấy loại thuộc loài này", responseEntity.getBody());
    }

    @Test
    @DisplayName("viewListBreedByUserUTCID01")
    void viewListBreedByUserUTCID01() {
        // Set up
        Long userId = 1L;
        Breed breed1 = new Breed();
        Breed breed2 = new Breed();
        List<Breed> breedList = new ArrayList<>();
        breedList.add(breed1);
        breedList.add(breed2);
        // Define behaviour of repository
        when(breedRepository.findByUserId(1L)).thenReturn(Optional.of(breedList));

        // Run service method
        ResponseEntity<?> responseEntity = breedService.viewListBreedByUser(userId);
        System.out.println(responseEntity.getBody());
        // Assert
        assertNotEquals(null, responseEntity.getBody());
    }

    @Test
    @DisplayName("viewListBreedByUserUTCID02")
    void viewListBreedByUserUTCID02() {
        // Set up
        Long userId = 15L;
        // Define behaviour of repository
        when(breedRepository.findByUserId(15L)).thenReturn(Optional.empty());

        // Run service method
        ResponseEntity<?> responseEntity = breedService.viewListBreedByUser(userId);
        System.out.println(responseEntity.getBody());
        // Assert
        assertEquals("Không tìm thấy loại", responseEntity.getBody());
    }
    @Test
    @DisplayName("viewListBreedByUserUTCID03")
    void viewListBreedByUserUTCID03() {
        // Set up
        Long userId = 0L;
        // Define behaviour of repository
        when(breedRepository.findByUserId(0L)).thenReturn(Optional.empty());

        // Run service method
        ResponseEntity<?> responseEntity = breedService.viewListBreedByUser(userId);
        System.out.println(responseEntity.getBody());
        // Assert
        assertEquals("Không tìm thấy loại", responseEntity.getBody());
    }

//    @Test
//    @DisplayName("loadBreedImageUTC01")
//    void loadBreedImageUTC01() {
//        // Set up
//        Breed breed = new Breed();
//        breed.setBreedId(1L);
//        breed.setImageSrc("334459975_110172365306095_7125549293764613853_n.png");
//
//        // Define behaviour of repository
//        when(breedRepository.findById(1L)).thenReturn(Optional.of(breed));
//        when(fileStorageServiceImpl.loadFileAsResource(breed.getImageSrc())).thenReturn(n);
//
//        // run service method
//        ResponseEntity<?> responseEntity = breedService.loadBreedImage(breed.getBreedId());
//        System.out.println(responseEntity.getBody());
//
//        //Assert
//        assertNotEquals("Tải file lỗi", responseEntity.getBody());
//        assertNotEquals("Không tìm thấy loại",responseEntity.getBody());
//    }
}