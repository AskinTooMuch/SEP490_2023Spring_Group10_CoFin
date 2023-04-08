/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 10/03/2023   1.0         DuongVV     First Deploy<br>
 * 06/02/2023   2.0         DuongNH     Add test case<br>
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
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("newSpecieUTCID01")
    void newSpecieUTCID01() {
        // Set up
        User user = new User();
        user.setUserId(1L);
        user.setStatus(2);
        NewSpecieDTO dto = new NewSpecieDTO();
        dto.setUserId(1L);
        dto.setSpecieName("Ngỗng");
        dto.setIncubationPeriod(100);
        dto.setEmbryolessDate(50);
        dto.setDiedEmbryoDate(60);
        dto.setBalutDate(70);
        dto.setHatchingDate(80);
        // Define behaviour of repository
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Run service method
        ResponseEntity<?> responseEntity = specieService.newSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tạo loài mới thành công",responseEntity.getBody());
    }

    @Test
    @DisplayName("newSpecieUTCID02")
    void newSpecieUTCID02() {
        // Set up
        User user = new User();
        user.setUserId(1L);
        user.setStatus(2);
        NewSpecieDTO dto = new NewSpecieDTO();
        dto.setUserId(1L);
        dto.setSpecieName("abc123");
        dto.setIncubationPeriod(100);
        dto.setEmbryolessDate(50);
        dto.setDiedEmbryoDate(60);
        dto.setBalutDate(70);
        dto.setHatchingDate(80);
        // Define behaviour of repository
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Run service method
        ResponseEntity<?> responseEntity = specieService.newSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tạo loài mới thành công",responseEntity.getBody());
    }

    @Test
    @DisplayName("newSpecieUTCID03")
    void newSpecieUTCID03() {
        // Set up
        User user = new User();
        user.setUserId(1L);
        user.setStatus(2);
        NewSpecieDTO dto = new NewSpecieDTO();
        dto.setUserId(1L);
        dto.setSpecieName("");
        dto.setIncubationPeriod(100);
        dto.setEmbryolessDate(50);
        dto.setDiedEmbryoDate(60);
        dto.setBalutDate(70);
        dto.setHatchingDate(80);
        // Define behaviour of repository
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Run service method
        ResponseEntity<?> responseEntity = specieService.newSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên loài không được để trống",responseEntity.getBody());
    }

    @Test
    @DisplayName("newSpecieUTCID04")
    void newSpecieUTCID04() {
        // Set up
        User user = new User();
        user.setUserId(1L);
        user.setStatus(2);
        NewSpecieDTO dto = new NewSpecieDTO();
        dto.setUserId(1L);
        dto.setSpecieName(null);
        dto.setIncubationPeriod(100);
        dto.setEmbryolessDate(50);
        dto.setDiedEmbryoDate(60);
        dto.setBalutDate(70);
        dto.setHatchingDate(80);
        // Define behaviour of repository
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Run service method
        ResponseEntity<?> responseEntity = specieService.newSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên loài không được để trống",responseEntity.getBody());
    }

    @Test
    @DisplayName("newSpecieUTCID05")
    void newSpecieUTCID05() {
        // Set up
        User user = new User();
        user.setUserId(1L);
        user.setStatus(2);
        NewSpecieDTO dto = new NewSpecieDTO();
        dto.setUserId(1L);
        dto.setSpecieName("Ngỗng");
        dto.setIncubationPeriod(4);
        dto.setEmbryolessDate(50);
        dto.setDiedEmbryoDate(60);
        dto.setBalutDate(70);
        dto.setHatchingDate(80);
        // Define behaviour of repository
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Run service method
        ResponseEntity<?> responseEntity = specieService.newSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tổng thời gian ấp không được nhỏ hơn 5 và lớn hơn 1000 ngày",responseEntity.getBody());
    }

    @Test
    @DisplayName("newSpecieUTCID06")
    void newSpecieUTCID06() {
        // Set up
        User user = new User();
        user.setUserId(1L);
        user.setStatus(2);
        NewSpecieDTO dto = new NewSpecieDTO();
        dto.setUserId(1L);
        dto.setSpecieName("Ngỗng");
        dto.setIncubationPeriod(1001);
        dto.setEmbryolessDate(50);
        dto.setDiedEmbryoDate(60);
        dto.setBalutDate(70);
        dto.setHatchingDate(80);
        // Define behaviour of repository
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Run service method
        ResponseEntity<?> responseEntity = specieService.newSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tổng thời gian ấp không được nhỏ hơn 5 và lớn hơn 1000 ngày",responseEntity.getBody());
    }

    @Test
    @DisplayName("newSpecieUTCID07")
    void newSpecieUTCID07() {
        // Set up
        User user = new User();
        user.setUserId(1L);
        user.setStatus(2);
        NewSpecieDTO dto = new NewSpecieDTO();
        dto.setUserId(1L);
        dto.setSpecieName("Ngỗng");
        dto.setIncubationPeriod(100);
        dto.setEmbryolessDate(0);
        dto.setDiedEmbryoDate(60);
        dto.setBalutDate(70);
        dto.setHatchingDate(80);
        // Define behaviour of repository
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Run service method
        ResponseEntity<?> responseEntity = specieService.newSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Mốc xác định trứng trắng không được nhỏ hơn 0 và phải nhỏ hơn tổng thời gian ấp",responseEntity.getBody());
    }

    @Test
    @DisplayName("newSpecieUTCID08")
    void newSpecieUTCID08() {
        // Set up
        User user = new User();
        user.setUserId(1L);
        user.setStatus(2);
        NewSpecieDTO dto = new NewSpecieDTO();
        dto.setUserId(1L);
        dto.setSpecieName("Ngỗng");
        dto.setIncubationPeriod(100);
        dto.setEmbryolessDate(200);
        dto.setDiedEmbryoDate(60);
        dto.setBalutDate(70);
        dto.setHatchingDate(80);
        // Define behaviour of repository
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Run service method
        ResponseEntity<?> responseEntity = specieService.newSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Mốc xác định trứng trắng không được nhỏ hơn 0 và phải nhỏ hơn tổng thời gian ấp",responseEntity.getBody());
    }

    @Test
    @DisplayName("newSpecieUTCID09")
    void newSpecieUTCID09() {
        // Set up
        User user = new User();
        user.setUserId(1L);
        user.setStatus(2);
        NewSpecieDTO dto = new NewSpecieDTO();
        dto.setUserId(1L);
        dto.setSpecieName("Ngỗng");
        dto.setIncubationPeriod(100);
        dto.setEmbryolessDate(50);
        dto.setDiedEmbryoDate(50);
        dto.setBalutDate(70);
        dto.setHatchingDate(80);
        // Define behaviour of repository
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Run service method
        ResponseEntity<?> responseEntity = specieService.newSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Mốc xác định trứng loãng không được nhỏ hơn mốc xác định trứng trắng và phải nhỏ hơn tổng thời gian ấp",responseEntity.getBody());
    }

    @Test
    @DisplayName("newSpecieUTCID10")
    void newSpecieUTCID10() {
        // Set up
        User user = new User();
        user.setUserId(1L);
        user.setStatus(2);
        NewSpecieDTO dto = new NewSpecieDTO();
        dto.setUserId(1L);
        dto.setSpecieName("Ngỗng");
        dto.setIncubationPeriod(100);
        dto.setEmbryolessDate(50);
        dto.setDiedEmbryoDate(200);
        dto.setBalutDate(70);
        dto.setHatchingDate(80);
        // Define behaviour of repository
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Run service method
        ResponseEntity<?> responseEntity = specieService.newSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Mốc xác định trứng loãng không được nhỏ hơn mốc xác định trứng trắng và phải nhỏ hơn tổng thời gian ấp",responseEntity.getBody());
    }

    @Test
    @DisplayName("newSpecieUTCID11")
    void newSpecieUTCID11() {
        // Set up
        User user = new User();
        user.setUserId(1L);
        user.setStatus(2);
        NewSpecieDTO dto = new NewSpecieDTO();
        dto.setUserId(1L);
        dto.setSpecieName("Ngỗng");
        dto.setIncubationPeriod(100);
        dto.setEmbryolessDate(50);
        dto.setDiedEmbryoDate(60);
        dto.setBalutDate(60);
        dto.setHatchingDate(80);
        // Define behaviour of repository
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Run service method
        ResponseEntity<?> responseEntity = specieService.newSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Mốc xác định trứng lộn không được nhỏ hơn mốc xác định trứng loãng và phải nhỏ hơn tổng thời gian ấp",responseEntity.getBody());
    }

    @Test
    @DisplayName("newSpecieUTCID12")
    void newSpecieUTCID12() {
        // Set up
        User user = new User();
        user.setUserId(1L);
        user.setStatus(2);
        NewSpecieDTO dto = new NewSpecieDTO();
        dto.setUserId(1L);
        dto.setSpecieName("Ngỗng");
        dto.setIncubationPeriod(100);
        dto.setEmbryolessDate(50);
        dto.setDiedEmbryoDate(60);
        dto.setBalutDate(200);
        dto.setHatchingDate(80);
        // Define behaviour of repository
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Run service method
        ResponseEntity<?> responseEntity = specieService.newSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Mốc xác định trứng lộn không được nhỏ hơn mốc xác định trứng loãng và phải nhỏ hơn tổng thời gian ấp",responseEntity.getBody());
    }

    @Test
    @DisplayName("newSpecieUTCID13")
    void newSpecieUTCID13() {
        // Set up
        User user = new User();
        user.setUserId(1L);
        user.setStatus(2);
        NewSpecieDTO dto = new NewSpecieDTO();
        dto.setUserId(1L);
        dto.setSpecieName("Ngỗng");
        dto.setIncubationPeriod(100);
        dto.setEmbryolessDate(50);
        dto.setDiedEmbryoDate(60);
        dto.setBalutDate(70);
        dto.setHatchingDate(10);
        // Define behaviour of repository
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Run service method
        ResponseEntity<?> responseEntity = specieService.newSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Mốc chuyển trứng sang máy nở không được nhỏ hơn mốc xác định trứng lộn và phải nhỏ hơn tổng thời gian ấp",responseEntity.getBody());
    }

    @Test
    @DisplayName("newSpecieUTCID14")
    void newSpecieUTCID14() {
        // Set up
        User user = new User();
        user.setUserId(1L);
        user.setStatus(2);
        NewSpecieDTO dto = new NewSpecieDTO();
        dto.setUserId(1L);
        dto.setSpecieName("Ngỗng");
        dto.setIncubationPeriod(100);
        dto.setEmbryolessDate(50);
        dto.setDiedEmbryoDate(60);
        dto.setBalutDate(70);
        dto.setHatchingDate(200);
        // Define behaviour of repository
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Run service method
        ResponseEntity<?> responseEntity = specieService.newSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Mốc chuyển trứng sang máy nở không được nhỏ hơn mốc xác định trứng lộn và phải nhỏ hơn tổng thời gian ấp",responseEntity.getBody());
    }

    @Test
    @DisplayName("listSpecieUTCID01")
    void listSpecieUTCID01() {
        // Set up
        User user = new User();
        user.setUserId(1L);
        user.setStatus(2);

        Specie specie1 = new Specie();
        specie1.setSpecieId(1L);
        Specie specie2 = new Specie();
        specie2.setSpecieId(2L);

        List<Specie> specieList = new ArrayList<>();
        specieList.add(specie1);
        specieList.add(specie2);

        List<IncubationPhase> phaseList = new ArrayList<>();

        // Define behaviour of repository
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(specieRepository.findByUserId(1L)).thenReturn(Optional.of(specieList));
        when(incubationPhaseRepository.findAll()).thenReturn(phaseList);

        // Run service method
        ResponseEntity<?> responseEntity = specieService.listSpecie(user.getUserId());
        List<DetailSpecieDTO> detailSpecieDTOList = (List<DetailSpecieDTO>) responseEntity.getBody();
        // Assert
        assertEquals(2,detailSpecieDTOList.size());
    }

    @Test
    @DisplayName("listSpecieUTCID02")
    void listSpecieUTCID02() {
        // Set up
        User user = new User();
        user.setUserId(15L);
        user.setStatus(2);

        List<Specie> specieList = new ArrayList<>();

        List<IncubationPhase> phaseList = new ArrayList<>();

        // Define behaviour of repository
        when(userRepository.findById(15L)).thenReturn(Optional.of(user));
        when(specieRepository.findByUserId(15L)).thenReturn(Optional.of(specieList));
        when(incubationPhaseRepository.findAll()).thenReturn(phaseList);

        // Run service method
        ResponseEntity<?> responseEntity = specieService.listSpecie(user.getUserId());
        List<DetailSpecieDTO> detailSpecieDTOList = (List<DetailSpecieDTO>) responseEntity.getBody();
        // Assert
        assertEquals(0,detailSpecieDTOList.size());
    }

    @Test
    @DisplayName("listSpecieUTCID03")
    void listSpecieUTCID03() {
        // Set up
        User user = new User();
        user.setUserId(0L);
        user.setStatus(0);

        // Define behaviour of repository
        when(userRepository.findById(0L)).thenReturn(Optional.of(user));

        // Run service method
        ResponseEntity<?> responseEntity = specieService.listSpecie(user.getUserId());
        // Assert
        assertEquals("Không tìm thấy tài khoản hoặc tài khoản đã bị vô hiệu hóa",responseEntity.getBody());
    }

    @Test
    @DisplayName("getSpecieUTCID01")
    void getSpecieUTCID01() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        specie.setSpecieName("Ngỗng");
        specie.setStatus(true);

        List<IncubationPhase> incubationPhaseList = new ArrayList<>();

        IncubationPhase incubationPhase1 = new IncubationPhase();
        incubationPhase1.setSpecieId(1L);
        incubationPhase1.setPhaseNumber(2);
        incubationPhase1.setPhasePeriod(30);

        IncubationPhase incubationPhase2 = new IncubationPhase();
        incubationPhase2.setSpecieId(1L);
        incubationPhase2.setPhaseNumber(3);
        incubationPhase2.setPhasePeriod(30);

        IncubationPhase incubationPhase3 = new IncubationPhase();
        incubationPhase3.setSpecieId(1L);
        incubationPhase3.setPhaseNumber(4);
        incubationPhase3.setPhasePeriod(30);

        IncubationPhase incubationPhase4 = new IncubationPhase();
        incubationPhase4.setSpecieId(1L);
        incubationPhase4.setPhaseNumber(5);
        incubationPhase4.setPhasePeriod(30);

        incubationPhaseList.add(incubationPhase1);
        incubationPhaseList.add(incubationPhase2);
        incubationPhaseList.add(incubationPhase3);
        incubationPhaseList.add(incubationPhase4);

        // Define behaviour of repository
        when(specieRepository.findById(1L)).thenReturn(Optional.of(specie));
        when(incubationPhaseRepository.findIncubationPhasesBySpecieId(specie.getSpecieId())).thenReturn(Optional.of(incubationPhaseList));
        // Run service method
        ResponseEntity<?> responseEntity = specieService.getSpecie(specie.getSpecieId());
        System.out.println(responseEntity.toString());
        // Assert
        assertNotEquals("Không tìm thấy loài hoặc loài đã bị vô hiệu hóa",responseEntity.getBody());
        assertNotEquals(null,responseEntity.getBody());
    }

    @Test
    @DisplayName("getSpecieUTCID02")
    void getSpecieUTCID02() {
        // Set up
        Long specieId = 0L;

        // Define behaviour of repository
        when(specieRepository.findById(0L)).thenReturn(Optional.empty());
        // Run service method
        ResponseEntity<?> responseEntity = specieService.getSpecie(specieId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Không tìm thấy loài hoặc loài đã bị vô hiệu hóa",responseEntity.getBody());
    }

    @Test
    @DisplayName("saveSpecieUTCID01")
    void saveSpecieUTCID01() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        specie.setStatus(true);
        EditSpecieDTO dto = new EditSpecieDTO();
        dto.setSpecieId(1L);
        dto.setSpecieName("Ngỗng");
        dto.setIncubationPeriod(100);
        dto.setEmbryolessDate(50);
        dto.setDiedEmbryoDate(60);
        dto.setBalutDate(70);
        dto.setHatchingDate(80);
        // Define behaviour of repository
        when(specieRepository.findById(1L)).thenReturn(Optional.of(specie));
        // Run service method
        ResponseEntity<?> responseEntity = specieService.saveSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Lưu thông tin loài thành công",responseEntity.getBody());
    }

    @Test
    @DisplayName("saveSpecieUTCID02")
    void saveSpecieUTCID02() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        specie.setStatus(true);
        EditSpecieDTO dto = new EditSpecieDTO();
        dto.setSpecieId(1L);
        dto.setSpecieName("abc123");
        dto.setIncubationPeriod(100);
        dto.setEmbryolessDate(50);
        dto.setDiedEmbryoDate(60);
        dto.setBalutDate(70);
        dto.setHatchingDate(80);
        // Define behaviour of repository
        when(specieRepository.findById(1L)).thenReturn(Optional.of(specie));
        // Run service method
        ResponseEntity<?> responseEntity = specieService.saveSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Lưu thông tin loài thành công",responseEntity.getBody());
    }

    @Test
    @DisplayName("saveSpecieUTCID03")
    void saveSpecieUTCID03() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        specie.setStatus(true);
        EditSpecieDTO dto = new EditSpecieDTO();
        dto.setSpecieId(1L);
        dto.setSpecieName("");
        dto.setIncubationPeriod(100);
        dto.setEmbryolessDate(50);
        dto.setDiedEmbryoDate(60);
        dto.setBalutDate(70);
        dto.setHatchingDate(80);
        // Run service method
        ResponseEntity<?> responseEntity = specieService.saveSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên loài không được để trống",responseEntity.getBody());
    }

    @Test
    @DisplayName("saveSpecieUTCID04")
    void saveSpecieUTCID04() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        specie.setStatus(true);
        EditSpecieDTO dto = new EditSpecieDTO();
        dto.setSpecieId(1L);
        dto.setSpecieName(null);
        dto.setIncubationPeriod(100);
        dto.setEmbryolessDate(50);
        dto.setDiedEmbryoDate(60);
        dto.setBalutDate(70);
        dto.setHatchingDate(80);
        // Run service method
        ResponseEntity<?> responseEntity = specieService.saveSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên loài không được để trống",responseEntity.getBody());
    }

    @Test
    @DisplayName("saveSpecieUTCID05")
    void saveSpecieUTCID05() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        specie.setStatus(true);
        EditSpecieDTO dto = new EditSpecieDTO();
        dto.setSpecieId(1L);
        dto.setSpecieName("Ngỗng");
        dto.setIncubationPeriod(4);
        dto.setEmbryolessDate(50);
        dto.setDiedEmbryoDate(60);
        dto.setBalutDate(70);
        dto.setHatchingDate(80);
        // Run service method
        ResponseEntity<?> responseEntity = specieService.saveSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tổng thời gian ấp không được nhỏ hơn 5 và lớn hơn 1000 ngày",responseEntity.getBody());
    }

    @Test
    @DisplayName("saveSpecieUTCID06")
    void saveSpecieUTCID06() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        specie.setStatus(true);
        EditSpecieDTO dto = new EditSpecieDTO();
        dto.setSpecieId(1L);
        dto.setSpecieName("Ngỗng");
        dto.setIncubationPeriod(1001);
        dto.setEmbryolessDate(50);
        dto.setDiedEmbryoDate(60);
        dto.setBalutDate(70);
        dto.setHatchingDate(80);
        // Run service method
        ResponseEntity<?> responseEntity = specieService.saveSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tổng thời gian ấp không được nhỏ hơn 5 và lớn hơn 1000 ngày",responseEntity.getBody());
    }

    @Test
    @DisplayName("saveSpecieUTCID07")
    void saveSpecieUTCID07() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        specie.setStatus(true);
        EditSpecieDTO dto = new EditSpecieDTO();
        dto.setSpecieId(1L);
        dto.setSpecieName("Ngỗng");
        dto.setIncubationPeriod(100);
        dto.setEmbryolessDate(0);
        dto.setDiedEmbryoDate(60);
        dto.setBalutDate(70);
        dto.setHatchingDate(80);
        // Run service method
        ResponseEntity<?> responseEntity = specieService.saveSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Mốc xác định trứng trắng không được nhỏ hơn 0 và phải nhỏ hơn tổng thời gian ấp",responseEntity.getBody());
    }

    @Test
    @DisplayName("saveSpecieUTCID08")
    void saveSpecieUTCID08() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        specie.setStatus(true);
        EditSpecieDTO dto = new EditSpecieDTO();
        dto.setSpecieId(1L);
        dto.setSpecieName("Ngỗng");
        dto.setIncubationPeriod(100);
        dto.setEmbryolessDate(200);
        dto.setDiedEmbryoDate(60);
        dto.setBalutDate(70);
        dto.setHatchingDate(80);
        // Run service method
        ResponseEntity<?> responseEntity = specieService.saveSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Mốc xác định trứng trắng không được nhỏ hơn 0 và phải nhỏ hơn tổng thời gian ấp",responseEntity.getBody());
    }

    @Test
    @DisplayName("saveSpecieUTCID09")
    void saveSpecieUTCID09() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        specie.setStatus(true);
        EditSpecieDTO dto = new EditSpecieDTO();
        dto.setSpecieId(1L);
        dto.setSpecieName("Ngỗng");
        dto.setIncubationPeriod(100);
        dto.setEmbryolessDate(50);
        dto.setDiedEmbryoDate(50);
        dto.setBalutDate(70);
        dto.setHatchingDate(80);
        // Run service method
        ResponseEntity<?> responseEntity = specieService.saveSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Mốc xác định trứng loãng không được nhỏ hơn mốc xác định trứng trắng và phải nhỏ hơn tổng thời gian ấp",responseEntity.getBody());
    }

    @Test
    @DisplayName("saveSpecieUTCID10")
    void saveSpecieUTCID10() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        specie.setStatus(true);
        EditSpecieDTO dto = new EditSpecieDTO();
        dto.setSpecieId(1L);
        dto.setSpecieName("Ngỗng");
        dto.setIncubationPeriod(100);
        dto.setEmbryolessDate(50);
        dto.setDiedEmbryoDate(200);
        dto.setBalutDate(70);
        dto.setHatchingDate(80);
        // Run service method
        ResponseEntity<?> responseEntity = specieService.saveSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Mốc xác định trứng loãng không được nhỏ hơn mốc xác định trứng trắng và phải nhỏ hơn tổng thời gian ấp",responseEntity.getBody());
    }

    @Test
    @DisplayName("saveSpecieUTCID11")
    void saveSpecieUTCID11() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        specie.setStatus(true);
        EditSpecieDTO dto = new EditSpecieDTO();
        dto.setSpecieId(1L);
        dto.setSpecieName("Ngỗng");
        dto.setIncubationPeriod(100);
        dto.setEmbryolessDate(50);
        dto.setDiedEmbryoDate(60);
        dto.setBalutDate(60);
        dto.setHatchingDate(80);
        // Run service method
        ResponseEntity<?> responseEntity = specieService.saveSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Mốc xác định trứng lộn không được nhỏ hơn mốc xác định trứng loãng và phải nhỏ hơn tổng thời gian ấp",responseEntity.getBody());
    }

    @Test
    @DisplayName("saveSpecieUTCID12")
    void saveSpecieUTCID12() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        specie.setStatus(true);
        EditSpecieDTO dto = new EditSpecieDTO();
        dto.setSpecieId(1L);
        dto.setSpecieName("Ngỗng");
        dto.setIncubationPeriod(100);
        dto.setEmbryolessDate(50);
        dto.setDiedEmbryoDate(60);
        dto.setBalutDate(200);
        dto.setHatchingDate(80);
        // Run service method
        ResponseEntity<?> responseEntity = specieService.saveSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Mốc xác định trứng lộn không được nhỏ hơn mốc xác định trứng loãng và phải nhỏ hơn tổng thời gian ấp",responseEntity.getBody());
    }

    @Test
    @DisplayName("saveSpecieUTCID13")
    void saveSpecieUTCID13() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        specie.setStatus(true);
        EditSpecieDTO dto = new EditSpecieDTO();
        dto.setSpecieId(1L);
        dto.setSpecieName("Ngỗng");
        dto.setIncubationPeriod(100);
        dto.setEmbryolessDate(50);
        dto.setDiedEmbryoDate(60);
        dto.setBalutDate(200);
        dto.setHatchingDate(80);
        // Run service method
        ResponseEntity<?> responseEntity = specieService.saveSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Mốc xác định trứng lộn không được nhỏ hơn mốc xác định trứng loãng và phải nhỏ hơn tổng thời gian ấp",responseEntity.getBody());
    }

    @Test
    @DisplayName("saveSpecieUTCID14")
    void saveSpecieUTCID14() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        specie.setStatus(true);
        EditSpecieDTO dto = new EditSpecieDTO();
        dto.setSpecieId(1L);
        dto.setSpecieName("Ngỗng");
        dto.setIncubationPeriod(100);
        dto.setEmbryolessDate(50);
        dto.setDiedEmbryoDate(60);
        dto.setBalutDate(70);
        dto.setHatchingDate(200);
        // Run service method
        ResponseEntity<?> responseEntity = specieService.saveSpecie(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Mốc chuyển trứng sang máy nở không được nhỏ hơn mốc xác định trứng lộn và phải nhỏ hơn tổng thời gian ấp",responseEntity.getBody());
    }

    @Test
    @DisplayName("deleteSpecieUTCID01")
    void deleteSpecieUTCID01() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(1L);
        // Define behaviour of repository
        when(specieRepository.findById(1L)).thenReturn(Optional.of(specie));
        // Run service method
        ResponseEntity<?> responseEntity = specieService.deleteSpecie(1L);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Xóa loài thành công",responseEntity.getBody());
    }

    @Test
    @DisplayName("deleteSpecieUTCID02")
    void deleteSpecieUTCID02() {
        // Set up
        Specie specie = new Specie();
        specie.setSpecieId(0L);
        // Define behaviour of repository
        when(specieRepository.findById(0L)).thenReturn(Optional.empty());
        // Run service method
        ResponseEntity<?> responseEntity = specieService.deleteSpecie(0L);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Không tìm thấy loài",responseEntity.getBody());
    }
}