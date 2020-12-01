package com.br.eduriacore.dto;

import lombok.Data;

@Data
public class AuthenticationDto {
    
    private String name;
    private String email;
    private String token;
}
