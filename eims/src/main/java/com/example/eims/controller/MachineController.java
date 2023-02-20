package com.example.eims.controller;

import com.example.eims.dto.NewMachineDTO;
import com.example.eims.entity.Machine;
import com.example.eims.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/machine")
public class MachineController {
    @Autowired
    private MachineRepository machineRepository;

    /**
     * API to get all of their machines.
     * The facilityId is the id of current logged-in user's selected facility.
     *
     * @param facilityId
     * @return list of Machines
     */
    @GetMapping("/all")
    public List<Machine> getAllCustomer(Long facilityId) {
        // Get all machines of the current facility
        List<Machine> machineList = machineRepository.findByFacilityId(facilityId);
        return machineList;
    }

    /**
     * API to get a machine.
     * id is the id of the machine
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Machine getMachine(@PathVariable Long id) {
        // Get a machine of the current facility
        Machine machine = machineRepository.findById(id).get();
        return machine;
    }

    /**
     * API to create a machine of a facility.
     * The DTO contains the facility id, machine type id, name, max and current capacity
     *
     * @param newMachineDTO
     * @return
     */
    @PostMapping()
    public ResponseEntity<?> createMachine(NewMachineDTO newMachineDTO) {
        // Retrieve machine information and create new machine
        Machine machine = new Machine();
        machine.setMachineTypeId(newMachineDTO.getMachineTypeId());
        machine.setFacilityId(newMachineDTO.getMachineTypeId());
        machine.setMachineName(newMachineDTO.getName());
        machine.setMaxCapacity(newMachineDTO.getMaxCapacity());
        machine.setCurCapacity(0);
        machine.setAddedDate(new Date(new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime())));
        machine.setActive(true);
        machineRepository.save(machine);
        // Save
        return new ResponseEntity<>("Machine created!", HttpStatus.OK);
    }

    /**
     * API to update a machine of a facility.
     * The DTO contains the facility id, machine type id, name, max and current capacity, status
     * id is the id of the machine
     *
     * @param newMachineDTO, id
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMachine(@PathVariable Long id, NewMachineDTO newMachineDTO) {
        // Retrieve machine's new information
        Machine machine = new Machine();
        machine.setMachineName(newMachineDTO.getName());
        machine.setMaxCapacity(newMachineDTO.getMaxCapacity());
        machine.setCurCapacity(newMachineDTO.getCurCapacity());
        machine.setActive(newMachineDTO.isActive());
        // Save
        machineRepository.save(machine);
        return new ResponseEntity<>("Machine updated!", HttpStatus.OK);
    }

    /**
     * API to delete a machine of a facility.
     * id is the id of the machine
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        // Delete
        machineRepository.deleteById(id);
        return new ResponseEntity<>("Machine deleted!", HttpStatus.OK);
    }
}
