package com.br.eduriacore.repository;

import com.br.eduriacore.model.Course;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long>{

}