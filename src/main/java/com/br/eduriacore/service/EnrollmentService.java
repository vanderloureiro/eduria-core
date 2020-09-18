package com.br.eduriacore.service;

import java.util.Optional;

import com.br.eduriacore.dto.EnrollmentDto;
import com.br.eduriacore.exception.NotFoundException;
import com.br.eduriacore.form.EnrollmentForm;
import com.br.eduriacore.mapper.EnrollmentMapper;
import com.br.eduriacore.model.Course;
import com.br.eduriacore.model.Enrollment;
import com.br.eduriacore.model.Student;
import com.br.eduriacore.repository.EnrollmentRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {
    
    private EnrollmentRepository repository;
    private EnrollmentMapper mapper;
    private StudentService studentService;
    private CourseService courseService;

    public EnrollmentService(EnrollmentRepository repository, StudentService studentService, EnrollmentMapper mapper,
            CourseService courseService) {
        this.repository = repository;
        this.studentService = studentService;
        this.mapper = mapper;
        this.courseService = courseService;
    }

    public EnrollmentDto create(EnrollmentForm form) {
        Course course   = this.courseService.getEntityById(form.getCourseId());
        Student student = this.studentService.getEntityById(form.getStudentId());

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollment.setScore(0.0);

        return this.mapper.toDto(this.repository.save(enrollment));
    }

    public Page<EnrollmentDto> get(Pageable pagination) {
        return this.repository.findAll(pagination).map(
            enrollment -> {
                return this.mapper.toDto(enrollment);
            }
        );
    }

    public EnrollmentDto getById(Long id) {
        return this.mapper.toDto(this.getEntityById(id));
    }

    public Enrollment getEntityById(Long id) {
        Optional<Enrollment> enrollment = this.repository.findById(id);
        return enrollment.orElseThrow(() -> new NotFoundException("Enrollment not found"));
    }

    public void delete(Long id) {
        this.repository.deleteById(id);
    }

    public EnrollmentDto updateEnrollment(Enrollment updatedEnrollment) {
        return this.mapper.toDto(this.repository.save(updatedEnrollment));
    }
}