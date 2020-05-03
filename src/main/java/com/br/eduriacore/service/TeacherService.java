package com.br.eduriacore.service;

import com.br.eduriacore.dto.TeacherDto;
import com.br.eduriacore.mapper.TeacherMapper;
import com.br.eduriacore.repository.TeacherRepository;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    private TeacherRepository teacherRepository;
    private TeacherMapper teacherMapper;

    public TeacherService(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }

    public TeacherDto create() {
        return null;
    }

    public Page<TeacherDto> get() {
        return null;
    }

    public TeacherDto getById(Long id) {
        return null;
    }

    public TeacherDto update() {
        return null;
    }

    public void delete(Long Id) {

    }
    
}