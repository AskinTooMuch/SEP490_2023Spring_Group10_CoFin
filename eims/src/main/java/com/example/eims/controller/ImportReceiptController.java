/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 22/02/2023    1.0        DuongVV          First Deploy<br>
 * 28/02/2023    2.0        DuongVV          Update function, add Paging<br>
 * 01/03/2023    2.1        ChucNV           (Line 64, method viewImportsByUserPaging) @GetMapping("/allByUserPaging") -> @GetMapping("/allImportByUserPaging")<br>
 * 02/03/2023    3.0        DuongVV          New code structure<br>
 */

package com.example.eims.controller;
import com.example.eims.service.interfaces.IImportReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/import")
public class ImportReceiptController {
    @Autowired
    private IImportReceiptService importReceiptService;

    /**
     * Get all import bill from suppliers of an Owner.
     *
     * @param userId the id of supplier
     * @return list of import receipts
     */
    @GetMapping("/allByUser")
    public ResponseEntity<?> viewImportsByUser(@RequestParam Long userId) {
        return importReceiptService.viewImportsByUser(userId);
    }

    /**
     * Get all import bill from suppliers of an Owner with paging.
     *
     * @param userId the id of current logged-in user.
     * @param page the page number
     * @param size the size of page
     * @param sort sorting type
     * @return list of import receipts
     */
    @GetMapping("/allImportByUserPaging")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> viewImportsByUserPaging(@RequestParam(name = "userId") Long userId,
                                                  @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                                  @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
        return importReceiptService.viewImportsByUserPaging(userId, page, size, sort);
    }

    /**
     * Get all import bill from a supplier of an owner.
     *
     * @param supplierId the id of the supplier
     * @return list of import receipts
     */
    @GetMapping("/allBySupplier")
    public ResponseEntity<?> viewImportsBySupplier(@RequestParam Long supplierId) {
        return importReceiptService.viewImportsBySupplier(supplierId);
    }

    /**
     * Get all import bill from suppliers of an Owner with paging.
     *
     * @param supplierId the id of current logged-in user.
     * @param page the page number
     * @param size the size of page
     * @param sort sorting type
     * @return list of import receipts
     */
    @GetMapping("/allByUserPaging")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> viewImportsBySupplierPaging(@RequestParam(name = "supplierId") Long supplierId,
                                                     @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                     @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                                     @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
        return importReceiptService.viewImportsBySupplierPaging(supplierId, page, size, sort);
    }

    /**
     * Get imports statistics of an owner.
     *
     * @param userId the id of the owner
     * @return list of import receipts
     */
    @GetMapping("/statistic")
    public ResponseEntity<?> viewImportStatistic(@RequestParam Long userId) {
        return importReceiptService.viewImportStatistic(userId);
    }
}
