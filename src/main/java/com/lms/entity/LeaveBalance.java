package com.lms.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "leave_balance")
public class LeaveBalance
{
    @EmbeddedId
    private LeaveBalanceId id;

    private int balance;

    public LeaveBalance()
    {
    }

    public LeaveBalanceId getId()
    {
        return id;
    }

    public void setId(LeaveBalanceId id)
    {
        this.id = id;
    }

    public int getBalance()
    {
        return balance;
    }

    public void setBalance(int balance)
    {
        this.balance = balance;
    }
}