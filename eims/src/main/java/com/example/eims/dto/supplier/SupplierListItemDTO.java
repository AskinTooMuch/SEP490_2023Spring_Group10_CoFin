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
