package console.pages;

import console.pagescore.Page;

import users.Student;
import academic.Course;

public class SchedulePage extends Page {
    public SchedulePage() {
        super("Student's Schedule");

        addAction("View Schedule", this::viewSchedule);
    }

    private void viewSchedule() {
        if (!(console.getCurrentUser() instanceof Student)) {
            console.getRenderer().renderError("Only students have a schedule.");
            return;
        }

        Student student = (Student) console.getCurrentUser();
        var courses = student.viewCourses();

        console.getRenderer().renderHeader("Current Courses");
        if (courses.isEmpty()) {
            console.getRenderer().renderMessage("You are not registered for any courses.");
        } else {
            for (Course course : courses) {
                console.getRenderer().renderData(course.getCourseId(), course.getName() + " (" + course.getCredits() + " credits)");
            }
        }
        console.getInput().waitForEnter();
    }
}
