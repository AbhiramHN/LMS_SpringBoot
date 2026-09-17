package com.lms.service;

import com.lms.entity.Employee;
import com.lms.entity.LeaveBalance;
import com.lms.entity.LeaveRequest;
import com.lms.enums.Designation;
import com.lms.enums.LeaveStatus;
import com.lms.exception.LeaveAlreadyProcessedException;
import com.lms.exception.ResourceNotFoundException;
import com.lms.repository.EmployeeRepository;
import com.lms.repository.LeaveBalanceRepository;
import com.lms.repository.LeaveRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.lms.exception.ForbiddenException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveApprovalService
{
    private final EmployeeRepository employeeRepository;

    private final LeaveBalanceRepository leaveBalanceRepository;

    private final LeaveRequestRepository leaveRequestRepository;


    public LeaveRequest approveLeave(int leaveId, String managerEmployeeId)
    {
        Employee manager = employeeRepository
                .findByEmployeeId(managerEmployeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Manager not found"));

        LeaveRequest leaveRequest = leaveRequestRepository
                .findById(leaveId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Leave request not found"));

        if (leaveRequest.getStatus() != LeaveStatus.PENDING)
        {
            throw new LeaveAlreadyProcessedException("Leave request has already been processed");
        }

        Employee employee = employeeRepository
                .findByEmployeeId(leaveRequest.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));



        if (manager.getDesignation() == Designation.LEAD)
        {
            if (employee.getDesignation() != Designation.EXECUTIVE)
            {
                throw new ForbiddenException(
                        "Lead can approve only Executive leave requests");
            }
        }


        else if (manager.getDesignation() == Designation.MANAGER)
        {
            if (manager.getEmployeeId()
                    .equals(employee.getEmployeeId()))
            {
                throw new ForbiddenException(
                        "Manager cannot approve their own leave request");
            }
        }


        else
        {
            throw new ForbiddenException(
                    "Only Leads and Managers can approve leave requests");
        }


        leaveRequest.setStatus(LeaveStatus.APPROVED);

        leaveRequest.setApprovedBy(
                manager.getEmployeeId());

        leaveRequest.setApprovalDate(
                LocalDate.now());

        return leaveRequestRepository.save(leaveRequest);
    }


    public List<LeaveRequest> getPendingLeaves(Employee approver)
    {
        List<LeaveRequest> pendingLeaves =
                leaveRequestRepository
                        .findByStatus(LeaveStatus.PENDING);

        return pendingLeaves.stream()
                .filter(leaveRequest -> {

                    Employee employee = employeeRepository
                            .findByEmployeeId(
                                    leaveRequest.getEmployeeId())
                            .orElse(null);

                    if (employee == null)
                    {
                        return false;
                    }

                    if (approver.getDesignation() == Designation.LEAD)
                    {
                        return employee.getDesignation()
                                == Designation.EXECUTIVE;
                    }

                    if (approver.getDesignation() == Designation.MANAGER)
                    {
                        return !approver.getEmployeeId()
                                .equals(employee.getEmployeeId());
                    }

                    return false;
                })
                .toList();
    }


    public LeaveRequest rejectLeave(int leaveId, String managerEmployeeId)
    {
        Employee manager = employeeRepository
                .findByEmployeeId(managerEmployeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Manager not found"));

        LeaveRequest leaveRequest = leaveRequestRepository
                .findById(leaveId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Leave request not found"));

        if (leaveRequest.getStatus() != LeaveStatus.PENDING)
        {
            throw new LeaveAlreadyProcessedException("Leave request has already been processed");
        }

        Employee employee = employeeRepository
                .findByEmployeeId(leaveRequest.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));


        if (manager.getDesignation() == Designation.LEAD)
        {
            if (employee.getDesignation() != Designation.EXECUTIVE)
            {
                throw new ForbiddenException(
                        "Lead can reject only Executive leave requests");
            }
        }

        else if (manager.getDesignation() == Designation.MANAGER)
        {
            if (manager.getEmployeeId()
                    .equals(employee.getEmployeeId()))
            {
                throw new ForbiddenException(
                        "Manager cannot reject their own leave request");
            }
        }

        else
        {
            throw new ForbiddenException(
                    "Only Leads and Managers can reject leave requests");
        }


        LeaveBalance leaveBalance = leaveBalanceRepository
                .findByIdEmployeeId(employee.getEmployeeId())
                .stream()
                .filter(balance ->
                        balance.getId().getLeaveType()
                                == leaveRequest.getLeaveType())
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException("Leave balance not found"));

        leaveBalance.setBalance(
                leaveBalance.getBalance()
                        + leaveRequest.getNumberOfDays());

        leaveBalanceRepository.save(leaveBalance);


        leaveRequest.setStatus(LeaveStatus.REJECTED);

        leaveRequest.setApprovedBy(
                manager.getEmployeeId());

        leaveRequest.setApprovalDate(
                LocalDate.now());

        return leaveRequestRepository.save(leaveRequest);
    }
}