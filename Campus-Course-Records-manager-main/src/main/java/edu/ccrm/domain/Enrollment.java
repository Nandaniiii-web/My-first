package edu.ccrm.domain;

import java.time.LocalDate;

public class Enrollment {
    private final Student student;
    private final Course course;
    private final LocalDate enrolledOn;
    private double marks = Double.NaN;

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.enrolledOn = LocalDate.now();
    }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public LocalDate getEnrolledOn() { return enrolledOn; }

    public void setMarks(double marks) { this.marks = marks; }
    public double getMarks() { return marks; }

    public Grade getLetterGrade() { return Grade.fromMarks(marks); }

    @Override
    public String toString() {
        return String.format("%s -> %s : marks=%.2f, grade=%s",
                student.getFullName(), course.getCode(),
                Double.isNaN(marks) ? 0.0 : marks,
                getLetterGrade());
    }
}
