package com.br.eduriacore.service;

import java.util.List;
import java.util.Random;

import com.br.eduriacore.dto.EnrollmentDto;
import com.br.eduriacore.dto.QuestionDto;
import com.br.eduriacore.dto.QuestionPresentedDto;
import com.br.eduriacore.dto.ResponseResultDto;
import com.br.eduriacore.form.AnswerQuestionForm;
import com.br.eduriacore.mapper.EvaluatorMapper;
import com.br.eduriacore.model.Enrollment;
import com.br.eduriacore.model.enums.StateEnum;

import org.springframework.stereotype.Service;

@Service
public class EvaluatorService {

    private QtableService qtableService;
    private EvaluatorMapper evaluatorMapper;
    private QuestionService questionService;
    private EnrollmentService enrollmentService;
    private RewardPolicyService rewardPolicyService;

    public EvaluatorService(QtableService qtableService, EvaluatorMapper evaluatorMapper,
            EnrollmentService enrollmentService, QuestionService questionService,
            RewardPolicyService rewardPolicyService) {
        this.qtableService       = qtableService;
        this.evaluatorMapper     = evaluatorMapper;
        this.enrollmentService   = enrollmentService;
        this.questionService     = questionService;
        this.rewardPolicyService = rewardPolicyService;
    }

    public QuestionPresentedDto presentNewQuestion(Long enrollmentId) {
        EnrollmentDto enrollmentDto = this.enrollmentService.getById(enrollmentId);

        int bestAction = this.qtableService.getBestAction(enrollmentDto.getQtableId());

        List<QuestionDto> selectedQuestions = this.questionService.getQuestionByLevelAndCourse(
            bestAction, enrollmentDto.getCourseId());
        
        Random rand = new Random();
        
        QuestionDto selectedRandomQuestion = selectedQuestions.get(rand.nextInt(selectedQuestions.size())); 
        return this.evaluatorMapper.toQuestionPresentedDto(selectedRandomQuestion, enrollmentDto.getEnrollmentId());
    }
    
    public ResponseResultDto answerQuestion(AnswerQuestionForm answerForm) {

        QuestionDto question     = this.questionService.getById(answerForm.getQuestionId());
        Enrollment enrollment    = this.enrollmentService.getEntityById(answerForm.getEnrollmentId());
        ResponseResultDto result = new ResponseResultDto();

        result.setEnrollmentId(enrollment.getEnrollmentId());
        result.setQuestionId(question.getQuestionId());
        result.setCorrectAlternative(this.getCorrectAlternative(question));

        if ( question.getCorrectAlternative() == answerForm.getSelectedAlternative()) {
            result = this.registerCurrectAnswer(result, enrollment);
        } else {
            result = this.registerWrongAnswer(result, enrollment);
        }

        StateEnum qlearningState = this.returnLevel(result.getScore());
        this.qtableService.changeCurrentState(enrollment.getQtable().getQTableId(), qlearningState);

        return result;
    }

    private ResponseResultDto registerCurrectAnswer(ResponseResultDto result, Enrollment enrollment) {
        double reward = this.rewardPolicyService.findReward(enrollment, true);
        this.qtableService.applyReinforcement(enrollment.getQtable().getQTableId(), reward);
        result.setScore(this.updateEnrollmentScore(enrollment, true));
        result.setCorrectResponse(true);
        return result;
    }

    private ResponseResultDto registerWrongAnswer(ResponseResultDto result, Enrollment enrollment) {
        double reward = this.rewardPolicyService.findReward(enrollment, false);
        this.qtableService.applyReinforcement(enrollment.getQtable().getQTableId(), reward);
        result.setScore(this.updateEnrollmentScore(enrollment, false));
        result.setCorrectResponse(false);
        return result;
    }

    private String getCorrectAlternative(QuestionDto question) {
        switch (question.getCorrectAlternative()) {
            case 1:
                return "1 - " + question.getAlternative1();
            case 2:
                return "2 - " + question.getAlternative2();
            case 3:
                return "3 - " + question.getAlternative3();
            default:
                return "4 - " + question.getAlternative4();
        }
    }

    private Double updateEnrollmentScore(Enrollment enrollment, boolean isRightAnswer) {
        if (isRightAnswer && enrollment.getScore() < 10) {
            Double newScore = enrollment.getScore() + 1;
            enrollment.setScore(newScore);
            enrollment.setLevel(this.returnLevel(newScore));
        } else if ( !isRightAnswer && enrollment.getScore() > 0) {
            Double newScore = enrollment.getScore() - 1;
            enrollment.setScore(newScore);
            enrollment.setLevel(this.returnLevel(newScore));
        }
        return this.enrollmentService.updateEnrollment(enrollment).getScore();
    }

    private StateEnum returnLevel(Double score) {
        if ( score < 4 ) 
            return StateEnum.BEGINNER;
        else if ( score >= 4 && score < 7 ) 
            return StateEnum.INTERMEDIATE;
        else 
            return StateEnum.ADVANCED;
    }


}