package com.truextend.test.model.repository;

import com.truextend.test.model.entity.StudentClass;
import com.truextend.test.model.entity.embeddable.StudentClassKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentClassRepository extends JpaRepository<StudentClass, StudentClassKey> {

    List<StudentClass> findByStudent(long id);

    List<StudentClass> findByCodeClass(String lastName);
}
