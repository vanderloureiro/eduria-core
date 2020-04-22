package com.br.eduriacore.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "professor")
public class Professor extends Person {

    private List<Course> courses;
    
}