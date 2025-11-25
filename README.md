# My-first
A console-based Java SE project for managing students, courses, enrollments, grades, transcripts, and file utilities.
This project demonstrates OOP principles, Java Streams, NIO.2, Date/Time API, exception handling, design patterns (Singleton, Builder), enums, and recursion.

🚀 Features
1. Student Management
Add, list, update, deactivate students
Print student profile & transcript
2. Course Management
Add, list, update, deactivate courses
Search & filter (by instructor, department, semester) using Streams API
3. Enrollment & Grading
Enroll/Unenroll students into courses
Business rules: max credits per semester
Record marks & compute GPA
Enum Grade & Semester
Transcript generation (using toString() + polymorphism)
4. File Operations (NIO.2)
Import/Export CSV files
Export students, courses, enrollments
Backup exported files into timestamped folders
Recursive utility → compute backup folder size
5. CLI Workflow
Menu-driven interface with loops, switch, break/continue
Options: Students, Courses, Enrollments, Grades, Import/Export, Backup, Reports, Exit
🛠️ Tech Stack
Java SE 24
Maven 3.9+
Uses: Java Streams, NIO.2, Date/Time API, OOP principles, Design Patterns
Prerequisites
Java 24+
Apache Maven 3.9+
