package com.br.eduriacore.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.br.eduriacore.dto.QuestionDto;
import com.br.eduriacore.exception.NotFoundException;
import com.br.eduriacore.mapper.QuestionMapper;
import com.br.eduriacore.model.Course;
import com.br.eduriacore.model.Question;
import com.br.eduriacore.repository.QuestionRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    
    private QuestionMapper mapper;
    private QuestionRepository repository;
    private CourseService courseService;

    public QuestionService(QuestionMapper mapper, QuestionRepository repository, CourseService courseService) {
        this.mapper        = mapper;
        this.repository    = repository;
        this.courseService = courseService;
    }

    public QuestionDto create(QuestionDto questionDto) {
        Course course = this.courseService.getEntityById(questionDto.getCourseId());
        Question questionToSave = this.mapper.toEntityWithoutId(questionDto);
        questionToSave.setCourse(course);
        return this.mapper.toDto(this.repository.save(questionToSave));
    }

    public QuestionDto getById(Long id) {
        return this.mapper.toDto(this.getEntityById(id));
    }

    public Page<QuestionDto> get(Pageable pagination) {
        return this.repository.findAll(pagination).map(question -> {
            return this.mapper.toDto(question);
        });
    }

    public QuestionDto update(QuestionDto questionDto, Long id) {
        Course course = this.courseService.getEntityById(questionDto.getCourseId());
        Optional<Question> question = this.repository.findById(id);
        if (question.isPresent()) {
            Question questionToUpdate = this.mapper.toEntityWithoutId(questionDto);
            questionToUpdate.setQuestionId(id);
            questionToUpdate.setCourse(course);
            return this.mapper.toDto(this.repository.save(questionToUpdate));
        }
        throw new NotFoundException("Question not found");
    }

    public List<QuestionDto> getQuestionByLevelAndContentOrder(int level, int contentOrder) {
        return this.repository.findAllByLevelAndContentOrder(level, contentOrder).stream().map(
            questionEntity -> {
                return this.mapper.toDto(questionEntity);
            }
        ).collect(Collectors.toList());
    }

    public void delete(Long id) {
        this.repository.deleteById(id);
    }

    public Question getEntityById(Long id) {
        Optional<Question> question = this.repository.findById(id);
        if (question.isPresent()) {
            return question.get();
        }
        throw new NotFoundException("Question not found");
    }
}