package com.br.eduriacore.mapper;

import com.br.eduriacore.dto.EnrollmentDto;
import com.br.eduriacore.model.Enrollment;

import org.springframework.stereotype.Component;

@Component
public class EnrollmentMapper {
    
    public EnrollmentDto toDto(Enrollment entity) {
        EnrollmentDto dto = new EnrollmentDto();
        dto.setEnrollmentId(entity.getEnrollmentId());
        dto.setCourseId(entity.getCourse().getCourseId());
        dto.setStudentId(entity.getStudent().getId());
        dto.setScore(entity.getScore());
        dto.setEasyQuestionsAnsweredCorrect(entity.getEasyQuestionsAnsweredCorrect());
        dto.setMediumQuestionsAnsweredCorrect(entity.getMediumQuestionsAnsweredCorrect());
        dto.setHardQuestionsAnsweredCorrect(entity.getHardQuestionsAnsweredCorrect());
        dto.setQttAllQuestionsAnswered(entity.getQttAllQuestionsAnswered());
        dto.setLastQuestionLevel(entity.getLastQuestionLevel());
        dto.setLastQuestionWasAnsweredCorrect(entity.getLastQuestionWasAnsweredCorrect());
        return dto;
    }

}