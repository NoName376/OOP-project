package console.pages;

import console.pagescore.Page;
import core.UniversityKernel;
import users.*;
import parsers.*;
import research.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import research.ResearchDecorator;

public class UserManagementPage extends Page {
    public UserManagementPage() {
        super("User Management");
        addAction("List All Users", this::listUsers);
        addAction("Filter Students", this::filterStudents);
        addAction("Filter Employees", this::filterEmployees);
        addAction("Add Student", this::addStudent);
        addAction("Add Teacher", this::addTeacher);
        addAction("Add Researcher Status", this::addResearcher);
        addAction("Update User Profile", () -> new UpdateUserPage().display());
        addAction("Delete User by ID", this::deleteUser);
    }

    private void listUsers() {
        renderUserList(UniversityKernel.getInstance().getUsers(), "All Users");
    }

    private void filterStudents() {
        List<User> students = UniversityKernel.getInstance().getUsers().stream()
                .filter(u -> u instanceof Student)
                .collect(Collectors.toList());
        renderUserList(students, "Students Only");
    }

    private void filterEmployees() {
        List<User> employees = UniversityKernel.getInstance().getUsers().stream()
                .filter(u -> u instanceof Employee)
                .collect(Collectors.toList());
        renderUserList(employees, "Employees Only");
    }

    private void renderUserList(List<User> users, String header) {
        console.getRenderer().renderHeader(header);
        if (users.isEmpty()) {
            console.getRenderer().renderMessage("No users found.");
        } else {
            for (User u : users) {
                String researcherInfo = (u instanceof IResearcher && ((IResearcher)u).getHIndex() > 0) ? " [Researcher]" : "";
                console.getRenderer().renderData(u.getId(), u.getFullName() + " (@" + u.getUsername() + ")" + researcherInfo);
            }
        }
        console.getInput().waitForEnter();
    }

    private void deleteUser() {
        String id = console.getInput().read("Enter User ID to delete", new StringParser(false));
        User u = UniversityKernel.getInstance().findUserById(id);
        if (u != null) {
            UniversityKernel.getInstance().getUsers().remove(u);
            console.getRenderer().renderSuccess("User " + id + " removed.");
        } else {
            console.getRenderer().renderError("User not found.");
        }
        console.getInput().waitForEnter();
    }

    private void addStudent() {
        String id = console.getInput().read("ID", new StringParser(false));
        String username = console.getInput().read("Username", new StringParser(false));
        String password = console.getInput().read("Password", new StringParser(false));
        String firstName = console.getInput().read("First Name", new StringParser(false));
        String lastName = console.getInput().read("Last Name", new StringParser(false));
        String email = console.getInput().read("Email", new StringParser(false));
        
        console.getRenderer().renderMessage("Degree: 1-BACHELOR, 2-MASTER, 3-PHD");
        int degreeIdx = console.getInput().read("Select Degree", new IntegerParser(1, 3));
        DegreeType degree = DegreeType.values()[degreeIdx - 1];
        
        int year = console.getInput().read("Year of Study", new IntegerParser(1, 4));

        Student student = UserFactory.createStudent(id, username, password, firstName, lastName, email, degree, year);
        UniversityKernel.getInstance().getUsers().add(student);
        console.getRenderer().renderSuccess("Student added!");
        console.getInput().waitForEnter();
    }

    private void addTeacher() {
        String id = console.getInput().read("ID", new StringParser(false));
        String username = console.getInput().read("Username", new StringParser(false));
        String password = console.getInput().read("Password", new StringParser(false));
        String firstName = console.getInput().read("First Name", new StringParser(false));
        String lastName = console.getInput().read("Last Name", new StringParser(false));
        String email = console.getInput().read("Email", new StringParser(false));
        
        double salary = console.getInput().read("Salary", new DoubleParser(0, 1000000));
        String dept = console.getInput().read("Department", new StringParser(false));
        
        console.getRenderer().renderMessage("Title: 1-TUTOR, 2-LECTURER, 3-PROFESSOR");
        int titleIdx = console.getInput().read("Select Title", new IntegerParser(1, 3));
        TeacherTitle title = TeacherTitle.values()[titleIdx - 1];

        Teacher teacher = UserFactory.createTeacher(id, username, password, firstName, lastName, email, salary, dept, title);
        UniversityKernel.getInstance().getUsers().add(teacher);
        console.getRenderer().renderSuccess("Teacher added!");
        console.getInput().waitForEnter();
    }

    private void addResearcher() {
        String username = console.getInput().read("Username of user to upgrade", new StringParser(false));
        User user = UniversityKernel.getInstance().findUserByUsername(username);
        
        if (user == null) {
            console.getRenderer().renderError("User not found!");
            return;
        }

        if (user instanceof Admin) {
            console.getRenderer().renderError("Admins cannot be researchers!");
            return;
        }

        if (user instanceof Employee) {
            Employee e = (Employee) user;
            if (e.getResearchComponent() != null) {
                console.getRenderer().renderError("Already a researcher!");
            } else {
                e.setResearchComponent(new ResearchDecorator(e));
                console.getRenderer().renderSuccess(username + " is now a researcher!");
            }
        } else if (user instanceof Student) {
            Student s = (Student) user;
            if (s.getResearchComponent() != null) {
                console.getRenderer().renderError("Already a researcher!");
            } else {
                s.setResearchComponent(new ResearchDecorator(s));
                console.getRenderer().renderSuccess(username + " is now a researcher!");
            }
        } else {
            console.getRenderer().renderError("This user type cannot become a researcher.");
        }
        console.getInput().waitForEnter();
    }
}
