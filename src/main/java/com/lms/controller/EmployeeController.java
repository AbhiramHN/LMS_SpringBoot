package com.lms.controller;

import com.lms.dto.request.EmployeeRegistrationRequest;
import com.lms.entity.Employee;
import com.lms.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController
{
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/register")
    public Employee registerEmployee(@RequestBody EmployeeRegistrationRequest request)
    {
        return employeeService.registerEmployee(request);
    }
}