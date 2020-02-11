package com.truextend.test.controller;

import com.truextend.test.controller.dto.ClassDto;
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
@RequestMapping("/api/v1/classes")
public class ClassController {

    @Autowired
    ClassService classService;
    @Autowired
    StudentClassServive studentClassServive;
    @Autowired
    StudentService studentService;

    @GetMapping
    public ResponseEntity<?> listAll(Optional<String> title) {
        List<Class> result;
        if (title.isPresent())
            result = classService.findByTitle(title.get());
        else {
            result = classService.findAll();
        }
        return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_FIND_SUCCESSFULLY).data(result).build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> persist(@RequestBody ClassDto classDto) {
        classService.persist(new Class(), classDto);
        return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_PERSIST_SUCCESSFULLY).data(null).build(), HttpStatus.CREATED);
    }

    @PutMapping("/{code}")
    public ResponseEntity<?> update(@PathVariable("code") String code, @RequestBody ClassDto classDto) {
        Class aClass = classService.get(code);
        if (aClass != null) {
            aClass = classService.persist(aClass, classDto);
            return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_UPDATE_SUCCESSFULLY).data(null).build(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResultResponse.builder().status(false).message(RestConstant.RESPONSE_NOT_FOUND_RECORD + code).data(null).build(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<?> delete(@PathVariable("code") String code) {
        Class aClass = classService.get(code);
        if (aClass != null) {
            classService.delete(aClass);
            return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_DELETE_SUCCESSFULLY).data(null).build(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResultResponse.builder().status(false).message(RestConstant.RESPONSE_NOT_FOUND_RECORD + code).data(null).build(), HttpStatus.OK);
        }
    }

    @PostMapping("/{code}/student")
    public ResponseEntity<?> persistStudents(@PathVariable("code") String code, @RequestBody long[] students) {
        Class aClass = classService.get(code);
        if (aClass != null) {

            List<StudentClass> studentClasses = studentClassServive.findByCodeClass(code);
            for (StudentClass studentClass : studentClasses) {
                studentClassServive.delete(studentClass);
            }
            for (long student : students) {
                StudentClass studentClass = new StudentClass();
                studentClass.setStudent(student);
                studentClass.setCodeClass(code);
                studentClassServive.persist(studentClass);
            }
            return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_PERSIST_SUCCESSFULLY).data(null).build(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(ResultResponse.builder().status(false).message(RestConstant.RESPONSE_NOT_FOUND_RECORD + code).data(null).build(), HttpStatus.OK);
        }
    }

    @GetMapping("/{code}/students")
    public ResponseEntity<?> listAllStudentsByClass(@PathVariable("code") String code) {
        List<Student> result = new ArrayList<>();
        List<StudentClass> studentClasses = studentClassServive.findByCodeClass(code);
        for (StudentClass studentClass : studentClasses)
            result.add(studentService.get(studentClass.getStudent()));
        return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_FIND_SUCCESSFULLY).data(result).build(), HttpStatus.OK);
    }
}
