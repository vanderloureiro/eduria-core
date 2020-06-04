package com.br.eduriacore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "q_table")
public class Qtable {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="q_table_id")
    private Long qTableId;

    @Column(nullable=false)
    private Double L1C1;
    @Column(nullable=false)
    private Double L1C2;
    @Column(nullable=false)
    private Double L1C3;

    @Column(nullable=false)
    private Double L2C1;
    @Column(nullable=false)
    private Double L2C2;
    @Column(nullable=false)
    private Double L2C3;

    @Column(nullable=false)
    private Double L3C1;
    @Column(nullable=false)
    private Double L3C2;
    @Column(nullable=false)
    private Double L3C3;

    @Column(name="qtd_random", nullable=false)
    private int qtdRandom;

    @Column(name="index_current_state", nullable=false)
    private int indexCurrentState;

    @Column(name="current_random", nullable=false)
    private int currentRandom;

}