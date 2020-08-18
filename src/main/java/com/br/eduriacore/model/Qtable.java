package com.br.eduriacore.model;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
        this.cells.add(new Cell(StateEnum.BEGINNER, LevelQuestionEnum.EASY, 0.0));
        this.cells.add(new Cell(StateEnum.BEGINNER, LevelQuestionEnum.MEDIUM, 0.0));
        this.cells.add(new Cell(StateEnum.BEGINNER, LevelQuestionEnum.HARD, 0.0));
        this.cells.add(new Cell(StateEnum.INTERMEDIATE, LevelQuestionEnum.EASY, 0.0));
        this.cells.add(new Cell(StateEnum.INTERMEDIATE, LevelQuestionEnum.MEDIUM, 0.0));
        this.cells.add(new Cell(StateEnum.INTERMEDIATE, LevelQuestionEnum.HARD, 0.0));
        this.cells.add(new Cell(StateEnum.ADVANCED, LevelQuestionEnum.EASY, 0.0));
        this.cells.add(new Cell(StateEnum.ADVANCED, LevelQuestionEnum.MEDIUM, 0.0));
        this.cells.add(new Cell(StateEnum.ADVANCED, LevelQuestionEnum.HARD, 0.0));
        this.qtdExploration = qtdExploration;
        this.currentState   = StateEnum.BEGINNER;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="q_table_id")
    private Long qTableId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "qtable")
    private List<Cell> cells;

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
        List<Cell> list = this.cells
                            .stream()
                            .filter(cell -> cell.getState() == this.currentState)
                            .collect(Collectors.toList());

        return list.stream()
                .max(Comparator.comparing(Cell::getValue))
                .get().getLevel();
    }

    public Double getBestActionValueByState(StateEnum state) {
        List<Cell> list = this.cells
                            .stream()
                            .filter(cell -> cell.getState() == state)
                            .collect(Collectors.toList());

        return list.stream()
                    .max(Comparator.comparing(Cell::getValue))
                    .get().getValue();
    }
    
    public Qtable applyReinforcement(Double reward) {

        Double bestActionActual    = this.getBestActionValueByState(this.currentState);
        Double bestActionNextState = this.getBestActionValueByState(
            StateEnum.getNextState(this.currentState));

        // Q(s, t) = Q(s, t) + alpha * (r + gamma * max(s+1, a) - Q(s, t))
        Double newValue = bestActionActual + this.ALPHA * 
            (reward + this.GAMMA * bestActionNextState - bestActionActual);

        this.setBestAction(newValue);
        return this;
    }

    private void setBestAction(Double value) {
        LevelQuestionEnum level = this.getBestActionByCurrentState();
        Cell cellFound = this.cells
            .stream().filter(cell -> cell.getLevel() == level && cell.getState() == this.currentState)
            .findFirst().get();

        cellFound.setValue(value);
    }


}