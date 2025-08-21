package com.example.student.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.student.entity.Student;
import com.example.student.enums.Role;
import com.example.student.repository.StudentRepository;


@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Student Registration (sets role to STUDENT and encodes password)
    public Student registerStudent(Student student) {
        student.setRole(Role.STUDENT);
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        return studentRepository.save(student);
    }

    // Save or Update (used by admin and student update)
    public Student saveStudent(Student student) {
        // If password is not encoded, encode it
        if (student.getPassword() != null && !student.getPassword().startsWith("$2a$")) {
            student.setPassword(passwordEncoder.encode(student.getPassword()));
        }
        return studentRepository.save(student);
    }

    // Admin-only: Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Admin-only: Search by name
    public List<Student> searchStudentsByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }

    // Admin-only: Search by roll number
    public Optional<Student> getStudentByRollNumber(Long rollNumber) {
        return studentRepository.findByRollNumber(rollNumber);
    }

    // Admin-only: Delete student
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    // Find by email (for login / student update)
    public Optional<Student> getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }
}