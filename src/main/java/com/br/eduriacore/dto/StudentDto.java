package com.br.eduriacore.dto;

import lombok.Data;

@Data
public class StudentDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    
}