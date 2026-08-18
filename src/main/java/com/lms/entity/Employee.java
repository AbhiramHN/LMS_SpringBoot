package com.lms.entity;

import com.lms.enums.Designation;
import com.lms.enums.Gender;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Employee
{
    @Id
    private String employeeId;

    private String name;

    @Enumerated(EnumType.STRING)
    private Designation designation;

    private int age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String password;

    private LocalDate joiningDate;
}