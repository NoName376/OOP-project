package users;

import academic.Course;
import academic.Mark;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import research.IResearcher;
import research.ResearchDecorator;
import research.ResearchPaper;
import core.UniversityKernel;

public class Teacher extends Employee {
    public Teacher(String id, String username, String passwordHash, String firstName, String lastName, String email,
                   double salary, LocalDate hireDate, String department, TeacherTitle title) {
        super(id, username, passwordHash, firstName, lastName, email, salary, hireDate, department);
        this.title = title;
        this.courses = new ArrayList<>();
        if (title == TeacherTitle.PROFESSOR) {
            this.researchComponent = new ResearchDecorator(this);
        }
    }

    public void putMark(Student s, Course c, Mark m) {
        s.addMark(c, m);
        System.out.println("Mark put for student " + s.getFullName() + " in course " + c.getName());
    }

    public List<Course> viewCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    public List<Student> viewStudents(String courseId) {
        return UniversityKernel.getInstance().getUsers().stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .filter(s -> s.viewCourses().stream()
                        .anyMatch(c -> c.getCourseId().equals(courseId)))
                .collect(java.util.stream.Collectors.toList());
    }

    public void sendComplaint(Student target, int urgency) {
        System.out.println("Complaint sent against " + target.getFullName() + " with urgency " + urgency);
    }

    public void addRating(int score) {
        this.ratings.add(score);
    }

    public double getAverageRating() {
        if (ratings.isEmpty()) return 0.0;
        return ratings.stream().mapToInt(Integer::intValue).average().orElse(0.0);
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
    private List<Integer> ratings = new ArrayList<>();
}
