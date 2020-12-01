package com.br.eduriacore.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.br.eduriacore.model.Student;
import com.br.eduriacore.model.Teacher;
import com.br.eduriacore.repository.StudentRepository;
import com.br.eduriacore.repository.TeacherRepository;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private TeacherRepository teacherRepository;
    private StudentRepository studentRepository;

    public AuthenticationFilter(TokenService tokenService, TeacherRepository teacherRepository,
            StudentRepository studentRepository) {
        this.tokenService = tokenService;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getToken(request);
        
        if (tokenService.verifyIfValid(token)) {
            tryAuthenticateWithTeacher(token);
            tryAuthenticateWithStudent(token);
        }
        
        filterChain.doFilter(request, response);
    }

    private void tryAuthenticateWithTeacher(String token) {
        Long idPerson = tokenService.getPersonId(token);
        Optional<Teacher> teacher = this.teacherRepository.findById(idPerson);
        
        if (teacher.isPresent()) {

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(teacher, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
		
    }

    private void tryAuthenticateWithStudent(String token) {
        Long idPerson = tokenService.getPersonId(token);

		Optional<Student> student = this.studentRepository.findById(idPerson);
        
        if (student.isPresent()) {

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(student, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
    
    private String getToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.substring(7, token.length());
	}
}
