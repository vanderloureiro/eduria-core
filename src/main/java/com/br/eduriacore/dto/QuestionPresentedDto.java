package com.br.eduriacore.dto;

import lombok.Data;

@Data
public class QuestionPresentedDto {
    
    private Long questionId;
    private Long courseId;
    private Long enrollmentId;
    private String text;
    private String description;
    private String alternative1;
    private String alternative2;
    private String alternative3;
    private String alternative4;
    private int questionLevel;

}