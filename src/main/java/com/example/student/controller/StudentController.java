package com.example.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.student.entity.Student;
import com.example.student.service.StudentService;

import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Student Registration
    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(@RequestBody Student student) {
        try {
            Student savedStudent = studentService.registerStudent(student);
            return ResponseEntity.ok(savedStudent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error registering student: " + e.getMessage());
        }
    }

    // Student Update Own Info
    @PutMapping("/update")
    public ResponseEntity<?> updateStudent(@RequestBody Student student) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<Student> existingStudent = studentService.getStudentByEmail(email);
            
            if (existingStudent.isPresent()) {
                student.setId(existingStudent.get().getId());
                student.setEmail(email); // Keep original email
                student.setRole(existingStudent.get().getRole()); // Keep original role
                
                // Handle password - if not changing, keep existing encoded password
                if (student.getPassword() == null || student.getPassword().isEmpty()) {
                    student.setPassword(existingStudent.get().getPassword());
                } else {
                    student.setPassword(passwordEncoder.encode(student.getPassword()));
                }
                
                Student updatedStudent = studentService.saveStudent(student);
                return ResponseEntity.ok(updatedStudent);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating student: " + e.getMessage());
        }
    }
}