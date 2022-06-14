package com.example.mas_gui.repository;

import com.example.mas_gui.model.Employee;
import com.example.mas_gui.model.Pack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository
    extends JpaRepository<Employee,Long> {
}
