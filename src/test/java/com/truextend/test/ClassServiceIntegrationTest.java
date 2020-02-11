package com.truextend.test;

import com.truextend.test.controller.dto.ClassDto;
import com.truextend.test.model.entity.Class;
import com.truextend.test.service.ClassService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ClassServiceIntegrationTest {

    @Autowired
    private ClassService classService;
    public static final String CLASS_CODE = "12345678";

    @Test
    public void contextLoads() {
    }


    @Test
    public void ensureGetById() {
        Class aClass = classService.get(CLASS_CODE);
        assertNotNull(aClass);
        assertTrue(aClass instanceof Class);
    }

    @Test
    public void ensureFindByTitle() {
        List<Class> classes = classService.findByTitle("Calculo I");
        assertNotNull(classes);
        assertTrue(classes.size() > 0);
    }

    @Test
    public void ensureGetAll() {
        List<Class> classes = classService.findAll();
        assertNotNull(classes);
        assertTrue(classes.size() > 0);
    }

    @Test
    public void ensureCRUDoperations() {
        ClassDto classDto = new ClassDto();
        classDto.setTitle("Title 1");
        classDto.setDescription("Description 1");
        Class newClass = classService.persist(new Class(), classDto);
        assertTrue(newClass instanceof Class);
        classDto.setTitle("Title changed");
        Class updateClass = classService.persist(newClass, classDto);
        assertTrue(updateClass instanceof Class);
        assertEquals(updateClass, newClass);
        classService.delete(updateClass);
    }

}
