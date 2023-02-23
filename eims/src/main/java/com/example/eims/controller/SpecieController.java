package com.example.eims.controller;

import com.example.eims.dto.specie.NewSpecieDTO;
import com.example.eims.entity.Specie;
import com.example.eims.entity.User;
import com.example.eims.repository.SpecieRepository;
import com.example.eims.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/specie")
public class SpecieController {
    @Autowired
    private SpecieRepository specieRepository;

    @Autowired
    UserRepository userRepository;
    @PersistenceContext
    private EntityManager em;
    @PostMapping("/new")
    //@Secured({"ROLE_OWNER"})
    public ResponseEntity<String> sendUserDetail(@RequestBody NewSpecieDTO newSpecieDTO){
        System.out.println(newSpecieDTO);
        Optional<User> userOpt = userRepository.findByPhone(newSpecieDTO.getPhone());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Specie specie = new Specie();
            specie.setSpecieId(100L);
            specie.setUserId(user.getUserId());
            specie.setSpecieName(newSpecieDTO.getSpecieName());
            specie.setIncubationPeriod(newSpecieDTO.getIncubationPeriod());
            specie.setStatus(true);
            specieRepository.save(specie);
            return new ResponseEntity<>("Specie added successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Account not found with phone number "+newSpecieDTO.getPhone(), HttpStatus.BAD_REQUEST);
    }
}
