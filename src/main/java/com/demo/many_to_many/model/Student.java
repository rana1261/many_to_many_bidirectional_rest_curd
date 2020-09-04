package com.demo.many_to_many.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "student")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 65)
    @Column(name = "Student_name")
    private String studentName;

    @NotNull
    @Size(max = 65)
    @Column(name = "Student_mark")
    private int studentMark;

//      This code work when database have laptop_student table.
/*    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            mappedBy = "student"
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore*/

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    @JoinTable(
            name = "student_laptop",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "laptop_id")}
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Set<Laptop> laptop= new HashSet<Laptop>();

    public Student() {

    }

    public Student(String studentName, int studentMark) {
        this.studentName = studentName;
        this.studentMark = studentMark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getStudentMark() {
        return studentMark;
    }

    public void setStudentMark(int studentMark) {
        this.studentMark = studentMark;
    }

    public Set<Laptop> getLaptop() {
        return laptop;
    }

    public void setLaptop(Set<Laptop> laptop) {
        this.laptop = laptop;
    }

}