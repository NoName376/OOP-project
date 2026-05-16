package users;

import core.UniversityKernel;
import java.time.LocalDate;
import java.util.List;

public class Admin extends Employee {
    public Admin(String id, String username, String passwordHash, String firstName, String lastName, String email) {
        super(id, username, passwordHash, firstName, lastName, email);
    }

    public Admin(String id, String username, String passwordHash, String firstName, String lastName, String email,
                 double salary, LocalDate hireDate, String department) {
        super(id, username, passwordHash, firstName, lastName, email, salary, hireDate, department);
    }

    public void addUser(User u) {
        UniversityKernel.getInstance().getUsers().add(u);
    }

    public void removeUser(String userId) {
        User u = UniversityKernel.getInstance().findUserById(userId);
        if (u != null) {
            UniversityKernel.getInstance().getUsers().remove(u);
        }
    }

    public void updateUser(User u) {
    }

    public void viewLog(List<String> filter) {
    }
}
