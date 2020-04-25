package com.br.eduriacore.repository;

import com.br.eduriacore.model.Student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long>{

}