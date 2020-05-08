package com.br.eduriacore.controller;

import java.util.List;

import com.br.eduriacore.dto.CourseDto;
import com.br.eduriacore.service.CourseService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/course")
@RestController
public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<CourseDto> create(CourseDto courseDto) {
        return null;
    }

    @GetMapping
    public ResponseEntity<CourseDto> getById(Long id) {
        return null;
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> get() {
        return null;
    }

    @PutMapping
    public ResponseEntity<CourseDto> update() {
        return null;
    }

    @DeleteMapping
    public ResponseEntity<?> delete(Long id) {
        return null;
    }

    
    
}