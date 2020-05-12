package com.br.eduriacore.controller;

import java.util.List;

import com.br.eduriacore.dto.CourseDto;
import com.br.eduriacore.service.CourseService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@Api(value = "Course API")
@RequestMapping("/course")
@RestController
public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    @ApiOperation(value = "Creates a new course", response = CourseDto.class)
    public ResponseEntity<CourseDto> create(CourseDto courseDto) {
        CourseDto courseDtoResponse = this.courseService.create(courseDto);
        return ResponseEntity.ok().body(courseDtoResponse);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a course by their id", response = CourseDto.class)
    public ResponseEntity<CourseDto> getById(Long id) {
        CourseDto courseDtoResponse = this.courseService.getById(id);
        return ResponseEntity.ok().body(courseDtoResponse);
    }

    @GetMapping
    @ApiOperation(value = "Get a paginated list of courses", response = CourseDto.class, responseContainer = "List")
    public ResponseEntity<List<CourseDto>> get(
        @PageableDefault(sort = "courseId", direction = Direction.ASC, page = 0, size = 10) Pageable pagination) {
        
        Page<CourseDto> courses = this.courseService.get(pagination);
        HttpHeaders headers     = new HttpHeaders();
        headers.add("length", Long.toString(courses.getTotalElements()));
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "length");
        
        return ResponseEntity.ok().headers(headers).body(courses.getContent());
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Updates a course", response = CourseDto.class)
    public ResponseEntity<CourseDto> update(@RequestBody CourseDto courseDto, @PathVariable Long id) {
        try {
            CourseDto courseDtoToReturn = this.courseService.update(courseDto, id);
            return ResponseEntity.ok().body(courseDtoToReturn);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes a course by their id")
    public ResponseEntity<?> detele(@PathVariable Long id) {
        try {
            this.courseService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    
    
}