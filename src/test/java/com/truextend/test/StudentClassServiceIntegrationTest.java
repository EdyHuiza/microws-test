package com.truextend.test;

import com.truextend.test.model.entity.Student;
import com.truextend.test.model.entity.StudentClass;
import com.truextend.test.service.StudentClassServive;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
class StudentClassServiceIntegrationTest {

    @Autowired
    private StudentClassServive studentClassServive;
    public static final int STUDENT_ID = 1;
    public static final String CLASS_CODE = "12345678";

    @Test
    public void contextLoads() {
    }


    @Test
    public void ensureGetByStudentId() {
        List<StudentClass> studentClasses = studentClassServive.findByStudent(STUDENT_ID);
        assertNotNull(studentClasses);
        assertTrue(studentClasses.size() > 0);
    }

    @Test
    public void ensureGetByClassCode() {
        List<StudentClass> studentClasses = studentClassServive.findByCodeClass(CLASS_CODE);
        assertNotNull(studentClasses);
        assertTrue(studentClasses.size() > 0);
    }

    @Test
    public void ensureCRUDoperations() {
        StudentClass studentClass = new StudentClass();
        studentClass.setCodeClass(CLASS_CODE);
        studentClass.setStudent(2);
        StudentClass newStudentClass = studentClassServive.persist(studentClass);
        assertTrue(newStudentClass instanceof StudentClass);
        studentClassServive.delete(newStudentClass);
    }

}
