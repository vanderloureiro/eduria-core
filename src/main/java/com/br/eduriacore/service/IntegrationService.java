package com.br.eduriacore.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;

import com.br.eduriacore.model.Enrollment;
import com.br.eduriacore.model.enums.LevelQuestionEnum;

import org.springframework.stereotype.Service;

@Service
public class IntegrationService {

    public IntegrationService() { }

    public LevelQuestionEnum getLevelQuestion(Enrollment enrollment) {
        
        int index = new Random().nextInt(3);
        return Arrays.asList(LevelQuestionEnum.values()).get(index);
    }
    
    private Integer calculateScore(Enrollment enrollment, Integer qttAllCourseQuestions) {

        Integer qqAllAnswedQuestions = enrollment.getEasyQuestionsAnsweredCorrect() +
        enrollment.getMediumQuestionsAnsweredCorrect() + enrollment.getHardQuestionsAnsweredCorrect();

        return (qqAllAnswedQuestions * 100) / qttAllCourseQuestions;
    }

    private Integer calculateAge(LocalDate birthDate) {
        return LocalDate.now().getYear() - birthDate.getYear();
    }
}