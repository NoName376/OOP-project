package application;

import core.UniversityKernel;
import console.core.UniversityConsole;
import users.Admin;
import users.Student;
import users.DegreeType;
import academic.Course;
import academic.CourseStatus;
import infrastructure.NewsEntry;
import utils.DataStorage;

public class Main {
    public static void main(String[] args) {
        DataStorage.load();

        if (UniversityKernel.getInstance().getUsers().isEmpty()) {
            Admin defaultAdmin = new Admin(
                "ADM-001",
                "admin",
                "admin",
                "System",
                "Administrator",
                "admin@university.edu"
            );
            UniversityKernel.getInstance().getUsers().add(defaultAdmin);

            Student testStudent = new Student(
                "STU-001", "student", "student", "Ivan", "Ivanov", "ivan@kbtu.kz",
                DegreeType.BACHELOR, 2
            );
            UniversityKernel.getInstance().getUsers().add(testStudent);

            Course oop = new Course("CS101", "Object-Oriented Programming", 3, CourseStatus.MAJOR, 2);
            oop.addLesson(new academic.Lesson(academic.LessonType.LECTURE, "Introduction to OOP", "Room 401"));
            oop.addLesson(new academic.Lesson(academic.LessonType.PRACTICE, "Classes and Objects", "Room 402"));

            Course calc = new Course("MATH101", "Calculus I", 5, CourseStatus.MAJOR, 1);
            Course algo = new Course("CS201", "Algorithms", 4, CourseStatus.MAJOR, 2);

            UniversityKernel.getInstance().getCourses().add(oop);
            UniversityKernel.getInstance().getCourses().add(calc);
            UniversityKernel.getInstance().getCourses().add(algo);

            users.Teacher teacher = new users.Teacher(
                "TCH-001", "teacher", "teacher", "Pakita", "Shamoi", "p.shamoi@kbtu.kz",
                500000, java.time.LocalDate.now(), "FIT", users.TeacherTitle.PROFESSOR
            );
            UniversityKernel.getInstance().getUsers().add(teacher);
            oop.addInstructor(teacher);

            UniversityKernel.getInstance().getResearchProjects().add(new research.ResearchProject("AI in Education"));


            DataStorage.save();
        }

        UniversityConsole.getInstance().run();
    }
}
