package com.truextend.test.service;

import com.truextend.test.controller.dto.ClassDto;
import com.truextend.test.model.entity.Class;

import java.util.List;

public interface ClassService {

    Class persist(Class aClass, ClassDto classDto);

    Class get(String code);

    void delete(Class aClass);

    List<Class> findAll();

    List<Class> findByTitle(String title);
}
