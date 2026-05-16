package users;

import java.time.LocalDate;

public class Manager extends Employee {
    public Manager(String id, String username, String passwordHash, String firstName, String lastName, String email,
                   double salary, LocalDate hireDate, String department, ManagerType type) {
        super(id, username, passwordHash, firstName, lastName, email, salary, hireDate, department);
        this.type = type;
    }

    public ManagerType getType() { return type; }

    private ManagerType type;
}
