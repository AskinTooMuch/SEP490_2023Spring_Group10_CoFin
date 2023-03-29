/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 28/03/2023    1.0        DuongVV          First Deploy<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.eggProduct.*;
import com.example.eims.service.interfaces.IEggProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EggProductServiceImpl implements IEggProductService {
    @PersistenceContext
    private final EntityManager em;

    public EggProductServiceImpl(EntityManager em) {
        this.em = em;
    }

    /**
     * Get all available egg product.
     *
     * @param facilityId the id of the facility
     * @return
     */
    @Override
    public ResponseEntity<?> getAllAvailableProduct(Long facilityId) {
        EggProductAvailableStocksDTO stockList = new EggProductAvailableStocksDTO();
        // Available eggs
        Query q = em.createNamedQuery("viewStockEgg");
        q.setParameter(1, facilityId);
        List<EggProductViewStockEggDTO> eggStocks = q.getResultList();
        stockList.setEggStocks(eggStocks);

        // Available eggs
        q = em.createNamedQuery("viewStockPoultry");
        q.setParameter(1, facilityId);
        List<EggProductViewStockPoultryDTO> poultryStocks = q.getResultList();
        stockList.setPoultryStocks(poultryStocks);

        return new ResponseEntity<>(stockList, HttpStatus.OK);
    }

    /**
     * Get all egg product of a phase.
     *
     * @param facilityId  the id of the facility
     * @param phaseNumber the phase number of egg product
     * @return
     */
    @Override
    public ResponseEntity<?> getAvailableProductByPhase(Long facilityId, int phaseNumber) {
        List<Integer> phaseEgg = Arrays.asList(0, 2, 3, 4, 6);
        List<Integer> phasePoultry = Arrays.asList(7, 8, 9);

        // Egg's phases
        if (phaseEgg.contains(phaseNumber)) {
            // Available eggs
            Query q = em.createNamedQuery("viewStockAnEgg");
            q.setParameter(1, facilityId);
            q.setParameter(2, phaseNumber);
            List<EggProductViewStockAnEggDTO> eggStocks = q.getResultList();
            return new ResponseEntity<>(eggStocks, HttpStatus.OK);
        }

        // Poultry's phases
        if (phasePoultry.contains(phaseNumber)) {
            // Available eggs
            Query q = em.createNamedQuery("viewStockAPoultry");
            q.setParameter(1, facilityId);
            q.setParameter(2, phaseNumber);
            List<EggProductViewStockAPoultryDTO> poultryStock = q.getResultList();
            return new ResponseEntity<>(poultryStock, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
