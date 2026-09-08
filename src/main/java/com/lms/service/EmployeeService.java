package com.lms.service;

import com.lms.dto.request.EmployeeRegistrationRequest;
import com.lms.dto.response.EmployeeResponse;
import com.lms.entity.Employee;
import com.lms.entity.LeaveBalance;
import com.lms.entity.LeaveBalanceId;
import com.lms.enums.LeaveType;
import com.lms.repository.EmployeeRepository;
import com.lms.repository.LeaveBalanceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService
{
    private final EmployeeRepository employeeRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;

    public EmployeeService(EmployeeRepository employeeRepository, LeaveBalanceRepository leaveBalanceRepository) {

        this.employeeRepository = employeeRepository;
        this.leaveBalanceRepository = leaveBalanceRepository;
    }

    @Transactional
    public EmployeeResponse registerEmployee(EmployeeRegistrationRequest request)
    {
        Employee employee = new Employee();

        String employeeId = generateEmployeeId();

        employee.setEmployeeId(employeeId);
        employee.setName(request.getName());
        employee.setDesignation(request.getDesignation());
        employee.setAge(request.getAge());
        employee.setGender(request.getGender());
        employee.setPassword(request.getPassword());
        employee.setJoiningDate(request.getJoiningDate());

        Employee savedEmployee = employeeRepository.save(employee);

        initializeLeaveBalances(savedEmployee.getEmployeeId());

        EmployeeResponse response = new EmployeeResponse();

        response.setEmployeeId(savedEmployee.getEmployeeId());
        response.setName(savedEmployee.getName());
        response.setDesignation(savedEmployee.getDesignation());
        response.setAge(savedEmployee.getAge());
        response.setGender(savedEmployee.getGender());
        response.setJoiningDate(savedEmployee.getJoiningDate());

        return response;
    }

    private String generateEmployeeId()
    {
        String lastEmployeeId = employeeRepository.findLastEmployeeId();

        if (lastEmployeeId == null)
        {
            return "EMP0001";
        }

        int number = Integer.parseInt(lastEmployeeId.substring(3));

        number++;

        return String.format("EMP%04d", number);
    }

    private void initializeLeaveBalances(
            String employeeId)
    {
        createLeaveBalance(employeeId, LeaveType.CL, 12);
        createLeaveBalance(employeeId, LeaveType.EL, 15);
        createLeaveBalance(employeeId, LeaveType.SL, 10);
        createLeaveBalance(employeeId, LeaveType.ML, 180);
        createLeaveBalance(employeeId, LeaveType.PL, 10);
        createLeaveBalance(employeeId, LeaveType.DL, 5);
        createLeaveBalance(employeeId, LeaveType.LWP, 0);
    }

    private void createLeaveBalance(String employeeId, LeaveType leaveType, int balance)
    {
        LeaveBalance leaveBalance = new LeaveBalance();

        LeaveBalanceId id = new LeaveBalanceId(employeeId, leaveType);

        leaveBalance.setId(id);

        leaveBalance.setBalance(balance);

        leaveBalanceRepository.save(leaveBalance);
    }
}