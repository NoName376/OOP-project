package console.pages;

import console.pagescore.Page;
import users.Student;
import academic.Course;
import academic.Mark;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ManagerPage extends Page {
    public ManagerPage() {
        super("Manager Dashboard");
        addAction("Academic Performance Report", this::academicReport);
        addAction("Course Statistics", this::courseStats);
        addAction("View Students by GPA", this::viewStudentsByGPA);
        addAction("Manage News", () -> new NewsPage().display());
        addAction("Logout", () -> console.logout());
    }

    private void courseStats() {
        console.getRenderer().renderHeader("Course Academic Performance");
        List<Course> courses = core.UniversityKernel.getInstance().getCourses();
        if (courses.isEmpty()) {
            console.getRenderer().renderMessage("No courses found.");
        } else {
            for (Course c : courses) {
                List<Mark> marks = core.UniversityKernel.getInstance().getUsers().stream()
                    .filter(u -> u instanceof Student)
                    .map(u -> (Student) u)
                    .map(s -> s.getTranscript().getRecords().get(c))
                    .filter(m -> m != null)
                    .collect(Collectors.toList());

                double avg = marks.stream().mapToDouble(Mark::getTotal).average().orElse(0.0);
                console.getRenderer().renderData(c.getName(), 
                    String.format("Average Score: %.1f | Students: %d", avg, marks.size()));
            }
        }
        console.getInput().waitForEnter();
    }

    private void academicReport() {
        console.getRenderer().renderHeader("Academic Performance Statistics");
        List<users.User> users = core.UniversityKernel.getInstance().getUsers();
        List<Student> students = users.stream()
            .filter(u -> u instanceof Student)
            .map(u -> (Student) u)
            .collect(Collectors.toList());

        if (students.isEmpty()) {
            console.getRenderer().renderMessage("No students found.");
        } else {
            double avgGPA = students.stream().mapToDouble(Student::getGpa).average().orElse(0.0);
            long totalFails = students.stream().mapToInt(Student::getFailedCount).sum();
            
            console.getRenderer().renderData("Total Students", String.valueOf(students.size()));
            console.getRenderer().renderData("Average University GPA", String.format("%.2f", avgGPA));
            console.getRenderer().renderData("Total Failures (F)", String.valueOf(totalFails));
        }
        console.getInput().waitForEnter();
    }

    private void viewStudentsByGPA() {
        console.getRenderer().renderHeader("Students sorted by GPA");
        List<users.User> users = core.UniversityKernel.getInstance().getUsers();
        List<Student> students = users.stream()
            .filter(u -> u instanceof Student)
            .map(u -> (Student) u)
            .sorted((s1, s2) -> Double.compare(s2.getGpa(), s1.getGpa()))
            .collect(Collectors.toList());

        for (Student s : students) {
            console.getRenderer().renderData(s.getFullName(), String.format("GPA: %.2f", s.getGpa()));
        }
        console.getInput().waitForEnter();
    }
}
