package com.lms.dto.response;

import com.lms.enums.Designation;
import com.lms.enums.Gender;

import java.time.LocalDate;

public class EmployeeResponse
{
    private String employeeId;
    private String name;
    private Designation designation;
    private int age;
    private Gender gender;
    private LocalDate joiningDate;

    public String getEmployeeId()
    {
        return employeeId;
    }

    public void setEmployeeId(String employeeId)
    {
        this.employeeId = employeeId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Designation getDesignation()
    {
        return designation;
    }

    public void setDesignation(Designation designation)
    {
        this.designation = designation;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public Gender getGender()
    {
        return gender;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    public LocalDate getJoiningDate()
    {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate)
    {
        this.joiningDate = joiningDate;
    }
}