package com.lms.service;

import com.lms.dto.request.LeaveApplicationRequest;
import com.lms.entity.Employee;
import com.lms.entity.LeaveBalance;
import com.lms.entity.LeaveBalanceId;
import com.lms.entity.LeaveRequest;
import com.lms.enums.LeaveStatus;
import com.lms.enums.LeaveType;
import com.lms.repository.EmployeeRepository;
import com.lms.repository.LeaveBalanceRepository;
import com.lms.repository.LeaveRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LeaveService
{
    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private List<LeaveValidatable> validationRules;

    private LeaveValidatable getValidationRule(LeaveType leaveType)
    {
        for (LeaveValidatable validationRule : validationRules)
        {
            if (validationRule.getLeaveType() == leaveType)
            {
                return validationRule;
            }
        }

        throw new RuntimeException("Invalid leave type.");
    }

    @Transactional
    public String applyLeave(LeaveApplicationRequest request)
    {
        Employee employee = employeeRepository.findByEmployeeId(request.getEmployeeId()).orElseThrow(() -> new RuntimeException("Employee not found."));

        LeaveValidatable validationRule = getValidationRule(request.getLeaveType());

        boolean isValid = validationRule.validate(employee, request);

        if (!isValid)
        {
            return "Leave request failed.";
        }

        LeaveBalance leaveBalance = null;

        if (request.getLeaveType() != LeaveType.LWP)
        {
            LeaveBalanceId leaveBalanceId = new LeaveBalanceId(request.getEmployeeId(), request.getLeaveType());

            leaveBalance = leaveBalanceRepository.findById(leaveBalanceId).orElseThrow(() ->
                                    new RuntimeException("Leave balance not found."));
        }

        long numberOfDays = ChronoUnit.DAYS.between(request.getFromDate(), request.getToDate()) + 1;

        if (request.getLeaveType() != LeaveType.LWP)
        {
            leaveBalance.setBalance(leaveBalance.getBalance() - (int) numberOfDays);

            leaveBalanceRepository.save(leaveBalance);
        }

        LeaveRequest leaveRequest = new LeaveRequest();

        leaveRequest.setEmployeeId(request.getEmployeeId());

        leaveRequest.setLeaveType(request.getLeaveType());

        leaveRequest.setFromDate(request.getFromDate());

        leaveRequest.setToDate(request.getToDate());

        leaveRequest.setNumberOfDays((int) numberOfDays);

        leaveRequest.setReason(request.getReason());

        leaveRequest.setStatus(LeaveStatus.PENDING);

        leaveRequestRepository.save(leaveRequest);

        return "Leave applied successfully.";
    }

    public List<LeaveBalance> getLeaveBalances(String employeeId)
    {
        return leaveBalanceRepository.findByIdEmployeeId(employeeId);
    }

    public List<LeaveRequest> getLeaveHistory(String employeeId)
    {
        return leaveRequestRepository.findByEmployeeId(employeeId);
    }
}