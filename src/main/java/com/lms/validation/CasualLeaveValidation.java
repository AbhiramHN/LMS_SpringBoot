package com.lms.validation;

import com.lms.dto.request.LeaveApplicationRequest;
import com.lms.entity.Employee;
import com.lms.entity.LeaveBalance;
import com.lms.entity.LeaveBalanceId;
import com.lms.enums.LeaveType;
import com.lms.exception.ResourceNotFoundException;
import com.lms.repository.LeaveBalanceRepository;
import com.lms.repository.LeaveRequestRepository;
import com.lms.service.LeaveValidationService;
import org.springframework.stereotype.Component;

@Component
public class CasualLeaveValidation extends LeaveValidationService
{
    private final LeaveBalanceRepository leaveBalanceRepository;

    public CasualLeaveValidation(
            LeaveRequestRepository leaveRequestRepository,
            LeaveBalanceRepository leaveBalanceRepository)
    {
        super(leaveRequestRepository);
        this.leaveBalanceRepository = leaveBalanceRepository;
    }

    @Override
    public LeaveType getLeaveType()
    {
        return LeaveType.CL;
    }

    @Override
    protected boolean validateSpecificLeave(
            Employee employee,
            LeaveApplicationRequest request,
            long numberOfDays)
    {
        LeaveBalanceId leaveBalanceId = new LeaveBalanceId(employee.getEmployeeId(), LeaveType.CL);

        LeaveBalance leaveBalance = leaveBalanceRepository.findById(leaveBalanceId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Leave balance not found."));

        if (numberOfDays > leaveBalance.getBalance())
        {
            return false;
        }

        return true;
    }
}