package com.br.eduriacore.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.br.eduriacore.dto.EnrollmentDto;
import com.br.eduriacore.dto.QuestionDto;
import com.br.eduriacore.dto.QuestionPresentedDto;
import com.br.eduriacore.dto.ResponseResultDto;
import com.br.eduriacore.form.AnswerQuestionForm;
import com.br.eduriacore.mapper.EvaluatorMapper;
import com.br.eduriacore.model.Qtable;

import org.springframework.stereotype.Service;

@Service
public class EvaluatorService {

    private Map<String, Double> rewardsRules = new HashMap<String, Double>();

    private QtableService qtableService;
    private EvaluatorMapper evaluatorMapper;
    private QuestionService questionService;
    private EnrollmentService enrollmentService;

    public EvaluatorService(QtableService qtableService, EvaluatorMapper evaluatorMapper,
            EnrollmentService enrollmentService, QuestionService questionService) {
        this.qtableService = qtableService;
        this.evaluatorMapper = evaluatorMapper;
        this.enrollmentService = enrollmentService;
        this.questionService = questionService;
        this.fillMap();
    }

    private void fillMap() {
        this.rewardsRules.put("CL1C1", 10.0);
        this.rewardsRules.put("CL1C2", 10.0);
        this.rewardsRules.put("CL1C3", 10.0);
        this.rewardsRules.put("CL2C1", 10.0);
        this.rewardsRules.put("CL2C2", 10.0);
        this.rewardsRules.put("CL2C3", 10.0);
        this.rewardsRules.put("CL3C1", 10.0);
        this.rewardsRules.put("CL3C2", 10.0);
        this.rewardsRules.put("CL3C3", 10.0);
        
        this.rewardsRules.put("WL1C1", -10.0);
        this.rewardsRules.put("WL1C2", -10.0);
        this.rewardsRules.put("WL1C3", -10.0);
        this.rewardsRules.put("WL2C1", -10.0);
        this.rewardsRules.put("WL2C2", -10.0);
        this.rewardsRules.put("WL2C3", -10.0);
        this.rewardsRules.put("WL3C1", -10.0);
        this.rewardsRules.put("WL3C2", -10.0);
        this.rewardsRules.put("WL3C3", -10.0);
    }

    private Qtable responseReward(Long idQtable, boolean isCorrectResponse) {
        int bestActionIndex = this.qtableService.getBestAction(idQtable);
        Qtable qtable = this.qtableService.getById(idQtable);
        double reward = isCorrectResponse ? correctAnswerReward(qtable, bestActionIndex) : wrongAnswerReward(qtable, bestActionIndex);
        return this.qtableService.applyReinforcement(idQtable, reward);
    }

    private Double correctAnswerReward(Qtable qtable, int bestActionIndex) {
        String keyHash = "C";
        return this.rewardsRules.get(keyHash);
    }

    private Double wrongAnswerReward(Qtable qtable, int bestActionIndex) {
        String keyHash = "W";
        return this.rewardsRules.get(keyHash);
    }

    public QuestionPresentedDto presentNewQuestion(Long enrollmentId) {
        EnrollmentDto enrollmentDto = this.enrollmentService.getById(enrollmentId);
        List<QuestionDto> selectedQuestions = this.questionService.getQuestionByLevelAndCourse(
            enrollmentDto.getLevel(), enrollmentDto.getCourseId());
        
        Random rand = new Random();
        QuestionDto selectedRandomQuestion = selectedQuestions.get(rand.nextInt(selectedQuestions.size())); 
        return this.evaluatorMapper.toQuestionPresentedDto(selectedRandomQuestion, enrollmentDto.getEnrollmentId());
    }

    /*
        Create update of note of student
    */
    public ResponseResultDto answerQuestion(AnswerQuestionForm answerForm) {
        QuestionDto question = this.questionService.getById(answerForm.getQuestionId());
        EnrollmentDto enrollmentDto = this.enrollmentService.getById(answerForm.getEnrollmentId());
        ResponseResultDto result = new ResponseResultDto();
        result.setEnrollmentId(enrollmentDto.getEnrollmentId());
        result.setQuestionId(question.getQuestionId());
        result.setCorrectAlternative(this.getCorrectAlternative(question));

        if ( question.getCorrectAlternative() == answerForm.getSelectedAlternative()) {
            this.responseReward(enrollmentDto.getQtableId(), true);
            result.setCorrectResponse(true);
        } else {
            this.responseReward(enrollmentDto.getQtableId(), false);
            result.setCorrectResponse(false);
        }
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


}