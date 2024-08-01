package com.example.milestone.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.milestone.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
