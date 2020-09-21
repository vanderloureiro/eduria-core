package com.br.eduriacore.model;

import java.util.UUID;

import com.br.eduriacore.model.enums.LevelQuestionEnum;

import lombok.Data;

@Data
public class IntelligenceResponse {
    
    private UUID registerUuid;
    private LevelQuestionEnum selectedLevel;
}