package users;

import core.UniversityKernel;
import java.time.LocalDate;
import java.util.List;

public class Admin extends Employee {
    public Admin(String id, String username, String passwordHash, String firstName, String lastName, String email,
                 double salary, LocalDate hireDate, String department) {
        super(id, username, passwordHash, firstName, lastName, email, salary, hireDate, department);
    }

    public void addUser(User u) {
        UniversityKernel.getInstance().getUsers().add(u);
        System.out.println("User added: " + u.getUsername());
    }

    public void removeUser(String userId) {
        User u = UniversityKernel.getInstance().findUserById(userId);
        if (u != null) {
            UniversityKernel.getInstance().getUsers().remove(u);
            System.out.println("User removed: " + u.getUsername());
        }
    }

    public void updateUser(User u) {
        System.out.println("User updated: " + u.getUsername());
    }

    public void viewLog(List<String> filter) {
        System.out.println("Viewing log with filter: " + filter);
    }
}
