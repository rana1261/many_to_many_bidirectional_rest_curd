package com.demo.many_to_many.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.many_to_many.exception.NotFoundException;
import com.demo.many_to_many.model.Laptop;
import com.demo.many_to_many.model.Student;
import com.demo.many_to_many.repository.LaptopRepository;
import com.demo.many_to_many.repository.StudentRepository;


@RestController
public class LaptopController {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    LaptopRepository laptopRepository;


    /* post a Laptop  */
    @PostMapping(value = "laptops")
    public void saveLaptop(@Valid @RequestBody Laptop laptop) {

        laptopRepository.save(laptop);
    }


    /* get all laptop form database laptop table or
     kono student table er data ekhane asbe na or
      kotogulo laptop ache tar list dekhabe*/
    @GetMapping(value = "laptops")
    public List<Laptop> getAllLaptop() {
        return laptopRepository.findAll();
    }



    /*get a Laptop by Laptop ID*/
    @GetMapping("/laptops/{laptopId}")
    public Laptop getLaptopById(@PathVariable Long laptopId) {

        return laptopRepository.findById(laptopId).orElseThrow(
                () -> new NotFoundException("Student not found with id " + laptopId));


    }




    /*Without student table row, Delete only Laptop row*/
    @DeleteMapping("laptops/{laptopId}")
    public String deleteLaptop( @PathVariable Long laptopId) {


        return laptopRepository.findById(laptopId)
                .map(laptop -> {
                    laptopRepository.delete(laptop);
                    return "Deleted Successfully!";
                }).orElseThrow(() -> new NotFoundException("Laptop not found!"));
    }




    /*  akta laptop id diye update korbe
        akane kono student table er data update kora jabe na*/
    @PutMapping("laptops/{laptopId}")
    public Laptop updateLaptop( @PathVariable Long laptopId,
                               @Valid @RequestBody Laptop laptopUpdated) {

        return laptopRepository.findById(laptopId)
                .map(laptop -> {
                    laptop.setLaptopName(laptopUpdated.getLaptopName());
                    return laptopRepository.save(laptop);
                }).orElseThrow(() -> new NotFoundException("Laptop not found!"));
    }
    /*
     * @PutMapping(value="laptops/update") public void updateStudent(@RequestBody
     * Laptop laptop) { Laptop lap=laptopRepository.getOne(laptop.getLid());
     * lap.setlName(laptop.getlName()); laptopRepository.save(lap); }
     */





    /*get  all Students by Laptop ID or  oi laptop id er under e koiti student ache ta dekhabe */
    @GetMapping("laptops/{laptopId}/students")
    public Set<Student> getStudents(@PathVariable Long laptopId){
        // Finds lecturer by id and returns it's recorded students, otherwise throws exception
        return this.laptopRepository.findById(laptopId).map((laptop) -> {
            return laptop.getStudent();
        }).orElseThrow(() -> new NotFoundException("Laptop", laptopId));
    }




/*

//    This code work when database have laptop_student table.
//    Here,we are working on many to many relationship. Based on one laptop id,
//      we will add one student Id or more student Id

    @PostMapping("laptops/{laptopId}/students/{studentId}")
    public Set<Student> addStudent(@PathVariable Long laptopId, @PathVariable Long studentId){
        // Finds a persisted student
        Student student = this.studentRepository.findById(studentId).orElseThrow(
                () -> new NotFoundException("Student", studentId)
        );

        // Finds a laptop and adds the given student to the laptop's set.
        return this.laptopRepository.findById(laptopId).map((laptop) -> {
            laptop.getStudent().add(student);
            return this.laptopRepository.save(laptop).getStudent();
        }).orElseThrow(() -> new NotFoundException("Laptop", laptopId));

    }

*/






   // This code work when database have student_laptop table.
   // Here,we are working on many to many relationship. Based on one student id,
   // we will add one laptop Id or more laptop Id
    @PostMapping("students/{studentId}/laptops/{laptopId}")
    public Set<Laptop> addLaptop(@PathVariable Long studentId, @PathVariable Long laptopId) {
        Laptop laptop = laptopRepository.findById(laptopId).
                orElseThrow(() -> new NotFoundException("Laptop", laptopId));

        // Finds a student and adds the given Laptop to the Student's set.
        return studentRepository.findById(studentId).map(student -> {
            student.getLaptop().add(laptop);
            return studentRepository.save(student).getLaptop();
        }).orElseThrow(() -> new NotFoundException("Student", studentId));
    }

}



