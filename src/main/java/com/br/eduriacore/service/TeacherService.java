package com.br.eduriacore.service;

import java.util.Optional;

import com.br.eduriacore.dto.TeacherDto;
import com.br.eduriacore.exception.NotFoundException;
import com.br.eduriacore.mapper.TeacherMapper;
import com.br.eduriacore.model.Teacher;
import com.br.eduriacore.repository.TeacherRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    private TeacherRepository repository;
    private TeacherMapper mapper;
    private PasswordEncoder passwordEncoder;

    public TeacherService(TeacherRepository repository, TeacherMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public TeacherDto create(TeacherDto teacherDto) {
        Teacher entityToSave = this.mapper.toEntityWithoutId(teacherDto);
        entityToSave.setPassword(
            passwordEncoder.encode(entityToSave.getPassword())
        );
        return this.mapper.toDto(this.repository.save(entityToSave));
    }

    public Page<TeacherDto> get(Pageable pagination) {
        return this.repository.findAll(pagination).map(
            teacherEntity -> {
                return this.mapper.toDto(teacherEntity);
            }
        );
    }

    public TeacherDto getById(Long id) {
        return this.mapper.toDto(this.getEntityById(id));
    }

    public Teacher getEntityById(Long id) {
        Optional<Teacher> teacher = this.repository.findById(id);
        return teacher.orElseThrow(() -> new NotFoundException("Teacher not found"));
    }

    public TeacherDto update(TeacherDto teacherDto, Long id) {
        Optional<Teacher> teacher = this.repository.findById(id);
        if (teacher.isPresent()) {
            Teacher teacherToUpdate = this.mapper.toEntityWithoutId(teacherDto);
            teacherToUpdate.setId(teacher.get().getId());
            teacherToUpdate.setPassword(
                passwordEncoder.encode(teacherToUpdate.getPassword())
            );
            return this.mapper.toDto(this.repository.save(teacherToUpdate));
        }
        throw new NotFoundException("Teacher not found");
    }

    public void delete(Long id) {
        this.repository.deleteById(id);
    }
    
}