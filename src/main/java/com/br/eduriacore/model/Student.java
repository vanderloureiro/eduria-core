package com.br.eduriacore.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "student")
public class Student extends Person {

    @OneToMany(mappedBy = "student")
    private List<Enrollment> enrollments;

}