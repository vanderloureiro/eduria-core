package com.br.eduriacore.dto;

import lombok.Data;

@Data
public class EnrollmentDto {

    private Long enrollmentId;
    private Long studentId;
    private Long courseId;
    private Double score;

}