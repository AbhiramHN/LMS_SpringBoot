package com.lms.repository;

import com.lms.entity.LeaveBalance;
import com.lms.entity.LeaveBalanceId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, LeaveBalanceId>
{
    List<LeaveBalance> findByIdEmployeeId(String employeeId);
}