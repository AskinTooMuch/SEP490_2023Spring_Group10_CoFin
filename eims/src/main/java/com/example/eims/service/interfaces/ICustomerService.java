package com.example.eims.service.interfaces;

import com.example.eims.dto.customer.CreateCustomerDTO;
import com.example.eims.dto.customer.UpdateCustomerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface ICustomerService {
    /**
     * Get all of user's customers.
     *
     * @param userId the id of current logged-in user.
     * @return list of Customers
     */
    public ResponseEntity<?> getAllCustomer(Long userId);
    public ResponseEntity<?> getCustomer(Long customerId);
    public ResponseEntity<?> createCustomer(CreateCustomerDTO createCustomerDTO);
    public ResponseEntity<?> showFormUpdate(Long customerId);
    public ResponseEntity<?> updateCustomer(Long customerId, UpdateCustomerDTO updateCustomerDTO);
    public ResponseEntity<?> deleteCustomer(Long customerId);
    public ResponseEntity<?> searchCustomer(String key, Long userId);
    public ResponseEntity<?> getAllCustomerPaging(Long userId, Integer page, Integer size, String sort);
}
