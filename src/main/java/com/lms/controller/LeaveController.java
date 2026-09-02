package com.lms.controller;

import com.lms.dto.request.LeaveApplicationRequest;
import com.lms.entity.Employee;
import com.lms.entity.LeaveBalance;
import com.lms.entity.LeaveRequest;
import com.lms.enums.Designation;
import com.lms.enums.Designation;
import com.lms.service.LeaveApprovalService;
import com.lms.service.LeaveRevokeService;
import com.lms.service.LeaveService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.lms.dto.request.RevokeLeaveRequest;
import com.lms.dto.request.ManagerActionRequest;

import java.util.List;


@RestController
@RequestMapping("/leave")
public class LeaveController
{

    final private LeaveService leaveService;
    final private LeaveApprovalService leaveApprovalService;
    final private LeaveRevokeService leaveRevokeService;


    public LeaveController(
            LeaveService leaveService,
            LeaveApprovalService leaveApprovalService,
            LeaveRevokeService leaveRevokeService) {

        this.leaveService = leaveService;
        this.leaveApprovalService = leaveApprovalService;
        this.leaveRevokeService = leaveRevokeService;
    }

    @PostMapping("/apply")
    public String applyLeave(
            @RequestBody LeaveApplicationRequest request,
            HttpSession session)
    {
        Employee employee = (Employee) session.getAttribute("employee");

        if (employee == null)
        {
            return "User is not logged in";
        }

        request.setEmployeeId(employee.getEmployeeId());

        return leaveService.applyLeave(request);
    }



    @GetMapping("/pending")
    public ResponseEntity<?> getPendingLeaves(HttpSession session)
    {
        Employee employee =
                (Employee) session.getAttribute("employee");

        if (employee == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("User is not logged in");
        }

        if (employee.getDesignation() != Designation.LEAD
                && employee.getDesignation() != Designation.MANAGER)
        {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Only Leads and Managers can view pending leaves");
        }

        return ResponseEntity.ok(
                leaveApprovalService.getPendingLeaves(employee)
        );
    }

    @GetMapping("/history")
    public ResponseEntity<?> getLeaveHistory(HttpSession session)
    {
        Employee employee =
                (Employee) session.getAttribute("employee");

        if (employee == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("User is not logged in");
        }

        return ResponseEntity.ok(
                leaveService.getLeaveHistory(employee.getEmployeeId())
        );
    }


    @GetMapping("/balance/{employeeId}")
    public List<LeaveBalance> getLeaveBalances(@PathVariable String employeeId)
    {
        return leaveService.getLeaveBalances(employeeId);
    }


    @PutMapping("/{leaveId}/approve")
    public ResponseEntity<?> approveLeave(
            @PathVariable int leaveId,
            HttpSession session)
    {
        Employee manager =
                (Employee) session.getAttribute("employee");

        if (manager == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("User is not logged in");
        }

        try
        {
            return ResponseEntity.ok(
                    leaveApprovalService.approveLeave(
                            leaveId,
                            manager.getEmployeeId()
                    )
            );
        }
        catch (RuntimeException e)
        {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{leaveId}/reject")
    public ResponseEntity<?> rejectLeave(
            @PathVariable int leaveId,
            HttpSession session)
    {
        Employee manager =
                (Employee) session.getAttribute("employee");

        if (manager == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("User is not logged in");
        }

        try
        {
            return ResponseEntity.ok(
                    leaveApprovalService.rejectLeave(
                            leaveId,
                            manager.getEmployeeId()
                    )
            );
        }
        catch (RuntimeException e)
        {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        }
    }


    @PutMapping("/{leaveId}/revoke")
    public ResponseEntity<?> revokeLeave(@PathVariable int leaveId, HttpSession session)
    {
        Employee manager = (Employee) session.getAttribute("employee");

        if (manager == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("User is not logged in");
        }

        RevokeLeaveRequest request = new RevokeLeaveRequest();

        request.setManagerEmployeeId(manager.getEmployeeId());

        try
        {
            return ResponseEntity.ok(leaveRevokeService.revokeLeave(leaveId, request));
        }
        catch (RuntimeException exception)
        {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(exception.getMessage());
        }
    }


    @GetMapping("/revocable")
    public ResponseEntity<?> getRevocableLeaves(HttpSession session)
    {
        Employee employee =
                (Employee) session.getAttribute("employee");

        if (employee == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("User is not logged in");
        }

        if (employee.getDesignation() != Designation.LEAD
                && employee.getDesignation() != Designation.MANAGER)
        {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Only Leads and Managers can revoke leaves");
        }

        return ResponseEntity.ok(
                leaveRevokeService.getRevocableLeaves(employee)
        );
    }
}