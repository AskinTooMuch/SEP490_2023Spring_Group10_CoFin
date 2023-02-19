package com.example.eims.repository;

import com.example.eims.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByRoleName(String roleName);

    Optional<UserRole> findByRoleId(Long roleId);
}
