package users;

import academic.Course;
import infrastructure.NewsEntry;
import infrastructure.Registration;
import infrastructure.Request;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Manager extends Employee {
    public Manager(String id, String username, String passwordHash, String firstName, String lastName, String email,
                   double salary, LocalDate hireDate, String department, ManagerType type) {
        super(id, username, passwordHash, firstName, lastName, email, salary, hireDate, department);
        this.type = type;
        this.requests = new ArrayList<>();
    }

    public void approveRegistration(Registration reg) {
        System.out.println("Approving registration: " + reg.getRegistrationId());
    }

    public void addCourse(Course c) {
        core.UniversityKernel.getInstance().getCourses().add(c);
        System.out.println("Course added: " + c.getName());
    }

    public void assignTeacher(Teacher t, Course c) {
        c.addInstructor(t);
        System.out.println("Assigned " + t.getFullName() + " to " + c.getName());
    }

    public String createPerformanceReport() {
        return "Performance Report for " + getFullName();
    }

    public void manageNews(NewsEntry news) {
        System.out.println("Managing news: " + news.getTitle());
    }

    public List<Request> viewRequests() {
        return requests;
    }

    public ManagerType getType() { return type; }

    private ManagerType type;
    private List<Request> requests;
}
