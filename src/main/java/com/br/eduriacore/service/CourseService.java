package com.br.eduriacore.service;

import java.util.Optional;

import com.br.eduriacore.dto.CourseDto;
import com.br.eduriacore.exception.NotFoundException;
import com.br.eduriacore.mapper.CourseMapper;
import com.br.eduriacore.model.Course;
import com.br.eduriacore.model.Teacher;
import com.br.eduriacore.repository.CourseRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    
    private CourseMapper mapper;
    private CourseRepository repository;
	private TeacherService teacherService;

    public CourseService(CourseMapper mapper, CourseRepository repository, TeacherService teacherService) {
		this.mapper = mapper;
        this.repository = repository;
		this.teacherService = teacherService;
    }

    public CourseDto create(CourseDto courseDto) {
        Teacher teacher     = this.teacherService.getEntityById(courseDto.getTeacherId());
        Course courseToSave = new Course();
        courseToSave.setCourseName(courseDto.getCourseName());
        courseToSave.setTeacher(teacher);
        courseToSave.setIntegrationUri(courseDto.getIntegrationUri());
        courseToSave.setIntegrationName(courseDto.getIntegrationName());
        return this.mapper.toDto(this.repository.save(courseToSave));
    }

    public CourseDto getById(Long id) {
        return this.mapper.toDto(this.getEntityById(id));
    }

    public Course getEntityById(Long id) {
        Optional<Course> course = this.repository.findById(id);
        return course.orElseThrow(() -> new NotFoundException("Course not found"));
    }

    public Page<CourseDto> get(Pageable pagination) {
        return this.repository.findAll(pagination).map( 
            course -> {
                return this.mapper.toDto(course);
            });
    }

    public CourseDto update(CourseDto courseDto, Long id) {
        Optional<Course> course = this.repository.findById(id);
        Teacher teacher = this.teacherService.getEntityById(courseDto.getTeacherId());
        
        course.orElseThrow(() -> new NotFoundException("Course not found"));

        Course courseToSave = new Course();
        courseToSave.setCourseName(courseDto.getCourseName());
        courseToSave.setTeacher(teacher);
        courseToSave.setCourseId(id);
        courseToSave.setIntegrationUri(courseDto.getIntegrationUri());
        courseToSave.setIntegrationName(courseDto.getIntegrationName());
        return this.mapper.toDto(this.repository.save(courseToSave));
    }

    public void delete(Long id) {
        this.repository.deleteById(id);
    }

}