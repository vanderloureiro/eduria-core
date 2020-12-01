package com.br.eduriacore.security;

import java.util.Optional;

import com.br.eduriacore.dto.AuthenticationDto;
import com.br.eduriacore.exception.NotFoundException;
import com.br.eduriacore.exception.UnauthorizedException;
import com.br.eduriacore.form.AuthenticationForm;
import com.br.eduriacore.model.Person;
import com.br.eduriacore.model.Student;
import com.br.eduriacore.model.Teacher;
import com.br.eduriacore.repository.StudentRepository;
import com.br.eduriacore.repository.TeacherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;

    public AuthenticationDto authenticate(AuthenticationForm form) {
        
        UsernamePasswordAuthenticationToken dadosLogin 
            = new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword());

        try {
            this.authManager.authenticate(dadosLogin);
            return this.createReturnObject(form.getEmail());
        } catch (Exception e) {
            throw new UnauthorizedException("Usuário não autorizado");
        }
    }

    private AuthenticationDto createReturnObject(String email) {

        Person person = this.getPerson(email);

        String token = this.tokenService.generateToken(person.getId());
        AuthenticationDto dto = new AuthenticationDto();
        dto.setToken(token);
        dto.setName(person.getName());
        dto.setEmail(person.getEmail());
        return dto;
    }

    private Person getPerson(String email) {

        Optional<Student> student = this.studentRepository.findByEmail(email);
        if (student.isPresent()) {
            return student.get();
        }

        Optional<Teacher> teacher = this.teacherRepository.findByEmail(email);
        if (teacher.isPresent()) {
            return teacher.get();
        }

        throw new NotFoundException("Not found");
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Student> student = this.studentRepository.findByEmail(email);
        if (student.isPresent()) {
            return student.get();
        }

        Optional<Teacher> teacher = this.teacherRepository.findByEmail(email);
        if (teacher.isPresent()) {
            return teacher.get();
        }

        throw new UsernameNotFoundException("User not found");
    }


    
}
