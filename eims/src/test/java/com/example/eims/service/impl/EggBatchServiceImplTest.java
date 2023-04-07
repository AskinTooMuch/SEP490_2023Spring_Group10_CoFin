 /*
  * Copyright (C) 2023, FPT University <br>
  * SEP490 - SEP490_G10 <br>
  * EIMS <br>
  * Eggs Incubating Management System <br>
  *
  * Record of change:<br>
  * DATE         Version     Author      DESCRIPTION<br>
  * 06/04/2023   1.0         DuongVV     First Deploy<br>
  */

package com.example.eims.service.impl;

import com.example.eims.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class EggBatchServiceImplTest {
    @Mock
    EggBatchRepository eggBatchRepository;
    @Mock
    ImportReceiptRepository importReceiptRepository;
    @Mock
    SupplierRepository supplierRepository;
    @Mock
    SpecieRepository specieRepository;
    @Mock
    BreedRepository breedRepository;
    @Mock
    EggProductRepository eggProductRepository;
    @Mock
    EggLocationRepository eggLocationRepository;
    @Mock
    MachineRepository machineRepository;
    @Mock
    IncubationPhaseRepository incubationPhaseRepository;
    @Mock
    MachineTypeRepository machineTypeRepository;
    @InjectMocks
    EggBatchServiceImpl eggBatchService;
    @Test
    void getEggBatch() {

    }

    @Test
    void viewListEggBatch() {
    }

    @Test
    void updateEggBatch() {
    }

    @Test
    void setDoneEggBatch() {
    }
}