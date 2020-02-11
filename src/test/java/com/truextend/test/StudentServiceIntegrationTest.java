package com.truextend.test;

import com.truextend.test.controller.dto.StudentDto;
import com.truextend.test.model.entity.Student;
import com.truextend.test.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class StudentServiceIntegrationTest {

    @Autowired
    private StudentService studentService;
    public static final int STUDENT_ID = 1;


    @Test
    public void contextLoads() {
    }


    @Test
    public void ensureGetById() {
        Student student = studentService.get(STUDENT_ID);
        assertNotNull(student);
        assertTrue(student instanceof Student);
    }

    @Test
    public void ensureFindByFirstName() {
        List<Student> students = studentService.findByFirstName("Edy");
        assertNotNull(students);
        assertTrue(students.size() > 0);
    }

    @Test
    public void ensureFindByLastName() {
        List<Student> students = studentService.findByLastName("Huiza");
        assertNotNull(students);
        assertTrue(students.size() > 0);
    }

    @Test
    public void ensureFindByLastNameAndLastName() {
        List<Student> students = studentService.findByFirstNameAndLastName("Edy", "Huiza");
        assertNotNull(students);
        assertTrue(students.size() > 0);
    }

    @Test
    public void ensureGetAll() {
        List<Student> students = studentService.findAll();
        assertNotNull(students);
        assertTrue(students.size() > 0);
    }

    @Test
    public void ensureCRUDoperations() {
        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName("Edy");
        studentDto.setLastName("HUIZA YAMPASI");
        Student newStudent = studentService.persist(new Student(), studentDto);
        assertTrue(newStudent instanceof Student);
        studentDto.setLastName("Huiza Yampasi");
        Student updateStudent = studentService.persist(newStudent, studentDto);
        assertTrue(updateStudent instanceof Student);
        assertEquals(updateStudent, newStudent);
        studentService.delete(updateStudent);
    }

}
