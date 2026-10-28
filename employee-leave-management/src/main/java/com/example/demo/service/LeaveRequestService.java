
package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.model.LeaveRequest;

@Service
public class LeaveRequestService {

    private final List<LeaveRequest> leaveRequests = new ArrayList<>();
    private Long nextLeaveRequestId = 1L;

    private final EmployeeService employeeService;

    public LeaveRequestService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Apply for leave
    public LeaveRequest applyLeave(LeaveRequest request) {

        Employee employee =
                employeeService.getEmployeeById(request.getEmployeeId());

        if (employee == null) {
            throw new RuntimeException(
                    "Employee not found with id: " + request.getEmployeeId());
        }

        if (request.getStartDate() == null
                || request.getEndDate() == null) {
            throw new RuntimeException("Start date and end date are required");
        }

        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new RuntimeException(
                    "End date cannot be before start date");
        }

        // Calculate leave days inclusively
        long days = java.time.temporal.ChronoUnit.DAYS.between(
                request.getStartDate(), request.getEndDate()) + 1;

        request.setNumberOfDays((int) days);
        request.setLeaveRequestId(nextLeaveRequestId++);
        request.setStatus("PENDING");

        leaveRequests.add(request);

        return request;
    }

    // Get all leave requests
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequests;
    }

    // Get leave request by ID
    public LeaveRequest getLeaveRequestById(Long id) {
        return leaveRequests.stream()
                .filter(request ->
                        request.getLeaveRequestId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "Leave request not found with id: " + id));
    }

    // Get requests for a particular employee
    public List<LeaveRequest> getLeaveRequestsByEmployeeId(Long employeeId) {
        return leaveRequests.stream()
                .filter(request ->
                        request.getEmployeeId().equals(employeeId))
                .toList();
    }

    // Approve leave
    public LeaveRequest approveLeave(Long id) {
        LeaveRequest request = getLeaveRequestById(id);

        if (!request.getStatus().equals("PENDING")) {
            throw new RuntimeException(
                    "Only pending requests can be approved or rejected");
        }

        Employee employee =
                employeeService.getEmployeeById(request.getEmployeeId());

        if (employee == null) {
            throw new RuntimeException(
                    "Employee not found with id: " + request.getEmployeeId());
        }

        if (employee.getLeaveBalance() < request.getNumberOfDays()) {
            throw new RuntimeException("Insufficient leave balance");
        }

        employee.setLeaveBalance(
                employee.getLeaveBalance() - request.getNumberOfDays());

        request.setStatus("APPROVED");

        return request;
    }

    // Reject leave
    public LeaveRequest rejectLeave(Long id) {
        LeaveRequest request = getLeaveRequestById(id);

        if (!request.getStatus().equals("PENDING")) {
            throw new RuntimeException(
                    "Only pending requests can be approved or rejected");
        }

        request.setStatus("REJECTED");

        return request;
    }

    // Check employee leave balance
    public Integer getLeaveBalance(Long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);

        if (employee == null) {
            throw new RuntimeException(
                    "Employee not found with id: " + employeeId);
        }

        return employee.getLeaveBalance();
    }
}
