package users;

import core.IMessageSender;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Employee extends User implements IMessageSender, research.IResearcher {
    public Employee(String id, String username, String passwordHash, String firstName, String lastName, String email) {
        this(id, username, passwordHash, firstName, lastName, email, 0, LocalDate.now(), "Default");
    }

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
    public int getHIndex() {
        return researchComponent != null ? researchComponent.getHIndex() : 0;
    }

    @Override
    public List<research.ResearchPaper> getPapers() {
        return researchComponent != null ? researchComponent.getPapers() : new ArrayList<>();
    }

    @Override
    public void printPapers(java.util.Comparator<research.ResearchPaper> sorter) {
        if (researchComponent != null) researchComponent.printPapers(sorter);
    }

    @Override
    public void addPaper(research.ResearchPaper p) {
        if (researchComponent != null) researchComponent.addPaper(p);
    }

    public void setResearchComponent(research.ResearchDecorator researchComponent) {
        this.researchComponent = researchComponent;
    }

    public research.ResearchDecorator getResearchComponent() {
        return researchComponent;
    }

    @Override
    public void sendMessage(Employee receiver, String content) {
    }

    public double getSalary() {
        return salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public String getDepartment() {
        return department;
    }

    public double[] getSalaryHistory() {
        return salaryHistory.stream().mapToDouble(Double::doubleValue).toArray();
    }

    protected double salary;
    protected LocalDate hireDate;
    protected String department;
    protected List<Double> salaryHistory;
    protected research.ResearchDecorator researchComponent;
}
