/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 02/04/2023    1.0        DuongVV          First Deploy<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.eggBatch.EggBatchDataForExportItemDTO;
import com.example.eims.dto.eggProduct.EggProductCreateExportDTO;
import com.example.eims.dto.eggProduct.EggProductDataForExportItemDTO;
import com.example.eims.dto.eggProduct.EggProductViewExportDetailDTO;
import com.example.eims.dto.exportReceipt.CreateExportDTO;
import com.example.eims.dto.exportReceipt.CreateExportDataItemDTO;
import com.example.eims.dto.exportReceipt.ExportReceiptDetailDTO;
import com.example.eims.dto.exportReceipt.ExportReceiptListItemDTO;
import com.example.eims.entity.*;
import com.example.eims.repository.*;
import com.example.eims.service.interfaces.IExportReceiptService;
import com.example.eims.utils.StringDealer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.time.LocalDateTime;

@Service
public class ExportReceiptServiceImpl implements IExportReceiptService {
    @Autowired
    private final ExportReceiptRepository exportReceiptRepository;
    @Autowired
    private final ExportDetailRepository exportDetailRepository;
    @Autowired
    private final ImportReceiptRepository importReceiptRepository;
    @Autowired
    private final EggBatchRepository eggBatchRepository;
    @Autowired
    private final EggProductRepository eggProductRepository;
    @Autowired
    private final BreedRepository breedRepository;
    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private final IncubationPhaseRepository incubationPhaseRepository;

    public ExportReceiptServiceImpl(ExportReceiptRepository exportReceiptRepository,
                                    ExportDetailRepository exportDetailRepository,
                                    ImportReceiptRepository importReceiptRepository,
                                    EggBatchRepository eggBatchRepository, EggProductRepository eggProductRepository,
                                    BreedRepository breedRepository, CustomerRepository customerRepository,
                                    IncubationPhaseRepository incubationPhaseRepository) {
        this.exportReceiptRepository = exportReceiptRepository;
        this.exportDetailRepository = exportDetailRepository;
        this.importReceiptRepository = importReceiptRepository;
        this.eggBatchRepository = eggBatchRepository;
        this.eggProductRepository = eggProductRepository;
        this.breedRepository = breedRepository;
        this.customerRepository = customerRepository;
        this.incubationPhaseRepository = incubationPhaseRepository;
    }

