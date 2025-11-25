package edu.ccrm.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.domain.Student;
import edu.ccrm.service.DataStore;
import edu.ccrm.util.IdGenerator;

public class ImportExportService {
    private final DataStore ds = DataStore.getInstance();
    private final Path dataDir = AppConfig.getInstance().getDataDir();

    public void importStudents(Path csvPath) throws IOException {
        try (Stream<String> lines = Files.lines(csvPath)) {
            lines.skip(1).map(this::parseStudent).forEach(ds::addStudent);
        }
    }

    private Student parseStudent(String line) {
        String[] p = line.split(",");
        String id = p.length > 0 && !p[0].isBlank() ? p[0].trim() : IdGenerator.nextId();
        String reg = p.length > 1 ? p[1].trim() : "REG-UNK";
        String name = p.length > 2 ? p[2].trim() : "Unknown";
        String email = p.length > 3 ? p[3].trim() : "";
        return new Student(id, reg, name, email);
    }

    public void importCourses(Path csvPath) throws IOException {
        try (Stream<String> lines = Files.lines(csvPath)) {
            lines.skip(1).map(this::parseCourse).forEach(ds::addCourse);
        }
    }

    private Course parseCourse(String line) {
        String[] p = line.split(",");
        String code = p.length > 0 ? p[0].trim() : "C-" + IdGenerator.nextId();
        Course.Builder b = new Course.Builder(code);
        if (p.length > 1) b.title(p[1].trim());
        if (p.length > 2) b.credits(Integer.parseInt(p[2].trim()));
        if (p.length > 3) b.instructor(p[3].trim());
        if (p.length > 4) {
            try { b.semester(Semester.valueOf(p[4].trim())); } catch (Exception ignored) {}
        }
        if (p.length > 5) b.department(p[5].trim());
        return b.build();
    }

    public Path exportStudents() throws IOException {
        Path out = dataDir.resolve("students_export.csv");
        try (BufferedWriter w = Files.newBufferedWriter(out)) {
            w.write("id,regNo,fullName,email\n");
            for (Student s : ds.allStudents()) {
                w.write(String.format("%s,%s,%s,%s\n",
                        s.getId(), s.getRegNo(), s.getFullName(), s.getEmail()));
            }
        }
        return out;
    }

    public Path exportCourses() throws IOException {
        Path out = dataDir.resolve("courses_export.csv");
        try (BufferedWriter w = Files.newBufferedWriter(out)) {
            w.write("code,title,credits,instructor,semester,department\n");
            for (Course c : ds.allCourses()) {
                w.write(String.format("%s,%s,%d,%s,%s,%s\n",
                        c.getCode(), c.getTitle(), c.getCredits(),
                        c.getInstructor(), c.getSemester(), c.getDepartment()));
            }
        }
        return out;
    }
}
