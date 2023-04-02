/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 02/03/2023   1.0         DuongVV     First Deploy<br>
 * 29/03/2023   1.1         DuongVV     Update checks<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.eggBatch.CreateEggBatchDTO;
import com.example.eims.dto.eggBatch.EggBatchViewImportDTO;
import com.example.eims.dto.importReceipt.CreateImportDTO;
import com.example.eims.dto.importReceipt.ImportReceiptDetailDTO;
import com.example.eims.dto.importReceipt.ImportReceiptListItemDTO;
import com.example.eims.dto.importReceipt.ImportReceiptStatisticDTO;
import com.example.eims.entity.Breed;
import com.example.eims.entity.EggBatch;
import com.example.eims.entity.ImportReceipt;
import com.example.eims.entity.Supplier;
import com.example.eims.repository.BreedRepository;
import com.example.eims.repository.EggBatchRepository;
import com.example.eims.repository.ImportReceiptRepository;
import com.example.eims.repository.SupplierRepository;
import com.example.eims.service.interfaces.IImportReceiptService;
import com.example.eims.utils.StringDealer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ImportReceiptServiceImpl implements IImportReceiptService {

    @Autowired
    private final ImportReceiptRepository importReceiptRepository;
    @Autowired
    private final SupplierRepository supplierRepository;
    @Autowired
    private final EggBatchRepository eggBatchRepository;
    @Autowired
    private final BreedRepository breedRepository;
    private final StringDealer stringDealer;
    @PersistenceContext
    private final EntityManager em;

    public ImportReceiptServiceImpl(ImportReceiptRepository importReceiptRepository,
                                    EggBatchRepository eggBatchRepository, SupplierRepository supplierRepository,
                                    BreedRepository breedRepository, EntityManager em) {
        this.importReceiptRepository = importReceiptRepository;
        this.eggBatchRepository = eggBatchRepository;
        this.supplierRepository = supplierRepository;
        this.breedRepository = breedRepository;
        this.stringDealer = new StringDealer();
        this.em = em;
    }

    /**
     * Get all import bill a facility.
     *
     * @param facilityId the id of facility
     * @return list of import receipts
     */
    @Override
    public ResponseEntity<?> viewImportsByFacility(Long facilityId) {
        Optional<List<ImportReceipt>> importReceiptListOptional = importReceiptRepository.findByFacilityId(facilityId);
        if (importReceiptListOptional.isPresent()) {
            List<ImportReceipt> importReceiptList = importReceiptListOptional.get();
            List<ImportReceiptListItemDTO> listDTO = new ArrayList<>();
            for (ImportReceipt receipt : importReceiptList) {
                ImportReceiptListItemDTO importReceiptListItemDTO = new ImportReceiptListItemDTO();
                importReceiptListItemDTO.setImportId(receipt.getImportId());
                importReceiptListItemDTO.setSupplierId(receipt.getSupplierId());
                Supplier supplier = supplierRepository.findBySupplierId(receipt.getSupplierId()).get();
                importReceiptListItemDTO.setSupplierName(supplier.getSupplierName());
                importReceiptListItemDTO.setImportDate(receipt.getImportDate());
                importReceiptListItemDTO.setTotal(receipt.getTotal());
                importReceiptListItemDTO.setPaid(receipt.getPaid());
                importReceiptListItemDTO.setStatus(receipt.getStatus());
                listDTO.add(importReceiptListItemDTO);
            }
            return new ResponseEntity<>(listDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get all import bill from suppliers of an Owner with paging.
     *
     * @param userId the id of current logged-in user.
     * @param page   the page number
     * @param size   the size of page
     * @param sort   sorting type
     * @return list of import receipts
     */
    @Override
    public ResponseEntity<?> viewImportsByOwnerPaging(Long userId, Integer page, Integer size, String sort) {
        // Get sorting type
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("importId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("importId").descending();
        }
        // Get all customers of the current User with Paging
        Page<ImportReceipt> importReceiptPage = importReceiptRepository.findAllByUserId(userId, PageRequest.of(page, size, sortable));
        List<ImportReceiptListItemDTO> dtoList = new ArrayList<>();
        for (ImportReceipt importReceipt : importReceiptPage) {
            Long importId = importReceipt.getImportId();
            ImportReceipt receipt = importReceiptRepository.findByImportId(importId).get();
            ImportReceiptListItemDTO importReceiptListItemDTO = new ImportReceiptListItemDTO();
            importReceiptListItemDTO.setImportId(receipt.getImportId());
            importReceiptListItemDTO.setSupplierId(receipt.getSupplierId());
            Supplier supplier = supplierRepository.findBySupplierId(receipt.getSupplierId()).get();
            importReceiptListItemDTO.setSupplierName(supplier.getSupplierName());
            importReceiptListItemDTO.setImportDate(receipt.getImportDate());
            importReceiptListItemDTO.setTotal(receipt.getTotal());
            importReceiptListItemDTO.setPaid(receipt.getPaid());
            importReceiptListItemDTO.setStatus(receipt.getStatus());
            dtoList.add(importReceiptListItemDTO);
        }
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    /**
     * Get all import bill from a supplier of an owner.
     *
     * @param supplierId the id of the supplier
     * @return list of import receipts
     */
    @Override
    public ResponseEntity<?> viewImportsBySupplier(Long supplierId) {
        Optional<List<ImportReceipt>> importReceiptList = importReceiptRepository.findBySupplierId(supplierId);
        if (importReceiptList.isPresent()) {
            return new ResponseEntity<>(importReceiptList.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get all import bill from suppliers of an Owner with paging.
     *
     * @param supplierId the id of current logged-in user.
     * @param page       the page number
     * @param size       the size of page
     * @param sort       sorting type
     * @return list of import receipts
     */
    @Override
    public ResponseEntity<?> viewImportsBySupplierPaging(Long supplierId, Integer page, Integer size, String sort) {
        // Get sorting type
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("importId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("importId").descending();
        }
        // Get all customers of the current User with Paging
        Page<ImportReceipt> importReceiptPage = importReceiptRepository.findAllBySupplierId(supplierId, PageRequest.of(page, size, sortable));
        return new ResponseEntity<>(importReceiptPage, HttpStatus.OK);
    }

    /**
     * Get imports statistics of an owner.
     *
     * @param userId the id of the owner
     * @return list of import receipts
     */
    @Override
    public ResponseEntity<?> viewImportStatistic(Long userId) {
        Query query = em.createNamedQuery("getImportReceiptStatisticByUserId");
        query.setParameter(1, userId);
        List<ImportReceiptStatisticDTO> statistics = query.getResultList();
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

    /**
     * Create an import.
     *
     * @param createImportDTO contains supplier's id, user's id who create the import, facility's id, import date,
     *                        total, paid, status, list of import items.
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<?> createImport(CreateImportDTO createImportDTO) {
        // Check input
        // Supplier id
        if (createImportDTO.getSupplierId() == null) { // Supplier not selected
            return new ResponseEntity<>("Hãy chọn nhà cung cấp", HttpStatus.BAD_REQUEST);
        }
        // Import date
        if (createImportDTO.getImportDate() == null || createImportDTO.getImportDate().equals("")) { // Import date empty
            return new ResponseEntity<>("Hãy nhập ngày nhập hóa đơn", HttpStatus.BAD_REQUEST);
        }
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        if (createImportDTO.getImportDate().isAfter(now)) { // Import date must be before today
            String time = now.toString().replace("T", " ");
            String timeNow = time.subSequence(0, 18).toString();
            return new ResponseEntity<>("Ngày nhập hóa đơn phải trước bây giờ (" + timeNow + ")",
                    HttpStatus.BAD_REQUEST);
        }
        Long dateDiff = stringDealer.dateDiff(Date.valueOf(createImportDTO.getImportDate().toLocalDate()),
                Date.valueOf(LocalDate.now()));
        if (dateDiff > 3) { // Import date must be less than 3 days before today
            return new ResponseEntity<>("Hãy nhập ngày nhập hóa đơn không cách hiện tại quá 3 ngày", HttpStatus.BAD_REQUEST);
        }
        // Egg batch list
        List<CreateEggBatchDTO> eggBatchDTOList = createImportDTO.getEggBatchList();
        if (eggBatchDTOList.size() == 0) {
            return new ResponseEntity<>("Hãy tạo ít nhất 1 lô trứng", HttpStatus.BAD_REQUEST);
        }
        // Amount and Price
        float total = 0;
        for (CreateEggBatchDTO eggBatch : createImportDTO.getEggBatchList()) {
            if (eggBatch.getBreedId() == null) {
                return new ResponseEntity<>("Chưa chọn loại", HttpStatus.BAD_REQUEST);
            }
            if (eggBatch.getAmount() <= 0) { // Amount negative
                return new ResponseEntity<>("Số lượng trứng phải lớn hơn 0", HttpStatus.BAD_REQUEST);
            }
            if (eggBatch.getPrice() < 0) { // Price negative
                return new ResponseEntity<>("Đơn giá phải lớn hơn hoặc bằng 0", HttpStatus.BAD_REQUEST);
            }
            if (eggBatch.getPrice() > 9999999999999.99) { // Price over limit
                return new ResponseEntity<>("Đơn giá không được vượt quá 9999999999999.99", HttpStatus.BAD_REQUEST);
            }
            if (eggBatch.getPrice() * eggBatch.getAmount() > 9999999999999.99) { // Price over limit
                return new ResponseEntity<>("Giá trị lô trứng không được vượt quá 9999999999999.99",
                        HttpStatus.BAD_REQUEST);
            }
            total += eggBatch.getPrice() * eggBatch.getAmount();
        }
        try {
            // Set attribute to save import receipt
            ImportReceipt importReceipt = new ImportReceipt();
            importReceipt.setUserId(createImportDTO.getUserId());
            importReceipt.setSupplierId(createImportDTO.getSupplierId());
            importReceipt.setFacilityId(createImportDTO.getFacilityId());
            importReceipt.setImportDate(createImportDTO.getImportDate());
            importReceipt.setTotal(total);
            importReceipt.setPaid(0F);
            importReceipt.setStatus(1);
            ImportReceipt importInserted = importReceiptRepository.save(importReceipt);
            Long importId = importInserted.getImportId();
            // Save egg batch(s)
            for (CreateEggBatchDTO eggBatchDTO : createImportDTO.getEggBatchList()) {
                EggBatch eggBatch = new EggBatch();
                eggBatch.setImportId(importId);
                eggBatch.setBreedId(eggBatchDTO.getBreedId());
                eggBatch.setAmount(eggBatchDTO.getAmount());
                eggBatch.setPrice(eggBatchDTO.getPrice());
                eggBatch.setNeedAction(1);
                eggBatch.setDateAction(now.plusHours(7));
                eggBatch.setStatus(1);
                eggBatchRepository.save(eggBatch);
            }
            return new ResponseEntity<>("Tạo hóa đơn nhập thành công", HttpStatus.OK);
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * View detail of an import.
     *
     * @param importId the id of import receipt.
     * @return
     */
    @Override
    public ResponseEntity<?> getImport(Long importId) {
        Optional<ImportReceipt> importReceiptOptional = importReceiptRepository.findByImportId(importId);
        if (importReceiptOptional.isPresent()) {
            ImportReceiptDetailDTO importReceiptDetailDTO = new ImportReceiptDetailDTO();
            ImportReceipt importReceipt = importReceiptOptional.get();
            importReceiptDetailDTO.setImportId(importId);
            importReceiptDetailDTO.setTotal(importReceipt.getTotal());
            importReceiptDetailDTO.setPaid(importReceipt.getPaid());
            // Supplier
            Supplier supplier = supplierRepository.findBySupplierId(importReceipt.getSupplierId()).get();
            importReceiptDetailDTO.setSupplierId(supplier.getSupplierId());
            importReceiptDetailDTO.setSupplierName(supplier.getSupplierName());
            importReceiptDetailDTO.setSupplierPhone(supplier.getSupplierPhone());
            importReceiptDetailDTO.setImportDate(importReceipt.getImportDate());
            // Egg batch
            List<EggBatchViewImportDTO> eggBatchViewImportDTOList = new ArrayList<>();
            List<EggBatch> eggBatchList = eggBatchRepository.findByImportId(importId).get();
            for (EggBatch eggBatch : eggBatchList) {
                EggBatchViewImportDTO eggBatchViewImportDTO = new EggBatchViewImportDTO();
                eggBatchViewImportDTO.setEggBatchId(eggBatch.getEggBatchId());
                eggBatchViewImportDTO.setBreedId(eggBatch.getBreedId());
                Breed breed = breedRepository.findByBreedId(eggBatch.getBreedId()).get();
                eggBatchViewImportDTO.setBreedName(breed.getBreedName());
                eggBatchViewImportDTO.setAmount(eggBatch.getAmount());
                eggBatchViewImportDTO.setPrice(eggBatch.getPrice());
                eggBatchViewImportDTOList.add(eggBatchViewImportDTO);
            }
            importReceiptDetailDTO.setEggBatchList(eggBatchViewImportDTOList);
            return new ResponseEntity<>(importReceiptDetailDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update paid amount of import receipt.
     *
     * @param importId the id of import receipt.
     * @param paid
     * @return
     */
    @Override
    public ResponseEntity<?> updatePaidOfImport(Long importId, Float paid) {
        Optional<ImportReceipt> importReceiptOptional = importReceiptRepository.findByImportId(importId);
        if (importReceiptOptional.isPresent()) {
            ImportReceipt importReceipt = importReceiptOptional.get();
            if (paid < 0F || paid > importReceipt.getTotal() || paid > 9999999999999.99) {
                return new ResponseEntity<>("Số tiền đã trả không hợp lệ", HttpStatus.BAD_REQUEST);
            }
            importReceipt.setPaid(paid);
            importReceiptRepository.save(importReceipt);
            return new ResponseEntity<>("Cập nhật số tiền đã trả", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}

