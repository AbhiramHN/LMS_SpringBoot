package com.lms.service;

import com.lms.dto.request.RevokeLeaveRequest;
import com.lms.entity.Employee;
import com.lms.entity.LeaveBalance;
import com.lms.entity.LeaveRequest;
import com.lms.enums.Designation;
import com.lms.enums.LeaveStatus;
import com.lms.enums.LeaveType;
import com.lms.repository.EmployeeRepository;
import com.lms.repository.LeaveBalanceRepository;
import com.lms.repository.LeaveRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class LeaveRevokeService
{
    private final EmployeeRepository employeeRepository;

    private final LeaveRequestRepository leaveRequestRepository;

    private final LeaveBalanceRepository leaveBalanceRepository;

    @Transactional
    public LeaveRequest revokeLeave(
            int leaveId,
            RevokeLeaveRequest request)
    {
        Employee manager = employeeRepository
                .findByEmployeeId(
                        request.getManagerEmployeeId())
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        LeaveRequest leaveRequest = leaveRequestRepository
                .findById(leaveId)
                .orElseThrow(() -> new RuntimeException(
                        "Leave request not found"));

        Employee employee = employeeRepository.findByEmployeeId(leaveRequest.getEmployeeId())
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));

        if (manager.getDesignation() != Designation.LEAD
                && manager.getDesignation() != Designation.MANAGER)
        {
            throw new RuntimeException(
                    "Only Leads and Managers can revoke leave requests");
        }

        if (manager.getDesignation() == Designation.LEAD)
        {
            if (employee.getDesignation()
                    != Designation.EXECUTIVE)
            {
                throw new RuntimeException(
                        "Lead can revoke only Executive leave requests");
            }
        }


        if (manager.getDesignation() == Designation.MANAGER)
        {
            if (manager.getEmployeeId()
                    .equals(employee.getEmployeeId()))
            {
                throw new RuntimeException(
                        "Manager cannot revoke their own leave request");
            }
        }


        if (leaveRequest.getStatus()
                != LeaveStatus.APPROVED)
        {
            throw new RuntimeException("Only approved leave requests can be revoked");
        }


        if (!leaveRequest.getFromDate()
                .isAfter(LocalDate.now()))
        {
            throw new RuntimeException("Only future leave requests can be revoked");
        }


        if (leaveRequest.getLeaveType() != LeaveType.LWP)
        {
            LeaveBalance leaveBalance = leaveBalanceRepository
                            .findByIdEmployeeId(
                                    leaveRequest.getEmployeeId())
                            .stream()
                            .filter(balance ->
                                    balance.getId()
                                            .getLeaveType()
                                            == leaveRequest
                                            .getLeaveType())
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Leave balance not found"));

            leaveBalance.setBalance(leaveBalance.getBalance() + leaveRequest.getNumberOfDays());

            leaveBalanceRepository.save(
                    leaveBalance);
        }

        leaveRequest.setStatus(
                LeaveStatus.REVOKED);

        return leaveRequestRepository.save(
                leaveRequest);
    }

    public java.util.List<LeaveRequest> getRevocableLeaves(
            Employee manager)
    {
        return leaveRequestRepository
                .findByStatus(LeaveStatus.APPROVED)
                .stream()
                .filter(leaveRequest -> {

                    // Leave must be in the future
                    if (!leaveRequest.getFromDate()
                            .isAfter(java.time.LocalDate.now()))
                    {
                        return false;
                    }

                    Employee employee = employeeRepository
                            .findByEmployeeId(
                                    leaveRequest.getEmployeeId())
                            .orElse(null);

                    if (employee == null)
                    {
                        return false;
                    }

                    if (manager.getDesignation() == Designation.LEAD)
                    {
                        return employee.getDesignation()
                                == Designation.EXECUTIVE;
                    }

                    if (manager.getDesignation() == Designation.MANAGER)
                    {
                        return !manager.getEmployeeId()
                                .equals(employee.getEmployeeId());
                    }

                    return false;
                })
                .toList();
    }
}