package com.truextend.test.service;

import com.truextend.test.controller.dto.ClassDto;
import com.truextend.test.model.entity.Class;
import com.truextend.test.model.repository.ClassRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    ClassRepository classRepository;

    @Override
    @Transactional
    public Class persist(Class aClass, ClassDto classDto) {
        BeanUtils.copyProperties(classDto, aClass);
        classRepository.save(aClass);
        return aClass;
    }

    @Override
    @Transactional(readOnly = true)
    public Class get(String code) {
        return classRepository.findById(code).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Class aClass) {
        classRepository.delete(aClass);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Class> findAll() {
        return classRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Class> findByTitle(String title) {
        return classRepository.findByTitle(title);
    }
}
