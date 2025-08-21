package com.example.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.student.entity.Student;
import com.example.student.service.StudentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    @Autowired
    private StudentService studentService;

    // Add Student
    @PostMapping("/add")
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        try {
            Student savedStudent = studentService.saveStudent(student);
            return ResponseEntity.ok(savedStudent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding student: " + e.getMessage());
        }
    }

    // Update Student
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        try {
            student.setId(id);
            Student updatedStudent = studentService.saveStudent(student);
            return ResponseEntity.ok(updatedStudent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating student: " + e.getMessage());
        }
    }

    // Delete student
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok("Student deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting student: " + e.getMessage());
        }
    }

    // List all students
    @GetMapping("/all")
    public ResponseEntity<?> getAllStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching students: " + e.getMessage());
        }
    }

    // Search by name
    @GetMapping("/search/name")
    public ResponseEntity<?> searchByName(@RequestParam String name) {
        try {
            List<Student> students = studentService.searchStudentsByName(name);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error searching students: " + e.getMessage());
        }
    }

    // Search by roll number
    @GetMapping("/search/roll")
    public ResponseEntity<?> searchByRollNumber(@RequestParam Long rollNumber) {
        try {
            Optional<Student> student = studentService.getStudentByRollNumber(rollNumber);
            if (student.isPresent()) {
                return ResponseEntity.ok(student.get());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error searching student: " + e.getMessage());
        }
    }
}