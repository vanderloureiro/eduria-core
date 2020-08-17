package com.br.eduriacore.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.br.eduriacore.exception.NotFoundException;
import com.br.eduriacore.model.Qtable;
import com.br.eduriacore.model.enums.StateEnum;
import com.br.eduriacore.repository.QtableRepository;

import org.springframework.stereotype.Service;

@Service
public class QtableService {

    private final double ALPHA = 0.5;
    private final double GAMMA = 0.5;

    private QtableRepository repository;

    public QtableService(QtableRepository repository) {
        this.repository = repository;
    }

    public Qtable createDefaultQtable(int qtdExploration, int initialState) {
        Qtable table = new Qtable(qtdExploration);
        return this.repository.save(table);
    }

    public Integer getBestAction(Long id) {
        Qtable qtable = this.getById(id);
        if (qtable.getCurrentExploration() > qtable.getQtdExploration()) {
            return this.getBestActionIndex(qtable);
        } else {
            qtable.setCurrentExploration(qtable.getCurrentExploration() + 1);
            this.repository.save(qtable);
            Random generator = new Random();
            return generator.nextInt(3);
        }
    }

    private Integer getBestActionIndex(Qtable qtable) {
        if (qtable.getIndexCurrentState() == 1)
            return this.returnBestActionIndex(qtable.getL1C1(), qtable.getL1C2(), qtable.getL1C3());
        else if (qtable.getIndexCurrentState() == 2) 
            return this.returnBestActionIndex(qtable.getL2C1(), qtable.getL2C2(), qtable.getL2C3());
        else 
            return this.returnBestActionIndex(qtable.getL3C1(), qtable.getL3C2(), qtable.getL3C3());
    }

    private Double getBestActionNextState(Qtable qtable) {
        if (qtable.getIndexCurrentState() == 1) 
            return this.returnBestActionValue(qtable.getL2C1(), qtable.getL2C2(), qtable.getL2C3());
        else if (qtable.getIndexCurrentState() == 2) 
            return this.returnBestActionValue(qtable.getL3C1(), qtable.getL3C2(), qtable.getL3C3());
        else 
            return this.returnBestActionValue(qtable.getL1C1(), qtable.getL1C2(), qtable.getL1C3());
        
    }

    private Double returnBestActionValue(Double col1Value, Double col2Value, Double col3Value) {
        List<Double> array = Arrays.asList(col1Value, col2Value, col3Value);
        return array.stream().max(Comparator.naturalOrder()).get();
    }

    private int returnBestActionIndex(Double col1Value, Double col2Value, Double col3Value) {
        int bestAction = 1;
        if (col2Value > col1Value) bestAction = 2;
        if (col3Value > col2Value) bestAction = 3;
        return bestAction;
    }

    public Qtable applyReinforcement(Long qtableId, Double reward) {
        Qtable qtable  = this.getById(qtableId);
        int bestActionIndex = this.getBestActionIndex(qtable);
        Double bestActionNextState = this.getBestActionNextState(qtable);
        Double bestActionActual    = this.getMatrixValue(qtable, qtable.getIndexCurrentState(), bestActionIndex);

        // Q(s, t) = Q(s, t) + alpha * (r + gamma * max(s+1, a) - Q(s, t))
        Double newValue = bestActionActual + this.ALPHA * 
            (reward + this.GAMMA * bestActionNextState - bestActionActual);
        
        return this.updateNewValue(qtable, newValue);
    }


    private Double getMatrixValue(Qtable qtable, int state, int bestActionIndex) {
        if (state == 0) {
            return Arrays.asList(qtable.getL1C1(), qtable.getL1C2(), qtable.getL1C3()).get(bestActionIndex);
        } else if (state == 1) {
            return Arrays.asList(qtable.getL2C1(), qtable.getL2C2(), qtable.getL2C3()).get(bestActionIndex);
        } else {
            return Arrays.asList(qtable.getL3C1(), qtable.getL3C2(), qtable.getL3C3()).get(bestActionIndex);
        }
    }

    // refatorar
    private Qtable updateNewValue(Qtable qtable, Double newValue) {
        int bestActionIndex = this.getBestAction(qtable.getQTableId());
        if (qtable.getIndexCurrentState() == 0){
            if (bestActionIndex == 0) qtable.setL1C1(newValue);
            else if (bestActionIndex == 1) qtable.setL1C2(newValue);
            else qtable.setL1C3(newValue);
        } else if (qtable.getIndexCurrentState() == 1){
            if (bestActionIndex == 0) qtable.setL2C1(newValue);
            else if (bestActionIndex == 1) qtable.setL2C2(newValue);
            else qtable.setL2C3(newValue);
        } else {
            if (bestActionIndex == 0) qtable.setL3C1(newValue);
            else if (bestActionIndex == 1) qtable.setL3C2(newValue);
            else qtable.setL3C3(newValue);
        }
        return this.repository.save(qtable);
    }

    public Qtable changeCurrentState(Long qtableId, StateEnum newState) {
        Qtable qtable = this.getById(qtableId);
        qtable.setCurrentState(newState);
        return this.repository.save(qtable);
    }

    public Qtable getById(Long id) {
        Optional<Qtable> table = this.repository.findById(id);
        return table.orElseThrow(() -> new NotFoundException("Qtable not found"));
    }

}