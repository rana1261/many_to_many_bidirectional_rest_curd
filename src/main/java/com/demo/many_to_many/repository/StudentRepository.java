package com.demo.many_to_many.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.many_to_many.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}