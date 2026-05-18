package console.pages;

import console.pagescore.Page;
import users.Student;
import academic.Course;
import java.util.List;

public class MyProfilePage extends Page {
    public MyProfilePage() {
        super("My Profile Cabinet");
        addAction("Personal Info", this::viewPersonalInfo);
        addAction("Current Enrolled Courses", this::viewCurrentCourses);
        addAction("Academic Status", this::viewStatus);
    }

    private void viewPersonalInfo() {
        Student student = (Student) console.getCurrentUser();
        console.getRenderer().renderHeader("Personal Information");
        console.getRenderer().renderData("Full Name", student.getFullName());
        console.getRenderer().renderData("ID", student.getId());
        console.getRenderer().renderData("Username", student.getUsername());
        console.getRenderer().renderData("Email", student.getEmail());
        console.getInput().waitForEnter();
    }

    private void viewCurrentCourses() {
        Student student = (Student) console.getCurrentUser();
        List<Course> courses = student.viewCourses();
        console.getRenderer().renderHeader("Currently Enrolled Courses");
        if (courses.isEmpty()) {
            console.getRenderer().renderMessage("You are not enrolled in any courses.");
        } else {
            for (Course c : courses) {
                console.getRenderer().renderData(c.getCourseId(), c.getName() + " (" + c.getCredits() + " credits)");
            }
        }
        console.getInput().waitForEnter();
    }

    private void viewStatus() {
        Student student = (Student) console.getCurrentUser();
        console.getRenderer().renderHeader("Academic Status");
        console.getRenderer().renderData("Degree", student.getDegreeType().toString());
        console.getRenderer().renderData("Year of Study", String.valueOf(student.getYearOfStudy()));
        console.getRenderer().renderData("Total Credits Earned", String.valueOf(student.getTotalCredits()));
        console.getRenderer().renderData("Current GPA", String.format("%.2f", student.getGpa()));
        console.getInput().waitForEnter();
    }
}
