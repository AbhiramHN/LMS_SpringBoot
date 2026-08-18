package com.lms.repository;

import com.lms.entity.LeaveRequest;
import com.lms.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer>
{
    boolean existsByEmployeeIdAndStatus(String employeeId, LeaveStatus status);

    List<LeaveRequest> findByEmployeeId(String employeeId);

    boolean existsByEmployeeIdAndFromDateLessThanEqualAndToDateGreaterThanEqual(String employeeId, LocalDate toDate, LocalDate fromDate);
}