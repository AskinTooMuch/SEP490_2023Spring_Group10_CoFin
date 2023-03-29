package com.example.eims.repository;

import com.example.eims.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PayrollRepository extends JpaRepository<Payroll,Long> {
    Optional<List<Payroll>> findAllByOwnerId(Long ownerId);

    Optional<Payroll> findByPayrollId(Long payrollId);

    @Query(value = "select A.*\n" +
            "from eims.payroll as A inner join eims.user as B\n" +
            "on A.employee_id = B.user_id\n" +
            "where A.owner_id = ?1 AND (B.username like %?2% or A.phone like %?2%)", nativeQuery = true)
    Optional<List<Payroll>> searchPayroll(Long ownerId, String searchKey);
}
