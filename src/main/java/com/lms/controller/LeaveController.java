package com.lms.controller;

import com.lms.dto.request.LeaveApplicationRequest;
import com.lms.entity.LeaveBalance;
import com.lms.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leave")
public class LeaveController
{
    @Autowired
    private LeaveService leaveService;

    @PostMapping("/apply")
    public String applyLeave(@RequestBody LeaveApplicationRequest request)
    {
        return leaveService.applyLeave(request);
    }


    @GetMapping("/balance/{employeeId}")
    public List<LeaveBalance> getLeaveBalances(@PathVariable String employeeId)
    {
        return leaveService.getLeaveBalances(employeeId);
    }
}