package com.truextend.test.service;

import com.truextend.test.model.entity.StudentClass;
import com.truextend.test.model.repository.StudentClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentClassServiveImpl implements StudentClassServive {

    @Autowired
    StudentClassRepository studentClassRepository;

    @Override
    @Transactional
    public StudentClass persist(StudentClass studentClass) {
        studentClassRepository.save(studentClass);
        return studentClass;
    }

    @Override
    @Transactional
    public void delete(StudentClass studentClass) {
        studentClassRepository.delete(studentClass);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentClass> findByStudent(long student) {
        return studentClassRepository.findByStudent(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentClass> findByCodeClass(String codeClass) {
        return studentClassRepository.findByCodeClass(codeClass);
    }
}
