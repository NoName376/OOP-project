package users;

import core.IMessageSender;
import infrastructure.Request;
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
        this.inbox = new ArrayList<>();
        this.requests = new ArrayList<>();
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
        String message = "From " + getFullName() + ": " + content;
        receiver.inbox.add(message);
        core.UniversityKernel.getInstance().getLogger()
                .log(getFullName() + " sent a message to " + receiver.getFullName());
    }

    public List<String> getInbox() {
        return inbox;
    }

    public void submitRequest(String content) {
        String requestId = "REQ-" + getId() + "-" + (requests.size() + 1);
        Request request = new Request(requestId, content);
        requests.add(request);
        core.UniversityKernel.getInstance().getLogger()
                .log(getFullName() + " submitted request: " + content);
    }

    public List<Request> getRequests() {
        return requests;
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
    protected List<String> inbox;
    protected List<Request> requests;
}