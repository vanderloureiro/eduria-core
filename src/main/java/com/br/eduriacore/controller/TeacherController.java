package com.br.eduriacore.controller;

import java.net.URI;
import java.util.List;

import com.br.eduriacore.dto.TeacherDto;
import com.br.eduriacore.service.TeacherService;

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
@Api(value = "Teachers API")
@RequestMapping("/teacher")
@RestController
public class TeacherController {
    
    private TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    @ApiOperation(value = "Creates a new teacher", response = TeacherDto.class)
    public ResponseEntity<TeacherDto> create(
        @RequestBody TeacherDto teacherDto, UriComponentsBuilder uriBuilder) {
        try {
            TeacherDto teacher = this.teacherService.create(teacherDto);
            URI uri = uriBuilder.path("/teacher/{id}").buildAndExpand(teacher.getId()).toUri();
            return ResponseEntity.created(uri).body(teacher);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a teacher by their id", response = TeacherDto.class)
    public ResponseEntity<TeacherDto> findById(@PathVariable Long id) {
        try {
            TeacherDto dto = this.teacherService.getById(id); 
            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @ApiOperation(value = "Get a paginated list of teachers", response = TeacherDto.class, responseContainer = "List")
    public ResponseEntity<List<TeacherDto>> get(
        @PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable pagination) {
        
        Page<TeacherDto> teachers = this.teacherService.get(pagination);
        HttpHeaders headers       = new HttpHeaders();
        headers.add("length", Long.toString(teachers.getTotalElements()));
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "length");
        
        return ResponseEntity.ok().headers(headers).body(teachers.getContent());
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Updates a teacher", response = TeacherDto.class)
    public ResponseEntity<TeacherDto> update(@RequestBody TeacherDto teacherDto, @PathVariable Long id) {
        try {
            TeacherDto teacherDtoToReturn = this.teacherService.update(teacherDto, id);
            return ResponseEntity.ok().body(teacherDtoToReturn);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes a student by their id")
    public ResponseEntity<?> detele(@PathVariable Long id) {
        try {
            this.teacherService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}