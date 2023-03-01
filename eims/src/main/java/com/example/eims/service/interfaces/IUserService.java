package com.example.eims.service.interfaces;

import com.example.eims.dto.user.UpdateUserDTO;
import com.example.eims.dto.user.UserDetailDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface IUserService {
    public ResponseEntity<UserDetailDTO> sendUserDetail(Long userId);
    public ResponseEntity<?> getAllUser();
    public ResponseEntity<?> getAllUser(Integer page, Integer size, String sort);
    public ResponseEntity<?> showFormUpdate(Long userId);
    public ResponseEntity<?> updateUser(UpdateUserDTO updateUserDTO);
}
