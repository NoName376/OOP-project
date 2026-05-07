package users;

import core.IMessageSender;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Employee extends User implements IMessageSender {
    public Employee(String id, String username, String passwordHash, String firstName, String lastName, String email, 
                    double salary, LocalDate hireDate, String department) {
        super(id, username, passwordHash, firstName, lastName, email);
        this.salary = salary;
        this.hireDate = hireDate;
        this.department = department;
        this.salaryHistory = new ArrayList<>();
        this.salaryHistory.add(salary);
    }

    @Override
    public void sendMessage(Employee receiver, String content) {
        System.out.println("Message from " + this.firstName + " to " + receiver.getFirstName() + ": " + content);
    }

    public double getSalary() { return salary; }
    public LocalDate getHireDate() { return hireDate; }
    public String getDepartment() { return department; }
    
    public double[] getSalaryHistory() {
        return salaryHistory.stream().mapToDouble(Double::doubleValue).toArray();
    }

    protected double salary;
    protected LocalDate hireDate;
    protected String department;
    protected List<Double> salaryHistory;
}
