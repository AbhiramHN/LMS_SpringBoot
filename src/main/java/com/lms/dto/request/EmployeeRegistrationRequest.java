package com.lms.dto.request;

import com.lms.enums.Designation;
import com.lms.enums.Gender;

import java.time.LocalDate;

public class EmployeeRegistrationRequest
{
    private String name;
    private Designation designation;
    private int age;
    private Gender gender;
    private String password;
    private LocalDate joiningDate;

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

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
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