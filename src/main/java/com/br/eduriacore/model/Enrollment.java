package com.br.eduriacore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.br.eduriacore.model.enums.LevelQuestionEnum;

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
    
    private Integer score;

    @Column(name="easy_questions_answered_correct")
    private Integer easyQuestionsAnsweredCorrect;
    
    @Column(name="medium_questions_answered_correct")
    private Integer mediumQuestionsAnsweredCorrect;

    @Column(name="hard_questions_answered_correct")
    private Integer hardQuestionsAnsweredCorrect;

    @Column(name="qtt_all_questions_answered")
    private Integer qttAllQuestionsAnswered;
    
    @Enumerated(EnumType.STRING)
    @Column(name="last_question_level")
    private LevelQuestionEnum lastQuestionLevel;

    @Column(name="last_question_was_answered_correct")
    private Boolean lastQuestionWasAnsweredCorrect;
}