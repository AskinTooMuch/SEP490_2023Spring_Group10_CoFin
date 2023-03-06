/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 19/02/2023    1.0        DuongVV          First Deploy<br>
 * 06/03/2023    1.1        DuongVV          Add function<br>
 */

package com.example.eims.dto.facility;

import com.example.eims.entity.Facility;
import lombok.Data;

@Data
public class UpdateFacilityDTO {
    private Long facilityId;
    private String facilityName;
    private String facilityAddress;
    private String foundDate;
    private String businessLicenseNumber;
    private String hotline;
    private int status;

    public void getFromEntity(Facility facility) {
        this.facilityId = facility.getFacilityId();
        this.facilityName = facility.getFacilityName();
        this.facilityAddress = facility.getFacilityAddress();
        this.foundDate = facility.getFacilityFoundDate().toString();
        this.businessLicenseNumber = facility.getBusinessLicenseNumber();
        this.hotline = facility.getHotline();
        this.status = facility.getStatus();
    }
}
