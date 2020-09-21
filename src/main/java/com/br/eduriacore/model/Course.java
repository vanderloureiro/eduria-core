package com.br.eduriacore.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="course_id")
    private Long courseId;

    @Column(name="course_id", nullable = false)
    private String integrationUri;

    @Column(name="integration_name", nullable = false)
    private String integrationName;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<Enrollment> enrollments;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<Question> questions;
}