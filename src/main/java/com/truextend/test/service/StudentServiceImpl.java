package com.truextend.test.service;

import com.truextend.test.controller.dto.StudentDto;
import com.truextend.test.model.entity.Student;
import com.truextend.test.model.repository.StudentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;


    @Override
    @Transactional
    public Student persist(Student student, StudentDto studentDto) {
        BeanUtils.copyProperties(studentDto, student);
        studentRepository.save(student);
        return student;
    }

    @Override
    @Transactional(readOnly = true)
    public Student get(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Student student) {
        studentRepository.delete(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findByFirstName(String firstName) {
        return studentRepository.findByFirstName(firstName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findByLastName(String lastName) {
        return studentRepository.findByLastName(lastName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findByFirstNameAndLastName(String firstName, String lastName) {
        return studentRepository.findAllByFirstNameAndLastName(firstName, lastName);
    }
}
