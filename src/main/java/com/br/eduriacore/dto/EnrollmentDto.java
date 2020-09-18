package com.br.eduriacore.dto;

import com.br.eduriacore.model.enums.LevelQuestionEnum;

import lombok.Data;

@Data
public class EnrollmentDto {

    private Long enrollmentId;
    private Long studentId;
    private Long courseId;
    private Integer score;
    private Integer easyQuestionsAnsweredCorrect;
    private Integer mediumQuestionsAnsweredCorrect;
    private Integer hardQuestionsAnsweredCorrect;
    private Integer qttAllQuestionsAnswered;
    private LevelQuestionEnum lastQuestionLevel;
    private Boolean lastQuestionWasAnsweredCorrect;

}