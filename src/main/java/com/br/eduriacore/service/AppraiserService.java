package com.br.eduriacore.service;

import java.util.HashMap;
import java.util.Map;

import com.br.eduriacore.model.Qtable;

import org.springframework.stereotype.Service;

@Service
public class AppraiserService {

    private Map<String, Double> rewardsRules = new HashMap<String, Double>();
    private QtableService qtableService;

    public AppraiserService(QtableService qtableService) {
        this.qtableService = qtableService;
        this.fillMap();
    }

    private void fillMap() {
        this.rewardsRules.put("CL1C1", 10.0);
        this.rewardsRules.put("CL1C2", 10.0);
        this.rewardsRules.put("CL1C3", 10.0);
        this.rewardsRules.put("CL2C1", 10.0);
        this.rewardsRules.put("CL2C2", 10.0);
        this.rewardsRules.put("CL2C3", 10.0);
        this.rewardsRules.put("CL3C1", 10.0);
        this.rewardsRules.put("CL3C2", 10.0);
        this.rewardsRules.put("CL3C3", 10.0);
        this.rewardsRules.put("WL1C1", 10.0);
        this.rewardsRules.put("WL1C2", 10.0);
        this.rewardsRules.put("WL1C3", 10.0);
        this.rewardsRules.put("WL2C1", 10.0);
        this.rewardsRules.put("WL2C2", 10.0);
        this.rewardsRules.put("WL2C3", 10.0);
        this.rewardsRules.put("WL3C1", 10.0);
        this.rewardsRules.put("WL3C2", 10.0);
        this.rewardsRules.put("WL3C3", 10.0);
    }

    public Qtable responseReward(Long idQtable, boolean isCorrectResponse) {
        int bestActionIndex = this.qtableService.getBestAction(idQtable);
        Qtable qtable = this.qtableService.getById(idQtable);
        double reward = isCorrectResponse ? correctAnswerReward(qtable, bestActionIndex) : wrongAnswerReward(qtable, bestActionIndex);
        return this.qtableService.applyReinforcement(idQtable, reward);
    }

    private Double correctAnswerReward(Qtable qtable, int bestActionIndex) {
        String keyHash = "C";
        return this.rewardsRules.get(keyHash);
    }

    private Double wrongAnswerReward(Qtable qtable, int bestActionIndex) {
        String keyHash = "W";
        return this.rewardsRules.get(keyHash);
    }
}