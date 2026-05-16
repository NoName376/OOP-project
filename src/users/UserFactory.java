package users;

import java.time.LocalDate;

public class UserFactory {
    public static Student createStudent(String id, String username, String password, String firstName, String lastName, String email, DegreeType degree, int year) {
        return new Student(id, username, password, firstName, lastName, email, degree, year);
    }

    public static Teacher createTeacher(String id, String username, String password, String firstName, String lastName, String email, double salary, String dept, TeacherTitle title) {
        return new Teacher(id, username, password, firstName, lastName, email, salary, LocalDate.now(), dept, title);
    }

    public static Manager createManager(String id, String username, String password, String firstName, String lastName, String email, double salary, String dept, ManagerType type) {
        return new Manager(id, username, password, firstName, lastName, email, salary, LocalDate.now(), dept, type);
    }
}
