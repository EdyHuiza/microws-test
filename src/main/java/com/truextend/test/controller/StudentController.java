package com.truextend.test.controller;

import com.truextend.test.controller.dto.StudentDto;
import com.truextend.test.model.entity.Class;
import com.truextend.test.model.entity.Student;
import com.truextend.test.model.entity.StudentClass;
import com.truextend.test.service.ClassService;
import com.truextend.test.service.StudentClassServive;
import com.truextend.test.service.StudentService;
import com.truextend.test.util.RestConstant;
import com.truextend.test.util.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@Slf4j
@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    @Autowired
    StudentService studentService;
    @Autowired
    StudentClassServive studentClassServive;
    @Autowired
    ClassService classService;

    @GetMapping("")
    public ResponseEntity<?> listAll(String firstName, String lastName) {
        try {
            List<Student> result;
            if (firstName != null && lastName != null)
                result = studentService.findByLastNameFirstName(firstName, lastName);
            else if (firstName != null)
                result = studentService.findByFirstName(firstName);
            else if (lastName != null)
                result = studentService.findByLastName(lastName);
            else
                result = studentService.findAll();

            return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_FIND_SUCCESSFULLY).data(result).build(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(RestConstant.RESPONSE_FIND_ERROR, e);
            return new ResponseEntity<>(new HashMap<>().put("message", RestConstant.RESPONSE_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> persist(@RequestBody StudentDto studentDto) {
        try {
            Student student = studentService.persist(new Student(), studentDto);
            return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_PERSIST_SUCCESSFULLY).data(null).build(), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(RestConstant.RESPONSE_PERSIST_ERROR, e);
            return new ResponseEntity<>(new HashMap<>().put("message", RestConstant.RESPONSE_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody StudentDto studentDto) {
        try {
            Student student = studentService.get(id);
            if (student != null) {
                student = studentService.persist(student, studentDto);
                return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_UPDATE_SUCCESSFULLY).data(null).build(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(ResultResponse.builder().status(false).message(RestConstant.RESPONSE_NOT_FOUND_RECORD + id).data(null).build(), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error(RestConstant.RESPONSE_UPDATE_ERROR, e);
            return new ResponseEntity<>(new HashMap<>().put("message", RestConstant.RESPONSE_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            Student student = studentService.get(id);
            if (student != null) {
                studentService.delete(student);
                return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_DELETE_SUCCESSFULLY).data(null).build(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(ResultResponse.builder().status(false).message(RestConstant.RESPONSE_NOT_FOUND_RECORD + id).data(null).build(), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error(RestConstant.RESPONSE_DELETE_ERROR, e);
            return new ResponseEntity<>(new HashMap<>().put("message", RestConstant.RESPONSE_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/classes")
    public ResponseEntity<?> persistClases(@PathVariable("id") long id, @RequestBody String[] codeClasses) {
        try {
            Student student = studentService.get(id);
            if (student != null) {
                List<StudentClass> studentClasses = studentClassServive.findByStudent(id);
                for (StudentClass studentClass : studentClasses) {
                    studentClassServive.delete(studentClass);
                }
                for (String aClass : codeClasses) {
                    StudentClass studentClass = new StudentClass();
                    studentClass.setStudent(id);
                    studentClass.setCodeClass(aClass);
                    studentClassServive.persist(studentClass);
                }
                return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_PERSIST_SUCCESSFULLY).data(null).build(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(ResultResponse.builder().status(false).message(RestConstant.RESPONSE_NOT_FOUND_RECORD + id).data(null).build(), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error(RestConstant.RESPONSE_UPDATE_ERROR, e);
            return new ResponseEntity<>(new HashMap<>().put("message", RestConstant.RESPONSE_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/classes")
    public ResponseEntity<?> listAllClassesByStudent(@PathVariable("id") long id) {
        try {
            List<Class> result = new ArrayList<>();
            List<StudentClass> studentClasses = studentClassServive.findByStudent(id);
            for (StudentClass studentClass : studentClasses)
                result.add(classService.get(studentClass.getCodeClass()));
            return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_FIND_SUCCESSFULLY).data(result).build(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(RestConstant.RESPONSE_FIND_ERROR, e);
            return new ResponseEntity<>(new HashMap<>().put("message", RestConstant.RESPONSE_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
