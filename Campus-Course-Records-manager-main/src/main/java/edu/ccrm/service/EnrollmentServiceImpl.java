package edu.ccrm.service;

import java.util.Objects;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Student;
import edu.ccrm.exceptions.DuplicateEnrollmentException;
import edu.ccrm.exceptions.MaxCreditLimitExceededException;

public class EnrollmentServiceImpl implements EnrollmentService {
    private final DataStore ds = DataStore.getInstance();
    private static final int MAX_CREDITS = 24;

    @Override
    public void enroll(String studentId, String courseCode)
            throws DuplicateEnrollmentException, MaxCreditLimitExceededException {
        Student s = ds.getStudent(studentId);
        Course c = ds.getCourse(courseCode);
        Objects.requireNonNull(s, "Student not found");
        Objects.requireNonNull(c, "Course not found");

        if (s.getEnrollments().stream().anyMatch(e -> e.getCourse().getCode().equals(courseCode))) {
            throw new DuplicateEnrollmentException("Student already enrolled in " + courseCode);
        }

        int currentCredits = s.getEnrollments().stream().mapToInt(e -> e.getCourse().getCredits()).sum();
        if (currentCredits + c.getCredits() > MAX_CREDITS) {
            throw new MaxCreditLimitExceededException("Enrolling would exceed max credits (" + MAX_CREDITS + ")");
        }

        Enrollment en = new Enrollment(s, c);
        s.addEnrollment(en);
    }

    @Override
    public void recordMarks(String studentId, String courseCode, double marks) throws Exception {
        Student s = ds.getStudent(studentId);
        if (s == null) throw new Exception("Student not found");
        Enrollment target = s.getEnrollments().stream()
                .filter(e -> e.getCourse().getCode().equals(courseCode))
                .findFirst().orElse(null);
        if (target == null) throw new Exception("Enrollment not found");
        target.setMarks(marks);
    }

    @Override
    public double computeGpa(String studentId) {
        Student s = ds.getStudent(studentId);
        if (s == null) return 0.0;
        double totalPoints = s.getEnrollments().stream()
                .filter(e -> !Double.isNaN(e.getMarks()))
                .mapToDouble(e -> e.getCourse().getCredits() * e.getLetterGrade().getPoints())
                .sum();
        double totalCredits = s.getEnrollments().stream()
                .filter(e -> !Double.isNaN(e.getMarks()))
                .mapToDouble(e -> e.getCourse().getCredits())
                .sum();
        return totalCredits == 0.0 ? 0.0 : totalPoints / totalCredits;
    }
}
