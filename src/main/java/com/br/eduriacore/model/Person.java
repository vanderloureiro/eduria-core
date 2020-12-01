package com.br.eduriacore.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person {

    @Id
    @GeneratedValue
    protected Long id;
    
    @Column(nullable = false)
    protected String name;

    @Column(nullable = false, length = 40, unique = true)
    protected String email;

    @Column(nullable = false)
    protected String password;

    @Column(length = 40)
    protected String phone;

    @Column(nullable = false)
    protected LocalDate birthDate;

}