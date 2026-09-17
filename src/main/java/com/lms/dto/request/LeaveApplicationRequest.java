package com.lms.dto.request;

import com.lms.enums.LeaveType;

import java.time.LocalDate;

public class LeaveApplicationRequest
{
    private String employeeId;

    private LeaveType leaveType;

    private LocalDate fromDate;

    private LocalDate toDate;

    private String reason;

    public String getEmployeeId()
    {
        return employeeId;
    }

    public void setEmployeeId(String employeeId)
    {
        this.employeeId = employeeId;
    }

    public LeaveType getLeaveType()
    {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType)
    {
        this.leaveType = leaveType;
    }

    public LocalDate getFromDate()
    {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate)
    {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate()
    {
        return toDate;
    }

    public void setToDate(LocalDate toDate)
    {
        this.toDate = toDate;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }
}