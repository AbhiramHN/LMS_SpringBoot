package com.lms.controller;

import com.lms.entity.Employee;
import com.lms.enums.Designation;
import com.lms.service.ReportService;
import com.lms.thread.ReportGenerationThread;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerateReportController
{
    private final ReportService reportService;

    public GenerateReportController(ReportService reportService)
    {
        this.reportService = reportService;
    }

    @GetMapping("/api/v1/generateReport")
    public ResponseEntity<String> generateReport(
            HttpSession session)
    {
        Employee employee =
                (Employee) session.getAttribute("employee");

        if (employee == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("User is not logged in.");
        }

        if (employee.getDesignation() != Designation.MANAGER)
        {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Not Authorized.");
        }

        ReportGenerationThread reportGenerationThread =
                new ReportGenerationThread(reportService);

        reportGenerationThread.start();

        return ResponseEntity.ok(
                "Report generation started."
        );
    }
}