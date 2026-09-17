package com.lms.validation;

import com.lms.dto.request.LeaveApplicationRequest;
import com.lms.entity.Employee;
import com.lms.enums.LeaveType;
import com.lms.repository.LeaveRequestRepository;
import com.lms.service.LeaveValidationService;
import org.springframework.stereotype.Component;

@Component
public class LeaveWithoutPayValidation extends LeaveValidationService
{
    public LeaveWithoutPayValidation(LeaveRequestRepository leaveRequestRepository) {
        super(leaveRequestRepository);
    }

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