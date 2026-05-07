package users;

import java.time.LocalDate;

public class Professor extends Teacher {
    public Professor(String id, String username, String passwordHash, String firstName, String lastName, String email,
                     double salary, LocalDate hireDate, String department) {
        super(id, username, passwordHash, firstName, lastName, email, salary, hireDate, department, TeacherTitle.PROFESSOR);
    }
}
