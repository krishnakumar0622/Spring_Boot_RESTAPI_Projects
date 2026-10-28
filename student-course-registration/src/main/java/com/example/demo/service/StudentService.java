
package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Student;
import com.example.demo.exception.StudentNotFoundException;

@Service
public class StudentService {

    private final List<Student> students = new ArrayList<>();
    private Long nextId = 1L;

    // Add student
    public Student addStudent(Student student) {
        student.setStudentId(nextId++);
        students.add(student);
        return student;
    }

    // Get all students
    public List<Student> getAllStudents() {
        return students;
    }

    // Get student by ID
    public Student getStudentById(Long id) {
        return students.stream()
                .filter(student -> student.getStudentId().equals(id))
                .findFirst()
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    // Update entire student
    public Student updateStudent(Long id, Student updatedStudent) {
        Student existingStudent = getStudentById(id);

        existingStudent.setStudentName(updatedStudent.getStudentName());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setAge(updatedStudent.getAge());
        existingStudent.setCourseName(updatedStudent.getCourseName());

        return existingStudent;
    }

    // Delete student
    public boolean deleteStudent(Long id) {
        return students.removeIf(student -> student.getStudentId().equals(id));
    }

    // Count students
    public int getTotalStudents() {
        return students.size();
    }

    // Search students by course name
    public List<Student> getStudentsByCourseName(String courseName) {
        return students.stream()
                .filter(student -> student.getCourseName()
                        .equalsIgnoreCase(courseName))
                .toList();
    }

    // Partially update student
    public Student patchStudent(Long id, Student updates) {
        Student existingStudent = getStudentById(id);

        if (updates.getStudentName() != null) {
            existingStudent.setStudentName(updates.getStudentName());
        }

        if (updates.getEmail() != null) {
            existingStudent.setEmail(updates.getEmail());
        }

        if (updates.getAge() != null) {
            existingStudent.setAge(updates.getAge());
        }

        if (updates.getCourseName() != null) {
            existingStudent.setCourseName(updates.getCourseName());
        }

        return existingStudent;
    }
}

