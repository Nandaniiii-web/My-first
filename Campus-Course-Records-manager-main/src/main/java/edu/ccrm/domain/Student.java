package edu.ccrm.domain;

import java.util.*;

public class Student extends Person {
    private final String regNo;
    private boolean active = true;
    // courseCode -> Enrollment
    private final Map<String, Enrollment> enrollments = new LinkedHashMap<>();

    public Student(String id, String regNo, String fullName, String email) {
        super(id, fullName, email);
        this.regNo = regNo;
    }

    public String getRegNo() { return regNo; }
    public boolean isActive() { return active; }
    public void deactivate() { this.active = false; }

    public void addEnrollment(Enrollment e) { enrollments.put(e.getCourse().getCode(), e); }
    public void removeEnrollment(String courseCode) { enrollments.remove(courseCode); }

    public Collection<Enrollment> getEnrollments() {
        return Collections.unmodifiableCollection(enrollments.values());
    }

    @Override
    public String profile() {
        return String.format("Student[id=%s, regNo=%s, name=%s, email=%s, active=%s]",
                id, regNo, fullName, email, active);
    }
}
