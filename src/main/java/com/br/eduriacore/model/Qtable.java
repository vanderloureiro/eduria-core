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
import javax.persistence.Transient;

import com.br.eduriacore.model.enums.LevelQuestionEnum;
import com.br.eduriacore.model.enums.StateEnum;

import lombok.Data;

@Data
@Entity
@Table(name = "q_table")
public class Qtable {

    @Transient
    private final double ALPHA = 0.5;
    @Transient
    private final double GAMMA = 0.5;
 
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

    public LevelQuestionEnum getBestActionByCurrentState() {
        if (this.currentState == StateEnum.BEGINNER) {
            return this.getBestAction(this.BEGINNER_EASY, this.BEGINNER_MEDIUM, this.BEGINNER_HARD);
        } else if (this.currentState == StateEnum.INTERMEDIATE) {
            return this.getBestAction(this.INTERMEDIATE_EASY, this.INTERMEDIATE_MEDIUM, this.INTERMEDIATE_HARD);
        } else {
            return this.getBestAction(this.ADVANCED_EASY, this.ADVANCED_MEDIUM, this.ADVANCED_HARD);
        }
    }

    private LevelQuestionEnum getBestAction(Double action1, Double action2, Double action3) {
        if ( action1 >= action2 && action1 >= action3) return LevelQuestionEnum.EASY;
        if ( action2 >= action1 && action2 >= action3) return LevelQuestionEnum.MEDIUM;
        return LevelQuestionEnum.HARD;
    }

    public Double getBestActionValueByState(StateEnum state) {
        if (state == StateEnum.BEGINNER) {
            return this.getBestActionValue(this.BEGINNER_EASY, this.BEGINNER_MEDIUM, this.BEGINNER_HARD);
        } else if (state == StateEnum.INTERMEDIATE) {
            return this.getBestActionValue(this.INTERMEDIATE_EASY, this.INTERMEDIATE_MEDIUM, this.INTERMEDIATE_HARD);
        } else {
            return this.getBestActionValue(this.ADVANCED_EASY, this.ADVANCED_MEDIUM, this.ADVANCED_HARD);
        }
    }
    
    private Double getBestActionValue(Double col1Value, Double col2Value, Double col3Value) {
        List<Double> array = Arrays.asList(col1Value, col2Value, col3Value);
        return array.stream().max(Comparator.naturalOrder()).get();
    }

    public Qtable applyReinforcement(Double reward) {

        Double bestActionActual    = this.getBestActionValueByState(this.currentState);
        Double bestActionNextState = this.getBestActionValueByState(this.getNextState());

        // Q(s, t) = Q(s, t) + alpha * (r + gamma * max(s+1, a) - Q(s, t))
        Double newValue = bestActionActual + this.ALPHA * 
            (reward + this.GAMMA * bestActionNextState - bestActionActual);

        this.setBestAction(newValue);
        return this;
    }

    private StateEnum getNextState() {
        if (this.currentState == StateEnum.BEGINNER) return StateEnum.INTERMEDIATE;
        if (this.currentState == StateEnum.INTERMEDIATE) return StateEnum.ADVANCED; 
        return StateEnum.BEGINNER;
    }

    private void setBestAction(Double value) {
        LevelQuestionEnum level = this.getBestActionByCurrentState();
        if (this.currentState == StateEnum.BEGINNER) {
            this.setBeginner(level, value);
        } else if (this.currentState == StateEnum.INTERMEDIATE){
            this.setIntermiate(level, value);
        } else {
            this.setAdvanced(level, value);
        }
    }

    private void setBeginner(LevelQuestionEnum level, Double value) {
        if (level == LevelQuestionEnum.EASY) this.BEGINNER_EASY = value;
        if (level == LevelQuestionEnum.MEDIUM) this.BEGINNER_MEDIUM = value;
        if (level == LevelQuestionEnum.HARD) this.BEGINNER_HARD = value;
    }

    private void setIntermiate(LevelQuestionEnum level, Double value) {
        if (level == LevelQuestionEnum.EASY) this.INTERMEDIATE_EASY = value;
        if (level == LevelQuestionEnum.MEDIUM) this.INTERMEDIATE_MEDIUM = value;
        if (level == LevelQuestionEnum.HARD) this.INTERMEDIATE_HARD = value;
    }

    private void setAdvanced(LevelQuestionEnum level, Double value) {
        if (level == LevelQuestionEnum.EASY) this.ADVANCED_EASY = value;
        if (level == LevelQuestionEnum.MEDIUM) this.ADVANCED_MEDIUM = value;
        if (level == LevelQuestionEnum.HARD) this.ADVANCED_HARD = value;
    }


}