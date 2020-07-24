package com.br.eduriacore.dto;

import lombok.Data;

@Data
public class ResponseResultDto {
    
    private Long questionId;
    private Long enrollmentId;
    private Double score;
    private boolean isCorrectResponse;
    private String correctAlternative;
}