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

    public String toLatexRowTable() {
        String formatedText = " & " + this.studentId;
        formatedText += " & " + this.studentAge;
        formatedText += " & " + this.courseName;
        formatedText += " & " + this.easyQuestionsAnsweredCorrect;
        formatedText += " & " + this.mediumQuestionsAnsweredCorrect;
        formatedText += " & " + this.hardQuestionsAnsweredCorrect;
        formatedText += " & " + this.qttAllQuestionsAnswered;
        formatedText += " & " + this.qttAllCourseQuestions;
        formatedText += " & " + this.score;
        formatedText += " & " + this.lastQuestionLevel;
        formatedText += " & " + this.lastQuestionWasAnsweredCorrect;

        return formatedText + "\\";
    }
}