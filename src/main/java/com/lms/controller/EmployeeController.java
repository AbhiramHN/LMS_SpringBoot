package com.lms.controller;

import com.lms.dto.request.EmployeeRegistrationRequest;
import com.lms.dto.response.EmployeeResponse;
import com.lms.entity.Employee;
import com.lms.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController
{
    final private EmployeeService employeeService;

    EmployeeController(EmployeeService employeeService)
    {
        this.employeeService = employeeService;
    }

    @PostMapping("/register")
    public EmployeeResponse registerEmployee(@RequestBody EmployeeRegistrationRequest request)
    {
        return employeeService.registerEmployee(request);
    }
}