    /**
     * Get all export bill of a facility.
     *
     * @param facilityId the id of facility
     * @return list of import receipts
     */
    @Override
    public ResponseEntity<?> viewExportsByFacility(Long facilityId) {
        Optional<List<ExportReceipt>> exportReceiptListOptional = exportReceiptRepository
                .findByFacilityIdOrderByExportDateDesc(facilityId);
        if (exportReceiptListOptional.isPresent()) {
            List<ExportReceipt> exportReceiptList = exportReceiptListOptional.get();
            List<ExportReceiptListItemDTO> listDTO = new ArrayList<>();
            for (ExportReceipt receipt : exportReceiptList) {
                ExportReceiptListItemDTO exportReceiptListItemDTO = new ExportReceiptListItemDTO();
                exportReceiptListItemDTO.setExportId(receipt.getExportId());
                exportReceiptListItemDTO.setCustomerId(receipt.getCustomerId());
                Customer customer = customerRepository.findByCustomerId(receipt.getCustomerId()).get();
                exportReceiptListItemDTO.setCustomerName(customer.getCustomerName());
                exportReceiptListItemDTO.setExportDate(receipt.getExportDate());
                exportReceiptListItemDTO.setTotal(receipt.getTotal());
                exportReceiptListItemDTO.setPaid(receipt.getPaid());
                exportReceiptListItemDTO.setStatus(receipt.getStatus());
                listDTO.add(exportReceiptListItemDTO);
            }
            return new ResponseEntity<>(listDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    /**
     * Get all egg batch's available egg products.
     *
     * @param facilityId the id of the facility.
     * @return
     */
    @Override
    public ResponseEntity<?> getExportData(Long facilityId) {
        // List dto
        List<EggBatchDataForExportItemDTO> eggBatchListDto = new ArrayList<>();
        List<List<EggProductDataForExportItemDTO>> eggProductListDto = new ArrayList<>();
        List<CreateExportDataItemDTO> dtoList = new ArrayList<>();

        // Process
        List<EggBatch> eggBatchList = new ArrayList<>();
        Optional<List<ImportReceipt>> importReceiptListOptional = importReceiptRepository.findByFacilityId(facilityId);
        if (importReceiptListOptional.isEmpty()) {
            return new ResponseEntity<>(dtoList, HttpStatus.OK);
        }
        // Find egg batches
        for (ImportReceipt importReceipt : importReceiptListOptional.get()) {
            Optional<List<EggBatch>> eggBatchListOptional = eggBatchRepository.findByImportId(importReceipt.getImportId());
            eggBatchListOptional.ifPresent(eggBatchList::addAll);
        }
        // Add egg batches to dto
        for (EggBatch eggBatch : eggBatchList) {
            // Remove new egg batch
            int progress = 0;
            Optional<EggProduct> eggProductOptional = eggProductRepository.
                    findEggProductLastPhase(eggBatch.getEggBatchId());
            if (eggProductOptional.isPresent()) {
                Long incubationPhaseId = eggProductOptional.get().getIncubationPhaseId();
                IncubationPhase incubationPhase = incubationPhaseRepository.findByIncubationPhaseId(incubationPhaseId).get();
                progress = incubationPhase.getPhaseNumber();
            }
            if (progress == 0) continue;
            // Add egg batch to dto
            EggBatchDataForExportItemDTO dtoItem = new EggBatchDataForExportItemDTO();
            // Breed
            Breed breed = breedRepository.findByBreedId(eggBatch.getBreedId()).get();
            dtoItem.setEggBatchId(eggBatch.getEggBatchId());
            dtoItem.setBreedId(breed.getBreedId());
            dtoItem.setBreedName(breed.getBreedName());
            eggBatchListDto.add(dtoItem);
        }

        // List product's phase number
        List<Integer> phases = Arrays.asList(0, 2, 3, 4, 6, 7, 8, 9);
        ArrayList<Integer> phaseList = new ArrayList<>(phases);

        // Check sold out
        for (int i = 0; i < eggBatchListDto.size(); i++) {
            //for (EggBatchDataForExportItemDTO eggBatch : eggBatchListDto) {
            EggBatchDataForExportItemDTO eggBatch = eggBatchListDto.get(i);
            boolean soldOut = true;
            List<EggProduct> eggProductList = eggProductRepository.findByEggBatchId(eggBatch.getEggBatchId()).get();
            List<EggProductDataForExportItemDTO> eggProductDataForExportItemDTOS = new ArrayList<>();

            for (EggProduct eggProduct : eggProductList) {
                IncubationPhase incubationPhase = incubationPhaseRepository
                        .findByIncubationPhaseId(eggProduct.getIncubationPhaseId()).get();
                if (phaseList.contains(incubationPhase.getPhaseNumber())) {
                    // Available egg product
                    if (eggProduct.getCurAmount() > 0) {
                        soldOut = false;
                        EggProductDataForExportItemDTO item = new EggProductDataForExportItemDTO();
                        item.setEggProductId(eggProduct.getProductId());
                        item.setProductName(incubationPhase.getPhaseDescription());
                        item.setCurAmount(eggProduct.getCurAmount());
                        eggProductDataForExportItemDTOS.add(item);
                    }
                }
                // Remove Sold out product
                if (soldOut) {
                    eggProductDataForExportItemDTOS = new ArrayList<>();
                } else {
                    eggProductListDto.add(eggProductDataForExportItemDTOS);
                }
            }
            if (soldOut) {
                eggBatchListDto.remove(eggBatch);
            }
        }

        for (int i = 0; i < eggBatchListDto.size(); i++) {
            CreateExportDataItemDTO dto = new CreateExportDataItemDTO();
            dto.setEggBatch(eggBatchListDto.get(i));
            dto.setEggProductList(eggProductListDto.get(i));
            dtoList.add(dto);
        }
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    /**
     * Create an export.
     *
     * @param createExportDTO contains customer's id, user's id who create the export, facility's id, export date
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<?> createExport(CreateExportDTO createExportDTO) {
        // Check input
        // Customer id
        if (createExportDTO.getCustomerId() == null) { // Customer not selected
            return new ResponseEntity<>("Hãy chọn khách hàng", HttpStatus.BAD_REQUEST);
        }
        // Export date
        LocalDateTime now = LocalDateTime.now();
        // Egg Product List
        List<EggProductCreateExportDTO> eggProductList = createExportDTO.getEggProductList();
        if (eggProductList.size() == 0) {
            return new ResponseEntity<>("Hãy chọn ít nhất 1 sản phẩm để bán", HttpStatus.BAD_REQUEST);
        }
        Set inputSet = new HashSet(eggProductList);
        if (inputSet.size() < eggProductList.size()) {
            return new ResponseEntity<>("Có sản phẩm bị trùng lặp", HttpStatus.BAD_REQUEST);
        }
        // Amount and Price
        float total = 0;
        for (EggProductCreateExportDTO eggProduct : eggProductList) {
            if (eggProduct.getEggProductId() == null) {
                return new ResponseEntity<>("Chưa chọn loại", HttpStatus.BAD_REQUEST);
            }
            if (eggProduct.getExportAmount() <= 0 || eggProduct.getExportAmount() > eggProduct.getCurAmount()) { // Amount export must be less than current amount
                return new ResponseEntity<>("Số lượng xuất không hợp lệ", HttpStatus.BAD_REQUEST);
            }
            if (eggProduct.getPrice() < 0 || eggProduct.getPrice() > 9999999999999.99) { // Price over limit
                return new ResponseEntity<>("Đơn giá không hợp lệ", HttpStatus.BAD_REQUEST);
            }
            if (eggProduct.getVaccine() < 0 || eggProduct.getVaccine() > 9999999999999.99) { // Price over limit
                return new ResponseEntity<>("Đơn giá vaccine không hợp lệ", HttpStatus.BAD_REQUEST);
            }
            if (eggProduct.getExportAmount() * (eggProduct.getVaccine() + eggProduct.getPrice()) > 9999999999999.99) { // over floww
                return new ResponseEntity<>("Tổng giá trị đơn hàng quá lớn", HttpStatus.BAD_REQUEST);
            }
            total += eggProduct.getExportAmount() * (eggProduct.getVaccine() + eggProduct.getPrice());
        }
        try {
            // Set attribute to save export receipt
            ExportReceipt exportReceipt = new ExportReceipt();
            exportReceipt.setCustomerId(createExportDTO.getCustomerId());
            exportReceipt.setUserId(createExportDTO.getUserId());
            exportReceipt.setFacilityId(createExportDTO.getFacilityId());
            exportReceipt.setExportDate(now);
            exportReceipt.setTotal(total);
            exportReceipt.setPaid(0F);
            exportReceipt.setStatus(1);
            ExportReceipt exportReceiptInserted = exportReceiptRepository.save(exportReceipt);
            Long exportId = exportReceiptInserted.getExportId();
            // Save exportDetail, update egg product current amount
            for (EggProductCreateExportDTO dto : createExportDTO.getEggProductList()) {
                System.out.println(dto);
                ExportDetail exportDetail = new ExportDetail();
                exportDetail.setExportId(exportId);
                exportDetail.setProductId(dto.getEggProductId());
                exportDetail.setPrice(dto.getPrice());
                exportDetail.setVaccinePrice(dto.getVaccine());
                exportDetail.setAmount(dto.getExportAmount());
                exportDetail.setStatus(1);
                exportDetailRepository.save(exportDetail);

                EggProduct eggProduct = eggProductRepository.getByProductId(dto.getEggProductId()).get();
                eggProduct.setCurAmount(eggProduct.getCurAmount() - dto.getExportAmount());
                eggProductRepository.save(eggProduct);

            }
            return new ResponseEntity<>("Tạo hóa đơn xuất thành công", HttpStatus.OK);
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>("Tạo hóa đơn xuất thất bại", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * View detail of an export.
     *
     * @param exportId the id of export receipt.
     * @return
     */
    @Override
    public ResponseEntity<?> getExport(Long exportId) {
        Optional<ExportReceipt> exportReceiptOptional = exportReceiptRepository.findById(exportId);
        if (exportReceiptOptional.isPresent()) {
            ExportReceiptDetailDTO dto = new ExportReceiptDetailDTO();
            ExportReceipt exportReceipt = exportReceiptOptional.get();
            // Export
            dto.setExportId(exportId);
            dto.setExportDate(exportReceipt.getExportDate());
            dto.setTotal(exportReceipt.getTotal());
            dto.setPaid(exportReceipt.getPaid());
            // Customer
            Customer customer = customerRepository.findByCustomerId(exportReceipt.getCustomerId()).get();
            dto.setCustomerId(customer.getCustomerId());
            dto.setCustomerName(customer.getCustomerName());
            dto.setCustomerPhone(customer.getCustomerPhone());
            // Egg product - Export detail
            List<EggProductViewExportDetailDTO> eggProductList = new ArrayList<>();
            Optional<List<ExportDetail>> exportDetailListOptional = exportDetailRepository.findByExportId(exportId);

            for (ExportDetail exportDetail : exportDetailListOptional.get()) {
                EggProductViewExportDetailDTO dtoItem = new EggProductViewExportDetailDTO();
                EggProduct eggProduct = eggProductRepository.findById(exportDetail.getProductId()).get();
                IncubationPhase incubationPhase = incubationPhaseRepository
                        .findByIncubationPhaseId(eggProduct.getIncubationPhaseId()).get();
                EggBatch eggBatch = eggBatchRepository.findByEggBatchId(eggProduct.getEggBatchId()).get();
                Breed breed = breedRepository.findByBreedId(eggBatch.getBreedId()).get();
                // Set
                dtoItem.setBreedId(breed.getBreedId());
                dtoItem.setBreedName(breed.getBreedName());
                dtoItem.setProductName(incubationPhase.getPhaseDescription());
                dtoItem.setEggBatchId(eggBatch.getEggBatchId());
                dtoItem.setExportAmount(exportDetail.getAmount());
                dtoItem.setVaccine(exportDetail.getVaccinePrice());
                dtoItem.setPrice(exportDetail.getPrice());
                eggProductList.add(dtoItem);
            }

            dto.setEggProductList(eggProductList);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Không tìm thấy hóa đơn xuất", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update paid amount of export receipt.
     *
     * @param exportId the id of export receipt.
     * @param paid
     * @return
     */
    @Override
    public ResponseEntity<?> updatePaidOfExport(Long exportId, Float paid) {
        Optional<ExportReceipt> exportReceiptOptional = exportReceiptRepository.findById(exportId);
        if (exportReceiptOptional.isPresent()) {
            ExportReceipt exportReceipt = exportReceiptOptional.get();
            if (paid < 0F || paid > exportReceipt.getTotal() || paid > 9999999999999.99) {
                return new ResponseEntity<>("Số tiền đã trả không hợp lệ", HttpStatus.BAD_REQUEST);
            }
            exportReceipt.setPaid(paid);
            exportReceiptRepository.save(exportReceipt);
            return new ResponseEntity<>("Cập nhật số tiền đã trả thành công", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Không tìm thấy hóa đơn xuất", HttpStatus.BAD_REQUEST);
        }
    }
}
