package com.br.eduriacore.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.br.eduriacore.exception.NotFoundException;
import com.br.eduriacore.model.Qtable;
import com.br.eduriacore.model.enums.LevelQuestionEnum;
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

    public LevelQuestionEnum getBestAction(Long id) {
        Qtable qtable = this.getById(id);
        if (qtable.getCurrentExploration() > qtable.getQtdExploration()) {
            return qtable.getBestAction();
        } else {
            qtable.setCurrentExploration(qtable.getCurrentExploration() + 1);
            this.repository.save(qtable);

            int index = new Random().nextInt(3);
            return Arrays.asList(LevelQuestionEnum.values()).get(index);
        }
    }


    private Double returnBestActionValue(Double col1Value, Double col2Value, Double col3Value) {
        List<Double> array = Arrays.asList(col1Value, col2Value, col3Value);
        return array.stream().max(Comparator.naturalOrder()).get();
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