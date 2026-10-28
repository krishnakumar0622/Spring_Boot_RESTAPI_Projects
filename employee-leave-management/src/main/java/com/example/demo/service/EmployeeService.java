
package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;

@Service
public class EmployeeService {

    private final List<Employee> employees = new ArrayList<>();
    private Long nextId = 1L;

    // Register employee
    public Employee addEmployee(Employee employee) {
        employee.setEmployeeId(nextId++);

        // Give every new employee 20 leave days
        employee.setLeaveBalance(20);

        employees.add(employee);
        return employee;
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return employees;
    }

    // Get employee by ID
    public Employee getEmployeeById(Long id) {
        return employees.stream()
                .filter(employee -> employee.getEmployeeId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Update employee details
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existingEmployee = getEmployeeById(id);

        if (existingEmployee == null) {
            return null;
        }

        existingEmployee.setEmployeeName(updatedEmployee.getEmployeeName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());

        return existingEmployee;
    }

    // Delete employee
    public boolean deleteEmployee(Long id) {
        return employees.removeIf(
                employee -> employee.getEmployeeId().equals(id));
    }

    // Count employees
    public int getTotalEmployees() {
        return employees.size();
    }
}
