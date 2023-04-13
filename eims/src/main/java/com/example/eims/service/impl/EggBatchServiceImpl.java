/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 15/03/2023   1.0         DuongVV     First Deploy<br>
 * 21/03/2023   1.1         DuongVV     Update checks<br>
 * 27/03/2023   2.0         DuongVV     Update update egg batch<br>
 * 29/03/2023   2.1         DuongVV     Update update egg batch return message<br>
 * 09/04/2023   2.2         DuongVV     Update functions<br>
 * 13/04/2023   2.3         DuongVV     Update functions<br>
 * 13/04/2023   3.0         DuongVV     Update functions, add update locations egg batch<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.eggBatch.EggBatchDetailDTO;
import com.example.eims.dto.eggBatch.EggBatchViewListItemDTO;
import com.example.eims.dto.eggBatch.UpdateEggBatchDTO;
import com.example.eims.dto.eggBatch.UpdateLocationEggBatchDTO;
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
            dto.setNeedAction(eggBatch.getNeedAction());
            dto.setDateAction(eggBatch.getDateAction());
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

            // Specie
            Specie specie = specieRepository.findById(breed.getSpecieId()).get();
            dto.setSpecieId(breed.getSpecieId());
            dto.setSpecieName(specie.getSpecieName());
            dto.setIncubationPeriod(specie.getIncubationPeriod());

            // Progress in days
            Long progressInDays = 0L;
            LocalDateTime startDate = null;
            // Egg Product
            Optional<List<EggProduct>> eggProductListOptional = eggProductRepository.findByEggBatchId(eggBatchId);
            List<EggProduct> eggProductList;
            if (eggProductListOptional.isEmpty()) {
                dto.setEggProductList(new ArrayList<>());
                dto.setMachineList(new ArrayList<>());
            } else {
                List<EggProduct> list;
                eggProductList = eggProductListOptional.get();
                EggProduct dummy = new EggProduct();
                dummy.setAmount(0);
                dummy.setCurAmount(0);
                // dummy list
                list = Arrays.asList(dummy, dummy, dummy, dummy, dummy, dummy, dummy, dummy, dummy, dummy, dummy, dummy, dummy);
                for (EggProduct eggProduct : eggProductList) {
                    IncubationPhase incubationPhase = incubationPhaseRepository.
                            findByIncubationPhaseId(eggProduct.getIncubationPhaseId()).get();
                    if (incubationPhase.getPhaseNumber() == 1) {
                        startDate = eggProduct.getIncubationDate();
                        progressInDays = stringDealer.dateDiff(
                                Date.valueOf(eggProduct.getIncubationDate().toLocalDate()),
                                Date.valueOf(LocalDate.now()));
                    }
                    list.set(incubationPhase.getPhaseNumber() + 1, eggProduct);
                }
                dto.setStartDate(startDate);
                dto.setProgressInDays(progressInDays);
                dto.setEggProductList(list);

                // Machine list
                List<EggLocationEggBatchDetailDTO> machineList = new ArrayList<>();
                EggLocationEggBatchDetailDTO dto1;
                for (EggProduct eggProduct : eggProductList) {
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
                    MachineType machineType = machineTypeRepository.findByMachineTypeId(machine.getMachineTypeId()).get();
                    String machineTypeName = machineType.getMachineTypeName();
                    MachineListItemDTO machineListItemDTO = new MachineListItemDTO();
                    machineListItemDTO.setMachineTypeName(machineTypeName);
                    machineListItemDTO.getFromEntity(machine);
                    notFullListDto.add(machineListItemDTO);
                }
                dto.setMachineNotFullList(notFullListDto);
            }

            // Progress, phase (incubation phase)
            // Get latest phase
            int progress = 0;
            String phase = "";
            Optional<EggProduct> eggProductOptional = eggProductRepository.
                    findEggProductLastPhase(eggBatchId);
            if (eggProductOptional.isPresent()) {
                Long incubationPhaseId = eggProductOptional.get().getIncubationPhaseId();
                IncubationPhase incubationPhase = incubationPhaseRepository.findByIncubationPhaseId(incubationPhaseId).get();
                phase = incubationPhase.getPhaseDescription();
                progress = incubationPhase.getPhaseNumber();
                dto.setPhase(phase);
                dto.setProgress(progress);
            } else {
                dto.setPhase("Chưa ấp");
                dto.setProgress(progress);
            }
            // List incubation phase to update
            List<IncubationPhase> phaseUpdateList = new ArrayList<>();
            // Get incubation phase list
            List<IncubationPhase> incubationPhaseList = incubationPhaseRepository.
                    getListIncubationPhaseForEggBatch(eggBatchId);
            // List item (incubationPhaseId)    | 0| 1| 2| 3| 4| 5| 6| 7| 8| 9| 10|
            // phase number                     |-1| 0| 1| 2| 3| 4| 5| 6| 7| 8| 9 |
            LocalDateTime now = LocalDateTime.now();
            long dateDiff = stringDealer
                    .dateDiff(Date.valueOf(eggBatch.getDateAction().minusHours(7).toLocalDate()), Date.valueOf(now.toLocalDate()));
            if (progress == 0) {
                phaseUpdateList.add(incubationPhaseList.get(1));
            }
            if (progress == 1) {
                phaseUpdateList.add(incubationPhaseList.get(progress + 2));
            }
            if (progress == 2 || progress == 3 || progress == 4) {
                if (dateDiff < 0) {
                    phaseUpdateList.add(incubationPhaseList.get(progress + 1));
                }
                if (dateDiff >= 0) {
                    phaseUpdateList.add(incubationPhaseList.get(progress + 2));
                }
            }
            if (progress == 5) {
                if (dto.getEggProductList().get(2).getCurAmount() > 0) {
                    phaseUpdateList.add(incubationPhaseList.get(6));
                } else {
                    phaseUpdateList.add(incubationPhaseList.get(7));
                }
            }
            if (progress == 7 || progress == 8 || progress == 9) {
                phaseUpdateList.add(incubationPhaseList.get(9));
                phaseUpdateList.add(incubationPhaseList.get(10));
            }
            dto.setPhaseUpdateList(phaseUpdateList);
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
        Optional<List<ImportReceipt>> importReceiptListOptional = importReceiptRepository
                .findByFacilityIdOrderByImportDateDesc(facilityId);
        if (importReceiptListOptional.isPresent()) {
            List<ImportReceipt> importReceiptList = importReceiptListOptional.get();
            List<EggBatchViewListItemDTO> dtoList = new ArrayList<>();
            for (ImportReceipt importReceipt : importReceiptList) {
                List<EggBatch> eggBatchList = eggBatchRepository
                        .findByImportIdOrderByStatusDesc(importReceipt.getImportId()).get();
                for (EggBatch eggBatch : eggBatchList) {
                    // List egg batch
                    EggBatchViewListItemDTO dto = new EggBatchViewListItemDTO();
                    // Egg batch
                    dto.setEggBatchId(eggBatch.getEggBatchId());
                    dto.setImportId(importReceipt.getImportId());
                    dto.setImportDate(importReceipt.getImportDate());
                    dto.setAmount(eggBatch.getAmount());
                    dto.setNeedAction(eggBatch.getNeedAction());
                    dto.setDateAction(eggBatch.getDateAction());
                    dto.setStatus(eggBatch.getStatus());
                    // Breed
                    Breed breed = breedRepository.findByBreedId(eggBatch.getBreedId()).get();
                    dto.setBreedId(eggBatch.getBreedId());
                    dto.setBreedName(breed.getBreedName());
                    // Specie
                    Specie specie = specieRepository.findById(breed.getSpecieId()).get();
                    dto.setIncubationPeriod(specie.getIncubationPeriod());
                    // Supplier
                    Supplier supplier = supplierRepository.findBySupplierId(importReceipt.getSupplierId()).get();
                    dto.setSupplierId(importReceipt.getSupplierId());
                    dto.setSupplierName(supplier.getSupplierName());
                    // Progress (incubation phase)
                    int progress = 0;
                    String phase = "";
                    Optional<EggProduct> eggProductOptional = eggProductRepository.
                            findEggProductLastPhase(eggBatch.getEggBatchId());
                    if (eggProductOptional.isPresent()) {
                        Long incubationPhaseId = eggProductOptional.get().getIncubationPhaseId();
                        IncubationPhase incubationPhase = incubationPhaseRepository.
                                findByIncubationPhaseId(incubationPhaseId).get();

                        phase = incubationPhase.getPhaseDescription();
                        progress = incubationPhase.getPhaseNumber();
                        dto.setPhase(phase);
                        dto.setProgress(progress);
                    } else {
                        dto.setPhase("Chưa ấp");
                        dto.setProgress(progress);
                    }

                    // Progress in days
                    Long progressInDays = 0L;
                    // Egg Product
                    Optional<List<EggProduct>> eggProductListOptional = eggProductRepository.
                            findByEggBatchId(eggBatch.getEggBatchId());
                    if (eggProductListOptional.isEmpty()) {
                        dto.setProgressInDays(0L);
                    } else {
                        for (EggProduct eggProduct : eggProductListOptional.get()) {
                            IncubationPhase incubationPhase = incubationPhaseRepository.
                                    findByIncubationPhaseId(eggProduct.getIncubationPhaseId()).get();
                            if (incubationPhase.getPhaseNumber() == 1) {
                                progressInDays = stringDealer.dateDiff(
                                        Date.valueOf(eggProduct.getIncubationDate().toLocalDate()),
                                        Date.valueOf(LocalDate.now()));
                                break;
                            }
                        }
                        dto.setProgressInDays(progressInDays);
                    }

                    // add to list
                    dtoList.add(dto);
                }
            }
            return new ResponseEntity<>(dtoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
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
        // Egg Batch
        Optional<EggBatch> eggBatchOptional = eggBatchRepository.findByEggBatchId(updateEggBatchDTO.getEggBatchId());
        if (eggBatchOptional.isEmpty()) {
            return new ResponseEntity<>("Không tìm thấy lô trứng", HttpStatus.BAD_REQUEST);
        }

        // Get incubation phase list
        List<IncubationPhase> incubationPhaseList = incubationPhaseRepository.
                getListIncubationPhaseForEggBatch(updateEggBatchDTO.getEggBatchId());
        // List item (incubationPhaseId)    | 0| 1| 2| 3| 4| 5| 6| 7| 8| 9| 10|
        // phase number                     |-1| 0| 1| 2| 3| 4| 5| 6| 7| 8| 9 |

        EggBatch eggBatch = eggBatchOptional.get();
        int eggBatchAmount = eggBatch.getAmount();
        LocalDateTime dateAction = eggBatch.getDateAction();

        // Get latest phase
        int progress = 0;
        Optional<EggProduct> eggProductLastPhaseOptional = eggProductRepository.
                findEggProductLastPhase(eggBatch.getEggBatchId());
        if (eggProductLastPhaseOptional.isPresent()) {
            Long incubationPhaseId = eggProductLastPhaseOptional.get().getIncubationPhaseId();
            IncubationPhase phase = incubationPhaseRepository.findByIncubationPhaseId(incubationPhaseId).get();
            progress = phase.getPhaseNumber();
        }

        // Need action
        int needActionUpdate = updateEggBatchDTO.getNeedAction();
        int needActionEggBatch = eggBatch.getNeedAction();
        LocalDateTime now = LocalDateTime.now();

        if (needActionEggBatch == 0 && progress < 7) {
            return new ResponseEntity<>("Chưa đến giai đoạn cần cập nhật", HttpStatus.BAD_REQUEST);
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
        List<Integer> listPhase = Arrays.asList(0, 2, 3, 4, 5, 6, 8, 9);
        if (!listPhase.contains(phaseNumber)) {
            return new ResponseEntity<>("Giai đoạn cập nhật không hợp lệ", HttpStatus.BAD_REQUEST);
        }
        // Get Machine list
        List<EggLocationUpdateEggBatchDTO> eggLocationDTOs = updateEggBatchDTO.getEggLocationUpdateEggBatchDTOS();
        if (eggLocationDTOs.size() > 0) {
            for (EggLocationUpdateEggBatchDTO item : eggLocationDTOs) {
                if (item.getAmountUpdate() == 0 && item.getOldAmount() == 0) {
                    eggLocationDTOs.remove(item);
                }
            }
        }

        try {
            // Find current egg product to update
            EggProduct eggProduct;
            Optional<EggProduct> eggProductOptional = eggProductRepository.
                    findByEggBatchIdAndIncubationPhaseId(
                            eggBatch.getEggBatchId(),
                            incubationPhaseList.get(phaseNumber + 1).getIncubationPhaseId());

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
                            "Số trứng không hợp lệ. Tổng số Trứng hư tổn, hao hụt(" + eggWastedAmount + ") và Trứng " +
                                    "vỡ/dập(" + amount + ") phải nhỏ hơn hoặc bằng số trứng của lô(" + eggBatchAmount + ")",
                            HttpStatus.BAD_REQUEST);
                }
                // All egg broken
                if (eggWastedAmount == eggBatchAmount) {
                    eggBatch.setStatus(0);
                    eggBatch.setNeedAction(0);
                    eggBatchRepository.save(eggBatch);
                    // Create Trứng hao hụt - egg-1
                    eggProduct = new EggProduct();
                    eggProduct.setEggBatchId(eggBatch.getEggBatchId());
                    eggProduct.setIncubationPhaseId(incubationPhaseList.get(0).getIncubationPhaseId());
                    eggProduct.setIncubationDate(now);
                    eggProduct.setAmount(eggWastedAmount);
                    eggProduct.setCurAmount(eggWastedAmount);
                    eggProduct.setStatus(true);
                    EggProduct eggWastedInserted = eggProductRepository.save(eggProduct);
                    return new ResponseEntity<>("Cập nhật lô trứng thành công", HttpStatus.OK);
                }

                // Check machine list
                if (eggLocationDTOs.isEmpty()) {
                    return new ResponseEntity<>("Chưa cập nhật vị trí trứng", HttpStatus.BAD_REQUEST);
                }
                Set inputSet = new HashSet();
                for (EggLocationUpdateEggBatchDTO eggLocation : eggLocationDTOs) {
                    inputSet.add(eggLocation.getMachineId());
                }
                if (inputSet.size() < eggLocationDTOs.size()) {
                    return new ResponseEntity<>("Có máy bị trùng lặp", HttpStatus.BAD_REQUEST);
                }

                // Check total eggs in list Machines
                int total = 0;
                for (EggLocationUpdateEggBatchDTO eggLocationDTO : eggLocationDTOs) {
                    if (eggLocationDTO.getMachineTypeId() == 2) {
                        return new ResponseEntity<>("Chỉ được chọn máy ấp trong giai đoạn này",
                                HttpStatus.BAD_REQUEST);
                    }
                    if (eggLocationDTO.getAmountUpdate() < 0
                            || eggLocationDTO.getAmountUpdate() >
                            (eggLocationDTO.getCapacity() - eggLocationDTO.getAmountCurrent())
                            || eggLocationDTO.getAmountUpdate() > eggBatchAmount) {
                        return new ResponseEntity<>("Số lượng trứng trong danh sách máy không hợp lệ",
                                HttpStatus.BAD_REQUEST);
                    }
                    total += eggLocationDTO.getAmountUpdate();
                }

                if (total != eggBatchAmount - eggWastedAmount - amount) {
                    return new ResponseEntity<>("Số lượng trứng trong danh sách máy không hợp lệ. " +
                            "Tổng số trứng trong danh sách máy(" + total + ") phải bằng số trứng của lô(" +
                            eggBatchAmount + ") trừ đi tổng số Trứng hư tổn, hao hụt(" + eggWastedAmount +
                            ") và Trứng vỡ/dập(" + amount + ")",
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

                // Create Trứng dập - egg0
                eggProduct = new EggProduct();
                eggProduct.setEggBatchId(eggBatch.getEggBatchId());
                eggProduct.setIncubationPhaseId(incubationPhaseList.get(1).getIncubationPhaseId());
                eggProduct.setIncubationDate(now);
                eggProduct.setAmount(amount);
                eggProduct.setCurAmount(amount);
                eggProduct.setStatus(true);
                EggProduct egg0Inserted = eggProductRepository.save(eggProduct);

                // Create Trứng đang ấp - egg1
                eggProduct = new EggProduct();
                eggProduct.setEggBatchId(eggBatch.getEggBatchId());
                eggProduct.setIncubationPhaseId(incubationPhaseList.get(2).getIncubationPhaseId());
                eggProduct.setIncubationDate(now);
                eggProduct.setAmount(eggBatchAmount - eggWastedAmount - amount);
                eggProduct.setCurAmount(eggBatchAmount - eggWastedAmount - amount);
                eggProduct.setStatus(true);
                EggProduct egg1Inserted = eggProductRepository.save(eggProduct);

                // Update egg batch
                eggBatch.setNeedAction(0);
                // Progress 1, set date action of phase 3
                eggBatch.setDateAction(now.plusDays(incubationPhaseList.get(3).getPhasePeriod()));
                eggBatchRepository.save(eggBatch);

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
                    Machine machine = machineRepository.findByMachineId(eggLocationDTO.getMachineId()).get();
                    machine.setCurCapacity(machineRepository.getCurrentAmount(machine.getMachineId()));
                    machineRepository.save(machine);
                }
            }

            // Update before moving to hatching
            // Update Trứng hao hụt (-1), trứng đang nở (1), Trứng trắng (2)/trứng loãng (3)/trứng lộn (4)
            // Create new (2/3/4) if not exist
            if ((phaseNumber == 2 || phaseNumber == 3 || phaseNumber == 4)
                    && phaseNumber - 1 > progress) {
                return new ResponseEntity<>("Chưa đến giai đoạn cập nhật " +
                        incubationPhaseList.get(phaseNumber + 1).getPhaseDescription(),
                        HttpStatus.BAD_REQUEST);
            }
            if ((phaseNumber == 0 || phaseNumber == 2 || phaseNumber == 3 || phaseNumber == 4)
                    && phaseNumber < progress) {
                return new ResponseEntity<>("Đã qua giai đoạn cập nhật " +
                        incubationPhaseList.get(phaseNumber + 1).getPhaseDescription(),
                        HttpStatus.BAD_REQUEST);
            }
            if ((phaseNumber == 2 || phaseNumber == 3 || phaseNumber == 4)
                    && (progress == phaseNumber - 1 || progress == phaseNumber)) {
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
                if (amount > eggIncubating.getCurAmount()) {
                    return new ResponseEntity<>("Số " + incubationPhase.getPhaseDescription() + " cập nhật " +
                            "không được lớn hơn số trứng đang ấp", HttpStatus.BAD_REQUEST);
                }
                if (amount + eggWastedAmount > eggIncubating.getCurAmount()) {
                    return new ResponseEntity<>("Số lượng trứng không hợp lệ. Tổng số Trứng hư tổn, hao hụt mới("
                            + eggWastedAmount + ") và " + incubationPhase.getPhaseDescription() + "(" + amount + ") phải " +
                            "nhỏ hơn hoặc bằng số Trứng đang ấp(" + eggIncubating.getCurAmount() + ")",
                            HttpStatus.BAD_REQUEST);
                }
                // All egg broken
                if (eggWastedAmount == eggIncubating.getCurAmount() && eggProductOptional.isEmpty()) {
                    eggBatch.setStatus(0);
                    eggBatch.setNeedAction(0);
                    eggBatchRepository.save(eggBatch);
                    // Update Trứng hao hụt - egg-1
                    eggWasted.setAmount(eggWasted.getAmount() + eggWastedAmount);
                    eggWasted.setCurAmount(eggWasted.getCurAmount() + eggWastedAmount);
                    EggProduct eggWastedInserted = eggProductRepository.save(eggWasted);

                    // Update egg location
                    List<EggLocation> eggLocations = eggLocationRepository
                            .findByProductId(eggIncubating.getProductId()).get();
                    eggLocationRepository.deleteAll(eggLocations);

                    // Update current amount of machine
                    for (EggLocationUpdateEggBatchDTO eggLocationDTO : eggLocationDTOs) {
                        Machine machine = machineRepository.findByMachineId(eggLocationDTO.getMachineId()).get();
                        machine.setCurCapacity(machineRepository.getCurrentAmount(machine.getMachineId()));
                        machineRepository.save(machine);

                    }
                    eggIncubating.setCurAmount(0);
                    eggProductRepository.save(eggIncubating);
                    return new ResponseEntity<>("Cập nhật lô trứng thành công", HttpStatus.OK);
                }

                // Not Update all incubating egg
                if (amount + eggWastedAmount != eggIncubating.getCurAmount()) {
                    // Check machine list
                    if (eggLocationDTOs.isEmpty()) {
                        return new ResponseEntity<>("Chưa cập nhật vị trí trứng", HttpStatus.BAD_REQUEST);
                    }
                    Set inputSet = new HashSet();
                    for (EggLocationUpdateEggBatchDTO eggLocation : eggLocationDTOs) {
                        inputSet.add(eggLocation.getMachineId());
                    }
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
                                || eggLocationDTO.getAmountUpdate() > eggLocationDTO.getCapacity()
                                || ((eggLocationDTO.getAmountUpdate() - eggLocationDTO.getOldAmount())
                                > (eggLocationDTO.getCapacity() - eggLocationDTO.getAmountCurrent()))
                                || eggLocationDTO.getAmountUpdate() > eggIncubating.getCurAmount()) {
                            return new ResponseEntity<>("Số lượng trứng trong danh sách máy không hợp lệ",
                                    HttpStatus.BAD_REQUEST);
                        }
                        total += eggLocationDTO.getAmountUpdate();
                    }
                    if (total != eggIncubating.getCurAmount() - eggWastedAmount - amount) {
                        return new ResponseEntity<>("Số lượng trứng trong danh sách máy không hợp lệ. Tổng số trứng" +
                                " trong danh sách máy(" + total + ") phải bằng số Trứng đang ấp trước đó(" +
                                eggIncubating.getCurAmount() + ") trừ đi số Trứng hư tổn, hao hụt(" + eggWastedAmount + ")" +
                                " và số " + incubationPhase.getPhaseDescription() + "(" + amount + ")",
                                HttpStatus.BAD_REQUEST);
                    }
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
                eggIncubating.setCurAmount(eggIncubating.getCurAmount() - eggWastedAmount - amount);
                EggProduct egg1Inserted = eggProductRepository.save(eggIncubating);

                // Update egg batch
                if (egg1Inserted.getAmount() == 0) {
                    eggBatch.setNeedAction(0);
                    eggBatch.setStatus(0);
                } else {
                    eggBatch.setNeedAction(needActionUpdate);
                    eggBatch.setDateAction(eggIncubating.getIncubationDate()
                            .plusDays(incubationPhaseList.get(phaseNumber + 2).getPhasePeriod()));
                }
                eggBatchRepository.save(eggBatch);

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
                    Machine machine = machineRepository.findByMachineId(eggLocationDTO.getMachineId()).get();
                    machine.setCurCapacity(machineRepository.getCurrentAmount(machine.getMachineId()));
                    machineRepository.save(machine);
                }
            }


            // Moving to hatching machine
            // Update Trứng hao hụt (-1)
            // Update trứng đang ấp (1)
            // Create/Update trứng đang nở (5)
            if (phaseNumber == 5) {
                if (progress < 4) {
                    return new ResponseEntity<>("Chưa đến giai đoạn cập nhật Trứng đang nở", HttpStatus.BAD_REQUEST);
                }
                if (progress > 5) {
                    return new ResponseEntity<>("Đã qua giai đoạn cập nhật Trứng đang nở", HttpStatus.BAD_REQUEST);
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

                // Vẫn còn trứng đang ấp, không được đánh dấu hoàn thành giai đoạn
                if (needActionUpdate == 0 && eggIncubating.getCurAmount() > amount) {
                    return new ResponseEntity<>("Không được xác nhận đã hoàn thành giai đoạn, vẫn còn trứng" +
                            " chưa cho sang máy nở", HttpStatus.BAD_REQUEST);
                }

                // Check amounts
                if (amount > eggIncubating.getCurAmount()) {
                    return new ResponseEntity<>("Số " + incubationPhase.getPhaseDescription() + " cập nhật " +
                            "không được lớn hơn số trứng đang ấp", HttpStatus.BAD_REQUEST);
                }

                if (eggWastedAmount + amount > eggIncubating.getCurAmount()) {
                    return new ResponseEntity<>(
                            "Số trứng không hợp lệ. Tổng số Trứng hư tổn, hao hụt mới(" + eggWastedAmount + ") và " +
                                    incubationPhase.getPhaseDescription() + "(" + amount + ") phải nhỏ hơn hoặc bằng số " +
                                    "Trứng đang ấp cũ (" + eggIncubating.getCurAmount() + ")",
                            HttpStatus.BAD_REQUEST);
                }
                // All egg broken
                if ((eggWastedAmount == eggIncubating.getCurAmount()) && eggProductOptional.isEmpty()) {
                    eggBatch.setStatus(0);
                    eggBatch.setNeedAction(0);
                    eggBatchRepository.save(eggBatch);
                    // Update Trứng hao hụt - egg-1
                    eggWasted.setAmount(eggWasted.getAmount() + eggWastedAmount);
                    eggWasted.setCurAmount(eggWasted.getCurAmount() + eggWastedAmount);
                    EggProduct eggWastedInserted = eggProductRepository.save(eggWasted);

                    // Update egg location
                    List<EggLocation> eggLocations = eggLocationRepository
                            .findByProductId(eggIncubating.getProductId()).get();
                    eggLocationRepository.deleteAll(eggLocations);

                    // Update current amount of machine
                    for (EggLocationUpdateEggBatchDTO eggLocationDTO : eggLocationDTOs) {
                        Machine machine = machineRepository.findByMachineId(eggLocationDTO.getMachineId()).get();
                        machine.setCurCapacity(machineRepository.getCurrentAmount(machine.getMachineId()));
                        machineRepository.save(machine);
                    }

                    eggIncubating.setCurAmount(0);
                    eggProductRepository.save(eggIncubating);
                    return new ResponseEntity<>("Cập nhật lô trứng thành công", HttpStatus.OK);
                }

                // Check machine list
                if (eggLocationDTOs.isEmpty()) {
                    return new ResponseEntity<>("Chưa cập nhật vị trí trứng", HttpStatus.BAD_REQUEST);
                }
                Set inputSet = new HashSet();
                for (EggLocationUpdateEggBatchDTO eggLocation : eggLocationDTOs) {
                    inputSet.add(eggLocation.getMachineId());
                }
                if (inputSet.size() < eggLocationDTOs.size()) {
                    return new ResponseEntity<>("Có máy bị trùng lặp", HttpStatus.BAD_REQUEST);
                }
                boolean onlyHatching = true;
                for (EggLocationUpdateEggBatchDTO eggLocationDTO : eggLocationDTOs) {
                    Machine machine = machineRepository.findByMachineId(eggLocationDTO.getMachineId()).get();
                    if (machine.getMachineTypeId() == 1 && eggLocationDTO.getAmountUpdate() > 0) {
                        onlyHatching = false;
                        break;
                    }
                }

                // Chưa chuyển hết sang nở, chọn cả máy ấp, nở
                if (amount + eggWastedAmount < eggIncubating.getCurAmount()) {
                    if (onlyHatching) {
                        return new ResponseEntity<>("Chưa chuyển hết sang nở, phải chọn cả máy ấp và máy nở",
                                HttpStatus.BAD_REQUEST);
                    }
                }
                // Chuyển hết sang nở, chỉ chọn máy nở
                if (amount + eggWastedAmount == eggIncubating.getCurAmount()) {
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
                                || ((eggLocationDTO.getAmountUpdate() - eggLocationDTO.getOldAmount())
                                > (eggLocationDTO.getCapacity() - eggLocationDTO.getAmountCurrent()))
                                || eggLocationDTO.getAmountUpdate() > eggLocationDTO.getCapacity()
                                || eggLocationDTO.getAmountUpdate() > eggIncubating.getCurAmount()) {
                            return new ResponseEntity<>("Số lượng trứng trong danh sách máy không hợp lệ",
                                    HttpStatus.BAD_REQUEST);
                        }
                    } else {
                        eggProduct = eggProductOptional.get();
                        if (eggLocationDTO.getAmountUpdate() < 0
                                || eggLocationDTO.getAmountUpdate() > eggLocationDTO.getCapacity()
                                || ((eggLocationDTO.getAmountUpdate() - eggLocationDTO.getOldAmount())
                                > (eggLocationDTO.getCapacity() - eggLocationDTO.getAmountCurrent()))
                                || eggLocationDTO.getAmountUpdate() >
                                (eggIncubating.getCurAmount() + amount + eggProduct.getAmount())) {
                            return new ResponseEntity<>("Số lượng trứng trong danh sách máy không hợp lệ",
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
                                "Tổng số trứng trong danh sách máy nở(" + totalHatching + ") phải bằng số trứng " +
                                "chuyển sang nở(" + amount + ")",
                                HttpStatus.BAD_REQUEST);
                    }
                    // Chưa chuyển sang nở hết
                    if (amount + eggWastedAmount < eggIncubating.getCurAmount()) {
                        if (totalIncubating + totalHatching != eggIncubating.getCurAmount() - eggWastedAmount) {
                            return new ResponseEntity<>("Số lượng trứng trong danh sách máy không hợp lệ. " +
                                    "Tổng số trứng trong danh sách máy (đang ấp và nở) (" + (totalIncubating +
                                    totalHatching) + ") phải bằng số trứng đang ấp trước đó(" + eggIncubating.getCurAmount()
                                    + ") trừ đi tổng số Trứng hư tổn, hao hụt cập nhật thêm(" + eggWastedAmount + ")",
                                    HttpStatus.BAD_REQUEST);
                        }
                    }
                    // Chuyển sang nở hết
                    if (amount + eggWastedAmount == eggIncubating.getCurAmount()) {
                        if (totalHatching != eggIncubating.getCurAmount() - eggWastedAmount) {
                            return new ResponseEntity<>("Số lượng trứng trong danh sách máy không hợp lệ. Chuyển" +
                                    " hết trứng sang nở: Tổng số trứng trong danh sách máy nở(" + totalHatching + ")  " +
                                    "phải bằng số trứng đang ấp trước đó (" + eggIncubating.getCurAmount() + ") trừ đi số " +
                                    "Trứng hư tổ, hao hụt cập nhật thêm(" + eggWastedAmount + ")",
                                    HttpStatus.BAD_REQUEST);
                        }
                    }
                } else {
                    eggProduct = eggProductOptional.get();
                    if (totalHatching != eggProduct.getAmount() + amount) {
                        return new ResponseEntity<>("Số lượng trứng trong danh sách máy không hợp lệ. " +
                                "Tổng số trứng trong danh sách máy nở(" + totalHatching + ") phải bằng số Trứng " +
                                "đang nở trước đó(" + eggProduct.getAmount() + ") và số Trứng chuyển sang nở mới(" +
                                amount + ")",
                                HttpStatus.BAD_REQUEST);
                    }
                    // Chưa chuyển sang nở hết
                    if (amount + eggWastedAmount < eggIncubating.getCurAmount()) {
                        if (totalIncubating + totalHatching != eggIncubating.getCurAmount() + eggProduct.getAmount()
                                - eggWastedAmount) {
                            return new ResponseEntity<>("Số lượng trứng trong danh sách máy không hợp lệ. " +
                                    "Tổng số trứng trong danh sách máy (đang ấp và nở) (" + totalIncubating +
                                    totalHatching + ") phải bằng tổng số Trứng đang ấp trước đó(" +
                                    eggIncubating.getCurAmount() + "), số Trứng đang nở trước đó (" + eggProduct.getAmount()
                                    + ") trừ đi tổng số Trứng hư tổn, hao hụt cập nhật thêm(" + eggWastedAmount + ")",
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

                // Update egg batch
                // Chuyển hết sang nở
                if (amount + eggWastedAmount == eggIncubating.getCurAmount()) {
                    eggBatch.setNeedAction(0);
                }

                // Update Trứng đang nở - egg1
                eggIncubating.setCurAmount(eggIncubating.getCurAmount() - amount - eggWastedAmount);
                eggProductRepository.save(eggIncubating);

                eggBatch.setDateAction(eggIncubating.getIncubationDate()
                        .plusDays(incubationPhaseList.get(7).getPhasePeriod()));

                eggBatchRepository.save(eggBatch);

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
                    Machine machine = machineRepository.findByMachineId(eggLocationDTO.getMachineId()).get();
                    machine.setCurCapacity(machineRepository.getCurrentAmount(machine.getMachineId()));
                    machineRepository.save(machine);
                }
            }

            // Update sau khi gà nở
            // Update Trứng hao hụt (-1)
            // Remove trứng đang nở (5)
            // Create trứng tắc (6), con nở (7)
            if (phaseNumber == 6) {
                if (eggProductOptional.isPresent()) {
                    return new ResponseEntity<>("Đã qua giai đoạn cập nhật Trứng tắc", HttpStatus.BAD_REQUEST);
                } else {
                    if (progress <= 4) {
                        return new ResponseEntity<>("Chưa đến giai đoạn cập nhật Trứng tắc", HttpStatus.BAD_REQUEST);
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
                    if (eggIncubating.getCurAmount() > 0) {
                        return new ResponseEntity<>("Vẫn còn trứng trong máy ấp, không thể cập nhật trứng Tắc", HttpStatus.BAD_REQUEST);
                    }

                    // Check amounts
                    if (amount > eggHatching.getAmount()) {
                        return new ResponseEntity<>("Số " + incubationPhase.getPhaseDescription() + " cập nhật " +
                                "không được lớn hơn số Trứng đang nở", HttpStatus.BAD_REQUEST);
                    }
                    if (eggWastedAmount + amount > eggHatching.getAmount()) {
                        return new ResponseEntity<>(
                                "Số trứng không hợp lệ. Tổng số Trứng hư tổn, hao hụt mới(" + eggWastedAmount + ") và " +
                                        "số " + incubationPhase.getPhaseDescription() + "(" + amount + ") phải nhỏ hơn hoặc " +
                                        "bằng số Trứng đang nở(" + eggHatching.getAmount() + ")",
                                HttpStatus.BAD_REQUEST);
                    }
                    // All egg broken
                    if (eggWastedAmount == eggHatching.getCurAmount()) {
                        eggBatch.setStatus(0);
                        eggBatch.setNeedAction(0);
                        eggBatchRepository.save(eggBatch);

                        // Update egg location
                        List<EggLocation> eggLocations = eggLocationRepository
                                .findByProductId(eggHatching.getProductId()).get();
                        eggLocationRepository.deleteAll(eggLocations);

                        // Update current amount of machine
                        for (EggLocationUpdateEggBatchDTO eggLocationDTO : eggLocationDTOs) {
                            Machine machine = machineRepository.findByMachineId(eggLocationDTO.getMachineId()).get();
                            machine.setCurCapacity(machineRepository.getCurrentAmount(machine.getMachineId()));
                            machineRepository.save(machine);

                        }

                        // Update Trứng hao hụt - egg-1
                        eggWasted.setAmount(eggWasted.getAmount() + eggWastedAmount);
                        eggWasted.setCurAmount(eggWasted.getCurAmount() + eggWastedAmount);
                        EggProduct eggWastedInserted = eggProductRepository.save(eggWasted);
                        return new ResponseEntity<>("Cập nhật lô trứng thành công", HttpStatus.OK);
                    }

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
                    eggProduct.setCurAmount(eggHatching.getAmount() - eggWastedAmount - amount);
                    eggProduct.setStatus(true);
                    EggProduct eggHatched = eggProductRepository.save(eggProduct);

                    // Remove Trứng đang nở - egg5
                    eggHatching.setCurAmount(0);
                    eggProductRepository.save(eggHatching);

                    // Remove egg location
                    List<EggLocation> eggLocations = eggLocationRepository
                            .findByProductId(eggHatching.getProductId()).get();
                    eggLocationRepository.deleteAll(eggLocations);

                    // Update current amount of machine
                    for (EggLocation eggLocation : eggLocations) {
                        Machine machine = machineRepository.findByMachineId(eggLocation.getMachineId()).get();
                        machine.setCurCapacity(machineRepository.getCurrentAmount(machine.getMachineId()));
                        machineRepository.save(machine);
                    }

                    // Update egg batch
                    eggBatch.setNeedAction(0);
                    eggBatchRepository.save(eggBatch);
                }
            }

            // Update sau khi phân loại Đực/Cái
            // Update Trứng hao hụt (-1), Con nở (7) , Con đực (8) / Con cái
            if (phaseNumber == 8 || phaseNumber == 9) {
                // incubation phase
                IncubationPhase incubationPhase = incubationPhaseList.get(phaseNumber + 1);

                if (progress <= 6) {
                    return new ResponseEntity<>("Chưa đến giai đoạn cập nhật " + incubationPhase.getPhaseDescription()
                            , HttpStatus.BAD_REQUEST);
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
                    return new ResponseEntity<>("Số " + incubationPhase.getPhaseDescription() + " " +
                            "không được vượt quá số lượng Con nở",
                            HttpStatus.BAD_REQUEST);
                }
                if (amount + eggWastedAmount > hatchedAmount) {
                    return new ResponseEntity<>("Tổng số " + incubationPhase.getPhaseDescription() + " ("
                            + amount + ") và Trứng hao hụt mới (" + eggWastedAmount + ") không được vượt quá " +
                            "số lượng Con nở (" + hatchedAmount + ")",
                            HttpStatus.BAD_REQUEST);
                }

                // All egg broken
                if (eggWastedAmount == eggHatched.getCurAmount()) {
                    eggBatch.setStatus(0);
                    eggBatch.setNeedAction(0);
                    eggBatchRepository.save(eggBatch);
                    // Update Trứng hao hụt - egg-1
                    eggWasted.setAmount(eggWasted.getAmount() + eggWastedAmount);
                    eggWasted.setCurAmount(eggWasted.getCurAmount() + eggWastedAmount);
                    EggProduct eggWastedInserted = eggProductRepository.save(eggWasted);

                    eggHatched.setCurAmount(0);
                    eggProductRepository.save(eggHatched);
                    return new ResponseEntity<>("Cập nhật lô trứng thành công", HttpStatus.OK);
                }

                if (eggProductOptional.isEmpty()) {
                    // Create
                    eggProduct = new EggProduct();
                    eggProduct.setEggBatchId(eggBatch.getEggBatchId());
                    eggProduct.setIncubationPhaseId(incubationPhaseList.get(phaseNumber + 1).getIncubationPhaseId());
                    eggProduct.setIncubationDate(now);
                    eggProduct.setAmount(amount);
                    eggProduct.setCurAmount(amount);
                    eggProduct.setStatus(true);
                    eggProductRepository.save(eggProduct);

                    // Update  Con nở - egg7
                    eggHatched.setAmount(hatchedAmount - amount - eggWastedAmount);
                    eggHatched.setCurAmount(hatchedAmount - amount - eggWastedAmount);
                    eggHatched = eggProductRepository.save(eggHatched);
                    // Set done
                    if (hatchedAmount - amount - eggWastedAmount == 0) {
                        eggBatch.setStatus(0);
                        eggBatch.setNeedAction(0);
                        eggBatchRepository.save(eggBatch);
                    }
                } else {
                    // Update
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
                        eggBatch.setNeedAction(0);
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
     * Update egg batch's locations.
     *
     * @param updateLocationEggBatchDTO
     * @return
     */
    @Override
    public ResponseEntity<?> updateLocationEggBatch(UpdateLocationEggBatchDTO updateLocationEggBatchDTO) {
        // Egg Batch
        Optional<EggBatch> eggBatchOptional = eggBatchRepository.findByEggBatchId(updateLocationEggBatchDTO.getEggBatchId());
        if (eggBatchOptional.isEmpty()) {
            return new ResponseEntity<>("Không tìm thấy lô trứng", HttpStatus.BAD_REQUEST);
        }

        EggBatch eggBatch = eggBatchOptional.get();
        // Get incubation phase list
        List<IncubationPhase> incubationPhaseList = incubationPhaseRepository.
                getListIncubationPhaseForEggBatch(eggBatch.getEggBatchId());
        // List item (incubationPhaseId)    | 0| 1| 2| 3| 4| 5| 6| 7| 8| 9| 10|
        // phase number                     |-1| 0| 1| 2| 3| 4| 5| 6| 7| 8| 9 |

        int wastedIncubating = updateLocationEggBatchDTO.getEggWastedIncubating();
        int wastedHatching = updateLocationEggBatchDTO.getEggWastedHatching();

        List<EggLocationEggBatchDetailDTO> locationsOld = updateLocationEggBatchDTO.getLocationsOld();
        System.out.println(locationsOld);
        List<EggLocationUpdateEggBatchDTO> locationsNew = updateLocationEggBatchDTO.getLocationsNew();
        System.out.println(locationsNew);
        // Get type of machine list old
        boolean onlyIncubatingOld = true;
        boolean onlyHatchingOld = true;
        boolean bothOld = false;
        for (EggLocationEggBatchDetailDTO item : locationsOld) {
            if (item.getMachineTypeId() == 1L) {
                onlyHatchingOld = false;
            }
            if (item.getMachineTypeId() == 2L) {
                onlyIncubatingOld = false;
            }
        }
        if (!onlyIncubatingOld && !onlyHatchingOld) {
            bothOld = true;
        }

        // Get amounts of list old
        int totalOld = 0;
        int incubatingAmountOld = 0;
        int hatchingAmountOld = 0;

        for (EggLocationEggBatchDetailDTO item : locationsOld) {
            if (item.getMachineTypeId() == 1L) {
                incubatingAmountOld += item.getAmount();
            }
            if (item.getMachineTypeId() == 2L) {
                hatchingAmountOld += item.getAmount();
            }
            totalOld += item.getAmount();
        }

        if ((wastedIncubating + wastedHatching) > totalOld || wastedIncubating < 0 || wastedIncubating < 0) {
            return new ResponseEntity<>("Số lượng trứng hao hụt không hợp lệ", HttpStatus.BAD_REQUEST);
        }

        // Check list new
        // Remove amount 0
        if (locationsNew.size() > 0) {
            for (EggLocationUpdateEggBatchDTO eggLocationDTO : locationsNew) {
                if (eggLocationDTO.getAmountUpdate() == 0) {
                    locationsNew.remove(eggLocationDTO);
                }
            }
        }
        // Empty after remove 0
        if (locationsNew.size() == 0) {
            return new ResponseEntity<>("Danh sách máy không hợp lệ", HttpStatus.BAD_REQUEST);
        }
        // Check duplicated
        Set inputSet = new HashSet();
        for (EggLocationUpdateEggBatchDTO eggLocation : locationsNew) {
            inputSet.add(eggLocation.getMachineId());
        }
        if (inputSet.size() < locationsNew.size()) {
            return new ResponseEntity<>("Có máy bị trùng lặp", HttpStatus.BAD_REQUEST);
        }

        // Check amount
        for (EggLocationUpdateEggBatchDTO item : locationsNew) {
            if (item.getAmountUpdate() < 0
                    || item.getAmountUpdate() >
                    (item.getCapacity() - item.getAmountCurrent())
                    || item.getAmountUpdate() > totalOld) {
                System.out.println(item.getAmountUpdate());
                System.out.println(item.getCapacity());
                System.out.println(item.getAmountCurrent());
                System.out.println(totalOld);
                return new ResponseEntity<>("Số lượng trứng trong danh sách máy không hợp lệ",
                        HttpStatus.BAD_REQUEST);
            }
        }

        // Check type machine
        boolean onlyIncubating = true;
        boolean onlyHatching = true;
        boolean both = false;
        for (EggLocationUpdateEggBatchDTO item : locationsNew) {
            if (item.getMachineTypeId() == 1L && item.getAmountUpdate() > 0) {
                onlyHatching = false;
            }
            if (item.getMachineTypeId() == 2L && item.getAmountUpdate() > 0) {
                onlyIncubating = false;
            }
        }
        if (!onlyIncubating && !onlyHatching) {
            both = true;
        }

        if (onlyIncubating != onlyIncubatingOld) {
            return new ResponseEntity<>("Chỉ được chọn máy ấp trong giai đoạn này", HttpStatus.BAD_REQUEST);
        }
        if (onlyHatching != onlyHatchingOld) {
            return new ResponseEntity<>("Chỉ được chọn máy nở trong giai đoạn này", HttpStatus.BAD_REQUEST);
        }
        if (both != bothOld) {
            return new ResponseEntity<>("Phải chọn cả máy ấp và nở trong giai đoạn này", HttpStatus.BAD_REQUEST);
        }

        // Check amounts
        int total = 0;
        int incubatingAmount = 0;
        int hatchingAmount = 0;

        for (EggLocationUpdateEggBatchDTO item : locationsNew) {
            if (item.getMachineTypeId() == 1L) {
                incubatingAmount += item.getAmountUpdate();
            }
            if (item.getMachineTypeId() == 2L) {
                hatchingAmount += item.getAmountUpdate();
            }
            total += item.getAmountUpdate();
        }
        if (total != totalOld - wastedIncubating - wastedHatching) {
            return new ResponseEntity<>("Tổng số trứng trong danh sách máy không hợp lệ", HttpStatus.BAD_REQUEST);
        }
        if (incubatingAmountOld < incubatingAmount) {
            return new ResponseEntity<>("Tổng số trứng đang ấp trong danh sách máy không thể lớn hơn trước đó", HttpStatus.BAD_REQUEST);
        }
        if (hatchingAmountOld < hatchingAmount) {
            return new ResponseEntity<>("Tổng số trứng đang nở trong danh sách máy không thể lớn hơn trước đó", HttpStatus.BAD_REQUEST);
        }

        // Update egg location
        EggProduct eggWasted = eggProductRepository.
                findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                        incubationPhaseList.get(0).getIncubationPhaseId()).get();

        eggWasted.setAmount(eggWasted.getAmount() + wastedIncubating + wastedHatching);
        eggWasted.setCurAmount(eggWasted.getCurAmount() + wastedIncubating + wastedHatching);

        if (onlyIncubating) {
            EggProduct eggIncubating = eggProductRepository.
                    findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                            incubationPhaseList.get(2).getIncubationPhaseId()).get();

            eggIncubating.setAmount(eggIncubating.getAmount() - wastedIncubating);
            eggIncubating.setCurAmount(eggIncubating.getCurAmount() - wastedIncubating);
            eggProductRepository.save(eggIncubating);

            List<EggLocation> eggLocations = eggLocationRepository
                    .findByProductId(eggIncubating.getProductId()).get();
            eggLocationRepository.deleteAll(eggLocations);

            for (EggLocationUpdateEggBatchDTO eggLocationDTO : locationsNew) {
                if (eggLocationDTO.getAmountUpdate() > 0) {
                    EggLocation eggLocation = new EggLocation();
                    eggLocation.setProductId(eggIncubating.getProductId());
                    eggLocation.setMachineId(eggLocationDTO.getMachineId());
                    eggLocation.setAmount(eggLocationDTO.getAmountUpdate());
                    eggLocation.setStatus(1);
                    eggLocationRepository.save(eggLocation);
                }
            }
        }

        if (both) {
            // Trứng đang ấp
            EggProduct eggIncubating = eggProductRepository.
                    findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                            incubationPhaseList.get(2).getIncubationPhaseId()).get();

            eggIncubating.setAmount(eggIncubating.getAmount() - wastedIncubating);
            eggIncubating.setCurAmount(eggIncubating.getCurAmount() - wastedIncubating);
            eggProductRepository.save(eggIncubating);

            // Trứng đang nở
            EggProduct eggHatching = eggProductRepository.
                    findByEggBatchIdAndIncubationPhaseId(eggBatch.getEggBatchId(),
                            incubationPhaseList.get(6).getIncubationPhaseId()).get();

            eggHatching.setAmount(eggHatching.getAmount() - wastedHatching);
            eggHatching.setCurAmount(eggHatching.getCurAmount() - wastedHatching);
            eggProductRepository.save(eggHatching);

            List<EggLocation> eggLocations = eggLocationRepository
                    .findByProductId(eggIncubating.getProductId()).get();
            eggLocationRepository.deleteAll(eggLocations);

            eggLocations = eggLocationRepository
                    .findByProductId(eggHatching.getProductId()).get();
            eggLocationRepository.deleteAll(eggLocations);

            for (EggLocationUpdateEggBatchDTO eggLocationDTO : locationsNew) {
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
        }

        // Update current amount of machine
        for (EggLocationEggBatchDetailDTO eggLocationDTO : locationsOld) {
            Machine machine = machineRepository.findByMachineId(eggLocationDTO.getMachineId()).get();
            machine.setCurCapacity(machineRepository.getCurrentAmount(machine.getMachineId()));
            machineRepository.save(machine);
        }

        for (EggLocationUpdateEggBatchDTO eggLocationDTO : locationsNew) {
            Machine machine = machineRepository.findByMachineId(eggLocationDTO.getMachineId()).get();
            machine.setCurCapacity(machineRepository.getCurrentAmount(machine.getMachineId()));
            machineRepository.save(machine);
        }
        return new ResponseEntity<>("Cập nhật vị trí lô trứng thành công", HttpStatus.OK);
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
        // Egg Batch
        Optional<EggBatch> eggBatchOptional = eggBatchRepository.findByEggBatchId(eggBatchId);
        if (eggBatchOptional.isEmpty()) {
            return new ResponseEntity<>("Không tìm thấy lô trứng", HttpStatus.BAD_REQUEST);
        }
        EggBatch eggBatch = eggBatchOptional.get();
        // Egg batch done
        if (eggBatch.getStatus() == 0) {
            return new ResponseEntity<>("Lô trứng đã hoàn thành, không thể cập nhật", HttpStatus.BAD_REQUEST);
        }

        // Get incubation phase list
        List<IncubationPhase> incubationPhaseList = incubationPhaseRepository.
                getListIncubationPhaseForEggBatch(eggBatchId);
        // List item (incubationPhaseId)    | 0| 1| 2| 3| 4| 5| 6| 7| 8| 9| 10|
        // phase number                     |-1| 0| 1| 2| 3| 4| 5| 6| 7| 8| 9 |


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
            if (progress <= 4 && egg1Optional.get().getAmount() != 0) {
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
        return new ResponseEntity<>("Đã hoàn thành lô trứng", HttpStatus.OK);
    }
}
