package com.br.eduriacore.dto;

import lombok.Data;

@Data
public class EnrollmentDto {

    private Long enrollmentId;
    private CourseDto course;
    private int level;
    private int contentOrder;

}