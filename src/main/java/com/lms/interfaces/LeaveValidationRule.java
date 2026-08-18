package com.lms.interfaces;

import com.lms.dto.request.LeaveApplicationRequest;
import com.lms.entity.Employee;
import com.lms.enums.LeaveType;

public interface LeaveValidationRule
{
    LeaveType getLeaveType();

    boolean validate(Employee employee, LeaveApplicationRequest request);
}