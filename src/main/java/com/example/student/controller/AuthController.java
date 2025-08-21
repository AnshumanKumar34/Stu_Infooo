package com.example.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.example.student.entity.Admin;
import com.example.student.entity.Student;
import com.example.student.security.JwtUtil;
import com.example.student.service.AdminService;
import com.example.student.service.StudentService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AdminService adminService;

    @Autowired
    private StudentService studentService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");
            
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );
            
            final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            final String role = userDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
            
            // FIXED: Only pass email to generateToken (matches your JwtUtil method signature)
            final String token = jwtUtil.generateToken(email);
            
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("role", role);
            response.put("email", email);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid email or password: " + e.getMessage());
        }
    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody Admin admin) {
        try {
            Admin savedAdmin = adminService.saveAdmin(admin);
            return ResponseEntity.ok(savedAdmin);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error registering admin: " + e.getMessage());
        }
    }

    @PostMapping("/register/student")
    public ResponseEntity<?> registerStudent(@RequestBody Student student) {
        try {
            Student savedStudent = studentService.registerStudent(student);
            return ResponseEntity.ok(savedStudent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error registering student: " + e.getMessage());
        }
    }
}