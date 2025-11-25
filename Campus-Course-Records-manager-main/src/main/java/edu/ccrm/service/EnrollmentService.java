package edu.ccrm.service;

import edu.ccrm.exceptions.DuplicateEnrollmentException;
import edu.ccrm.exceptions.MaxCreditLimitExceededException;

public interface EnrollmentService {
    void enroll(String studentId, String courseCode)
            throws DuplicateEnrollmentException, MaxCreditLimitExceededException;
    void recordMarks(String studentId, String courseCode, double marks) throws Exception;
    double computeGpa(String studentId);
}
