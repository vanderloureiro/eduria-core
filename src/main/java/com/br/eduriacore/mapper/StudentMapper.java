package com.br.eduriacore.mapper;

import com.br.eduriacore.dto.StudentDto;
import com.br.eduriacore.model.Student;

import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public StudentDto toDto(Student entity){
        StudentDto dto = new StudentDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setPhone(entity.getPhone());
        return dto;
    }

    public Student toEntityWithoutId(StudentDto dto) {
        Student entity = new Student();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setPhone(dto.getPhone());
        return entity;
    }

}