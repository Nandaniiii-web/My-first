package edu.ccrm.service;

import java.util.Collection;

import edu.ccrm.domain.Student;

public class StudentServiceImpl implements StudentService {
    private final DataStore ds = DataStore.getInstance();

    @Override
    public void addStudent(Student s) {
        ds.addStudent(s);
    }

    @Override
    public Student findById(String id) {
        return ds.getStudent(id);
    }

    @Override
    public Collection<Student> listAll() {
        return ds.allStudents();
    }
}
