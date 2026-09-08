package com.lms.validation;

import com.lms.dto.request.LeaveApplicationRequest;
import com.lms.entity.Employee;
import com.lms.entity.LeaveBalance;
import com.lms.entity.LeaveBalanceId;
import com.lms.enums.Gender;
import com.lms.enums.LeaveType;
import com.lms.repository.LeaveBalanceRepository;
import com.lms.service.LeaveValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MaternityLeaveValidation extends LeaveValidationService
{
    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    @Override
    public LeaveType getLeaveType()
    {
        return LeaveType.ML;
    }

    @Override
    protected boolean validateSpecificLeave(Employee employee, LeaveApplicationRequest request, long numberOfDays)
    {
        if (employee.getGender() != Gender.FEMALE)
        {
            return false;
        }

        LeaveBalanceId leaveBalanceId = new LeaveBalanceId(employee.getEmployeeId(), LeaveType.ML);

        LeaveBalance leaveBalance = leaveBalanceRepository.findById(leaveBalanceId)
                .orElseThrow(() -> new RuntimeException("Leave balance not found."));

        if (numberOfDays > leaveBalance.getBalance())
        {
            return false;
        }

        return true;
    }
}