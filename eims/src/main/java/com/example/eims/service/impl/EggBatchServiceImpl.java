/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 15/03/2023   1.0         DuongVV     First Deploy<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.eggBatch.EggBatchDetailDTO;
import com.example.eims.dto.eggBatch.EggBatchListItemDTO;
import com.example.eims.dto.eggBatch.UpdateEggBatchDTO;
import com.example.eims.dto.eggLocation.EggLocationEggBatchDetailDTO;
import com.example.eims.dto.eggLocation.EggLocationUpdateEggBatchDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private final StringDealer stringDealer;

    public EggBatchServiceImpl(EggBatchRepository eggBatchRepository, ImportReceiptRepository importReceiptRepository,
                               SupplierRepository supplierRepository, SpecieRepository specieRepository,
                               BreedRepository breedRepository, EggProductRepository eggProductRepository,
                               EggLocationRepository eggLocationRepository, MachineRepository machineRepository,
                               IncubationPhaseRepository incubationPhaseRepository) {
        this.eggBatchRepository = eggBatchRepository;
        this.importReceiptRepository = importReceiptRepository;
        this.supplierRepository = supplierRepository;
        this.specieRepository = specieRepository;
        this.breedRepository = breedRepository;
        this.eggProductRepository = eggProductRepository;
        this.eggLocationRepository = eggLocationRepository;
        this.machineRepository = machineRepository;
        this.incubationPhaseRepository = incubationPhaseRepository;
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
            List<EggProduct> eggProductList = eggProductRepository.findByEggBatchId(eggBatchId).get();
            dto.setEggProductList(eggProductList);

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

            // Machine list
            List<EggLocationEggBatchDetailDTO> machineList = new ArrayList<>();
            EggLocationEggBatchDetailDTO dto1;

            for (EggProduct eggProduct : eggProductList) {
                IncubationPhase incubationPhase = incubationPhaseRepository
                        .findByIncubationPhaseId(eggProduct.getIncubationPhaseId()).get();

                if ((incubationPhase.getPhaseNumber() == 1 && eggProduct.getCurAmount() > 0)
                        || (incubationPhase.getPhaseNumber() == 5)) { // Chỉ lấy product trứng đang ấp hoặc nở
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
                            dto1.setAmount(eggLocation.getAmount());
                            machineList.add(dto1);
                        }
                    }
                }
            }

            dto.setMachineList(machineList);

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
        // Get incubation list
        List<IncubationPhase> incubationPhaseList = incubationPhaseRepository.
                getListIncubationPhaseForEggBatch(updateEggBatchDTO.getEggBatchId());
        // Get eggs amount
        int eggWastedAmount = updateEggBatchDTO.getEggWasted();
        int amount = updateEggBatchDTO.getAmount();
        EggBatch eggBatch = eggBatchRepository.findByEggBatchId(updateEggBatchDTO.getEggBatchId()).get();
        int eggBatchAmount = eggBatch.getAmount();

        // List item (incubationPhaseId)    | 0| 1| 2| 3| 4| 5| 6| 7| 8| 9| 10|
        // phase number                     |-1| 0| 1| 2| 3| 4| 5| 6| 7| 8| 9 |

        // Get phase number of egg to insert
        int phase = updateEggBatchDTO.getPhase();
        LocalDateTime now = LocalDateTime.now();
        // Get Machine list
        List<EggLocationUpdateEggBatchDTO> eggLocationDTOs = updateEggBatchDTO.getEggLocationUpdateEggBatchDTOS();

        /*Optional<List<EggProduct>> eggProductListOptional = eggProductRepository.
                findByEggBatchId(eggBatch.getEggBatchId());*/
        try {
            // Find current egg product to update
            EggProduct eggProduct;
            Optional<EggProduct> eggProductOptional = eggProductRepository.
                    findByEggBatchIdAndIncubationPhaseId(
                            eggBatch.getEggBatchId(),
                            incubationPhaseList.get(phase + 1).getIncubationPhaseId());
            // First update, insert egg to incubating machine
            // Create Trứng hao hụt (-1), Trứng dập (0), trứng đang ấp (1)
            Optional<List<EggProduct>> listOptional = eggProductRepository.
                    findByEggBatchId(eggBatch.getEggBatchId());
            if (phase == 0 && listOptional.get().isEmpty()) { // Phase = 0, no egg product
                // Update Trứng hao hụt - egg-1 (create new)
                eggProduct = new EggProduct();
                eggProduct.setEggBatchId(eggBatch.getEggBatchId());
                eggProduct.setIncubationPhaseId(incubationPhaseList.get(0).getIncubationPhaseId());
                eggProduct.setIncubationDate(now);
                eggProduct.setAmount(eggWastedAmount);
                eggProduct.setCurAmount(eggWastedAmount);
                eggProduct.setStatus(true);
                EggProduct eggWastedInserted = eggProductRepository.save(eggProduct);
                System.out.println("egg-1:  " + eggWastedInserted);

                // Update Trứng dập - egg0 (create new)
                eggProduct = new EggProduct();
                eggProduct.setEggBatchId(eggBatch.getEggBatchId());
                eggProduct.setIncubationPhaseId(incubationPhaseList.get(1).getIncubationPhaseId());
                eggProduct.setIncubationDate(now);
                eggProduct.setAmount(amount);
                eggProduct.setCurAmount(amount);
                eggProduct.setStatus(true);
                EggProduct egg0Inserted = eggProductRepository.save(eggProduct);
                System.out.println("egg0:  " + egg0Inserted);

                // Update Trứng đang ấp - egg1 (create new)
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
                return new ResponseEntity<>("Cập nhật lô trứng thành công", HttpStatus.OK);
            }

            // Update before moving to hatching
            // Update Trứng hao hụt (-1), trứng đang nở (1), Trứng trắng (2)/trứng loãng (3)/trứng lộn (4)
            // Create new (2/3/4) if not exist
            if (phase == 2 || phase == 3 || phase == 4) {
                // Update Trứng hao hụt - egg-1
                EggProduct eggWasted = eggProductRepository.
                        findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                                incubationPhaseList.get(0).getIncubationPhaseId()).get();
                eggWasted.setAmount(eggWasted.getAmount() + eggWastedAmount);
                eggWasted.setCurAmount(eggWasted.getCurAmount() + eggWastedAmount);
                EggProduct eggWastedInserted = eggProductRepository.save(eggWasted);

                // Update Egg according to phase
                if (eggProductOptional.isPresent()) {
                    eggProduct = eggProductOptional.get();
                    eggProduct.setAmount(eggProduct.getAmount() + amount);
                    eggProduct.setCurAmount(eggProduct.getCurAmount() + amount);
                } else { // First time, create phase
                    eggProduct = new EggProduct();
                    eggProduct.setEggBatchId(eggBatch.getEggBatchId());
                    eggProduct.setIncubationPhaseId(incubationPhaseList.get(phase + 1).getIncubationPhaseId());
                    eggProduct.setIncubationDate(now);
                    eggProduct.setAmount(amount);
                    eggProduct.setCurAmount(amount);
                    eggProduct.setStatus(true);
                }
                eggProductRepository.save(eggProduct);

                // Update Trứng đang ấp - egg1
                EggProduct eggIncubating = eggProductRepository.
                        findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                                incubationPhaseList.get(2).getIncubationPhaseId()).get();
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
                return new ResponseEntity<>("Cập nhật lô trứng thành công", HttpStatus.OK);
            }

            // Moving to hatching machine
            // Update Trứng hao hụt (-1)
            // Remove trứng đang ấp (1)
            // Create trứng đang nở (5)
            if (phase == 5 && eggProductOptional.isEmpty()) {
                // Update Trứng hao hụt - egg-1
                EggProduct eggWasted = eggProductRepository.
                        findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                                incubationPhaseList.get(2).getIncubationPhaseId()).get();
                eggWasted.setAmount(eggWasted.getAmount() + eggWastedAmount);
                eggWasted.setCurAmount(eggWasted.getCurAmount() + eggWastedAmount);
                EggProduct eggWastedInserted = eggProductRepository.save(eggWasted);

                // Create Trứng đang ấp - egg5
                eggProduct = new EggProduct();
                eggProduct.setEggBatchId(eggBatch.getEggBatchId());
                eggProduct.setIncubationPhaseId(incubationPhaseList.get(phase + 1).getIncubationPhaseId());
                eggProduct.setIncubationDate(now);
                eggProduct.setAmount(amount);
                eggProduct.setCurAmount(amount);
                eggProduct.setStatus(true);
                EggProduct eggHatching = eggProductRepository.save(eggProduct);

                // Remove Trứng đang nở - egg1
                EggProduct eggIncubating = eggProductRepository.
                        findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                                incubationPhaseList.get(2).getIncubationPhaseId()).get();
                eggIncubating.setCurAmount(0);
                eggProductRepository.save(eggIncubating);

                // Update egg location
                List<EggLocation> eggLocations = eggLocationRepository
                        .findByProductId(eggIncubating.getProductId()).get();
                eggLocationRepository.deleteAll(eggLocations);

                for (EggLocationUpdateEggBatchDTO eggLocationDTO : eggLocationDTOs) {
                    if (eggLocationDTO.getAmountUpdate() > 0) {
                        EggLocation eggLocation = new EggLocation();
                        eggLocation.setProductId(eggHatching.getProductId());
                        eggLocation.setMachineId(eggLocationDTO.getMachineId());
                        eggLocation.setAmount(eggLocationDTO.getAmountUpdate());
                        eggLocation.setStatus(1);
                        eggLocationRepository.save(eggLocation);
                    }
                }
                return new ResponseEntity<>("Cập nhật lô trứng thành công", HttpStatus.OK);
            }

            // Update sau khi gà nở
            // Update Trứng hao hụt (-1)
            // Remove trứng đang nở (5)
            // Create trứng tắc (6), con nở (7)
            if (phase == 6 && eggProductOptional.isEmpty()) {
                // Update Trứng hao hụt - egg-1
                EggProduct eggWasted = eggProductRepository.
                        findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                                incubationPhaseList.get(2).getIncubationPhaseId()).get();

                eggWasted.setAmount(eggWasted.getAmount() + eggWastedAmount);
                eggWasted.setCurAmount(eggWasted.getCurAmount() + eggWastedAmount);
                EggProduct eggWastedInserted = eggProductRepository.save(eggWasted);

                // Remove Trứng đang nở - egg5
                EggProduct eggHatching = eggProductRepository.
                        findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                                incubationPhaseList.get(6).getIncubationPhaseId()).get();
                eggHatching.setCurAmount(0);
                eggProductRepository.save(eggHatching);

                // Create Trứng tắc - egg6
                eggProduct = new EggProduct();
                eggProduct.setEggBatchId(eggBatch.getEggBatchId());
                eggProduct.setIncubationPhaseId(incubationPhaseList.get(phase + 1).getIncubationPhaseId());
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
                eggProduct.setAmount(amount);
                eggProduct.setCurAmount(amount);
                eggProduct.setStatus(true);
                EggProduct eggHatched = eggProductRepository.save(eggProduct);

                // Remove egg location
                for (EggLocationUpdateEggBatchDTO eggLocationDTO : eggLocationDTOs) {
                    EggLocation eggLocation = eggLocationRepository.
                            findByEggLocationId(eggLocationDTO.getEggLocationId()).get();
                    eggLocationRepository.delete(eggLocation);
                }
                return new ResponseEntity<>("Cập nhật lô trứng thành công", HttpStatus.OK);
            }

            // Update sau khi phân loại Đực/Cái
            // Update Trứng hao hụt (-1), Con đực (8) , Con cái (9),
            if (phase == 8 && eggProductOptional.isEmpty()) {
                // Update Trứng hao hụt - egg-1
                EggProduct eggWasted = eggProductRepository.
                        findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                                incubationPhaseList.get(2).getIncubationPhaseId()).get();

                eggWasted.setAmount(eggWasted.getAmount() + eggWastedAmount);
                eggWasted.setCurAmount(eggWasted.getCurAmount() + eggWastedAmount);
                EggProduct eggWastedInserted = eggProductRepository.save(eggWasted);

                // Get Con nở - egg5
                EggProduct eggHatched = eggProductRepository.
                        findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                                incubationPhaseList.get(6).getIncubationPhaseId()).get();
                int hatchedAmount = eggHatched.getCurAmount();

                // Create Con đực - egg8
                eggProduct = new EggProduct();
                eggProduct.setEggBatchId(eggBatch.getEggBatchId());
                eggProduct.setIncubationPhaseId(incubationPhaseList.get(phase + 1).getIncubationPhaseId());
                eggProduct.setIncubationDate(now);
                eggProduct.setAmount(amount);
                eggProduct.setCurAmount(amount);
                eggProduct.setStatus(true);
                EggProduct male = eggProductRepository.save(eggProduct);

                // Create Con cái - egg9
                eggProduct = new EggProduct();
                eggProduct.setEggBatchId(eggBatch.getEggBatchId());
                eggProduct.setIncubationPhaseId(incubationPhaseList.get(phase + 2).getIncubationPhaseId());
                eggProduct.setIncubationDate(now);
                eggProduct.setAmount(hatchedAmount - amount - eggWastedAmount);
                eggProduct.setCurAmount(hatchedAmount - amount - eggWastedAmount);
                eggProduct.setStatus(true);
                EggProduct female = eggProductRepository.save(eggProduct);
                return new ResponseEntity<>("Cập nhật lô trứng thành công", HttpStatus.OK);
            }

        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>(iae.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Something wrong", HttpStatus.BAD_REQUEST);
    }
}

