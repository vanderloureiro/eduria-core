package com.br.eduriacore.dto;

import lombok.Data;

@Data
public class TeacherDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private CourseDto courses[];

}