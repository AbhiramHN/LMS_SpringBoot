package com.lms.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "leave_balance")
public class LeaveBalance
{
    @EmbeddedId
    private LeaveBalanceId id;

    private int balance;
}