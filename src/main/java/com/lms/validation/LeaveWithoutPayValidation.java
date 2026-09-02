package com.lms.validation;

import com.lms.dto.request.LeaveApplicationRequest;
import com.lms.entity.Employee;
import com.lms.enums.LeaveType;
import com.lms.service.LeaveValidationService;
import org.springframework.stereotype.Component;

@Component
public class LeaveWithoutPayValidation extends LeaveValidationService
{
    @Override
    public LeaveType getLeaveType()
    {
        return LeaveType.LWP;
    }

    @Override
    protected boolean validateSpecificLeave(Employee employee, LeaveApplicationRequest request, long numberOfDays)
    {
        return true;
    }
}