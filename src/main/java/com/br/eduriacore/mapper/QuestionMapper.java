package com.br.eduriacore.mapper;

import com.br.eduriacore.dto.QuestionDto;
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
        dto.setContentOrder(entity.getContentOrder());
        dto.setQuestionLevel(entity.getQuestionLevel());
        return dto;
    }

}