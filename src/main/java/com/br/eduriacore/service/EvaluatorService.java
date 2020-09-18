package com.br.eduriacore.service;

import com.br.eduriacore.dto.QuestionPresentedDto;
import com.br.eduriacore.dto.ResponseResultDto;
import com.br.eduriacore.form.AnswerQuestionForm;
import com.br.eduriacore.mapper.EvaluatorMapper;
import com.br.eduriacore.model.Enrollment;

import org.springframework.stereotype.Service;

@Service
public class EvaluatorService {

    private EvaluatorMapper evaluatorMapper;
    private QuestionService questionService;
    private EnrollmentService enrollmentService;

    public EvaluatorService(EvaluatorMapper evaluatorMapper, EnrollmentService enrollmentService,
        QuestionService questionService) {
        this.evaluatorMapper   = evaluatorMapper;
        this.enrollmentService = enrollmentService;
        this.questionService   = questionService;
    }

    public QuestionPresentedDto presentNewQuestion(Long enrollmentId) {
        return null;
    }
    
    public ResponseResultDto answerQuestion(AnswerQuestionForm answerForm) {
        this.calculateScore(null, null);
        return null;
    }

    private Integer calculateScore(Enrollment enrollment, Integer qttAllCourseQuestions) {

        Integer qqAllAnswedQuestions = enrollment.getEasyQuestionsAnsweredCorrect() +
        enrollment.getMediumQuestionsAnsweredCorrect() + enrollment.getHardQuestionsAnsweredCorrect();

        return (qqAllAnswedQuestions * 100) / qttAllCourseQuestions;
    }


}