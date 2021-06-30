package com.chabane.employeesever.dao;

import com.chabane.employeesever.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Integer> {
    Employee findById(int id);
    Employee findByEmail(String email);
    List<Employee> findAllByRole(String role);
    void deleteById(int id);
}
