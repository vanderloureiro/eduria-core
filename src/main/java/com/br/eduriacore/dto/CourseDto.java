package com.br.eduriacore.dto;

import lombok.Data;

@Data
public class CourseDto {

    private Long courseId;
    private Long teacherId;
    private String courseName;
    private String integrationUri;
    private Integer integrationPort;
    private String integrationName;

}