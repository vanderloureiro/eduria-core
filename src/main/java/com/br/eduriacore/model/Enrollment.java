package com.br.eduriacore.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.br.eduriacore.model.enums.StateEnum;

import lombok.Data;

@Data
@Entity
@Table(name = "enrollment")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="enrollment_id")
    private Long enrollmentId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    
    @Enumerated(EnumType.STRING)
    private StateEnum level;

    private Double score;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "qtable_id")
    private Qtable qtable;

}