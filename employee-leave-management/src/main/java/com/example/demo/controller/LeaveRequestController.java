
package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.LeaveRequest;
import com.example.demo.service.LeaveRequestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/leaves")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    public LeaveRequestController(
            LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    // Apply for leave
    @PostMapping
    public ResponseEntity<LeaveRequest> applyLeave(
            @Valid @RequestBody LeaveRequest request) {

        LeaveRequest savedRequest =
                leaveRequestService.applyLeave(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedRequest);
    }

    // Get all leave requests
    @GetMapping
    public ResponseEntity<List<LeaveRequest>> getAllLeaveRequests() {
        return ResponseEntity.ok(
                leaveRequestService.getAllLeaveRequests());
    }

    // Get leave request by ID
    @GetMapping("/{id}")
    public ResponseEntity<LeaveRequest> getLeaveRequestById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                leaveRequestService.getLeaveRequestById(id));
    }

    // Get leave requests for an employee
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveRequest>>
            getLeaveRequestsByEmployeeId(
                    @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                leaveRequestService
                        .getLeaveRequestsByEmployeeId(employeeId));
    }

    // Approve leave
    @PatchMapping("/{id}/approve")
    public ResponseEntity<LeaveRequest> approveLeave(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                leaveRequestService.approveLeave(id));
    }

    // Reject leave
    @PatchMapping("/{id}/reject")
    public ResponseEntity<LeaveRequest> rejectLeave(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                leaveRequestService.rejectLeave(id));
    }

    // Check leave balance
    @GetMapping("/employee/{employeeId}/balance")
    public ResponseEntity<Integer> getLeaveBalance(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                leaveRequestService.getLeaveBalance(employeeId));
    }
}
