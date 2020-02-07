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
import java.util.HashMap;
import java.util.List;

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

    @GetMapping("")
    public ResponseEntity<?> listAll(String title) {
        try {
            List<Class> result;
            if (title != null)
                result = classService.findByTitle(title);
            else
                result = classService.findAll();
            return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_FIND_SUCCESSFULLY).data(result).build(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(RestConstant.RESPONSE_FIND_ERROR, e);
            return new ResponseEntity<>(new HashMap<>().put("message", RestConstant.RESPONSE_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> persist(@RequestBody ClassDto classDto) {
        try {
            Class aClass = new Class();
            aClass.setCode(String.valueOf(System.currentTimeMillis()));
            classService.persist(aClass, classDto);
            return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_PERSIST_SUCCESSFULLY).data(null).build(), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(RestConstant.RESPONSE_PERSIST_ERROR, e);
            return new ResponseEntity<>(new HashMap<>().put("message", RestConstant.RESPONSE_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{code}")
    public ResponseEntity<?> update(@PathVariable("code") String code, @RequestBody ClassDto classDto) {
        try {
            Class aClass = classService.get(code);
            if (aClass != null) {
                aClass = classService.persist(aClass, classDto);
                return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_UPDATE_SUCCESSFULLY).data(null).build(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(ResultResponse.builder().status(false).message(RestConstant.RESPONSE_NOT_FOUND_RECORD + code).data(null).build(), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error(RestConstant.RESPONSE_UPDATE_ERROR, e);
            return new ResponseEntity<>(new HashMap<>().put("message", RestConstant.RESPONSE_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<?> delete(@PathVariable("code") String code) {
        try {
            Class aClass = classService.get(code);
            if (aClass != null) {
                classService.delete(aClass);
                return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_DELETE_SUCCESSFULLY).data(null).build(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(ResultResponse.builder().status(false).message(RestConstant.RESPONSE_NOT_FOUND_RECORD + code).data(null).build(), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error(RestConstant.RESPONSE_DELETE_ERROR, e);
            return new ResponseEntity<>(new HashMap<>().put("message", RestConstant.RESPONSE_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{code}/student")
    public ResponseEntity<?> persistStudents(@PathVariable("code") String code, @RequestBody long[] students) {
        try {
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
        } catch (Exception e) {
            log.error(RestConstant.RESPONSE_UPDATE_ERROR, e);
            return new ResponseEntity<>(new HashMap<>().put("message", RestConstant.RESPONSE_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{code}/students")
    public ResponseEntity<?> listAllStudentsByClass(@PathVariable("code") String code) {
        try {
            List<Student> result = new ArrayList<>();
            List<StudentClass> studentClasses = studentClassServive.findByCodeClass(code);
            for (StudentClass studentClass : studentClasses)
                result.add(studentService.get(studentClass.getStudent()));
            return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_FIND_SUCCESSFULLY).data(result).build(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(RestConstant.RESPONSE_FIND_ERROR, e);
            return new ResponseEntity<>(new HashMap<>().put("message", RestConstant.RESPONSE_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
