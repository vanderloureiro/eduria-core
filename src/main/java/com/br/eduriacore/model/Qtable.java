package com.br.eduriacore.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.br.eduriacore.model.enums.LevelQuestionEnum;
import com.br.eduriacore.model.enums.StateEnum;

import lombok.Data;

@Data
@Entity
@Table(name = "q_table")
public class Qtable {
 
    public Qtable(int qtdExploration) {
        this.BEGINNER_EASY       = 0.0;
        this.BEGINNER_MEDIUM     = 0.0;
        this.BEGINNER_HARD       = 0.0;
        this.INTERMEDIATE_EASY   = 0.0;
        this.INTERMEDIATE_MEDIUM = 0.0;
        this.INTERMEDIATE_HARD   = 0.0;
        this.ADVANCED_EASY       = 0.0;
        this.ADVANCED_MEDIUM     = 0.0;
        this.ADVANCED_HARD       = 0.0;
        this.qtdExploration      = qtdExploration;
        this.currentState        = StateEnum.BEGINNER;
    }

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

    @Column(name="qtd_exploration", nullable=false)
    private int qtdExploration;

    @Column(name="current_exploration", nullable=false)
    private int currentExploration;

    @Enumerated(EnumType.STRING)
    @Column(name="current_state", nullable=false)
    private StateEnum currentState;

    @OneToOne(mappedBy = "qtable")
    private Enrollment enrollment;

    private final double ALPHA = 0.5;
    private final double GAMMA = 0.5;

    public LevelQuestionEnum getBestAction() {
        if (this.currentState == StateEnum.BEGINNER)
            return null;
        if (this.currentState == StateEnum.INTERMEDIATE)
            return null;
        return null;
    }

    private double getMoreValue(Double col1Value, Double col2Value, Double col3Value) {
        List<Double> array = Arrays.asList(col1Value, col2Value, col3Value);
        return array.stream().max(Comparator.naturalOrder()).get();
    }

    public Qtable applyReinforcement(Double reward) {
        int bestActionIndex = this.getBestActionIndex(qtable);
        Double bestActionNextState = this.getBestActionNextState(qtable);
        Double bestActionActual    = this.getMatrixValue(qtable, qtable.getIndexCurrentState(), bestActionIndex);

        // Q(s, t) = Q(s, t) + alpha * (r + gamma * max(s+1, a) - Q(s, t))
        Double newValue = bestActionActual + this.ALPHA * 
            (reward + this.GAMMA * bestActionNextState - bestActionActual);
        
        return this.updateNewValue(qtable, newValue);
    }


}