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
@Table(name = "laptop")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Laptop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 65)
    @Column(name = "laptop_name")
    private String laptopName;

//      This code work when database have laptop_student table.
/*    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    @JoinTable(
            name = "laptop_student",
            joinColumns = {@JoinColumn(name = "laptop_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")}
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore*/

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            mappedBy = "laptop"
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore

    private Set<Student> student=new HashSet<Student>();

    public Laptop() {
    }

    public Laptop(String laptopName) {
        this.laptopName = laptopName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLaptopName() {
        return laptopName;
    }

    public void setLaptopName(String laptopName) {
        this.laptopName = laptopName;
    }

    public Set<Student> getStudent() {
        return student;
    }

    public void setStudent(Set<Student> student) {
        this.student = student;
    }

}
