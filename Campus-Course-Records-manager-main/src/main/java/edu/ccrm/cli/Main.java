package edu.ccrm.cli;

import java.nio.file.Path;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.domain.Student;
import edu.ccrm.exceptions.DuplicateEnrollmentException;
import edu.ccrm.exceptions.MaxCreditLimitExceededException;
import edu.ccrm.io.BackupService;
import edu.ccrm.io.ImportExportService;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.CourseServiceImpl;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.service.EnrollmentServiceImpl;
import edu.ccrm.service.StudentService;
import edu.ccrm.service.StudentServiceImpl;
import edu.ccrm.util.ConsoleUtils;
import edu.ccrm.util.IdGenerator;

public class Main {
    private static final StudentService studentService = new StudentServiceImpl();
    private static final CourseService courseService = new CourseServiceImpl();
    private static final EnrollmentService enrollmentService = new EnrollmentServiceImpl();
    private static final ImportExportService ioService = new ImportExportService();
    private static final BackupService backupService = new BackupService();

    public static void main(String[] args) throws Exception {
        System.out.println("Campus Course & Records Manager (CCRM) - Minimal Skeleton");
        System.out.println("Data dir: " + AppConfig.getInstance().getDataDir());
        boolean running = true;
        while (running) {
            printMenu();
            String c = ConsoleUtils.readLine(">> ");
            switch (c) {
                case "1" -> addStudentFlow();
                case "2" -> listStudents();
                case "3" -> addCourseFlow();
                case "4" -> listCourses();
                case "5" -> enrollFlow();
                case "6" -> recordMarksFlow();
                case "7" -> exportDataFlow();
                case "8" -> backupFlow();
                case "9" -> reportsFlow();
                case "0" -> { running = false; System.out.println("Bye."); }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Add Student");
        System.out.println("2. List Students");
        System.out.println("3. Add Course");
        System.out.println("4. List Courses");
        System.out.println("5. Enroll Student");
        System.out.println("6. Record Marks");
        System.out.println("7. Export Data (CSV)");
        System.out.println("8. Backup Data Dir");
        System.out.println("9. Reports (GPA top)");
        System.out.println("0. Exit");
    }

    private static void addStudentFlow() {
        ConsoleUtils.printHeader("Add Student");
        String name = ConsoleUtils.readLine("Full name: ");
        String email = ConsoleUtils.readLine("Email: ");
        String reg = ConsoleUtils.readLine("RegNo: ");
        Student s = new Student(IdGenerator.nextId(), reg.isBlank() ? "REG-" + System.nanoTime() : reg, name, email);
        studentService.addStudent(s);
        System.out.println("Added: " + s.profile());
    }

    private static void listStudents() {
        ConsoleUtils.printHeader("All Students");
        studentService.listAll().forEach(s -> System.out.println(s.profile()));
        ConsoleUtils.pause();
    }

    private static void addCourseFlow() {
        ConsoleUtils.printHeader("Add Course");
        String code = ConsoleUtils.readLine("Code: ");
        String title = ConsoleUtils.readLine("Title: ");
        int credits = Integer.parseInt(ConsoleUtils.readLine("Credits: "));
        String dept = ConsoleUtils.readLine("Department: ");
        String sem = ConsoleUtils.readLine("Semester (SPRING/SUMMER/FALL): ");
        Course c = new Course.Builder(code.isBlank() ? "C" + System.nanoTime() : code)
                .title(title).credits(credits).department(dept)
                .semester(Semester.valueOf(sem.toUpperCase())).build();
        courseService.addCourse(c);
        System.out.println("Added course: " + c);
    }

    private static void listCourses() {
        ConsoleUtils.printHeader("All Courses");
        courseService.listAll().forEach(c -> System.out.println(c));
        ConsoleUtils.pause();
    }

    private static void enrollFlow() {
        ConsoleUtils.printHeader("Enroll Student");
        String sid = ConsoleUtils.readLine("Student Id: ");
        String ccode = ConsoleUtils.readLine("Course Code: ");
        try {
            enrollmentService.enroll(sid, ccode);
            System.out.println("Enrolled successfully.");
        } catch (DuplicateEnrollmentException | MaxCreditLimitExceededException e) {
            System.out.println("Enrollment failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void recordMarksFlow() {
        ConsoleUtils.printHeader("Record Marks");
        String sid = ConsoleUtils.readLine("Student Id: ");
        String code = ConsoleUtils.readLine("Course Code: ");
        double m = Double.parseDouble(ConsoleUtils.readLine("Marks: "));
        try {
            enrollmentService.recordMarks(sid, code, m);
            System.out.println("Marks recorded.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void exportDataFlow() {
        ConsoleUtils.printHeader("Export Data");
        try {
            Path students = ioService.exportStudents();
            Path courses = ioService.exportCourses();
            System.out.println("Exported to: " + students + " , " + courses);
        } catch (Exception e) {
            System.out.println("Export failed: " + e.getMessage());
        }
    }

    private static void backupFlow() {
        ConsoleUtils.printHeader("Backup");
        try {
            Path data = AppConfig.getInstance().getDataDir();
            Path backup = backupService.backupDirectory(data);
            long size = backupService.computeSizeRecursively(backup);
            System.out.println("Backup created: " + backup);
            System.out.println("Total bytes: " + size);
        } catch (Exception e) {
            System.out.println("Backup failed: " + e.getMessage());
        }
    }

    private static void reportsFlow() {
        ConsoleUtils.printHeader("Reports: Top GPA (computed)");
        studentService.listAll().stream()
                .sorted((a, b) -> Double.compare(enrollmentService.computeGpa(b.getId()), enrollmentService.computeGpa(a.getId())))
                .limit(5)
                .forEach(s -> System.out.printf("%s | GPA=%.2f%n", s.getFullName(), enrollmentService.computeGpa(s.getId())));
        ConsoleUtils.pause();
    }
}
