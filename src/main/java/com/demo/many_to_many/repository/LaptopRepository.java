package com.demo.many_to_many.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.many_to_many.model.Laptop;


@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Long> {
    List<Laptop> findByStudentId(Long studentId);
}