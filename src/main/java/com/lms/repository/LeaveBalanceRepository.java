package com.lms.repository;

import com.lms.entity.LeaveBalance;
import com.lms.entity.LeaveBalanceId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveBalanceRepository
        extends JpaRepository<LeaveBalance, LeaveBalanceId>
{
}