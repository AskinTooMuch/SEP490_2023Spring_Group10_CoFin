/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 05/03/2023    1.0        DuongVV          First Deploy<br>
 */

package com.example.eims.dto.supplier;

import com.example.eims.entity.Supplier;
import lombok.Data;

@Data
public class SupplierListItemDTO {
    private Long supplierId;
    private String supplierName;
    private String phone;
    private Float total;
    private int status;

    public void getFromEntity(Supplier supplier) {
        this.supplierId = supplier.getSupplierId();
        this.supplierName = supplier.getSupplierName();
        this.phone = supplier.getSupplierPhone();
        this.status = supplier.getStatus();
    }
}
