package com.example.eims.repository;

import com.example.eims.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByPhoneNumber(String phone);
}
