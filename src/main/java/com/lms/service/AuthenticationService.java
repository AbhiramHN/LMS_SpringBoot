package com.lms.service;

import com.lms.dto.request.LoginRequest;
import com.lms.entity.Employee;
import com.lms.exception.UnauthorizedException;
import com.lms.repository.EmployeeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService
{
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(
            EmployeeRepository employeeRepository,
            PasswordEncoder passwordEncoder)
    {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Employee login(LoginRequest request)
    {
        Employee employee = employeeRepository.findByEmployeeId(request.getEmployeeId()).orElseThrow(() ->
                        new UnauthorizedException("Invalid employee ID or password"));

        if (!passwordEncoder.matches(request.getPassword(), employee.getPassword()))
        {
            throw new UnauthorizedException("Invalid employee ID or password");
        }

        return employee;
    }
}