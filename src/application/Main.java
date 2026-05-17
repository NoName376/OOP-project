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

            DataStorage.save();
        }

        UniversityConsole.getInstance().run();
    }
}
