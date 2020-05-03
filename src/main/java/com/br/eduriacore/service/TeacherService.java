package com.br.eduriacore.service;

import java.util.Optional;

import com.br.eduriacore.dto.TeacherDto;
import com.br.eduriacore.mapper.TeacherMapper;
import com.br.eduriacore.model.Teacher;
import com.br.eduriacore.repository.TeacherRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    private TeacherRepository repository;
    private TeacherMapper mapper;

    public TeacherService(TeacherRepository repository, TeacherMapper mapper) {
        this.repository = repository;
        this.mapper     = mapper;
    }

    public TeacherDto create(TeacherDto teacherDto) {
        Teacher entityToSave = this.mapper.toEntityWithoutId(teacherDto);
        return this.mapper.toDto(this.repository.save(entityToSave));
    }

    public Page<TeacherDto> get(Pageable pagination) {
        return this.repository.findAll(pagination).map(
            teacherEntity -> {
                return this.mapper.toDto(teacherEntity);
            }
        );
    }

    public TeacherDto getById(Long id) throws Exception {
        Optional<Teacher> teacher = this.repository.findById(id);
        if (teacher.isPresent()) {
            return this.mapper.toDto(teacher.get());
        }
        throw new Exception("Teacher not found");
    }

    public TeacherDto update(TeacherDto teacherDto, Long id) throws Exception {
        Optional<Teacher> teacher = this.repository.findById(id);
        if (teacher.isPresent()) {
            Teacher teacherToUpdate = this.mapper.toEntityWithoutId(teacherDto);
            teacherToUpdate.setId(teacher.get().getId());
            return this.mapper.toDto(this.repository.save(teacherToUpdate));
        }
        throw new Exception("Teacher not found");
    }

    public void delete(Long id) {
        this.repository.deleteById(id);
    }
    
}