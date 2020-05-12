package com.br.eduriacore.controller;

import java.net.URI;
import java.util.List;

import com.br.eduriacore.dto.QuestionDto;
import com.br.eduriacore.service.QuestionService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@Api(value = "Question API")
@RestController
@RequestMapping("/question")
public class QuestionController {

    private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }
    
    @PostMapping
    @ApiOperation(value = "Creates a new question", response = QuestionDto.class)
    public ResponseEntity<QuestionDto> create(
        @RequestBody QuestionDto QuestionDto, UriComponentsBuilder uriBuilder) {
        try {
            QuestionDto question = this.questionService.create(QuestionDto);
            URI uri = uriBuilder.path("/question/{id}").buildAndExpand(question.getQuestionId()).toUri();
            return ResponseEntity.created(uri).body(question);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    @ApiOperation(value = "Get a paginated list of questions", response = QuestionDto.class, responseContainer = "List")
    public ResponseEntity<List<QuestionDto>> get(
        @PageableDefault(sort = "questionId", direction = Direction.ASC, page = 0, size = 10) Pageable pagination) {
        
        Page<QuestionDto> questions = this.questionService.get(pagination);
        HttpHeaders headers       = new HttpHeaders();
        headers.add("length", Long.toString(questions.getTotalElements()));
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "length");
        
        return ResponseEntity.ok().headers(headers).body(questions.getContent());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a question by their id", response = QuestionDto.class)
    public ResponseEntity<QuestionDto> getById(@PathVariable Long id) {
        try {
            QuestionDto dto = this.questionService.getById(id); 
            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Updates a questions", response = QuestionDto.class)
    public ResponseEntity<QuestionDto> update(@RequestBody QuestionDto QuestionDto, @PathVariable Long id) {
        try {
            QuestionDto QuestionDtoToReturn = this.questionService.update(QuestionDto, id);
            return ResponseEntity.ok().body(QuestionDtoToReturn);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes a question by their id")
    public ResponseEntity<?> detele(@PathVariable Long id) {
        try {
            this.questionService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}