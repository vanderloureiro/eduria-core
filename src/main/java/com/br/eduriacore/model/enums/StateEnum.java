package com.br.eduriacore.model.enums;

public enum StateEnum {
    
    BEGINNER,
    INTERMEDIATE,
    ADVANCED;

    public static StateEnum returnStateByScore(Double score) {
        if ( score < 4 ) 
            return StateEnum.BEGINNER;
        else if ( score >= 4 && score < 7 ) 
            return StateEnum.INTERMEDIATE;
        else 
            return StateEnum.ADVANCED;
    }

    public static StateEnum getNextState(StateEnum state) {
        if (state == StateEnum.BEGINNER) return StateEnum.INTERMEDIATE;
        if (state == StateEnum.INTERMEDIATE) return StateEnum.ADVANCED; 
        return StateEnum.BEGINNER;
    }

}