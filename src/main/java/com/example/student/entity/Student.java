package com.example.student.entity;

import com.example.student.enums.Course;
import com.example.student.enums.Department;
import com.example.student.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // primary key

    @Column(unique = true)
    private Long rollNumber; // new roll number field

    private String name;

    @Column(unique = true)
    private String email;

    private String phone;

    private String password;

    @Enumerated(EnumType.STRING)
    private Course course;

    @Enumerated(EnumType.STRING)
    private Department department;

    @Enumerated(EnumType.STRING)
    private Role role = Role.STUDENT;
}