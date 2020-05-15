package com.br.eduriacore.controller;

import java.net.URI;
import java.util.List;

import com.br.eduriacore.dto.EnrollmentDto;
import com.br.eduriacore.service.EnrollmentService;

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
@Api(value = "enrollment API")
@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {

    private EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }
    
    @PostMapping
    @ApiOperation(value = "Creates a new enrollment", response = EnrollmentDto.class)
    public ResponseEntity<EnrollmentDto> create(
        @RequestBody EnrollmentDto enrollmentDto, UriComponentsBuilder uriBuilder) {
        try {
            EnrollmentDto enrollment = this.enrollmentService.create(enrollmentDto);
            URI uri = uriBuilder.path("/enrollment/{id}").buildAndExpand(enrollment.getEnrollmentId()).toUri();
            return ResponseEntity.created(uri).body(enrollment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    @ApiOperation(value = "Get a paginated list of enrollments", response = EnrollmentDto.class, responseContainer = "List")
    public ResponseEntity<List<EnrollmentDto>> get(
        @PageableDefault(sort = "enrollmentId", direction = Direction.ASC, page = 0, size = 10) Pageable pagination) {
        
        Page<EnrollmentDto> enrollments = this.enrollmentService.get(pagination);
        HttpHeaders headers             = new HttpHeaders();
        headers.add("length", Long.toString(enrollments.getTotalElements()));
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "length");
        
        return ResponseEntity.ok().headers(headers).body(enrollments.getContent());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a enrollment by their id", response = EnrollmentDto.class)
    public ResponseEntity<EnrollmentDto> getById(@PathVariable Long id) {
        try {
            EnrollmentDto dto = this.enrollmentService.getById(id); 
            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes a enrollment by their id")
    public ResponseEntity<?> detele(@PathVariable Long id) {
        try {
            this.enrollmentService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}