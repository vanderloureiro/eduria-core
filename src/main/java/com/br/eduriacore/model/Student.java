package com.br.eduriacore.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "student")
public class Student extends Person {

    private List<Enrollment> enrollments;

}