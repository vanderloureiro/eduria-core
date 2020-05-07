package com.br.eduriacore.mapper;

import com.br.eduriacore.dto.CourseDto;
import com.br.eduriacore.model.Course;

import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseDto toDto(Course entity) {
        CourseDto dto = new CourseDto();
        dto.setCourseId(entity.getCourseId());
        dto.setCourseName(entity.getCourseName());
        dto.setTeacherId(entity.getTeacher().getId());
        return dto;
    }
    
}