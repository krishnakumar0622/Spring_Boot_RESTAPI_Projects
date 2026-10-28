
package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Add student
    @PostMapping
    public ResponseEntity<Student> addStudent(
            @Valid @RequestBody Student student) {

        Student savedStudent = studentService.addStudent(student);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    // Get all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // Get student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    // Update entire student
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody Student student) {

        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    // Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {

        boolean deleted = studentService.deleteStudent(id);

        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student not found with id: " + id);
        }

        return ResponseEntity.ok("Student deleted successfully");
    }

    // Count students
    @GetMapping("/count")
    public ResponseEntity<Integer> getTotalStudents() {
        return ResponseEntity.ok(studentService.getTotalStudents());
    }

    // Search by course name
    @GetMapping("/course/{courseName}")
    public ResponseEntity<List<Student>> getStudentsByCourseName(
            @PathVariable String courseName) {

        return ResponseEntity.ok(
                studentService.getStudentsByCourseName(courseName));
    }

    // Partially update student
    @PatchMapping("/{id}")
    public ResponseEntity<Student> patchStudent(
            @PathVariable Long id,
            @RequestBody Student updates) {

        return ResponseEntity.ok(studentService.patchStudent(id, updates));
    }
}
