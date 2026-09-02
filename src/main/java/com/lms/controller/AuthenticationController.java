package com.lms.controller;

import com.lms.dto.request.LoginRequest;
import com.lms.entity.Employee;
import com.lms.service.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController
{
    final private AuthenticationService authenticationService;

    AuthenticationController(AuthenticationService authenticationService)
    {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpSession session)
    {
        Employee employee = authenticationService.login(request);

        if (employee == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid Employee ID or Password");
        }

        session.setAttribute("employee", employee);

        return ResponseEntity.ok("Login successful.");
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpSession session)
    {
        Employee employee = (Employee) session.getAttribute("employee");

        if (employee == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("User is not logged in");
        }

        Map<String, Object> response = new HashMap<>();

        response.put("employeeId", employee.getEmployeeId());
        response.put("name", employee.getName());
        response.put("designation", employee.getDesignation());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session)
    {
        session.invalidate();

        return ResponseEntity.ok("Logout successful.");
    }
}