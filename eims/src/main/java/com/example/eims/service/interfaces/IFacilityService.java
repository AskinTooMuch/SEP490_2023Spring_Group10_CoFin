package com.example.eims.service.interfaces;

import com.example.eims.dto.facility.CreateFacilityDTO;
import com.example.eims.dto.facility.UpdateFacilityDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface IFacilityService {
    public ResponseEntity<?> getAllFacility();
    public ResponseEntity<?> getFacilityOfOwner(Long userId);
    public ResponseEntity<?> createFacility(CreateFacilityDTO createFacilityDTO);
    public ResponseEntity<?> showFormUpdate(Long facilityId);
    public ResponseEntity<?> updateFacility(Long facilityId, UpdateFacilityDTO updateFacilityDTO);
    public ResponseEntity<?> deleteFacility(Long facilityId);
}
