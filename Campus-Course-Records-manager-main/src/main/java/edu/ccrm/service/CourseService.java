package edu.ccrm.service;

import java.util.Collection;

import edu.ccrm.domain.Course;

public interface CourseService {
    void addCourse(Course c);
    Course findByCode(String code);
    Collection<Course> listAll();
}
