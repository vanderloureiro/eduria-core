package com.br.eduriacore.repository;

import com.br.eduriacore.model.Enrollment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

}