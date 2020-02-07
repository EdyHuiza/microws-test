package com.truextend.test.model.entity.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class StudentClassKey implements Serializable {
    @Column(name = "student", nullable = false)
    private long student;

    @Column(name = "code_class", nullable = false)
    private String codeClass;
}
