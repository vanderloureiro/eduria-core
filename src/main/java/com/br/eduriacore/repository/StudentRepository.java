package com.br.eduriacore.repository;

import java.util.Optional;

import com.br.eduriacore.model.Student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long>{

    Optional<Student> findByEmail(String email);
}