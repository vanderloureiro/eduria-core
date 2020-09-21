package com.br.eduriacore.model;

import com.br.eduriacore.model.enums.IntelligenceLevelEnum;

import lombok.Data;

@Data
public class IntelligenceResponse {
    
    private Long registerId;
    private IntelligenceLevelEnum selectedLevel;
}