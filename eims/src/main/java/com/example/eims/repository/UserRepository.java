package com.example.eims.repository;

import com.example.eims.dto.user.UserDetailDTO;
import com.example.eims.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhone(String phone);
    Optional<User> findByPhoneAndPassword(String phone, String password);
    Boolean existsByPhone(String phone);

}
