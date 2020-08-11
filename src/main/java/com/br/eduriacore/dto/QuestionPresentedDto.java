package com.br.eduriacore.dto;

import com.br.eduriacore.model.enums.LevelQuestionEnum;

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
    private LevelQuestionEnum questionLevel;

}