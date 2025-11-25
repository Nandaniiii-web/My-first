package edu.ccrm.service;

import java.util.Collection;

import edu.ccrm.domain.Course;

public class CourseServiceImpl implements CourseService {
    private final DataStore ds = DataStore.getInstance();

    @Override
    public void addCourse(Course c) { ds.addCourse(c); }

    @Override
    public Course findByCode(String code) { return ds.getCourse(code); }

    @Override
    public Collection<Course> listAll() { return ds.allCourses(); }
}
