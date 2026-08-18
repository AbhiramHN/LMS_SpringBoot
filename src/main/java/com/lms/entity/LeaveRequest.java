package com.lms.entity;

import com.lms.enums.LeaveStatus;
import com.lms.enums.LeaveType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "leave_request")
public class LeaveRequest
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leaveId;

    private String employeeId;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    private LocalDate fromDate;

    private LocalDate toDate;

    private int numberOfDays;

    private String reason;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    private LocalDate requestDate;

    private LocalDate approvalDate;

    private String approvedBy;
}