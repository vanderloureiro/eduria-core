package com.br.eduriacore.mapper;

import com.br.eduriacore.dto.QuestionDto;
import com.br.eduriacore.dto.QuestionPresentedDto;
import com.br.eduriacore.model.Question;

import org.springframework.stereotype.Component;

@Component
public class EvaluatorMapper {
    
    public QuestionPresentedDto toQuestionPresentedDto(QuestionDto question, Long enrollmentId) {
        QuestionPresentedDto dto = new QuestionPresentedDto();
        dto.setQuestionId(question.getQuestionId());
        dto.setCourseId(question.getCourseId());
        dto.setEnrollmentId(enrollmentId);
        dto.setText(question.getText());
        dto.setDescription(question.getDescription());
        dto.setAlternative1(question.getAlternative1());
        dto.setAlternative2(question.getAlternative2());
        dto.setAlternative3(question.getAlternative3());
        dto.setAlternative4(question.getAlternative4());
        dto.setContentOrder(question.getContentOrder());
        dto.setQuestionLevel(question.getQuestionLevel());
        return dto;
    }
}