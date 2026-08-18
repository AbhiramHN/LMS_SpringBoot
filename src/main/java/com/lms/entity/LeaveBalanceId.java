package com.lms.entity;

import com.lms.enums.LeaveType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LeaveBalanceId implements Serializable
{
    private String employeeId;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    public LeaveBalanceId()
    {
    }

    public LeaveBalanceId(String employeeId, LeaveType leaveType)
    {
        this.employeeId = employeeId;
        this.leaveType = leaveType;
    }

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

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }

        if (!(object instanceof LeaveBalanceId that))
        {
            return false;
        }

        return Objects.equals(employeeId, that.employeeId)
                && leaveType == that.leaveType;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(employeeId, leaveType);
    }
}