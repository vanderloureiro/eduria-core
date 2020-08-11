package com.br.eduriacore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.br.eduriacore.model.enums.StateEnum;

import lombok.Data;

@Data
@Entity
@Table(name = "q_table")
public class Qtable {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="q_table_id")
    private Long qTableId;

    @Column(nullable=false)
    private Double BEGINNER_EASY;
    @Column(nullable=false)
    private Double BEGINNER_MEDIUM;
    @Column(nullable=false)
    private Double BEGINNER_HARD;
    @Column(nullable=false)
    private Double INTERMEDIATE_EASY;
    @Column(nullable=false)
    private Double INTERMEDIATE_MEDIUM;
    @Column(nullable=false)
    private Double INTERMEDIATE_HARD;
    @Column(nullable=false)
    private Double ADVANCED_EASY;
    @Column(nullable=false)
    private Double ADVANCED_MEDIUM;
    @Column(nullable=false)
    private Double ADVANCED_HARD;

    @Column(name="qtd_random", nullable=false)
    private int qtdRandom;

    @Column(name="current_random", nullable=false)
    private int currentRandom;

    @Enumerated(EnumType.STRING)
    @Column(name="current_state", nullable=false)
    private StateEnum currentState;

    @OneToOne(mappedBy = "qtable")
    private Enrollment enrollment;

}