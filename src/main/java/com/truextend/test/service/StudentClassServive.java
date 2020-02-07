package com.truextend.test.service;

import com.truextend.test.model.entity.StudentClass;

import java.util.List;

public interface StudentClassServive {

    StudentClass persist(StudentClass studentClass);

    void delete(StudentClass studentClass);

    List<StudentClass> findByStudent(long student);

    List<StudentClass> findByCodeClass(String codeClass);
}
