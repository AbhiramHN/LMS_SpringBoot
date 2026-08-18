package com.lms.repository;

import com.lms.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeRepository
        extends JpaRepository<Employee, String>
{
    @Query(
            value =
                    """
                    SELECT employee_id
                    FROM employee
                    ORDER BY employee_id DESC
                    LIMIT 1
                    """,
            nativeQuery = true
    )
    String findLastEmployeeId();

    Optional<Employee> findByEmployeeId(String employeeId);
}