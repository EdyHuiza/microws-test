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
import java.util.List;
import java.util.Optional;

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

    @GetMapping
    public ResponseEntity<?> listAll(Optional<String> firstName, Optional<String> lastName) {
        List<Student> result;
        if (firstName.isPresent() && lastName.isPresent())
            result = studentService.findByFirstNameAndLastName(firstName.get(), lastName.get());
        else if (firstName.isPresent())
            result = studentService.findByFirstName(firstName.get());
        else if (lastName.isPresent())
            result = studentService.findByLastName(lastName.get());
        else
            result = studentService.findAll();
        return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_FIND_SUCCESSFULLY).data(result).build(), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<?> persist(@RequestBody StudentDto studentDto) {
        Student student = studentService.persist(new Student(), studentDto);
        return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_PERSIST_SUCCESSFULLY).data(null).build(), HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody StudentDto studentDto) {
        Student student = studentService.get(id);
        if (student != null) {
            student = studentService.persist(student, studentDto);
            return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_UPDATE_SUCCESSFULLY).data(null).build(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResultResponse.builder().status(false).message(RestConstant.RESPONSE_NOT_FOUND_RECORD + id).data(null).build(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Student student = studentService.get(id);
        if (student != null) {
            studentService.delete(student);
            return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_DELETE_SUCCESSFULLY).data(null).build(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResultResponse.builder().status(false).message(RestConstant.RESPONSE_NOT_FOUND_RECORD + id).data(null).build(), HttpStatus.OK);
        }

    }

    @PostMapping("/{id}/classes")
    public ResponseEntity<?> persistClases(@PathVariable("id") long id, @RequestBody String[] codeClasses) {
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
    }

    @GetMapping("/{id}/classes")
    public ResponseEntity<?> listAllClassesByStudent(@PathVariable("id") long id) {
        List<Class> result = new ArrayList<>();
        List<StudentClass> studentClasses = studentClassServive.findByStudent(id);
        for (StudentClass studentClass : studentClasses)
            result.add(classService.get(studentClass.getCodeClass()));
        return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_FIND_SUCCESSFULLY).data(result).build(), HttpStatus.OK);

    }
}
