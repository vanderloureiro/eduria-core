package com.br.eduriacore.service;

import java.util.HashMap;
import java.util.Map;

import com.br.eduriacore.model.Enrollment;

import org.springframework.stereotype.Service;

@Service
public class RewardPolicyService {

    private Map<String, Double> rewardPolicy = new HashMap<String,Double>();

    public RewardPolicyService() {
        rewardPolicy.put("CORRECT_BEGINNER_EASY",       0.1);
        rewardPolicy.put("CORRECT_BEGINNER_MEDIUM",     0.2);
        rewardPolicy.put("CORRECT_BEGINNER_HARD",       0.4);
        rewardPolicy.put("CORRECT_INTERMEDIATE_EASY",   0.4);
        rewardPolicy.put("CORRECT_INTERMEDIATE_MEDIUM", 0.5);
        rewardPolicy.put("CORRECT_INTERMEDIATE_HARD",   0.6);
        rewardPolicy.put("CORRECT_ADVANCED_EASY",       0.7);
        rewardPolicy.put("CORRECT_ADVANCED_MEDIUM",     0.8);
        rewardPolicy.put("CORRECT_ADVANCED_HARD",       1.0);

        rewardPolicy.put("WRONG_BEGINNER_EASY",       -1.0);
        rewardPolicy.put("WRONG_BEGINNER_MEDIUM",     -0.8);
        rewardPolicy.put("WRONG_BEGINNER_HARD",       -0.7);
        rewardPolicy.put("WRONG_INTERMEDIATE_EASY",   -0.6);
        rewardPolicy.put("WRONG_INTERMEDIATE_MEDIUM", -0.5);
        rewardPolicy.put("WRONG_INTERMEDIATE_HARD",   -0.4);
        rewardPolicy.put("WRONG_ADVANCED_EASY",       -0.5);
        rewardPolicy.put("WRONG_ADVANCED_MEDIUM",     -0.2);
        rewardPolicy.put("WRONG_ADVANCED_HARD",       -0.1);
    }

    public Double findReward(Enrollment enrollment, boolean isCorrectResponse) {
        String stringFilter = this.generateStringFilter(enrollment, isCorrectResponse);
        return rewardPolicy.get(stringFilter);
    }

    private String generateStringFilter(Enrollment enrollment, boolean isCorrectResponse) {
        String filter = isCorrectResponse ? "CORRECT_" : "WRONG_";
        filter += enrollment.getState() + "_";
        filter += enrollment.getQtable().getBestActionByCurrentState();
        return filter;
    }
    
}