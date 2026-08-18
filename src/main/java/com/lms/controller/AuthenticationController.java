package com.lms.controller;

import com.lms.dto.request.LoginRequest;
import com.lms.entity.Employee;
import com.lms.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController
{
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request)
    {
        Employee employee = authenticationService.login(request);

        if (employee == null)
        {
            return "Login failed.";
        }

        return "Login successful.";
    }
}