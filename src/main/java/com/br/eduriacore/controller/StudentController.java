package com.br.eduriacore.controller;

import java.net.URI;
import java.util.List;

import com.br.eduriacore.dto.StudentDto;
import com.br.eduriacore.service.StudentService;

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
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@Api(value = "Students API")
@RestController
@RequestMapping("/student")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @ApiOperation(value = "Creates a new student", response = StudentDto.class)
    public ResponseEntity<StudentDto> create(
        @RequestBody StudentDto studentDto, UriComponentsBuilder uriBuilder) {
        try {
            StudentDto student = this.studentService.create(studentDto);
            URI uri = uriBuilder.path("/student/{id}").buildAndExpand(student.getId()).toUri();
            return ResponseEntity.created(uri).body(student);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    @ApiOperation(value = "Get a paginated list of students", response = StudentDto.class, responseContainer = "List")
    public ResponseEntity<List<StudentDto>> get(
        @PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable pagination) {
        
        Page<StudentDto> students = this.studentService.get(pagination);
        HttpHeaders headers       = new HttpHeaders();
        headers.add("length", Long.toString(students.getTotalElements()));
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "length");
        
        return ResponseEntity.ok().headers(headers).body(students.getContent());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a student by their id", response = StudentDto.class)
    public ResponseEntity<StudentDto> getById(@PathVariable Long id) {
        try {
            StudentDto dto = this.studentService.getById(id); 
            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Updates a students", response = StudentDto.class)
    public ResponseEntity<StudentDto> update(@RequestBody StudentDto studentDto, @PathVariable Long id) {
        try {
            StudentDto studentDtoToReturn = this.studentService.update(studentDto, id);
            return ResponseEntity.ok().body(studentDtoToReturn);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes a student by their id")
    public ResponseEntity<?> detele(@PathVariable Long id) {
        try {
            this.studentService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
}