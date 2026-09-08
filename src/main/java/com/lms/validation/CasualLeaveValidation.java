package com.lms.validation;

import com.lms.dto.request.LeaveApplicationRequest;
import com.lms.entity.Employee;
import com.lms.entity.LeaveBalance;
import com.lms.entity.LeaveBalanceId;
import com.lms.enums.LeaveType;
import com.lms.repository.LeaveBalanceRepository;
import com.lms.service.LeaveValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CasualLeaveValidation extends LeaveValidationService
{
    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    @Override
    public LeaveType getLeaveType()
    {
        return LeaveType.CL;
    }

    @Override
    protected boolean validateSpecificLeave(Employee employee, LeaveApplicationRequest request, long numberOfDays)
    {
        LeaveBalanceId leaveBalanceId = new LeaveBalanceId(employee.getEmployeeId(), LeaveType.CL);

        LeaveBalance leaveBalance = leaveBalanceRepository.findById(leaveBalanceId)
                .orElseThrow(() -> new RuntimeException("Leave balance not found."));

        if (numberOfDays > leaveBalance.getBalance())
        {
            return false;
        }

        return true;
    }
}