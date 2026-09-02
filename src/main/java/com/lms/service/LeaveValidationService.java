package com.lms.service;

import com.lms.dto.request.LeaveApplicationRequest;
import com.lms.entity.Employee;
import com.lms.enums.LeaveStatus;
import com.lms.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class LeaveValidationService implements LeaveValidatable
{
    @Autowired
    protected LeaveRequestRepository leaveRequestRepository;

    @Override
    public final boolean validate(Employee employee, LeaveApplicationRequest request)
    {
        long numberOfDays = calculateNumberOfDays(request);

        if (numberOfDays <= 0)
        {
            return false;
        }

        if (request.getFromDate().isAfter(request.getToDate()))
        {
            return false;
        }

        if (request.getFromDate().isBefore(LocalDate.now()))
        {
            return false;
        }

        if (hasPendingLeave(employee))
        {
            return false;
        }

        if (hasOverlappingLeave(employee, request))
        {
            return false;
        }

        return validateSpecificLeave(employee, request, numberOfDays);
    }

    protected long calculateNumberOfDays(LeaveApplicationRequest request)
    {
        return ChronoUnit.DAYS.between(request.getFromDate(), request.getToDate()) + 1;
    }

    protected boolean hasPendingLeave(Employee employee)
    {
        return leaveRequestRepository.existsByEmployeeIdAndStatus(employee.getEmployeeId(), LeaveStatus.PENDING);
    }

    protected boolean hasOverlappingLeave(Employee employee, LeaveApplicationRequest request)
    {
        return leaveRequestRepository.existsByEmployeeIdAndFromDateLessThanEqualAndToDateGreaterThanEqual(
                        employee.getEmployeeId(), request.getToDate(), request.getFromDate());
    }

    protected abstract boolean validateSpecificLeave(Employee employee, LeaveApplicationRequest request, long numberOfDays);
}