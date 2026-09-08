package com.lms.service;

import com.lms.dto.request.LeaveApplicationRequest;
import com.lms.entity.Employee;
import com.lms.enums.LeaveType;

public interface LeaveValidatable
{
    LeaveType getLeaveType();

    boolean validate(Employee employee, LeaveApplicationRequest request);
}