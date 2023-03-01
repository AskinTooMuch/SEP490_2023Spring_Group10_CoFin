package com.example.eims.service.interfaces;

import com.example.eims.dto.specie.EditSpecieDTO;
import com.example.eims.dto.specie.NewSpecieDTO;
import com.example.eims.entity.Specie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ISpecieService {
    public ResponseEntity<String> newSpecie(NewSpecieDTO newSpecieDTO);
    public ResponseEntity<List<Specie>> listSpecie(Long userId);
    public ResponseEntity<Specie> getSpecie(Long specieId);
    public ResponseEntity<Specie> saveSpecie(EditSpecieDTO editSpecieDTO);
    public ResponseEntity<String> deleteSpecie(Long specieId);
}
