package com.br.eduriacore.service;

import java.util.Optional;

import com.br.eduriacore.dto.StudentDto;
import com.br.eduriacore.exception.NotFoundException;
import com.br.eduriacore.mapper.StudentMapper;
import com.br.eduriacore.model.Student;
import com.br.eduriacore.repository.StudentRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private StudentRepository repository;
    private StudentMapper mapper;

    public StudentService(StudentRepository repository, StudentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public StudentDto getById(Long id) {
        return this.mapper.toDto(this.getEntityById(id));
    }

    public StudentDto create(StudentDto studentDto) {
        Student studentToSave = this.mapper.toEntityWithoutId(studentDto);
        Student returnStudent = this.repository.save(studentToSave);
        return this.mapper.toDto(returnStudent);
    }

    public StudentDto update(StudentDto studentDto, long id) {
        Student student = this.getEntityById(id);
        Student entity  = this.mapper.toEntityWithoutId(studentDto);
        entity.setId(student.getId());
        return this.mapper.toDto(this.repository.save(entity));
    }

    public Page<StudentDto> get(Pageable pagination) {
        return this.repository.findAll(pagination).map(
            student -> { 
                return this.mapper.toDto(student); 
            }
        );
    }

    public void delete(Long id) throws Exception {
        this.repository.deleteById(id);
    }

    public Student getEntityById(Long id) {
        Optional<Student> student = this.repository.findById(id);
        if (student.isPresent()) {
            return student.get();
        }
        throw new NotFoundException("Student not found");
    }

}