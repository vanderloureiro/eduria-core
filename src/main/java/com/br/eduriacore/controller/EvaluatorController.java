package com.br.eduriacore.controller;

import com.br.eduriacore.dto.QuestionPresentedDto;
import com.br.eduriacore.dto.ResponseResultDto;
import com.br.eduriacore.form.AnswerQuestionForm;
import com.br.eduriacore.service.EvaluatorService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@Api(value = "Main API for questions and answers")
@RestController
@RequestMapping("/evaluator")
public class EvaluatorController {

    private EvaluatorService evaluatorService;

    public EvaluatorController(EvaluatorService evaluatorService) {
        this.evaluatorService = evaluatorService;
    }
    
    @GetMapping("/{enrollmentId}")
    @ApiOperation(value = "Get a question by EnrollmentId", response = QuestionPresentedDto.class)
    public ResponseEntity<QuestionPresentedDto> presenteNewQuestion(@PathVariable Long enrollmentId) {
        try {
            return ResponseEntity.ok().body(this.evaluatorService.presentNewQuestion(enrollmentId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ApiOperation(value = "Answer a question", response = QuestionPresentedDto.class)
    public ResponseEntity<ResponseResultDto> answerQuestion(@RequestBody AnswerQuestionForm answerForm) {
        return ResponseEntity.ok().body(this.evaluatorService.answerQuestion(answerForm));
    }
}