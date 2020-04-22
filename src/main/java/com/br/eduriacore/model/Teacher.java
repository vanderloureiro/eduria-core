package com.br.eduriacore.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "teacher")
public class Teacher extends Person {

    @OneToMany(mappedBy = "teacher")
    private List<Course> courses;
    
}