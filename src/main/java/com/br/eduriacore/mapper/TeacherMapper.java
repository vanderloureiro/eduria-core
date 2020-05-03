package com.br.eduriacore.mapper;

import com.br.eduriacore.dto.TeacherDto;
import com.br.eduriacore.model.Teacher;

import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {
    
    public TeacherDto toDto(Teacher entity) {
        TeacherDto dto = new TeacherDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setPhone(entity.getPhone());
        return dto;
    }

    public Teacher toEntityWithoutId(TeacherDto dto) {
        Teacher entity = new Teacher();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setPhone(dto.getPhone());
        return entity;
    }
}