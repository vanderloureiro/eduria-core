package com.br.eduriacore.model;

import com.br.eduriacore.model.enums.IntelligenceLevelEnum;

import lombok.Data;

@Data
public class IntelligenceRequest {
    
    private Long registerId;
    private Long studentId;
    private Integer studentAge;
    private String courseName;
    private Integer easyQuestionsAnsweredCorrect;
    private Integer mediumQuestionsAnsweredCorrect;
    private Integer hardQuestionsAnsweredCorrect;
    private Integer qttAllQuestionsAnswered;
    private Integer qttAllCourseQuestions;
    private IntelligenceLevelEnum lastQuestionLevel;
    private boolean lastQuestionWasAnsweredCorrect;
}