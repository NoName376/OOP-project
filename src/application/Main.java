package application;

import academic.Course;
import academic.CourseStatus;
import console.core.UniversityConsole;
import core.UniversityKernel;
import infrastructure.NewsEntry;
import java.time.LocalDate;
import users.Admin;
import users.DegreeType;
import users.Manager;
import users.ManagerType;
import users.Student;
import users.Teacher;
import users.TeacherTitle;
import users.UserFactory;
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

            Student testStudent = UserFactory.createStudent("24B031692", "student", "student", "Paren", "Prostoi", "pivo@kbtu.kz", DegreeType.BACHELOR, 2);
            UniversityKernel.getInstance().getUsers().add(testStudent);

            Manager testManager = UserFactory.createManager("M001", "manager", "123", "Assel", "Berikova", "a_berikova@kbtu.kz", 500000, "OR", ManagerType.OR);
            UniversityKernel.getInstance().getUsers().add(testManager);

            Course oop = new Course("CS101", "Object-Oriented Programming", 3, CourseStatus.MAJOR, 2);
            oop.addLesson(new academic.Lesson(academic.LessonType.LECTURE, "OOP Lections"));
            oop.addLesson(new academic.Lesson(academic.LessonType.PRACTICE, "OOP practice"));

            Course calc = new Course("MATH101", "Calculus I", 5, CourseStatus.MAJOR, 1);
            Course algo = new Course("CS201", "Algorithms", 4, CourseStatus.MAJOR, 2);

            UniversityKernel.getInstance().getCourses().add(oop);
            UniversityKernel.getInstance().getCourses().add(calc);
            UniversityKernel.getInstance().getCourses().add(algo);

            users.Teacher teacher = UserFactory.createTeacher(
                    "TCH-001", "teacher", "teacher", "Pakita", "Shamoi", "p.shamoi@kbtu.kz",
                    500000, "FIT", users.TeacherTitle.PROFESSOR
            );
            UniversityKernel.getInstance().getUsers().add(teacher);
            oop.addInstructor(teacher);

            UniversityKernel.getInstance().getResearchProjects().add(new research.ResearchProject("AI in Education"));


            DataStorage.save();
        }

        UniversityConsole.getInstance().run();
    }
}