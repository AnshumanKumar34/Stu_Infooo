package com.example.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student.entity.Admin;
import com.example.student.repository.AdminRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    // Create or Update Admin
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    // Get all Admins
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    // Get Admin by ID
    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    // Delete Admin
    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }
}
