package console.pages;

import console.pagescore.Page;

import users.Student;
import academic.Course;
import academic.Mark;
import java.util.Map;

public class StudentDashboardPage extends Page {
    public StudentDashboardPage() {
        super("WSP Desktop");
        addAction("My Schedule", () -> new SchedulePage().display());
        addAction("View Transcript", this::viewTranscript);
        addAction("View GPA", this::viewGPA);
        addAction("News", () -> new NewsPage().display());
        addAction("Logout", () -> console.logout());
    }

    private void viewTranscript() {
        Student student = (Student) console.getCurrentUser();
        console.getRenderer().renderHeader("Academic Transcript");
        
        Map<Course, Mark> records = student.getTranscript().getRecords();
        if (records.isEmpty()) {
            console.getRenderer().renderMessage("No records found in transcript.");
        } else {
            for (var entry : records.entrySet()) {
                Course c = entry.getKey();
                Mark m = entry.getValue();
                console.getRenderer().renderData(c.getName(), 
                    String.format("1st: %.1f | 2nd: %.1f | Final: %.1f | Total: %.1f", 
                    m.getFirstAttestation(), m.getSecondAttestation(), m.getFinalExam(), m.getTotal()));
            }
        }
        console.getInput().waitForEnter();
    }

    private void viewGPA() {
        Student student = (Student) console.getCurrentUser();
        double gpa = student.getTranscript().calculateGPA();
        console.getRenderer().renderHeader("Academic Standing");
        console.getRenderer().renderData("Current GPA", String.format("%.2f", gpa));
        console.getInput().waitForEnter();
    }
}
