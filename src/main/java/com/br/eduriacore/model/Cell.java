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
import com.br.eduriacore.model.enums.StateEnum;

import lombok.Data;

@Data
@Entity
@Table(name = "cell")
public class Cell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cell_id")
    private Long cellId;

    @ManyToOne
    @JoinColumn(name = "q_table_id")
    private Qtable qtable;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StateEnum state;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LevelQuestionEnum level;

    @Column(nullable = false)
    private Double value;
}