package com.br.eduriacore.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import com.br.eduriacore.model.Enrollment;
import com.br.eduriacore.model.IntelligenceRequest;
import com.br.eduriacore.model.IntelligenceResponse;
import com.br.eduriacore.model.enums.LevelQuestionEnum;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IntegrationService {

    private QuestionService questionService;

    public IntegrationService(QuestionService questionService) {
        this.questionService = questionService;
    }

    public LevelQuestionEnum getLevelQuestion(Enrollment enrollment) {
        IntelligenceRequest request = this.createObjectRequest(enrollment);
        
        System.out.println(request.toString());

        RestTemplate restTemplate = new RestTemplate();
        String uri = enrollment.getCourse().getIntegrationUri();

        try {            
            ResponseEntity<IntelligenceResponse> response = restTemplate.postForEntity(uri, request, IntelligenceResponse.class);
    
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Response: " + response.getBody().getSelectedLevel());
                return response.getBody().getSelectedLevel();  
            } else {
                return this.generateRandomLevel();
            }
        } catch (Exception e) {
            return this.generateRandomLevel();
        }
    }

    private LevelQuestionEnum generateRandomLevel() {
        int index = new Random().nextInt(3);
        return Arrays.asList(LevelQuestionEnum.values()).get(index);
    }

    public IntelligenceRequest createObjectRequest(Enrollment enrollment) {
        IntelligenceRequest request   = new IntelligenceRequest();
        Integer qttAllCourseQuestions = this.questionService.countByCourse(enrollment.getCourse().getCourseId());

        request.setRegisterUuid(UUID.randomUUID());
        request.setStudentId(enrollment.getStudent().getId());
        request.setStudentAge(this.calculateAge(enrollment.getStudent().getBirthDate()));
        request.setCourseName(enrollment.getCourse().getCourseName());
        request.setEasyQuestionsAnsweredCorrect(enrollment.getEasyQuestionsAnsweredCorrect());
        request.setMediumQuestionsAnsweredCorrect(enrollment.getMediumQuestionsAnsweredCorrect());
        request.setHardQuestionsAnsweredCorrect(enrollment.getHardQuestionsAnsweredCorrect());
        request.setQttAllQuestionsAnswered(this.countAnsweredQuestions(enrollment));
        request.setQttAllCourseQuestions(qttAllCourseQuestions);
        request.setScore(this.calculateScore(enrollment, qttAllCourseQuestions));
        request.setLastQuestionLevel(enrollment.getLastQuestionLevel());
        request.setLastQuestionWasAnsweredCorrect(enrollment.getLastQuestionWasAnsweredCorrect());

        return request;
    }
    
    public Integer calculateScore(Enrollment enrollment, Integer qttAllCourseQuestions) {

        Integer qqAllAnswedQuestions = enrollment.getEasyQuestionsAnsweredCorrect() +
        enrollment.getMediumQuestionsAnsweredCorrect() + enrollment.getHardQuestionsAnsweredCorrect();

        return (qqAllAnswedQuestions * 100) / qttAllCourseQuestions;
    }

    private Integer countAnsweredQuestions(Enrollment enrollment) {
        return enrollment.getEasyQuestionsAnsweredCorrect() + enrollment.getMediumQuestionsAnsweredCorrect()
             + enrollment.getHardQuestionsAnsweredCorrect();
    }

    private Integer calculateAge(LocalDate birthDate) {
        return LocalDate.now().getYear() - birthDate.getYear();
    }
}