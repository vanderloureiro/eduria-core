package com.br.eduriacore.service;

import java.util.List;
import java.util.Random;

import com.br.eduriacore.dto.QuestionDto;
import com.br.eduriacore.dto.QuestionPresentedDto;
import com.br.eduriacore.dto.ResponseResultDto;
import com.br.eduriacore.form.AnswerQuestionForm;
import com.br.eduriacore.mapper.EvaluatorMapper;
import com.br.eduriacore.model.Enrollment;
import com.br.eduriacore.model.Question;
import com.br.eduriacore.model.enums.LevelQuestionEnum;

import org.springframework.stereotype.Service;

@Service
public class EvaluatorService {

    private EvaluatorMapper evaluatorMapper;
    private QuestionService questionService;
    private EnrollmentService enrollmentService;
    private IntegrationService integrationService;

    public EvaluatorService(EvaluatorMapper evaluatorMapper, EnrollmentService enrollmentService,
            QuestionService questionService, IntegrationService integrationService) {
        this.evaluatorMapper    = evaluatorMapper;
        this.enrollmentService  = enrollmentService;
        this.questionService    = questionService;
        this.integrationService = integrationService;
    }

    public QuestionPresentedDto presentNewQuestion(Long enrollmentId) {
        Enrollment enrollment   = this.enrollmentService.getEntityById(enrollmentId);
        LevelQuestionEnum level = this.integrationService.getLevelQuestion(enrollment);

        List<QuestionDto> questions = this.questionService.getQuestionByLevelAndCourse(level, enrollment.getCourse().getCourseId());

        Random rand = new Random();
        QuestionDto selectedRandomQuestion = questions.get(rand.nextInt(questions.size())); 

        return this.evaluatorMapper.toQuestionPresentedDto(selectedRandomQuestion, enrollmentId);
    }
    
    public ResponseResultDto answerQuestion(AnswerQuestionForm answerForm) {

        Question question     = this.questionService.getEntityById(answerForm.getQuestionId());
        Enrollment enrollment = this.enrollmentService.getEntityById(answerForm.getEnrollmentId());
        ResponseResultDto result = new ResponseResultDto();

        result.setEnrollmentId(enrollment.getEnrollmentId());
        result.setQuestionId(question.getQuestionId());
        result.setCorrectAlternative(question.getCorrectAlternativeToString());


        this.enrollmentService.updateEnrollment(enrollment);

        return null;
    }


}