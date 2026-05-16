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
        List<academic.Lesson> schedule = student.getSchedule();

        console.getRenderer().renderHeader("My Weekly Schedule");
        if (schedule.isEmpty()) {
            console.getRenderer().renderMessage("You have no scheduled lessons.");
        } else {
            // Sort by day of week
            schedule.sort(java.util.Comparator.comparing(academic.Lesson::getDay));
            for (academic.Lesson lesson : schedule) {
                console.getRenderer().renderData(lesson.getDay().toString() + " " + lesson.getTime(), 
                    lesson.toString());
            }
        }
        console.getInput().waitForEnter();
    }
}
