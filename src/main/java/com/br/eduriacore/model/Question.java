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
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="question_id")
    private Long questionId;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private String text;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "alternative1", nullable = false)
    private String alternative1;

    @Column(name = "alternative2", nullable = false)
    private String alternative2;
    
    @Column(name = "alternative3", nullable = false)
    private String alternative3;

    @Column(name = "alternative4", nullable = false)
    private String alternative4;

    @Column(name = "correct_alternative", nullable = false)
    private int correctAlternative;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_level", nullable = false)
    private LevelQuestionEnum questionLevel;

    public String getCorrectAlternativeToString() {
        switch (this.correctAlternative) {
            case 1:
                return "1 - " + this.alternative1;
            case 2:
                return "2 - " + this.alternative2;
            case 3:
                return "3 - " + this.alternative3;
            default:
                return "4 - " + this.alternative4;
        }
    }
 
}