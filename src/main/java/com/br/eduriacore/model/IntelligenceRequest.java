package com.br.eduriacore.model;

import java.util.UUID;

import com.br.eduriacore.model.enums.LevelQuestionEnum;

import lombok.Data;

@Data
public class IntelligenceRequest {
    
    private UUID registerUuid;
    private Long studentId;
    private Integer studentAge;
    private String courseName;
    private Integer easyQuestionsAnsweredCorrect;
    private Integer mediumQuestionsAnsweredCorrect;
    private Integer hardQuestionsAnsweredCorrect;
    private Integer qttAllQuestionsAnswered;
    private Integer qttAllCourseQuestions;
    private Integer score;
    private LevelQuestionEnum lastQuestionLevel;
    private Boolean lastQuestionWasAnsweredCorrect;
}