package com.br.eduriacore.repository;

import java.util.Optional;

import com.br.eduriacore.model.Teacher;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByEmail(String email);
}