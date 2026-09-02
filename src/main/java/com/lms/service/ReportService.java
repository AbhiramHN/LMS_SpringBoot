package com.lms.service;

import com.lms.enums.LeaveStatus;
import com.lms.repository.EmployeeRepository;
import com.lms.repository.LeaveRequestRepository;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class ReportService
{
    private final EmployeeRepository employeeRepository;

    private final LeaveRequestRepository leaveRequestRepository;

    public ReportService(
            EmployeeRepository employeeRepository,
            LeaveRequestRepository leaveRequestRepository)
    {
        this.employeeRepository = employeeRepository;

        this.leaveRequestRepository = leaveRequestRepository;
    }

    public void generateReport()
    {
        long totalEmployees =
                employeeRepository.count();

        long pendingLeaves =
                leaveRequestRepository
                        .countByStatus(LeaveStatus.PENDING);

        long approvedLeaves =
                leaveRequestRepository
                        .countByStatus(LeaveStatus.APPROVED);

        long rejectedLeaves =
                leaveRequestRepository
                        .countByStatus(LeaveStatus.REJECTED);

        long revokedLeaves =
                leaveRequestRepository
                        .countByStatus(LeaveStatus.REVOKED);

        try (FileWriter writer =
                     new FileWriter("D:\\Java Projects\\LMS-SpringBoot\\src\\main\\java\\com\\lms\\reports\\LeaveReport.txt"))
        {
            writer.write("=====================================\n");
            writer.write("     LEAVE MANAGEMENT REPORT\n");
            writer.write("=====================================\n\n");

            writer.write("Generated On : "
                    + LocalDateTime.now()
                    + "\n\n");

            writer.write("Total Employees : "
                    + totalEmployees
                    + "\n");

            writer.write("Pending Leaves  : "
                    + pendingLeaves
                    + "\n");

            writer.write("Approved Leaves : "
                    + approvedLeaves
                    + "\n");

            writer.write("Rejected Leaves : "
                    + rejectedLeaves
                    + "\n");

            writer.write("Revoked Leaves  : "
                    + revokedLeaves
                    + "\n");

            writer.write("\n=====================================\n");
            writer.write("End Of Report\n");
            writer.write("=====================================\n");

            System.out.println("Report generated successfully.");
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }
}