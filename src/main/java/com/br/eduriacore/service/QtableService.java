package com.br.eduriacore.service;

import java.util.Arrays;
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
            return qtable.getBestActionByCurrentState();
        } else {
            qtable.setCurrentExploration(qtable.getCurrentExploration() + 1);
            this.repository.save(qtable);

            int index = new Random().nextInt(3);
            return Arrays.asList(LevelQuestionEnum.values()).get(index);
        }
    }

    public Qtable applyReinforcement(Long qtableId, Double reward) {
        Qtable qtable  = this.getById(qtableId);
        qtable = qtable.applyReinforcement(reward);
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