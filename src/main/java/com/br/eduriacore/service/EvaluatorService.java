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
        Integer qttAllCourseQuestions = this.questionService.countByCourse(enrollment.getCourse().getCourseId());

        ResponseResultDto result = new ResponseResultDto();

        result.setEnrollmentId(enrollment.getEnrollmentId());
        result.setQuestionId(question.getQuestionId());
        result.setCorrectAlternative(question.getCorrectAlternativeToString());

        if (question.getCorrectAlternative() == answerForm.getSelectedAlternative()) {
            enrollment = this.registerCurrectAnswer(enrollment, question);
            result.setCorrectResponse(true);
        } else {
            enrollment = this.registerWrongAnswer(enrollment, question);
            result.setCorrectResponse(false);
        }

        result.setScore(this.integrationService.calculateScore(enrollment, qttAllCourseQuestions));

        return result;
    }

    private Enrollment registerCurrectAnswer(Enrollment enrollment, Question question) {
        enrollment.setLastQuestionWasAnsweredCorrect(true);
        enrollment.setLastQuestionLevel(question.getQuestionLevel());

        if (question.getQuestionLevel() == LevelQuestionEnum.EASY){
            enrollment.setEasyQuestionsAnsweredCorrect(
                enrollment.getEasyQuestionsAnsweredCorrect() + 1
            );
        }
        if (question.getQuestionLevel() == LevelQuestionEnum.MEDIUM) {
            enrollment.setMediumQuestionsAnsweredCorrect(
                enrollment.getMediumQuestionsAnsweredCorrect() + 1
            );
        }
        if (question.getQuestionLevel() == LevelQuestionEnum.HARD) {
            enrollment.setHardQuestionsAnsweredCorrect(
                enrollment.getHardQuestionsAnsweredCorrect() + 1
            );
        }
        enrollment.setQttAllQuestionsAnswered(
            enrollment.getQttAllQuestionsAnswered() + 1
        );

        this.enrollmentService.updateEnrollment(enrollment);
        return enrollment;
    }

    private Enrollment registerWrongAnswer(Enrollment enrollment, Question question) {
        enrollment.setLastQuestionWasAnsweredCorrect(false);
        enrollment.setLastQuestionLevel(question.getQuestionLevel());

        enrollment.setQttAllQuestionsAnswered(
            enrollment.getQttAllQuestionsAnswered() + 1
        );
        
        this.enrollmentService.updateEnrollment(enrollment);
        return enrollment;
    }

}