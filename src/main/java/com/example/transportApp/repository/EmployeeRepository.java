package com.example.transportApp.repository;

import com.example.transportApp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository
    extends JpaRepository<Employee,Long> {
}
