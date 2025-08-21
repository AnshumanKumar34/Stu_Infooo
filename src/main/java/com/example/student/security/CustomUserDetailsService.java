package com.example.student.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.student.entity.Admin;
import com.example.student.entity.Student;
import com.example.student.repository.AdminRepository;
import com.example.student.repository.StudentRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Check Admin
        Admin admin = adminRepository.findByEmail(email).orElse(null);
        if (admin != null) {
            return User.builder()
                    .username(admin.getEmail())  // email used as username
                    .password(admin.getPassword())
                    .roles(admin.getRole().name())
                    .build();
        }

        // Check Student
        Student student = studentRepository.findByEmail(email).orElse(null);
        if (student != null) {
            return User.builder()
                    .username(student.getEmail()) // email used as username
                    .password(student.getPassword())
                    .roles(student.getRole().name())
                    .build();
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}