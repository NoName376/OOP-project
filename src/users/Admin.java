package users;

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

    public void addUser(User u) {}
    public void removeUser(String userId) {}
    public void updateUser(User u) {}
    public void viewLog(List<String> filter) {}
}
