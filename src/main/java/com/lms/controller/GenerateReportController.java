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

    @GetMapping("/generateReport")
    public ResponseEntity<String> generateReport(
            HttpSession session)
    {
        Employee employee = (Employee) session.getAttribute("employee");

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

        ReportGenerationThread reportGenerationThread = new ReportGenerationThread(reportService);

        reportGenerationThread.start();

        return ResponseEntity.ok(
                """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Report Generation</title>

                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background: #f4f6f8;

                            display: flex;
                            justify-content: center;
                            align-items: center;

                            min-height: 100vh;
                            margin: 0;
                        }

                        .container {
                            text-align: center;
                        }

                        .card {
                            background: white;
                            padding: 40px;
                            border-radius: 15px;

                            box-shadow:
                                0 4px 15px rgba(0, 0, 0, 0.1);

                            min-width: 450px;
                        }

                        h2 {
                            margin-bottom: 15px;
                        }

                        p {
                            color: #555;
                            margin-bottom: 10px;
                        }

                        .back-button {
                            display: inline-block;

                            margin-top: 20px;

                            padding: 12px 25px;

                            background: #2563eb;
                            color: white;

                            text-decoration: none;

                            border-radius: 8px;

                            font-weight: bold;

                            transition: 0.2s;
                        }

                        .back-button:hover {
                            transform: translateY(-2px);
                            background: #1d4ed8;
                        }
                    </style>
                </head>

                <body>

                    <div class="container">

                        <div class="card">

                            <h2>Report generation started.</h2>

                            <p>
                                Your report is being generated
                                in the background.
                            </p>

                            <p>
                                Once completed, it will be available
                                in the <b>reports</b> folder.
                            </p>

                            <a class="back-button"
                               href="/pages/dashboard.html">
                                Back to Dashboard
                            </a>

                        </div>

                    </div>

                </body>
                </html>
                """
        );
    }
}