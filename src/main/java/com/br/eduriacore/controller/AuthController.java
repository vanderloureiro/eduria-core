package com.br.eduriacore.controller;

import java.net.URI;
import java.time.LocalDate;

import com.br.eduriacore.dto.AuthenticationDto;
import com.br.eduriacore.dto.TeacherDto;
import com.br.eduriacore.form.AuthenticationForm;
import com.br.eduriacore.security.AuthenticationService;
import com.br.eduriacore.service.TeacherService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@Api(value = "Auth API")
@RequestMapping("/auth")
@RestController
public class AuthController {
    
    private AuthenticationService authenticationService;
    private TeacherService teacherService;

    public AuthController(AuthenticationService authenticationService, TeacherService teacherService) {
        this.authenticationService = authenticationService;
        this.teacherService = teacherService;
    }

    @PostMapping
    @ApiOperation(value = "Login", response = AuthenticationDto.class)
    public ResponseEntity<AuthenticationDto> create(@RequestBody AuthenticationForm authenticationForm) {
        try {
            return ResponseEntity.ok().body(authenticationService.authenticate(authenticationForm));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/create-admin")
    @ApiOperation(value = "Creates a admin teacher", response = TeacherDto.class)
    public ResponseEntity<TeacherDto> create(UriComponentsBuilder uriBuilder) {
        try {
            TeacherDto teacherDto = new TeacherDto();
            teacherDto.setName("Admin");
            teacherDto.setEmail("admin@email.com");
            teacherDto.setPassword("admin123");
            teacherDto.setPhone("99999999");
            teacherDto.setBirthDate(LocalDate.of(1997, 03, 29));
            TeacherDto teacher = this.teacherService.create(teacherDto);
            URI uri = uriBuilder.path("/teacher/{id}").buildAndExpand(teacher.getId()).toUri();
            return ResponseEntity.created(uri).body(teacher);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
