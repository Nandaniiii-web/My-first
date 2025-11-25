package edu.ccrm.service;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Student;

public class DataStore {
    private static final DataStore INSTANCE = new DataStore();

    private final ConcurrentMap<String, Student> students = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Course> courses = new ConcurrentHashMap<>();

    private DataStore() {}

    public static DataStore getInstance() { return INSTANCE; }

    // students
    public void addStudent(Student s) { students.put(s.getId(), s); }
    public Student getStudent(String id) { return students.get(id); }
    public Collection<Student> allStudents() { return students.values(); }

    // courses
    public void addCourse(Course c) { courses.put(c.getCode(), c); }
    public Course getCourse(String code) { return courses.get(code); }
    public Collection<Course> allCourses() { return courses.values(); }
}
