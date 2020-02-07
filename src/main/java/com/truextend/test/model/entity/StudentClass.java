package com.truextend.test.model.entity;

import com.truextend.test.model.entity.embeddable.StudentClassKey;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "student_class")
@IdClass(StudentClassKey.class)
public class StudentClass {
    @Id
    private long student;
    @Id
    private String codeClass;
}
