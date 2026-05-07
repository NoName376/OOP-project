package users;

import academic.Course;
import academic.Mark;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import research.IResearcher;
import research.ResearchDecorator;
import research.ResearchPaper;

public class Teacher extends Employee implements IResearcher {
    public Teacher(String id, String username, String passwordHash, String firstName, String lastName, String email,
                   double salary, LocalDate hireDate, String department, TeacherTitle title) {
        super(id, username, passwordHash, firstName, lastName, email, salary, hireDate, department);
        this.title = title;
        this.courses = new ArrayList<>();
        if (title == TeacherTitle.PROFESSOR) {
            this.researchComponent = new ResearchDecorator(this);
        }
    }

    @Override
    public int getHIndex() {
        return researchComponent != null ? researchComponent.getHIndex() : 0;
    }

    @Override
    public List<ResearchPaper> getPapers() {
        return researchComponent != null ? researchComponent.getPapers() : new ArrayList<>();
    }

    @Override
    public void printPapers(java.util.Comparator<ResearchPaper> sorter) {
        if (researchComponent != null) researchComponent.printPapers(sorter);
    }

    @Override
    public void addPaper(ResearchPaper p) {
        if (researchComponent != null) researchComponent.addPaper(p);
    }

    public void putMark(Student s, Course c, Mark m) {
        s.getTranscript().addRecord(c, m);
        System.out.println("Mark put for student " + s.getFullName() + " in course " + c.getName());
    }

    public List<Course> viewCourses() {
        return courses;
    }

    public List<Student> viewStudents(String courseId) {
        // Placeholder implementation
        return new ArrayList<>();
    }

    public void sendComplaint(Student target, int urgency) {
        System.out.println("Complaint sent against " + target.getFullName() + " with urgency " + urgency);
    }

    public void setResearchComponent(ResearchDecorator researchComponent) {
        this.researchComponent = researchComponent;
    }

    public ResearchDecorator getResearchComponent() {
        return researchComponent;
    }

    public TeacherTitle getTitle() { return title; }

    private TeacherTitle title;
    private List<Course> courses;
    private ResearchDecorator researchComponent;
}
