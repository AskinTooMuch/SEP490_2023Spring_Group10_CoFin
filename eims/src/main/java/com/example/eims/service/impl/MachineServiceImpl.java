/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 02/03/2023   1.0         DuongVV     First Deploy<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.eggLocation.EggLocationMachineDetailDTO;
import com.example.eims.dto.machine.CreateMachineDTO;
import com.example.eims.dto.machine.MachineDetailDTO;
import com.example.eims.dto.machine.MachineListItemDTO;
import com.example.eims.dto.machine.UpdateMachineDTO;
import com.example.eims.entity.*;
import com.example.eims.repository.*;
import com.example.eims.service.interfaces.IMachineService;
import com.example.eims.utils.StringDealer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MachineServiceImpl implements IMachineService {
    @Autowired
    private final MachineRepository machineRepository;
    @Autowired
    private final FacilityRepository facilityRepository;
    @Autowired
    private final EggLocationRepository eggLocationRepository;
    @Autowired
    private final MachineTypeRepository machineTypeRepository;
    @Autowired
    private final EggProductRepository eggProductRepository;
    @Autowired
    private final BreedRepository breedRepository;
    private final StringDealer stringDealer;

    public MachineServiceImpl(MachineRepository machineRepository, FacilityRepository facilityRepository,
                              EggLocationRepository eggLocationRepository, MachineTypeRepository machineTypeRepository,
                              EggProductRepository eggProductRepository, BreedRepository breedRepository) {
        this.machineRepository = machineRepository;
        this.facilityRepository = facilityRepository;
        this.eggLocationRepository = eggLocationRepository;
        this.machineTypeRepository = machineTypeRepository;
        this.eggProductRepository = eggProductRepository;
        this.breedRepository = breedRepository;
        this.stringDealer = new StringDealer();
    }


    /**
     * Get all of their machines.
     *
     * @param facilityId the id of current logged-in user's selected facility.
     * @return list of Machines
     */
    @Override
    public ResponseEntity<?> getAllMachine(Long facilityId) {
        // Get all machines of the current facility
        Optional<List<Machine>> machineListOptional = machineRepository.findByFacilityId(facilityId);
        List<MachineListItemDTO> machineList = new ArrayList<>();
        if (machineListOptional.isPresent()) {
            for (Machine machine : machineListOptional.get()) {
                // Get and set attribute to DTO
                MachineType machineType = machineTypeRepository.findByMachineTypeId(machine.getMachineTypeId());
                String machineTypeName = machineType.getMachineTypeName();
                MachineListItemDTO machineListItemDTO = new MachineListItemDTO();
                machineListItemDTO.setMachineTypeName(machineTypeName);
                machineListItemDTO.getFromEntity(machine);
                machineList.add(machineListItemDTO);
            }
            return new ResponseEntity<>(machineList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get a machine.
     *
     * @param machineId the id of the machine
     * @return
     */
    @Override
    public ResponseEntity<?> getMachine(Long machineId) {
        // Get a machine of the current facility
        Optional<Machine> machineOptional = machineRepository.findById(machineId);
        if (machineOptional.isPresent()) {
            Machine machine = machineOptional.get();
            // Get and add attribute to DTO
            MachineDetailDTO machineDetailDTO = new MachineDetailDTO();
            machineDetailDTO.getFromEntity(machine);
            MachineType machineType = machineTypeRepository.findByMachineTypeId(machine.getMachineTypeId());
            machineDetailDTO.setMachineTypeName(machineType.getMachineTypeName());
            List<EggLocation> eggs = eggLocationRepository.getAllByMachineId(machineId).get();
            List<EggLocationMachineDetailDTO> eggLocations = new ArrayList<>();
            for (EggLocation eggLocation : eggs) {
                EggLocationMachineDetailDTO eggLocationMachineDetailDTO = new EggLocationMachineDetailDTO();
                eggLocationMachineDetailDTO.getFromEntity(eggLocation);
                EggProduct eggProduct = eggProductRepository.getByProductId(eggLocation.getProductId()).get();

                LocalDateTime dateTime = eggProduct.getIncubationDate();
                Date startDate = Date.valueOf(dateTime.toLocalDate());
                Date endDate = Date.valueOf(LocalDate.now());

                Breed breed = breedRepository.getBreedOfProduct(eggProduct.getProductId());
                int growthTime = breed.getGrowthTime();
                String breedName = breed.getBreedName();
                eggLocationMachineDetailDTO.setGrowthTime(growthTime);
                eggLocationMachineDetailDTO.setBreedName(breedName);
                eggLocationMachineDetailDTO.setIncubationDateToNow(stringDealer.dateDiff(startDate, endDate));
                eggLocationMachineDetailDTO.setEggBatchId(eggProduct.getEggBatchId());
                eggLocations.add(eggLocationMachineDetailDTO);
            }
            machineDetailDTO.setEggs(eggLocations);
            return new ResponseEntity<>(machineDetailDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get machines not full.
     *
     * @param facilityId the id of current logged-in user's selected facility.
     * @return
     */
    @Override
    public ResponseEntity<?> getMachinesNotFull(Long facilityId) {
        // Get all machines of the current facility
        Optional<List<Machine>> machineListOptional = machineRepository.findAllNotFull(facilityId);
        List<MachineListItemDTO> machineList = new ArrayList<>();
        if (machineListOptional.isPresent()) {
            for (Machine machine : machineListOptional.get()) {
                // Get and set attribute to DTO
                MachineType machineType = machineTypeRepository.findByMachineTypeId(machine.getMachineTypeId());
                String machineTypeName = machineType.getMachineTypeName();
                MachineListItemDTO machineListItemDTO = new MachineListItemDTO();
                machineListItemDTO.setMachineTypeName(machineTypeName);
                machineListItemDTO.getFromEntity(machine);
                machineList.add(machineListItemDTO);
            }
            return new ResponseEntity<>(machineList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Create a machine of a facility.
     *
     * @param createMachineDTO contains the facility id, machine type id, name, max and current capacity
     * @return
     */
    @Override
    public ResponseEntity<?> createMachine(CreateMachineDTO createMachineDTO) {
        // Check if facility is not running
        Long facilityId = createMachineDTO.getFacilityId();
        if (!facilityRepository.getStatusById(facilityId)) { /*Facility stopped running*/
            return new ResponseEntity<>("Cơ sở đã ngừng hoạt động", HttpStatus.BAD_REQUEST);
        } else {
            // Retrieve machine information and create new machine
            Machine machine = new Machine();
            // Check blank input
            // Machine's name
            String name = stringDealer.trimMax(createMachineDTO.getMachineName());
            if (name.equals("")) { /* Machine name is empty */
                return new ResponseEntity<>("Tên máy không được để trống", HttpStatus.BAD_REQUEST);
            }
            // Machine type
            Long machineType = createMachineDTO.getMachineTypeId();
            // Max capacity
            int maxCapacity = createMachineDTO.getMaxCapacity();
            if (maxCapacity <= 0) { /* Capacity must bigger than 0 */
                return new ResponseEntity<>("Sức chứa của máy phải lớn hơn 0", HttpStatus.BAD_REQUEST);
            }
            // Set attribute
            machine.setFacilityId(facilityId);
            machine.setMachineTypeId(machineType);
            machine.setMachineName(name);
            machine.setMaxCapacity(maxCapacity);
            machine.setCurCapacity(0);
            Date date = Date.valueOf(LocalDate.now());/* format yyyy-MM-dd*/
            machine.setAddedDate(date);
            machine.setStatus(1);
            // Save
            machineRepository.save(machine);
            return new ResponseEntity<>("Tạo máy mới thành công", HttpStatus.OK);
        }
    }

    /**
     * Show form update a machine.
     *
     * @param machineId the id of the machine
     * @return
     */
    @Override
    public ResponseEntity<?> showFormUpdate(Long machineId) {
        // Get a machine of the current facility
        Optional<Machine> machine = machineRepository.findById(machineId);
        if (machine.isPresent()) {
            UpdateMachineDTO updateMachineDTO = new UpdateMachineDTO();
            updateMachineDTO.getFromEntity(machine.get());
            return new ResponseEntity<>(updateMachineDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update a machine of a facility.
     *
     * @param updateMachineDTO contains the facility id, machine type id, name, max and current capacity, status
     * @return
     */
    @Override
    public ResponseEntity<?> updateMachine(UpdateMachineDTO updateMachineDTO) {
        // Retrieve machine's new information
        Optional<Machine> machineOptional = machineRepository.findByMachineId(updateMachineDTO.getMachineId());
        if (machineOptional.isPresent()) {
            Machine machine = machineOptional.get();
            // Check blank input
            // Machine's name
            String name = stringDealer.trimMax(updateMachineDTO.getMachineName());
            if (name.equals("")) { /* Machine name is empty */
                return new ResponseEntity<>("Tên máy không được để trống", HttpStatus.BAD_REQUEST);
            }
            machine.setMachineName(name);
            machine.setStatus(updateMachineDTO.getStatus());
            // Save
            machineRepository.save(machine);
            return new ResponseEntity<>("Cập nhật thông tin máy thành công", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Delete a machine of a facility.
     *
     * @param machineId the id of the machine
     * @return
     */
    @Override
    public ResponseEntity<?> deleteMachine(Long machineId) {
        if (machineRepository.existsByMachineId(machineId)) {
            // Check if Machine is running
            if (eggLocationRepository.existsByMachineId(machineId)) { /*Machine is running (have eggs in it)*/
                return new ResponseEntity<>("Chuyển trứng sang máy khác trước khi xóa", HttpStatus.BAD_REQUEST);
            } else {
                // Delete
                machineRepository.deleteById(machineId);
                return new ResponseEntity<>("Xóa máy thành công", HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get all of facility's machines with Paging.
     * facilityId is the id of current logged-in user's facility.
     *
     * @param facilityId
     * @param page       the page number
     * @param size       the size of page
     * @param sort       sorting type
     * @return page of machines
     */
    @Override
    public ResponseEntity<?> getAllMachinePaging(Long facilityId, Integer page, Integer size, String sort) {
        // Get sorting type
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("machineId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("machineId").descending();
        }
        // Get all customers of the current User with Paging
        Page<Machine> machinePage = machineRepository.findAllByFacilityId(facilityId, PageRequest.of(page, size, sortable));
        return new ResponseEntity<>(machinePage, HttpStatus.OK);
    }
}
