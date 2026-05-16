package console.pages;

import academic.Lesson;
import console.pagescore.Page;

import users.Student;
import academic.Course;

import java.util.List;

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
                List<Lesson> lessons = course.getLessons();
                if (lessons.isEmpty()) {
                    console.getRenderer().renderData("  ", "No lessons scheduled yet.");
                } else {
                    for (academic.Lesson l : lessons) {
                        console.getRenderer().renderData("  " + l.getType(), l.getTopic() + " [" + l.getRoom() + "]");
                    }
                }
            }
        }
        console.getInput().waitForEnter();
    }
}
