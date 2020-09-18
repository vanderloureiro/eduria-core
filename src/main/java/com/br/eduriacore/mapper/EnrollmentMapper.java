package com.br.eduriacore.mapper;

import com.br.eduriacore.dto.EnrollmentDto;
import com.br.eduriacore.model.Course;
import com.br.eduriacore.model.Enrollment;
import com.br.eduriacore.model.Student;

import org.springframework.stereotype.Component;

@Component
public class EnrollmentMapper {
    
    public EnrollmentDto toDto(Enrollment entity) {
        EnrollmentDto dto = new EnrollmentDto();
        dto.setEnrollmentId(entity.getEnrollmentId());
        dto.setCourseId(entity.getCourse().getCourseId());
        dto.setStudentId(entity.getStudent().getId());
        dto.setScore(entity.getScore());
        return dto;
    }

    public Enrollment toEntityWithoutId(EnrollmentDto dto) {
        Enrollment entity = new Enrollment();
        Course course     = new Course();
        Student student   = new Student();

        course.setCourseId(dto.getCourseId());
        student.setId(dto.getStudentId());

        entity.setCourse(course);
        entity.setStudent(student);
        return entity;
    }
}