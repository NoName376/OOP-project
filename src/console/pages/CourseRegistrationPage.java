package console.pages;

import academic.Course;
import console.pagescore.Page;
import core.UniversityKernel;
import exceptions.CreditLimitExceededException;
import java.util.List;
import java.util.stream.Collectors;
import parsers.IntegerParser;
import users.Student;

public class CourseRegistrationPage extends Page {
    public CourseRegistrationPage() {
        super("Course Registration");
        addAction("Show Available Courses", this::showAvailableCourses);
        addAction("Register for Course", this::registerForCourse);
    }

    public void showAvailableCourses() {
        Student student = (Student) console.getCurrentUser();
        List<Course> available = UniversityKernel.getInstance().getCourses().stream()
                .filter(c -> !student.viewCourses().contains(c))
                .collect(Collectors.toList());

        console.getRenderer().renderHeader("Available Courses");
        if (available.isEmpty()) {
            console.getRenderer().renderMessage("No courses available for registration.");
        } else {
            for (int i = 0; i < available.size(); i++) {
                Course c = available.get(i);
                console.getRenderer().renderData((i + 1) + ". " + c.getName(), 
                    c.getCredits() + " credits | Year: " + c.getTargetYear());
            }
        }
        console.getInput().waitForEnter();
    }

    public void registerForCourse() {
        Student student = (Student) console.getCurrentUser();
        if (student.getFailedCount() >= 3) {
            console.getRenderer().renderError("You have failed 3 or more courses. Registration blocked.");
            console.getInput().waitForEnter();
            return;
        }

        List<Course> available = UniversityKernel.getInstance().getCourses().stream()
                .filter(c -> !student.viewCourses().contains(c))
                .collect(Collectors.toList());

        if (available.isEmpty()) {
            console.getRenderer().renderError("No courses available.");
            return;
        }

        console.getRenderer().renderHeader("Register for Course");
        for (int i = 0; i < available.size(); i++) {
            console.getRenderer().renderData((i + 1) + ". " + available.get(i).getName(), "");
        }

        int choice = console.getInput().read("Select course number (0 to cancel)", new IntegerParser(0, available.size()));
        if (choice == 0) return;

        Course selected = available.get(choice - 1);
        try {
            student.registerForCourse(selected);
            console.getRenderer().renderMessage("Successfully registered for " + selected.getName());
        } catch (CreditLimitExceededException e) {
            console.getRenderer().renderError(e.getMessage());
        }
        console.getInput().waitForEnter();
    }
}
