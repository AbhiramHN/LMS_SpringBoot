package com.lms.validation;

import com.lms.dto.request.LeaveApplicationRequest;
import com.lms.entity.Employee;
import com.lms.enums.LeaveStatus;
import com.lms.enums.LeaveType;
import com.lms.service.LeaveValidatable;
import com.lms.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class LeaveWithoutPayValidation implements LeaveValidatable
{
    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Override
    public LeaveType getLeaveType()
    {
        return LeaveType.LWP;
    }

    @Override
    public boolean validate(Employee employee, LeaveApplicationRequest request)
    {
        long numberOfDays = ChronoUnit.DAYS.between(request.getFromDate(), request.getToDate()) + 1;

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

        if (leaveRequestRepository.existsByEmployeeIdAndStatus(employee.getEmployeeId(), LeaveStatus.PENDING))
        {
            return false;
        }

        if (leaveRequestRepository.existsByEmployeeIdAndFromDateLessThanEqualAndToDateGreaterThanEqual(employee.getEmployeeId(), request.getToDate(), request.getFromDate()))
        {
            return false;
        }

        return true;
    }
}