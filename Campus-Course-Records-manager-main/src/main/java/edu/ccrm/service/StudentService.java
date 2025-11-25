package edu.ccrm.service;

import java.util.Collection;

import edu.ccrm.domain.Student;

public interface StudentService {
    void addStudent(Student s);
    Student findById(String id);
    Collection<Student> listAll();
}
