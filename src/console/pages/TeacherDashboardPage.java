package console.pages;

import academic.Course;
import academic.Mark;
import console.pagescore.Page;
import core.UniversityKernel;
import parsers.IntegerParser;
import parsers.StringParser;
import users.Student;
import users.Teacher;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TeacherDashboardPage extends Page {

    public TeacherDashboardPage() {
        super("Teacher Dashboard");
        addAction("University News",       () -> new NewsPage().display());
        addAction("My Courses",            this::viewCourses);
        addAction("View Students",         this::viewStudents);
        addAction("Put / Update Marks",    this::manageMarks);
        addAction("Research",              this::openResearch);
        addAction("Send Complaint",        this::sendComplaint);
        addAction("Logout",                () -> console.logout());
    }

    private Teacher me() {
        return (Teacher) console.getCurrentUser();
    }

    private void viewCourses() {
        console.getRenderer().renderHeader("My Courses");

        List<Course> courses = me().viewCourses();
        if (courses.isEmpty()) {
            console.getRenderer().renderMessage("You are not assigned to any courses yet.");
        } else {
            for (Course c : courses) {
                long enrolled = UniversityKernel.getInstance().getUsers().stream()
                        .filter(u -> u instanceof Student)
                        .map(u -> (Student) u)
                        .filter(s -> s.viewCourses().contains(c))
                        .count();
                console.getRenderer().renderData(c.getName(),
                        c.getCourseId() + "  |  " + c.getCredits() + " cr"
                                + "  |  " + c.getStatus()
                                + "  |  Students: " + enrolled);
            }
        }
        console.getInput().waitForEnter();
    }


    private void viewStudents() {
        console.getRenderer().renderHeader("View Students");

        List<Course> courses = me().viewCourses();
        if (courses.isEmpty()) {
            console.getRenderer().renderMessage("You have no courses assigned.");
            console.getInput().waitForEnter();
            return;
        }

        for (int i = 0; i < courses.size(); i++) {
            console.getRenderer().renderMessage("[" + (i + 1) + "] " + courses.get(i).getName());
        }
        int cIdx = console.getInput().read(
                "Select course (0 = all)", new IntegerParser(0, courses.size()));

        List<Student> students;
        String courseLabel;
        if (cIdx == 0) {
            students = me().viewCourses().stream()
                    .flatMap(c -> me().viewStudents(c.getCourseId()).stream())
                    .distinct()
                    .collect(Collectors.toList());
            courseLabel = "All courses";
        } else {
            Course chosen = courses.get(cIdx - 1);
            students = me().viewStudents(chosen.getCourseId());
            courseLabel = chosen.getName();
        }

        console.getRenderer().renderHeader("Students – " + courseLabel);
        if (students.isEmpty()) {
            console.getRenderer().renderMessage("No students enrolled.");
        } else {
            for (Student s : students) {
                Mark m = (cIdx > 0)
                        ? s.getTranscript().getRecords().get(courses.get(cIdx - 1))
                        : null;
                String markStr = (m == null) ? "no mark" : "Total: " + m.getTotal();
                console.getRenderer().renderData(s.getFullName(),
                        "Year " + s.getYearOfStudy()
                                + "  |  GPA: " + String.format("%.2f", s.getGpa())
                                + "  |  " + markStr);
            }
        }
        console.getInput().waitForEnter();
    }

    private void manageMarks() {
        console.getRenderer().renderHeader("Put / Update Marks");

        List<Course> courses = me().viewCourses();
        if (courses.isEmpty()) {
            console.getRenderer().renderError("You have no courses assigned.");
            console.getInput().waitForEnter();
            return;
        }

        for (int i = 0; i < courses.size(); i++) {
            console.getRenderer().renderMessage("[" + (i + 1) + "] " + courses.get(i).getName());
        }
        int cIdx = console.getInput().read("Select course", new IntegerParser(1, courses.size())) - 1;
        Course course = courses.get(cIdx);

        List<Student> enrolled = me().viewStudents(course.getCourseId());
        if (enrolled.isEmpty()) {
            console.getRenderer().renderError("No students enrolled in " + course.getName());
            console.getInput().waitForEnter();
            return;
        }

        for (int i = 0; i < enrolled.size(); i++) {
            Student s = enrolled.get(i);
            Mark existing = s.getTranscript().getRecords().get(course);
            String markStr = (existing == null) ? "no mark yet" : "total: " + existing.getTotal();
            console.getRenderer().renderMessage("[" + (i + 1) + "] " + s.getFullName() + "  (" + markStr + ")");
        }
        int sIdx = console.getInput().read("Select student", new IntegerParser(1, enrolled.size())) - 1;
        Student student = enrolled.get(sIdx);

        Mark current = student.getTranscript().getRecords().get(course);
        if (current != null) {
            console.getRenderer().renderMessage("Current marks  →  Att1: " + current.getFirstAttestation()
                    + "  Att2: " + current.getSecondAttestation()
                    + "  Final: " + current.getFinalExam()
                    + "  Total: " + current.getTotal());
        }

        double att1  = console.getInput().read("1st Attestation  (0–30)", new IntegerParser(0, 30));
        double att2  = console.getInput().read("2nd Attestation  (0–30)", new IntegerParser(0, 30));
        double finalE= console.getInput().read("Final Exam       (0–40)", new IntegerParser(0, 40));

        Mark mark = new Mark(att1, att2, finalE);
        me().putMark(student, course, mark);

        console.getRenderer().renderSuccess(
                "Mark saved for " + student.getFullName()
                        + "  →  Total: " + mark.getTotal() + "  " + gradeLabel(mark.getTotal()));
    }


    private void openResearch() {
        if (me().getResearchComponent() == null) {
            console.getRenderer().renderError(
                    "Research is available to Professors only. Your title: " + me().getTitle());
            console.getInput().waitForEnter();
            return;
        }
    }

    private void sendComplaint() {
        console.getRenderer().renderHeader("Send Complaint");

        List<Student> myStudents = me().viewCourses().stream()
                .flatMap(c -> me().viewStudents(c.getCourseId()).stream())
                .distinct()
                .collect(Collectors.toList());

        if (myStudents.isEmpty()) {
            console.getRenderer().renderError("You don't have any students enrolled in your courses.");
            console.getInput().waitForEnter();
            return;
        }

        for (int i = 0; i < myStudents.size(); i++) {
            console.getRenderer().renderMessage("[" + (i + 1) + "] "
                    + myStudents.get(i).getFullName()
                    + "  (Year " + myStudents.get(i).getYearOfStudy() + ")");
        }
        int sIdx = console.getInput().read("Select student", new IntegerParser(1, myStudents.size())) - 1;
        int urgency = console.getInput().read("Urgency (1=low, 5=critical)", new IntegerParser(1, 5));

        me().sendComplaint(myStudents.get(sIdx), urgency);
        console.getRenderer().renderSuccess("Complaint submitted and logged.");
        console.getInput().waitForEnter();
    }

    private String gradeLabel(double total) {
        if (total >= 90) return "A";
        if (total >= 80) return "B";
        if (total >= 70) return "C";
        if (total >= 60) return "D";
        if (total >= 50) return "E";
        return "F (FAIL)";
    }
}