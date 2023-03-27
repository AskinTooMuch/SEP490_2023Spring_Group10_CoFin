package com.example.eims.service.impl;

import com.example.eims.dto.cost.CostDetailDTO;
import com.example.eims.dto.cost.CreateCostDTO;
import com.example.eims.dto.cost.UpdateCostDTO;
import com.example.eims.entity.Cost;
import com.example.eims.entity.Facility;
import com.example.eims.repository.CostRepository;
import com.example.eims.repository.FacilityRepository;
import com.example.eims.repository.UserRepository;
import com.example.eims.service.interfaces.ICostService;
import com.example.eims.utils.StringDealer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CostServiceImpl implements ICostService {

    private final StringDealer stringDealer = new StringDealer();

    @Autowired
    UserRepository userRepository;

    @Autowired
    CostRepository costRepository;

    @Autowired
    FacilityRepository facilityRepository;
    /**
     * Get all cost of a user
     *
     * @param userId owner's id
     * @return List of cost
     */
    @Override
    public ResponseEntity<?> getAllCost(Long userId) {
        if(!userRepository.existsById(userId)){
            return new ResponseEntity<>("Tài khoản không hợp lệ", HttpStatus.BAD_REQUEST);
        }
        Optional<List<Cost>> costOpt = costRepository.findAllByUserId(userId);
        if(costOpt.isPresent()){
            List<Cost> costList= costOpt.get();
            List<CostDetailDTO> costDetailList = new ArrayList<>();
            for (Cost cost: costList){
                CostDetailDTO costDetailDTO = new CostDetailDTO();
                costDetailDTO.getFromEntity(cost);
                costDetailList.add(costDetailDTO);
            }
            for (CostDetailDTO h: costDetailList){

                System.out.println(h.getCostItem());
            }
            return new ResponseEntity<>(costDetailList,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get all information of a cost
     *
     * @param costId Cost's id
     * @return message
     */
    @Override
    public ResponseEntity<?> getCostById(Long costId) {
        Optional<Cost> costOptional = costRepository.findById(costId);
        if(costOptional.isPresent()){
            Cost cost = costOptional.get();
            CostDetailDTO costDetailDTO = new CostDetailDTO();
            costDetailDTO.getFromEntity(cost);
            return new ResponseEntity<>(costDetailDTO, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Save new cost information to the database
     *
     * @param createCostDTO contain information of cost to be added to the database
     * @return message
     */
    @Override
    public ResponseEntity<?> createCost(CreateCostDTO createCostDTO) {
        Optional<Facility> facility = facilityRepository.findByFacilityId(createCostDTO.getFacilityId());
        if(!facility.isPresent()){ //check if facility exist
            return new ResponseEntity<>("Cơ sở hiện không được hoạt động", HttpStatus.BAD_REQUEST);
        }
        if(facility.get().getStatus() == 0){ // check if facility is active
            return new ResponseEntity<>("Cơ sở hiện không được hoạt động", HttpStatus.BAD_REQUEST);
        }
        // check if user is valid
        if(!userRepository.existsById(createCostDTO.getUserId())){
            return new ResponseEntity<>("Tài khoản không hợp lệ", HttpStatus.BAD_REQUEST);
        }
        // check cost item
        if(createCostDTO.getCostItem() == null || stringDealer.trimMax(createCostDTO.getCostItem()).equals("")){
            return new ResponseEntity<>("Tên chi phí không thể để trống", HttpStatus.BAD_REQUEST);
        }
        String costItem = stringDealer.trimMax(createCostDTO.getCostItem());
        // check cost amount
        Float costAmount = createCostDTO.getCostAmount();
        if(costAmount <= 0){
            return new ResponseEntity<>("Tổng chi phí phải lớn hơn 0", HttpStatus.BAD_REQUEST);
        }
        // check paid amount
        Float paidAmount = createCostDTO.getPaidAmount();
        if(paidAmount < 0){
            return new ResponseEntity<>("Số tiền đã thanh toán không được bé hơn 0", HttpStatus.BAD_REQUEST);
        }
        if(paidAmount > costAmount){
            return new ResponseEntity<>("Số tiền đã thanh toán không được lớn hơn tổng chi phí", HttpStatus.BAD_REQUEST);
        }
        // check if note exist
        String note = "";
        if(createCostDTO.getNote() != null && !stringDealer.trimMax(createCostDTO.getNote()).equals("")){
            note = createCostDTO.getNote();
        }
        // Set cost information
        Date issueDate = Date.valueOf(LocalDate.now());
        Cost newCost = new Cost();
        newCost.setUserId(createCostDTO.getUserId());
        newCost.setFacilityId(createCostDTO.getFacilityId());
        newCost.setCostItem(costItem);
        newCost.setCostAmount(costAmount);
        newCost.setPaidAmount(paidAmount);
        newCost.setIssueDate(issueDate);
        newCost.setNote(note);
        newCost.setStatus(true);

        // save new cost
        costRepository.save(newCost);
        return new ResponseEntity<>("Thêm chi phí thành công", HttpStatus.OK);
    }

    /**
     * Save new cost information to the database
     *
     * @param updateCostDTO contain cost's new information
     * @return message
     */
    @Override
    public ResponseEntity<?> updateCost(UpdateCostDTO updateCostDTO) {
        Optional<Facility> facility = facilityRepository.findByFacilityId(updateCostDTO.getFacilityId());
        if(!facility.isPresent()){ //check if facility exist
            return new ResponseEntity<>("Cơ sở hiện không được hoạt động", HttpStatus.BAD_REQUEST);
        }
        if(facility.get().getStatus() == 0){ // check if facility is active
            return new ResponseEntity<>("Cơ sở hiện không được hoạt động", HttpStatus.BAD_REQUEST);
        }
        // check if user is valid
        if(!userRepository.existsById(updateCostDTO.getUserId())){
            return new ResponseEntity<>("Tài khoản không hợp lệ", HttpStatus.BAD_REQUEST);
        }
        // check cost item
        if(updateCostDTO.getCostItem() == null || stringDealer.trimMax(updateCostDTO.getCostItem()).equals("")){
            return new ResponseEntity<>("Tên chi phí không thể để trống", HttpStatus.BAD_REQUEST);
        }
        String costItem = stringDealer.trimMax(updateCostDTO.getCostItem());
        // check cost amount
        Float costAmount = updateCostDTO.getCostAmount();
        if(costAmount <= 0){
            return new ResponseEntity<>("Tổng chi phí phải lớn hơn 0", HttpStatus.BAD_REQUEST);
        }
        // check paid amount
        Float paidAmount = updateCostDTO.getPaidAmount();
        if(paidAmount <= 0){
            return new ResponseEntity<>("Số tiền đã thanh toán không được bé hơn 0", HttpStatus.BAD_REQUEST);
        }
        if(paidAmount > costAmount){
            return new ResponseEntity<>("Số tiền đã thanh toán không được lớn hơn tổng chi phí", HttpStatus.BAD_REQUEST);
        }
        // check if note exist
        String note = "";
        if(updateCostDTO.getNote() != null && !stringDealer.trimMax(updateCostDTO.getNote()).equals("")){
            note = updateCostDTO.getNote();
        }
        // Set cost information
        Date issueDate = Date.valueOf(LocalDate.now());
        Cost newCost = new Cost();
        newCost.setCostId(updateCostDTO.getCostId());
        newCost.setUserId(updateCostDTO.getUserId());
        newCost.setFacilityId(updateCostDTO.getFacilityId());
        newCost.setCostItem(costItem);
        newCost.setCostAmount(costAmount);
        newCost.setPaidAmount(paidAmount);
        newCost.setIssueDate(issueDate);
        newCost.setNote(note);
        newCost.setStatus(true);

        // save new cost
        costRepository.save(newCost);
        return new ResponseEntity<>("Thêm chi phí thành công", HttpStatus.OK);
    }

    /**
     * Find cost by name
     *
     * @param userId   owner's id
     * @param costName cost name to find
     * @return list of Cost
     */
    @Override
    public ResponseEntity<?> searchCostByName(Long userId, String costName) {
        // check if user is valid
        if(!userRepository.existsById(userId)){
            return new ResponseEntity<>("Tài khoản không hợp lệ", HttpStatus.BAD_REQUEST);
        }
        Optional<List<Cost>> costList = costRepository.searchCostByName(userId,costName);
        if(!costList.isPresent()){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        List<Cost> cost = costList.get();
        List<CostDetailDTO> costDetailList = new ArrayList<>();
        for (Cost cost1: cost){
            CostDetailDTO costDetailDTO = new CostDetailDTO();
            costDetailDTO.getFromEntity(cost1);
            costDetailList.add(costDetailDTO);
        }
        return new ResponseEntity<>(costDetailList, HttpStatus.OK);
    }

    /**
     * Get all of user's cost with Paging.
     *
     * @param userId the id of the Owner
     * @param page   the page number
     * @param size   the size of page
     * @param sort   sorting type
     * @return list of Cost
     */
    @Override
    public ResponseEntity<?> getAllCostPaging(Long userId, Integer page, Integer size, String sort) {
        return null;
    }
}
