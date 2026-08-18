package com.lms.service;

import com.lms.dto.request.LoginRequest;
import com.lms.entity.Employee;
import com.lms.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService
{
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee login(LoginRequest request)
    {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmployeeId(request.getEmployeeId());

        if (optionalEmployee.isEmpty())
        {
            return null;
        }

        Employee employee = optionalEmployee.get();

        if (!employee.getPassword().equals(request.getPassword()))
        {
            return null;
        }

        return employee;
    }
}