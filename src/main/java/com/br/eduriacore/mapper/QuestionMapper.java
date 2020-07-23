package com.br.eduriacore.mapper;

import com.br.eduriacore.dto.QuestionDto;
import com.br.eduriacore.model.Course;
import com.br.eduriacore.model.Question;

import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {
    
    public QuestionDto toDto(Question entity) {
        QuestionDto dto = new QuestionDto();
        dto.setQuestionId(entity.getQuestionId());
        dto.setCourseId(entity.getCourse().getCourseId());
        dto.setText(entity.getText());
        dto.setDescription(entity.getDescription());
        dto.setAlternative1(entity.getAlternative1());
        dto.setAlternative2(entity.getAlternative2());
        dto.setAlternative3(entity.getAlternative3());
        dto.setAlternative4(entity.getAlternative4());
        dto.setCorrectAlternative(entity.getCorrectAlternative());
        dto.setQuestionLevel(entity.getQuestionLevel());
        return dto;
    }

    public Question toEntityWithoutId(QuestionDto dto) {
        Question entity = new Question();
        Course courseEntity = new Course();
        courseEntity.setCourseId(dto.getCourseId());

        entity.setCourse(courseEntity);
        entity.setText(dto.getText());
        entity.setDescription(dto.getDescription());
        entity.setAlternative1(dto.getAlternative1());
        entity.setAlternative2(dto.getAlternative2());
        entity.setAlternative3(dto.getAlternative3());
        entity.setAlternative4(dto.getAlternative4());
        entity.setCorrectAlternative(dto.getCorrectAlternative());
        entity.setQuestionLevel(dto.getQuestionLevel());
        return entity;
    }

}