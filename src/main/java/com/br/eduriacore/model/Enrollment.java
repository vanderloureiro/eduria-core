package com.br.eduriacore.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "enrollment")
public class Enrollment {

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
}