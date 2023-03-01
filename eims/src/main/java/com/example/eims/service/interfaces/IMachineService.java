package com.example.eims.service.interfaces;

import com.example.eims.dto.machine.CreateMachineDTO;
import com.example.eims.dto.machine.UpdateMachineDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface IMachineService {
    public ResponseEntity<?> getAllMachine(Long facilityId);
    public ResponseEntity<?> getMachine(Long machineId);
    public ResponseEntity<?> createMachine(CreateMachineDTO createMachineDTO);
    public ResponseEntity<?> showFormUpdate(Long machineId);
    public ResponseEntity<?> updateMachine(Long machineId, UpdateMachineDTO updateMachineDTO);
    public ResponseEntity<?> deleteCustomer(Long machineId);
    public ResponseEntity<?> getAllSupplierPaging(Long facilityId, Integer page, Integer size, String sort);
}
