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
                "ST-001",
                "student",
                "student",
                "John",
                "Doe",
                "student@university.edu",
                DegreeType.BACHELOR,
                1
            );
            
            Course oop = new Course("CS-201", "Object-Oriented Programming", 5, CourseStatus.MAJOR, 1);
            try {
                testStudent.registerForCourse(oop);
            } catch (Exception e) {}

            UniversityKernel.getInstance().getUsers().add(testStudent);
            
            UniversityKernel.getInstance().getNews().add(new NewsEntry(
                "Welcome to the new Semester!",
                "We are glad to see you all. Registration for courses is now open."
            ));

            DataStorage.save();
        }

        UniversityConsole.getInstance().run();
    }
}
