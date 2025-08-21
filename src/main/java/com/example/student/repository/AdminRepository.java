package com.example.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student.entity.Admin;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
}
