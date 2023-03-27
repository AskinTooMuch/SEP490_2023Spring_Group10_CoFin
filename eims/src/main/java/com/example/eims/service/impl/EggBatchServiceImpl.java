/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 15/03/2023   1.0         DuongVV     First Deploy<br>
 * 21/03/2023   1.0         DuongVV     Update checks<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.eggBatch.CreateEggBatchDTO;
import com.example.eims.dto.eggBatch.EggBatchDetailDTO;
import com.example.eims.dto.eggBatch.EggBatchListItemDTO;
import com.example.eims.dto.eggBatch.UpdateEggBatchDTO;
import com.example.eims.dto.eggLocation.EggLocationEggBatchDetailDTO;
import com.example.eims.dto.eggLocation.EggLocationUpdateEggBatchDTO;
import com.example.eims.dto.machine.MachineListItemDTO;
import com.example.eims.entity.*;
import com.example.eims.repository.*;
import com.example.eims.service.interfaces.IEggBatchService;
import com.example.eims.utils.StringDealer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class EggBatchServiceImpl implements IEggBatchService {
    @Autowired
    private final EggBatchRepository eggBatchRepository;
    @Autowired
    private final ImportReceiptRepository importReceiptRepository;
    @Autowired
    private final SupplierRepository supplierRepository;
    @Autowired
    private final SpecieRepository specieRepository;
    @Autowired
    private final BreedRepository breedRepository;
    @Autowired
    private final EggProductRepository eggProductRepository;
    @Autowired
    private final EggLocationRepository eggLocationRepository;
    @Autowired
    private final MachineRepository machineRepository;
    @Autowired
    private final IncubationPhaseRepository incubationPhaseRepository;
    @Autowired
    private final MachineTypeRepository machineTypeRepository;
    private final StringDealer stringDealer;

    public EggBatchServiceImpl(EggBatchRepository eggBatchRepository, ImportReceiptRepository importReceiptRepository,
                               SupplierRepository supplierRepository, SpecieRepository specieRepository,
                               BreedRepository breedRepository, EggProductRepository eggProductRepository,
                               EggLocationRepository eggLocationRepository, MachineRepository machineRepository,
                               IncubationPhaseRepository incubationPhaseRepository,
                               MachineTypeRepository machineTypeRepository) {
        this.eggBatchRepository = eggBatchRepository;
        this.importReceiptRepository = importReceiptRepository;
        this.supplierRepository = supplierRepository;
        this.specieRepository = specieRepository;
        this.breedRepository = breedRepository;
        this.eggProductRepository = eggProductRepository;
        this.eggLocationRepository = eggLocationRepository;
        this.machineRepository = machineRepository;
        this.incubationPhaseRepository = incubationPhaseRepository;
        this.machineTypeRepository = machineTypeRepository;
        this.stringDealer = new StringDealer();
    }

    /**
     * View an egg batch's details.
     *
     * @param eggBatchId the id of egg batch
     * @return
     */
    @Override
    public ResponseEntity<?> getEggBatch(Long eggBatchId) {
        EggBatchDetailDTO dto = new EggBatchDetailDTO();
        Optional<EggBatch> eggBatchOptional = eggBatchRepository.findByEggBatchId(eggBatchId);
        if (eggBatchOptional.isPresent()) {
            // Egg batch
            EggBatch eggBatch = eggBatchOptional.get();
            dto.setEggBatchId(eggBatchId);
            dto.setImportId(eggBatch.getImportId());
            dto.setAmount(eggBatch.getAmount());
            dto.setStatus(eggBatch.getStatus());
            // Import
            ImportReceipt importReceipt = importReceiptRepository.findByImportId(eggBatch.getImportId()).get();
            dto.setImportDate(importReceipt.getImportDate());

            // Supplier
            Supplier supplier = supplierRepository.findBySupplierId(importReceipt.getSupplierId()).get();
            dto.setSupplierId(supplier.getSupplierId());
            dto.setSupplierName(supplier.getSupplierName());
            // Breed
            Breed breed = breedRepository.findByBreedId(eggBatch.getBreedId()).get();
            dto.setBreedId(eggBatch.getBreedId());
            dto.setBreedName(breed.getBreedName());
            int growthTime = breed.getGrowthTime();
            dto.setGrowthTime(growthTime);
            // Specie
            Specie specie = specieRepository.findById(breed.getSpecieId()).get();
            dto.setSpecieId(breed.getSpecieId());
            dto.setSpecieName(specie.getSpecieName());

            // Egg Product
            Optional<List<EggProduct>> eggProductListOptional = eggProductRepository.findByEggBatchId(eggBatchId);
            if (eggProductListOptional.isEmpty()) {
                dto.setEggProductList(new ArrayList<>());
                dto.setMachineList(new ArrayList<>());
            } else {
                List<EggProduct> list;
                EggProduct dummy = new EggProduct();
                dummy.setAmount(0);
                dummy.setCurAmount(0);
                // dummy list
                list = Arrays.asList(dummy, dummy, dummy, dummy, dummy, dummy, dummy, dummy, dummy, dummy, dummy, dummy, dummy);
                for (EggProduct eggProduct : eggProductListOptional.get()) {
                    IncubationPhase incubationPhase = incubationPhaseRepository.
                            findByIncubationPhaseId(eggProduct.getIncubationPhaseId()).get();
                    list.set(incubationPhase.getPhaseNumber() + 1, eggProduct);
                }
                dto.setEggProductList(list);

                // Machine list
                List<EggLocationEggBatchDetailDTO> machineList = new ArrayList<>();
                EggLocationEggBatchDetailDTO dto1;
                for (EggProduct eggProduct : eggProductListOptional.get()) {
                    IncubationPhase incubationPhase = incubationPhaseRepository
                            .findByIncubationPhaseId(eggProduct.getIncubationPhaseId()).get();
                    // Chỉ lấy product trứng đang ấp hoặc nở
                    if ((incubationPhase.getPhaseNumber() == 1 && eggProduct.getCurAmount() > 0)
                            || (incubationPhase.getPhaseNumber() == 5 && eggProduct.getCurAmount() > 0)) {
                        Optional<List<EggLocation>> eggLocationOptional = eggLocationRepository.
                                findByProductId(eggProduct.getProductId());
                        if (eggLocationOptional.isPresent()) {
                            for (EggLocation eggLocation : eggLocationOptional.get()) {
                                // Eggs in hatching or incubating progress (phase number = 1 or 5)
                                dto1 = new EggLocationEggBatchDetailDTO();
                                dto1.setEggLocationId(eggLocation.getEggLocationId());
                                dto1.setProductId(eggLocation.getProductId());
                                Machine machine = machineRepository.findByMachineId(eggLocation.getMachineId()).get();
                                dto1.setMachineId(eggLocation.getMachineId());
                                dto1.setMachineName(machine.getMachineName());
                                dto1.setMachineTypeId(machine.getMachineTypeId());
                                dto1.setAmount(eggLocation.getAmount());
                                dto1.setMaxCapacity(machine.getMaxCapacity());
                                dto1.setCurCapacity(machine.getCurCapacity());
                                machineList.add(dto1);
                            }
                        }
                    }
                }
                dto.setMachineList(machineList);
            }

            // Not full machine list
            Optional<List<Machine>> notFullListOpt = machineRepository.findAllNotFull(importReceipt.getFacilityId());
            List<MachineListItemDTO> notFullListDto = new ArrayList<>();


            if (notFullListOpt.get().isEmpty()) {
                dto.setMachineNotFullList(new ArrayList<>());
            } else {
                List<Machine> itemList = notFullListOpt.get();
                // Set not full list
                for (Machine machine : itemList) {
                    // Get and set attribute to DTO
                    MachineType machineType = machineTypeRepository.findByMachineTypeId(machine.getMachineTypeId());
                    String machineTypeName = machineType.getMachineTypeName();
                    MachineListItemDTO machineListItemDTO = new MachineListItemDTO();
                    machineListItemDTO.setMachineTypeName(machineTypeName);
                    machineListItemDTO.getFromEntity(machine);
                    notFullListDto.add(machineListItemDTO);
                }
                dto.setMachineNotFullList(notFullListDto);
            }

            // Progress (incubation phase)
            // Get latest phase
            int progress = 0;
            Optional<EggProduct> eggProductOptional = eggProductRepository.
                    findEggProductLastPhase(eggBatchId);
            if (eggProductOptional.isPresent()) {
                Long incubationPhaseId = eggProductOptional.get().getIncubationPhaseId();
                IncubationPhase phase = incubationPhaseRepository.findByIncubationPhaseId(incubationPhaseId).get();
                progress = phase.getPhaseNumber();
            }
            dto.setProgress(progress);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * View list egg batch.
     *
     * @param facilityId the id of facility
     * @return
     */
    @Override
    public ResponseEntity<?> viewListEggBatch(Long facilityId) {
        Optional<List<ImportReceipt>> importReceiptListOptional = importReceiptRepository.findByFacilityId(facilityId);
        if (importReceiptListOptional.isPresent()) {
            List<ImportReceipt> importReceiptList = importReceiptListOptional.get();
            List<EggBatchListItemDTO> dtoList = new ArrayList<>();
            for (ImportReceipt importReceipt : importReceiptList) {
                List<EggBatch> eggBatchList = eggBatchRepository.findByImportId(importReceipt.getImportId()).get();
                for (EggBatch eggBatch : eggBatchList) {
                    // List egg batch
                    EggBatchListItemDTO dto = new EggBatchListItemDTO();
                    // Egg batch
                    dto.setEggBatchId(eggBatch.getEggBatchId());
                    dto.setImportId(importReceipt.getImportId());
                    dto.setImportDate(importReceipt.getImportDate());
                    dto.setAmount(eggBatch.getAmount());
                    dto.setStatus(eggBatch.getStatus());
                    // Breed
                    Breed breed = breedRepository.findByBreedId(eggBatch.getBreedId()).get();
                    dto.setBreedId(eggBatch.getBreedId());
                    dto.setBreedName(breed.getBreedName());
                    // Supplier
                    Supplier supplier = supplierRepository.findBySupplierId(importReceipt.getSupplierId()).get();
                    dto.setSupplierId(importReceipt.getSupplierId());
                    dto.setSupplierName(supplier.getSupplierName());
                    // Progress (incubation phase)
                    int progress = 0;
                    Optional<EggProduct> eggProductOptional = eggProductRepository.
                            findEggProductLastPhase(eggBatch.getEggBatchId());
                    if (eggProductOptional.isPresent()) {
                        Long incubationPhaseId = eggProductOptional.get().getIncubationPhaseId();
                        IncubationPhase phase = incubationPhaseRepository.
                                findByIncubationPhaseId(incubationPhaseId).get();
                        progress = phase.getPhaseNumber();
                    }
                    dto.setProgress(progress);
                    // add to list
                    dtoList.add(dto);
                }
            }
            return new ResponseEntity<>(dtoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update egg batch's status.
     *
     * @param updateEggBatchDTO
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<?> updateEggBatch(UpdateEggBatchDTO updateEggBatchDTO) {
        // Get incubation phase list
        List<IncubationPhase> incubationPhaseList = incubationPhaseRepository.
                getListIncubationPhaseForEggBatch(updateEggBatchDTO.getEggBatchId());
        // List item (incubationPhaseId)    | 0| 1| 2| 3| 4| 5| 6| 7| 8| 9| 10|
        // phase number                     |-1| 0| 1| 2| 3| 4| 5| 6| 7| 8| 9 |


        // Egg Batch
        Optional<EggBatch> eggBatchOptional = eggBatchRepository.findByEggBatchId(updateEggBatchDTO.getEggBatchId());
        if (eggBatchOptional.isEmpty()) {
            return new ResponseEntity<>("Không tìm thấy lô trứng", HttpStatus.BAD_REQUEST);
        }
        EggBatch eggBatch = eggBatchOptional.get();
        int eggBatchAmount = eggBatch.getAmount();

        // Get latest phase
        int progress = 0;
        Optional<EggProduct> eggProductLastPhaseOptional = eggProductRepository.
                findEggProductLastPhase(eggBatch.getEggBatchId());
        if (eggProductLastPhaseOptional.isPresent()) {
            Long incubationPhaseId = eggProductLastPhaseOptional.get().getIncubationPhaseId();
            IncubationPhase phase = incubationPhaseRepository.findByIncubationPhaseId(incubationPhaseId).get();
            progress = phase.getPhaseNumber();
        }

        // Get eggs amount
        // Trứng hao hụt
        int eggWastedAmount = updateEggBatchDTO.getEggWasted();
        if (eggWastedAmount < 0) {
            return new ResponseEntity<>("Số trứng hao hụt phải lớn hơn hoặc bằng 0", HttpStatus.BAD_REQUEST);
        }
        // Trứng theo phase
        int amount = updateEggBatchDTO.getAmount();
        if (amount < 0) {
            return new ResponseEntity<>("Số trứng cập nhật phải lớn hơn hoặc bằng 0", HttpStatus.BAD_REQUEST);
        }

        // Get phase number of egg to insert
        int phaseNumber = updateEggBatchDTO.getPhaseNumber();
        LocalDateTime now = LocalDateTime.now();
        // Get Machine list
        List<EggLocationUpdateEggBatchDTO> eggLocationDTOs = updateEggBatchDTO.getEggLocationUpdateEggBatchDTOS();

        try {
            // Find current egg product to update
            EggProduct eggProduct;
            Optional<EggProduct> eggProductOptional = eggProductRepository.
                    findByEggBatchIdAndIncubationPhaseId(
                            eggBatch.getEggBatchId(),
                            incubationPhaseList.get(phaseNumber + 1).getIncubationPhaseId());

            Optional<List<EggProduct>> listOptional = eggProductRepository.
                    findByEggBatchId(eggBatch.getEggBatchId());

            // First update, insert egg to incubating machine
            // Create Trứng hao hụt (-1), Trứng dập (0), trứng đang ấp (1)
            if (progress == 0 && phaseNumber != 0) { // Phase = 0, no egg product
                return new ResponseEntity<>("Lô trứng chưa được ấp, không thể cập nhật "
                        + incubationPhaseList.get(phaseNumber + 1).getPhaseDescription()
                        , HttpStatus.BAD_REQUEST);
            }

            if (phaseNumber == 0 && progress == 0) {
                // Check amounts
                if (eggWastedAmount + amount > eggBatchAmount) {
                    return new ResponseEntity<>(
                            "Số trứng không hợp lệ. Tổng số Trứng hư tổn, hao hụt và Trứng vỡ/dập phải " +
                                    "nhỏ hơn hoặc bằng số trứng của lô",
                            HttpStatus.BAD_REQUEST);
                }
                // Check machine list
                if (eggLocationDTOs.isEmpty()) {
                    return new ResponseEntity<>("Chưa cập nhật vị trí trứng", HttpStatus.BAD_REQUEST);
                }

                // Check total eggs in list Machines
                int total = 0;
                for (EggLocationUpdateEggBatchDTO eggLocationDTO : eggLocationDTOs) {
                    Machine machine = machineRepository.findByMachineId(eggLocationDTO.getMachineId()).get();
                    if (machine.getMachineTypeId() == 2) {
                        return new ResponseEntity<>("Chỉ được chọn máy ấp trong giai đoạn này",
                                HttpStatus.BAD_REQUEST);
                    }
                    if (eggLocationDTO.getAmountUpdate() < 0
                            || eggLocationDTO.getAmountUpdate() > (eggLocationDTO.getCapacity() - eggLocationDTO.getAmountCurrent())
                            || eggLocationDTO.getAmountUpdate() > eggBatchAmount) {
                        return new ResponseEntity<>("Số lượng trứng trong danh sách máy không hợp lệ",
                                HttpStatus.BAD_REQUEST);
                    }
                    total += eggLocationDTO.getAmountUpdate();
                }

                if (total != eggBatchAmount - eggWastedAmount - amount) {
                    return new ResponseEntity<>("Số lượng trứng trong danh sách máy không hợp lệ. " +
                            "Tổng số trứng trong danh sách máy phải bằng số trứng của lô trừ đi tổng số " +
                            "Trứng hư tổn, hao hụt và Trứng vỡ/dập",
                            HttpStatus.BAD_REQUEST);
                }

                // Create Trứng hao hụt - egg-1
                eggProduct = new EggProduct();
                eggProduct.setEggBatchId(eggBatch.getEggBatchId());
                eggProduct.setIncubationPhaseId(incubationPhaseList.get(0).getIncubationPhaseId());
                eggProduct.setIncubationDate(now);
                eggProduct.setAmount(eggWastedAmount);
                eggProduct.setCurAmount(eggWastedAmount);
                eggProduct.setStatus(true);
                EggProduct eggWastedInserted = eggProductRepository.save(eggProduct);
                System.out.println("egg-1:  " + eggWastedInserted);

                // Create Trứng dập - egg0
                eggProduct = new EggProduct();
                eggProduct.setEggBatchId(eggBatch.getEggBatchId());
                eggProduct.setIncubationPhaseId(incubationPhaseList.get(1).getIncubationPhaseId());
                eggProduct.setIncubationDate(now);
                eggProduct.setAmount(amount);
                eggProduct.setCurAmount(amount);
                eggProduct.setStatus(true);
                EggProduct egg0Inserted = eggProductRepository.save(eggProduct);
                System.out.println("egg0:  " + egg0Inserted);

                // Create Trứng đang ấp - egg1
                eggProduct = new EggProduct();
                eggProduct.setEggBatchId(eggBatch.getEggBatchId());
                eggProduct.setIncubationPhaseId(incubationPhaseList.get(2).getIncubationPhaseId());
                eggProduct.setIncubationDate(now);
                eggProduct.setAmount(eggBatchAmount - eggWastedAmount - amount);
                eggProduct.setCurAmount(eggBatchAmount - eggWastedAmount - amount);
                eggProduct.setStatus(true);
                EggProduct egg1Inserted = eggProductRepository.save(eggProduct);
                System.out.println("egg1:  " + egg1Inserted);

                // Create egg location
                for (EggLocationUpdateEggBatchDTO eggLocationDTO : eggLocationDTOs) {
                    if (eggLocationDTO.getAmountUpdate() > 0) {
                        EggLocation eggLocation = new EggLocation();
                        eggLocation.setProductId(egg1Inserted.getProductId());
                        eggLocation.setMachineId(eggLocationDTO.getMachineId());
                        eggLocation.setAmount(eggLocationDTO.getAmountUpdate());
                        eggLocation.setStatus(1);
                        eggLocationRepository.save(eggLocation);
                    }
                }
                // Update current amount of machine
                for (EggLocationUpdateEggBatchDTO eggLocationDTO : eggLocationDTOs) {
                    if (eggLocationDTO.getAmountUpdate() > 0) {
                        Machine machine = machineRepository.findByMachineId(eggLocationDTO.getMachineId()).get();
                        machine.setCurCapacity(machineRepository.getCurrentAmount(machine.getMachineId()));
                    }
                }
            }

            if ((phaseNumber == 0 || phaseNumber == 2 || phaseNumber == 3 || phaseNumber == 4)
                    && progress > phaseNumber) {
                return new ResponseEntity<>("Đã qua giai đoạn cập nhật " +
                        incubationPhaseList.get(phaseNumber + 1).getPhaseDescription(),
                        HttpStatus.BAD_REQUEST);
            }

            // Update before moving to hatching
            // Update Trứng hao hụt (-1), trứng đang nở (1), Trứng trắng (2)/trứng loãng (3)/trứng lộn (4)
            // Create new (2/3/4) if not exist
            if ((phaseNumber == 0 || phaseNumber == 2 || phaseNumber == 3 || phaseNumber == 4)
                    && progress <= phaseNumber) {
                IncubationPhase incubationPhase = incubationPhaseList.get(phaseNumber + 1);
                // Trứng hao hụt egg-1
                EggProduct eggWasted = eggProductRepository.
                        findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                                incubationPhaseList.get(0).getIncubationPhaseId()).get();

                // Trứng đang ấp egg1
                EggProduct eggIncubating = eggProductRepository.
                        findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                                incubationPhaseList.get(2).getIncubationPhaseId()).get();

                // Check amounts
                if (amount > eggIncubating.getAmount()) {
                    return new ResponseEntity<>("Số " + incubationPhase.getPhaseDescription() + " cập nhật " +
                            "không được lớn hơn số trứng đang ấp", HttpStatus.BAD_REQUEST);
                }
                if (eggWastedAmount + amount > eggIncubating.getAmount()) {
                    return new ResponseEntity<>("Số lượng trứng không hợp lệ. Tổng số Trứng hư tổn, hao hụt mới" +
                            " và " + incubationPhase.getPhaseDescription()
                            + " phải nhỏ hơn hoặc bằng số Trứng đang ấp",
                            HttpStatus.BAD_REQUEST);
                }
                // Check machine list
                if (eggLocationDTOs.isEmpty()) {
                    return new ResponseEntity<>("Chưa cập nhật vị trí trứng", HttpStatus.BAD_REQUEST);
                }
                List<CreateEggBatchDTO> eggBatchDupList = new ArrayList<>();
                Set inputSet = new HashSet(eggLocationDTOs);
                if (inputSet.size() < eggLocationDTOs.size()) {
                    return new ResponseEntity<>("Có máy bị trùng lặp", HttpStatus.BAD_REQUEST);
                }
                // Check total eggs in list Machines
                int total = 0;
                for (EggLocationUpdateEggBatchDTO eggLocationDTO : eggLocationDTOs) {
                    Machine machine = machineRepository.findByMachineId(eggLocationDTO.getMachineId()).get();
                    if (machine.getMachineTypeId() == 2) {
                        return new ResponseEntity<>("Chỉ được chọn máy ấp trong giai đoạn này",
                                HttpStatus.BAD_REQUEST);
                    }
                    if (eggLocationDTO.getAmountUpdate() < 0
                            || eggLocationDTO.getAmountUpdate() > (eggLocationDTO.getCapacity() - eggLocationDTO.getAmountCurrent())
                            || eggLocationDTO.getAmountUpdate() > eggIncubating.getAmount()) {
                        return new ResponseEntity<>("Số lượng trứng trong danh sách máy không hợp lệ",
                                HttpStatus.BAD_REQUEST);
                    }
                    total += eggLocationDTO.getAmountUpdate();
                }
                if (total != eggIncubating.getAmount() - eggWastedAmount - amount) {
                    return new ResponseEntity<>("Số lượng trứng trong danh sách máy không hợp lệ. " +
                            "Tổng số trứng trong danh sách máy phải bằng số Trứng đang ấp trước đó trừ đi tổng số " +
                            "Trứng hư tổn, hao hụt và " + incubationPhase.getPhaseDescription(),
                            HttpStatus.BAD_REQUEST);
                }
                // Update Trứng hao hụt - egg-1
                eggWasted.setAmount(eggWasted.getAmount() + eggWastedAmount);
                eggWasted.setCurAmount(eggWasted.getCurAmount() + eggWastedAmount);
                EggProduct eggWastedInserted = eggProductRepository.save(eggWasted);

                // Update Egg according to phase
                if (eggProductOptional.isPresent()) { // Existed
                    eggProduct = eggProductOptional.get();
                    eggProduct.setAmount(eggProduct.getAmount() + amount);
                    eggProduct.setCurAmount(eggProduct.getCurAmount() + amount);
                } else { // First time create phase
                    eggProduct = new EggProduct();
                    eggProduct.setEggBatchId(eggBatch.getEggBatchId());
                    eggProduct.setIncubationPhaseId(incubationPhaseList.get(phaseNumber + 1).getIncubationPhaseId());
                    eggProduct.setIncubationDate(now);
                    eggProduct.setAmount(amount);
                    eggProduct.setCurAmount(amount);
                    eggProduct.setStatus(true);
                }
                eggProductRepository.save(eggProduct);

                // Update Trứng đang ấp - egg1
                eggIncubating.setAmount(eggIncubating.getAmount() - eggWastedAmount - amount);
                eggIncubating.setCurAmount(eggIncubating.getCurAmount() - eggWastedAmount - amount);
                EggProduct egg1Inserted = eggProductRepository.save(eggIncubating);

                // Update egg location
                List<EggLocation> eggLocations = eggLocationRepository
                        .findByProductId(eggIncubating.getProductId()).get();
                eggLocationRepository.deleteAll(eggLocations);

                for (EggLocationUpdateEggBatchDTO eggLocationDTO : eggLocationDTOs) {
                    if (eggLocationDTO.getAmountUpdate() > 0) {
                        EggLocation eggLocation = new EggLocation();
                        eggLocation.setProductId(eggIncubating.getProductId());
                        eggLocation.setMachineId(eggLocationDTO.getMachineId());
                        eggLocation.setAmount(eggLocationDTO.getAmountUpdate());
                        eggLocation.setStatus(1);
                        eggLocationRepository.save(eggLocation);
                    }
                }
                // Update current amount of machine
                for (EggLocationUpdateEggBatchDTO eggLocationDTO : eggLocationDTOs) {
                    if (eggLocationDTO.getAmountUpdate() > 0) {
                        Machine machine = machineRepository.findByMachineId(eggLocationDTO.getMachineId()).get();
                        machine.setCurCapacity(machineRepository.getCurrentAmount(machine.getMachineId()));
                    }
                }
            }


            // Moving to hatching machine
            // Update Trứng hao hụt (-1)
            // Update trứng đang ấp (1)
            // Create/Update trứng đang nở (5)
            if (phaseNumber == 5) {
                if (progress <= 0) {
                    return new ResponseEntity<>("Chưa đến giai đoạn cập nhật Trứng đang nở", HttpStatus.BAD_REQUEST);
                }
                if (progress > 5) {
                    return new ResponseEntity<>("Đã qua giai đoạn cập nhật Trứng đang nở", HttpStatus.BAD_REQUEST);
                }

                IncubationPhase incubationPhase = incubationPhaseList.get(phaseNumber + 1);
                // Trứng hao hụt
                EggProduct eggWasted = eggProductRepository.
                        findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                                incubationPhaseList.get(2).getIncubationPhaseId()).get();

                // Trứng đang ấp
                EggProduct eggIncubating = eggProductRepository.
                        findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                                incubationPhaseList.get(2).getIncubationPhaseId()).get();
                if (progress == 5 && eggIncubating.getAmount() == 0) {
                    return new ResponseEntity<>("Đã qua giai đoạn cập nhật Trứng đang nở", HttpStatus.BAD_REQUEST);
                }
                // Check amounts
                if (amount > eggIncubating.getAmount()) {
                    return new ResponseEntity<>("Số " + incubationPhase.getPhaseDescription() + " cập nhật " +
                            "không được lớn hơn số trứng đang ấp", HttpStatus.BAD_REQUEST);
                }

                if (eggWastedAmount + amount > eggIncubating.getAmount()) {
                    return new ResponseEntity<>(
                            "Số trứng không hợp lệ. Tổng số Trứng hư tổn, hao hụt mới và " +
                                    incubationPhase.getPhaseDescription() + "nhỏ hơn hoặc bằng số Trứng đang ấp cũ",
                            HttpStatus.BAD_REQUEST);
                }

                // Check machine list
                if (eggLocationDTOs.isEmpty()) {
                    return new ResponseEntity<>("Chưa cập nhật vị trí trứng", HttpStatus.BAD_REQUEST);
                }
                boolean onlyHatching = true;
                for (EggLocationUpdateEggBatchDTO eggLocationDTO : eggLocationDTOs) {
                    Machine machine = machineRepository.findByMachineId(eggLocationDTO.getMachineId()).get();
                    if (machine.getMachineTypeId() == 1) {
                        onlyHatching = false;
                        break;
                    }
                }

                // Chưa chuyển hết sang nở, chọn cả máy ấp, nở
                if (amount < eggIncubating.getAmount()) {
                    if (onlyHatching) {
                        return new ResponseEntity<>("Chưa chuyển hết sang nở, phải chọn cả máy ấp và máy nở",
                                HttpStatus.BAD_REQUEST);
                    }
                }
                // Chuyển hết sang nở, chỉ chọn máy nở
                if (amount == eggIncubating.getAmount()) {
                    if (!onlyHatching) {
                        return new ResponseEntity<>("Chuyển sang nở toàn bộ trứng, chỉ được chọn máy nở",
                                HttpStatus.BAD_REQUEST);
                    }
                }

                // Check total eggs in list Machines
                int totalIncubating = 0;
                int totalHatching = 0;
                for (EggLocationUpdateEggBatchDTO eggLocationDTO : eggLocationDTOs) {
                    if (eggProductOptional.isEmpty()) {
                        if (eggLocationDTO.getAmountUpdate() < 0
                                || eggLocationDTO.getAmountUpdate() >
                                (eggLocationDTO.getCapacity() - eggLocationDTO.getAmountCurrent())
                                || eggLocationDTO.getAmountUpdate() > eggIncubating.getAmount()) {
                            return new ResponseEntity<>("Số lượng trứng trong danh sách máy không hợp lệ1",
                                    HttpStatus.BAD_REQUEST);
                        }
                    } else {
                        eggProduct = eggProductOptional.get();
                        if (eggLocationDTO.getAmountUpdate() < 0
                                || eggLocationDTO.getAmountUpdate() >
                                (eggLocationDTO.getCapacity() - eggLocationDTO.getAmountCurrent())
                                || eggLocationDTO.getAmountUpdate() >
                                (eggIncubating.getAmount() + amount + eggProduct.getAmount())) {
                            return new ResponseEntity<>("Số lượng trứng trong danh sách máy không hợp lệ2",
                                    HttpStatus.BAD_REQUEST);
                        }
                    }

                    Machine machine = machineRepository.findByMachineId(eggLocationDTO.getMachineId()).get();
                    if (machine.getMachineTypeId() == 1) {
                        totalIncubating += eggLocationDTO.getAmountUpdate();
                    }
                    if (machine.getMachineTypeId() == 2) {
                        totalHatching += eggLocationDTO.getAmountUpdate();
                    }
                }
                // tạo mới
                if (eggProductOptional.isEmpty()) {
                    if (totalHatching != amount) {
                        return new ResponseEntity<>("Số lượng trứng trong danh sách máy không hợp lệ. " +
                                "Tổng số trứng trong danh sách máy nở phải bằng số trứng chuyển sang nở",
                                HttpStatus.BAD_REQUEST);
                    }
                    // Chưa chuyển sang nở hết
                    if (amount < eggIncubating.getAmount()) {
                        if (totalIncubating + totalHatching != eggIncubating.getAmount() - eggWastedAmount) {
                            return new ResponseEntity<>("Số lượng trứng trong danh sách máy không hợp lệ. " +
                                    "Tổng số trứng trong danh sách máy (đang ấp và nở) phải bằng số trứng đang ấp trước đó " +
                                    "trừ đi tổng số Trứng hư tổn, hao hụt cập nhật thêm",
                                    HttpStatus.BAD_REQUEST);
                        }
                    }
                    // Chuyển sang nở hết
                    if (amount == eggIncubating.getAmount()) {
                        if (totalHatching != eggIncubating.getAmount() - eggWastedAmount) {
                            return new ResponseEntity<>("Số lượng trứng trong danh sách máy không hợp lệ. " +
                                    "Tổng số trứng trong danh sách máy nở phải bằng số trứng chuyển sang nở",
                                    HttpStatus.BAD_REQUEST);
                        }
                    }
                } else {
                    eggProduct = eggProductOptional.get();
                    if (totalHatching != eggProduct.getAmount() + amount) {
                        return new ResponseEntity<>("Số lượng trứng trong danh sách máy không hợp lệ. " +
                                "Tổng số trứng trong danh sách máy nở phải bằng số trứng chuyển sang nở sau khi cập nhật",
                                HttpStatus.BAD_REQUEST);
                    }
                    // Chưa chuyển sang nở hết
                    if (amount < eggIncubating.getAmount()) {
                        if (totalIncubating + totalHatching != eggIncubating.getAmount() + eggProduct.getAmount()
                                - eggWastedAmount) {
                            return new ResponseEntity<>("Số lượng trứng trong danh sách máy không hợp lệ. " +
                                    "Tổng số trứng trong danh sách máy (đang ấp/nở) phải bằng số trứng đang ấp trước đó " +
                                    "trừ đi tổng số Trứng hư tổn, hao hụt sau khi cập nhật",
                                    HttpStatus.BAD_REQUEST);
                        }
                    }
                }

                // Update Trứng hao hụt - egg-1
                eggWasted.setAmount(eggWasted.getAmount() + eggWastedAmount);
                eggWasted.setCurAmount(eggWasted.getCurAmount() + eggWastedAmount);
                EggProduct eggWastedInserted = eggProductRepository.save(eggWasted);

                // Create/Update Trứng đang ấp - egg5
                if (eggProductOptional.isEmpty()) {
                    // Create Trứng đang ấp - egg5
                    eggProduct = new EggProduct();
                    eggProduct.setEggBatchId(eggBatch.getEggBatchId());
                    eggProduct.setIncubationPhaseId(incubationPhaseList.get(phaseNumber + 1).getIncubationPhaseId());
                    eggProduct.setIncubationDate(now);
                    eggProduct.setAmount(amount);
                    eggProduct.setCurAmount(amount);
                    eggProduct.setStatus(true);
                } else { // Update
                    eggProduct = eggProductOptional.get();
                    eggProduct.setAmount(eggProduct.getAmount() + amount);
                    eggProduct.setCurAmount(eggProduct.getCurAmount() + amount);
                    eggProduct.setStatus(true);
                }
                EggProduct eggHatching = eggProductRepository.save(eggProduct);

                // Update Trứng đang nở - egg1
                eggIncubating.setAmount(eggIncubating.getAmount() - amount - eggWastedAmount);
                eggIncubating.setCurAmount(eggIncubating.getCurAmount() - amount - eggWastedAmount);
                eggProductRepository.save(eggIncubating);

                // Update egg location
                List<EggLocation> eggLocations = eggLocationRepository
                        .findByProductId(eggIncubating.getProductId()).get();
                eggLocationRepository.deleteAll(eggLocations);

                eggLocations = eggLocationRepository
                        .findByProductId(eggHatching.getProductId()).get();
                eggLocationRepository.deleteAll(eggLocations);

                for (EggLocationUpdateEggBatchDTO eggLocationDTO : eggLocationDTOs) {
                    if (eggLocationDTO.getAmountUpdate() > 0) {
                        EggLocation eggLocation = new EggLocation();
                        if (eggLocationDTO.getMachineTypeId() == 1L) {
                            eggLocation.setProductId(eggIncubating.getProductId());
                        } else {
                            eggLocation.setProductId(eggHatching.getProductId());
                        }
                        eggLocation.setMachineId(eggLocationDTO.getMachineId());
                        eggLocation.setAmount(eggLocationDTO.getAmountUpdate());
                        eggLocation.setStatus(1);
                        eggLocationRepository.save(eggLocation);
                    }
                }
                // Update current amount of machine
                for (EggLocationUpdateEggBatchDTO eggLocationDTO : eggLocationDTOs) {
                    if (eggLocationDTO.getAmountUpdate() > 0) {
                        Machine machine = machineRepository.findByMachineId(eggLocationDTO.getMachineId()).get();
                        machine.setCurCapacity(machineRepository.getCurrentAmount(machine.getMachineId()));
                    }
                }
            }

            // Update sau khi gà nở
            // Update Trứng hao hụt (-1)
            // Remove trứng đang nở (5)
            // Create trứng tắc (6), con nở (7)
            if (phaseNumber == 6 && eggProductOptional.isPresent()) {
                return new ResponseEntity<>("Đã qua giai đoạn cập nhật Trứng tắc", HttpStatus.BAD_REQUEST);
            }
            if (phaseNumber == 6 && eggProductOptional.isEmpty()) {
                if (progress <= 4) {
                    return new ResponseEntity<>("Chưa đến giai đoạn cập nhật Trứng tắc", HttpStatus.BAD_REQUEST);
                }
                if (progress >= 7) {
                    return new ResponseEntity<>("Đã qua giai đoạn cập nhật Trứng tắc", HttpStatus.BAD_REQUEST);
                }
                IncubationPhase incubationPhase = incubationPhaseList.get(phaseNumber + 1);

                // Trứng hao hụt
                EggProduct eggWasted = eggProductRepository.
                        findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                                incubationPhaseList.get(0).getIncubationPhaseId()).get();

                // Trứng đang ấp
                EggProduct eggIncubating = eggProductRepository.
                        findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                                incubationPhaseList.get(2).getIncubationPhaseId()).get();

                // Trứng đang nở
                EggProduct eggHatching = eggProductRepository.
                        findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                                incubationPhaseList.get(6).getIncubationPhaseId()).get();

                // Check đã chuyển sang nở hết
                if (eggIncubating.getAmount() > 0) {
                    return new ResponseEntity<>("Vẫn còn trứng trong máy ấp, không thể cập nhật trứng Tắc", HttpStatus.BAD_REQUEST);
                }

                // Check amounts
                if (amount > eggHatching.getAmount()) {
                    return new ResponseEntity<>("Số " + incubationPhase.getPhaseDescription() + " cập nhật " +
                            "không được lớn hơn số trứng đang nở", HttpStatus.BAD_REQUEST);
                }
                if (eggWastedAmount + amount > eggHatching.getAmount()) {
                    return new ResponseEntity<>(
                            "Số trứng không hợp lệ. Tổng số Trứng hư tổn, hao hụt mới và " +
                                    incubationPhase.getPhaseDescription() + "nhỏ hơn hoặc bằng số Trứng đang nở cũ",
                            HttpStatus.BAD_REQUEST);
                }

                // Update Trứng hao hụt - egg-1
                eggWasted.setAmount(eggWasted.getAmount() + eggWastedAmount);
                eggWasted.setCurAmount(eggWasted.getCurAmount() + eggWastedAmount);
                EggProduct eggWastedInserted = eggProductRepository.save(eggWasted);

                // Create Trứng tắc - egg6
                eggProduct = new EggProduct();
                eggProduct.setEggBatchId(eggBatch.getEggBatchId());
                eggProduct.setIncubationPhaseId(incubationPhaseList.get(phaseNumber + 1).getIncubationPhaseId());
                eggProduct.setIncubationDate(now);
                eggProduct.setAmount(amount);
                eggProduct.setCurAmount(amount);
                eggProduct.setStatus(true);
                EggProduct eggDied = eggProductRepository.save(eggProduct);

                // Create Con nở - egg7
                eggProduct = new EggProduct();
                eggProduct.setEggBatchId(eggBatch.getEggBatchId());
                eggProduct.setIncubationPhaseId(incubationPhaseList.get(8).getIncubationPhaseId());
                eggProduct.setIncubationDate(now);
                eggProduct.setAmount(eggHatching.getAmount() - eggWastedAmount - amount);
                eggProduct.setCurAmount(eggHatching.getCurAmount() - eggWastedAmount - amount);
                eggProduct.setStatus(true);
                EggProduct eggHatched = eggProductRepository.save(eggProduct);

                // Remove Trứng đang nở - egg5
                eggHatching.setAmount(0);
                eggHatching.setCurAmount(0);
                eggProductRepository.save(eggHatching);

                // Remove egg location
                List<EggLocation> eggLocations = eggLocationRepository
                        .findByProductId(eggHatching.getProductId()).get();
                eggLocationRepository.deleteAll(eggLocations);
            }

            // Update sau khi phân loại Đực/Cái
            // Update Trứng hao hụt (-1), Con nở (7) , Con đực (8),
            if (phaseNumber == 8) {
                if (progress <= 6) {
                    return new ResponseEntity<>("Chưa đến giai đoạn cập nhật Con đực", HttpStatus.BAD_REQUEST);
                }

                // Trứng hao hụt
                EggProduct eggWasted = eggProductRepository.
                        findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                                incubationPhaseList.get(0).getIncubationPhaseId()).get();

                // Con nở egg7
                EggProduct eggHatched = eggProductRepository.
                        findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                                incubationPhaseList.get(8).getIncubationPhaseId()).get();
                int hatchedAmount = eggHatched.getCurAmount();
                if (hatchedAmount == 0) {
                    return new ResponseEntity<>("Đã phân biệt đực/cái hết số con nở", HttpStatus.BAD_REQUEST);
                }
                // Check amounts
                if (amount > hatchedAmount) {
                    return new ResponseEntity<>("Số Con đực không được vượt quá số lượng Con nở",
                            HttpStatus.BAD_REQUEST);
                }
                if (amount + eggWastedAmount > hatchedAmount) {
                    return new ResponseEntity<>("Tổng số Con đực và Trứng hao hụt mới không được" +
                            " vượt quá số lượng Con nở", HttpStatus.BAD_REQUEST);
                }
                // Update Trứng hao hụt - egg-1
                eggWasted.setAmount(eggWasted.getAmount() + eggWastedAmount);
                eggWasted.setCurAmount(eggWasted.getCurAmount() + eggWastedAmount);
                EggProduct eggWastedInserted = eggProductRepository.save(eggWasted);

                if (eggProductOptional.isEmpty()) {
                    // Create Con đực - egg8
                    eggProduct = new EggProduct();
                    eggProduct.setEggBatchId(eggBatch.getEggBatchId());
                    eggProduct.setIncubationPhaseId(incubationPhaseList.get(phaseNumber + 1).getIncubationPhaseId());
                    eggProduct.setIncubationDate(now);
                    eggProduct.setAmount(amount);
                    eggProduct.setCurAmount(amount);
                    eggProduct.setStatus(true);
                    EggProduct male = eggProductRepository.save(eggProduct);

                    // Update  Con nở - egg7
                    eggHatched.setAmount(hatchedAmount - amount - eggWastedAmount);
                    eggHatched.setCurAmount(hatchedAmount - amount - eggWastedAmount);
                    eggHatched = eggProductRepository.save(eggHatched);
                    // Set done
                    if (hatchedAmount - amount - eggWastedAmount == 0) {
                        eggBatch.setStatus(0);
                        eggBatchRepository.save(eggBatch);
                    }

                } else {
                    // Update Con đực - egg8
                    eggProduct = eggProductOptional.get();
                    eggProduct.setAmount(eggProduct.getAmount() + amount);
                    eggProduct.setCurAmount(eggProduct.getCurAmount() + amount);
                    EggProduct male = eggProductRepository.save(eggProduct);
                    // Update  Con nở - egg7
                    eggHatched.setAmount(hatchedAmount - amount - eggWastedAmount);
                    eggHatched.setCurAmount(hatchedAmount - amount - eggWastedAmount);
                    eggProductRepository.save(eggHatched);
                    // Set done
                    if (hatchedAmount - amount - eggWastedAmount == 0) {
                        eggBatch.setStatus(0);
                        eggBatchRepository.save(eggBatch);
                    }
                }
                return new ResponseEntity<>("Cập nhật lô trứng thành công", HttpStatus.OK);
            }

            // Update sau khi phân loại Đực/Cái
            // Update Trứng hao hụt (-1), Con nở (7), Con cái (9),
            if (phaseNumber == 9) {
                if (progress <= 6) {
                    return new ResponseEntity<>("Chưa đến giai đoạn cập nhật Trứng hao hụt", HttpStatus.BAD_REQUEST);
                }

                // Trứng hao hụt
                EggProduct eggWasted = eggProductRepository.
                        findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                                incubationPhaseList.get(0).getIncubationPhaseId()).get();

                // Con nở egg7
                EggProduct eggHatched = eggProductRepository.
                        findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                                incubationPhaseList.get(8).getIncubationPhaseId()).get();
                int hatchedAmount = eggHatched.getCurAmount();
                if (hatchedAmount == 0) {
                    return new ResponseEntity<>("Đã phân biệt đực/cái hết số con nở", HttpStatus.BAD_REQUEST);
                }
                // Check amounts
                if (amount > hatchedAmount) {
                    return new ResponseEntity<>("Số Con cái không được vượt quá số lượng Con nở",
                            HttpStatus.BAD_REQUEST);
                }
                if (amount + eggWastedAmount > hatchedAmount) {
                    return new ResponseEntity<>("Tổng số Con cái và Trứng hao hụt mới không được" +
                            " vượt quá số lượng Con nở", HttpStatus.BAD_REQUEST);
                }
                // Update Trứng hao hụt - egg-1
                eggWasted.setAmount(eggWasted.getAmount() + eggWastedAmount);
                eggWasted.setCurAmount(eggWasted.getCurAmount() + eggWastedAmount);
                EggProduct eggWastedInserted = eggProductRepository.save(eggWasted);

                if (eggProductOptional.isEmpty()) {
                    // Create Con cái - egg9
                    eggProduct = new EggProduct();
                    eggProduct.setEggBatchId(eggBatch.getEggBatchId());
                    eggProduct.setIncubationPhaseId(incubationPhaseList.get(phaseNumber + 1).getIncubationPhaseId());
                    eggProduct.setIncubationDate(now);
                    eggProduct.setAmount(amount);
                    eggProduct.setCurAmount(amount);
                    eggProduct.setStatus(true);
                    EggProduct female = eggProductRepository.save(eggProduct);

                    // Update  Con nở - egg7
                    eggHatched.setAmount(hatchedAmount - amount - eggWastedAmount);
                    eggHatched.setCurAmount(hatchedAmount - amount - eggWastedAmount);
                    eggProductRepository.save(eggHatched);
                    // Set done
                    if (hatchedAmount - amount - eggWastedAmount == 0) {
                        eggBatch.setStatus(0);
                        eggBatchRepository.save(eggBatch);
                    }
                } else {
                    // Update Con đực - egg9
                    eggProduct = eggProductOptional.get();
                    eggProduct.setAmount(eggProduct.getAmount() + amount);
                    eggProduct.setCurAmount(eggProduct.getCurAmount() + amount);
                    EggProduct female = eggProductRepository.save(eggProduct);
                    // Update  Con nở - egg7
                    eggHatched.setAmount(hatchedAmount - amount - eggWastedAmount);
                    eggHatched.setCurAmount(hatchedAmount - amount - eggWastedAmount);
                    eggProductRepository.save(eggHatched);
                    // Set done
                    if (hatchedAmount - amount - eggWastedAmount == 0) {
                        eggBatch.setStatus(0);
                        eggBatchRepository.save(eggBatch);
                    }
                }
            }
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>(iae.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Cập nhật lô trứng thành công", HttpStatus.OK);
    }

    /**
     * Update egg batch's state.
     *
     * @param eggBatchId the id of egg batch
     * @param done       boolean value for state
     * @return
     */
    @Override
    public ResponseEntity<?> setDoneEggBatch(Long eggBatchId, boolean done) {
        // Get incubation phase list
        List<IncubationPhase> incubationPhaseList = incubationPhaseRepository.
                getListIncubationPhaseForEggBatch(eggBatchId);
        // List item (incubationPhaseId)    | 0| 1| 2| 3| 4| 5| 6| 7| 8| 9| 10|
        // phase number                     |-1| 0| 1| 2| 3| 4| 5| 6| 7| 8| 9 |

        // Egg Batch
        Optional<EggBatch> eggBatchOptional = eggBatchRepository.findByEggBatchId(eggBatchId);
        if (eggBatchOptional.isEmpty()) {
            return new ResponseEntity<>("Không tìm thấy lô trứng", HttpStatus.BAD_REQUEST);
        }
        EggBatch eggBatch = eggBatchOptional.get();

        // Get latest phase
        int progress = 0;
        Optional<EggProduct> eggProductLastPhaseOptional = eggProductRepository.
                findEggProductLastPhase(eggBatch.getEggBatchId());
        if (eggProductLastPhaseOptional.isPresent()) {
            Long incubationPhaseId = eggProductLastPhaseOptional.get().getIncubationPhaseId();
            IncubationPhase phase = incubationPhaseRepository.findByIncubationPhaseId(incubationPhaseId).get();
            progress = phase.getPhaseNumber();
        }
        if (done && eggBatch.getStatus() == 1) {
            // Chưa ấp
            if (progress == 0) {
                return new ResponseEntity<>("Lô trứng chưa được ấp, không thể cập nhật hoàn thành cho lô trứng",
                        HttpStatus.BAD_REQUEST);
            }
            // Trứng đang ấp egg1
            Optional<EggProduct> egg1Optional = eggProductRepository.
                    findByEggBatchIdAndIncubationPhaseId(
                            eggBatch.getEggBatchId(),
                            incubationPhaseList.get(2).getIncubationPhaseId());
            // Vẫn còn trứng đang ấp
            if (progress > 0 && egg1Optional.get().getAmount() != 0) {
                return new ResponseEntity<>("Vẫn còn trứng đang ấp, không thể cập nhật hoàn thành cho lô trứng",
                        HttpStatus.BAD_REQUEST);
            }
            // Trứng đang nở egg5
            Optional<EggProduct> egg5Optional = eggProductRepository.
                    findByEggBatchIdAndIncubationPhaseId(
                            eggBatch.getEggBatchId(),
                            incubationPhaseList.get(6).getIncubationPhaseId());
            // Vẫn còn trứng đang nở
            if (progress > 4 && egg5Optional.get().getAmount() != 0) {
                return new ResponseEntity<>("Vẫn còn trứng đang nở, không thể cập nhật hoàn thành cho lô trứng",
                        HttpStatus.BAD_REQUEST);
            }

            // Set status
            eggBatch.setStatus(0);
            eggBatchRepository.save(eggBatch);
        }
        if (!done && eggBatch.getStatus() == 0) {
            // Set status
            eggBatch.setStatus(1);
            eggBatchRepository.save(eggBatch);
            return new ResponseEntity<>("Đã hủy hoàn thành lô trứng", HttpStatus.OK);
        }
        return new ResponseEntity<>("Đã hoàn thành lô trứng", HttpStatus.OK);
    }
}
