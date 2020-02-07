package com.truextend.test.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "class")
public class Class {
    @Id
    @Column(name = "code")
    private String code;
    @Column(name = "title", length = 100, unique = true)
    private String title;
    @Column(name = "description")
    private String description;
}
