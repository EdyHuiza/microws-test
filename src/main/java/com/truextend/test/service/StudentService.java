package com.truextend.test.service;

import com.truextend.test.controller.dto.StudentDto;
import com.truextend.test.model.entity.Student;

import java.util.List;

public interface StudentService {

    Student persist(Student student, StudentDto studentDto);

    Student get(long id);

    void delete(Student student);

    List<Student> findAll();

    List<Student> findByFirstName(String firstName);

    List<Student> findByLastName(String lastName);

    List<Student> findByLastNameFirstName(String firstName, String lastName);
}
