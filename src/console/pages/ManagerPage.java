package console.pages;

import console.pagescore.Page;
import core.UniversityKernel;
import infrastructure.Request;
import infrastructure.RequestStatus;
import users.Employee;
import users.Student;
import users.Teacher;
import users.User;
import academic.Course;
import academic.Mark;
import parsers.IntegerParser;
import parsers.StringParser;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ManagerPage extends Page {
    public ManagerPage() {
        super("Manager Dashboard");
        addAction("Academic Performance Report",  this::academicReport);
        addAction("Course Statistics",            this::courseStats);
        addAction("View Students by GPA",         this::viewStudentsByGPA);
        addAction("View Students Alphabetically", this::viewStudentsAlphabetically);
        addAction("View Teachers",                this::viewTeachers);
        addAction("Assign Course to Teacher",     this::assignCourseToTeacher);
        addAction("Approve Course Registrations", this::approveCourseRegistrations);
        addAction("View Employee Requests",       this::viewEmployeeRequests);
        addAction("Manage News",                  () -> new NewsPage().display());
        addAction("Logout",                       () -> console.logout());
    }


    private void courseStats() {
        console.getRenderer().renderHeader("Course Academic Performance");
        List<Course> courses = UniversityKernel.getInstance().getCourses();
        if (courses.isEmpty()) {
            console.getRenderer().renderMessage("No courses found.");
        } else {
            for (Course c : courses) {
                List<Mark> marks = UniversityKernel.getInstance().getUsers().stream()
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
        List<User> users = UniversityKernel.getInstance().getUsers();
        List<Student> students = users.stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .collect(Collectors.toList());

        if (students.isEmpty()) {
            console.getRenderer().renderMessage("No students found.");
        } else {
            double avgGPA   = students.stream().mapToDouble(Student::getGpa).average().orElse(0.0);
            long totalFails = students.stream().mapToInt(Student::getFailedCount).sum();

            console.getRenderer().renderData("Total Students",        String.valueOf(students.size()));
            console.getRenderer().renderData("Average University GPA", String.format("%.2f", avgGPA));
            console.getRenderer().renderData("Total Failures (F)",    String.valueOf(totalFails));
        }
        console.getInput().waitForEnter();
    }

    private void viewStudentsByGPA() {
        console.getRenderer().renderHeader("Students sorted by GPA");
        List<Student> students = UniversityKernel.getInstance().getUsers().stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .sorted((s1, s2) -> Double.compare(s2.getGpa(), s1.getGpa()))
                .collect(Collectors.toList());

        for (Student s : students) {
            console.getRenderer().renderData(s.getFullName(), String.format("GPA: %.2f", s.getGpa()));
        }
        console.getInput().waitForEnter();
    }


    private void viewStudentsAlphabetically() {
        console.getRenderer().renderHeader("Students – Alphabetical Order");
        List<Student> students = UniversityKernel.getInstance().getUsers().stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .sorted(Comparator.comparing(Student::getFullName))
                .collect(Collectors.toList());

        if (students.isEmpty()) {
            console.getRenderer().renderMessage("No students found.");
        } else {
            for (int i = 0; i < students.size(); i++) {
                Student s = students.get(i);
                console.getRenderer().renderData((i + 1) + ". " + s.getFullName(),
                        "GPA: " + String.format("%.2f", s.getGpa()) + " | Year: " + s.getYearOfStudy());
            }
        }
        console.getInput().waitForEnter();
    }


    private void viewTeachers() {
        console.getRenderer().renderHeader("View Teachers");
        console.getRenderer().renderMenu(java.util.List.of("Sort by Name", "Sort by Department"));
        int sortChoice = console.getInput().read("Sort option (0 to skip)", new IntegerParser(0, 2));

        List<Teacher> teachers = UniversityKernel.getInstance().getUsers().stream()
                .filter(u -> u instanceof Teacher)
                .map(u -> (Teacher) u)
                .collect(Collectors.toList());

        if (teachers.isEmpty()) {
            console.getRenderer().renderMessage("No teachers found.");
            console.getInput().waitForEnter();
            return;
        }

        if (sortChoice == 1) {
            teachers.sort(Comparator.comparing(Teacher::getFullName));
        } else if (sortChoice == 2) {
            teachers.sort(Comparator.comparing(Teacher::getDepartment));
        }

        console.getRenderer().renderHeader("Teachers");
        for (int i = 0; i < teachers.size(); i++) {
            Teacher t = teachers.get(i);
            console.getRenderer().renderData((i + 1) + ". " + t.getFullName(),
                    "Dept: " + t.getDepartment() + " | Title: " + t.getTitle()
                            + " | Rating: " + String.format("%.1f", t.getAverageRating()));
        }
        console.getInput().waitForEnter();
    }


    private void assignCourseToTeacher() {
        console.getRenderer().renderHeader("Assign Course to Teacher");

        List<Course> courses = UniversityKernel.getInstance().getCourses();
        List<Teacher> teachers = UniversityKernel.getInstance().getUsers().stream()
                .filter(u -> u instanceof Teacher)
                .map(u -> (Teacher) u)
                .collect(Collectors.toList());

        if (courses.isEmpty()) {
            console.getRenderer().renderError("No courses available.");
            console.getInput().waitForEnter();
            return;
        }
        if (teachers.isEmpty()) {
            console.getRenderer().renderError("No teachers available.");
            console.getInput().waitForEnter();
            return;
        }

        for (int i = 0; i < courses.size(); i++) {
            console.getRenderer().renderData("[" + (i + 1) + "] " + courses.get(i).getName(),
                    courses.get(i).getCourseId());
        }
        int cChoice = console.getInput().read("Select course (0 to cancel)", new IntegerParser(0, courses.size()));
        if (cChoice == 0) return;
        Course course = courses.get(cChoice - 1);

        for (int i = 0; i < teachers.size(); i++) {
            console.getRenderer().renderData("[" + (i + 1) + "] " + teachers.get(i).getFullName(),
                    teachers.get(i).getDepartment());
        }
        int tChoice = console.getInput().read("Select teacher (0 to cancel)", new IntegerParser(0, teachers.size()));
        if (tChoice == 0) return;
        Teacher teacher = teachers.get(tChoice - 1);

        course.addInstructor(teacher);
        console.getRenderer().renderSuccess(teacher.getFullName() + " assigned to " + course.getName());
        console.getInput().waitForEnter();
    }


    private void approveCourseRegistrations() {
        console.getRenderer().renderHeader("Approve Course Registrations");

        List<Student> students = UniversityKernel.getInstance().getUsers().stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .filter(s -> !s.viewCourses().isEmpty())
                .collect(Collectors.toList());

        if (students.isEmpty()) {
            console.getRenderer().renderMessage("No students have registered for any courses.");
            console.getInput().waitForEnter();
            return;
        }

        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            console.getRenderer().renderData("[" + (i + 1) + "] " + s.getFullName(),
                    "Courses: " + s.viewCourses().size() + " | Credits: " + s.getTotalCredits());
        }

        int sChoice = console.getInput().read("Select student to review (0 to cancel)", new IntegerParser(0, students.size()));
        if (sChoice == 0) return;
        Student student = students.get(sChoice - 1);

        List<Course> enrolled = student.viewCourses();
        console.getRenderer().renderHeader("Enrolled Courses for " + student.getFullName());
        for (int i = 0; i < enrolled.size(); i++) {
            Course c = enrolled.get(i);
            console.getRenderer().renderData("[" + (i + 1) + "] " + c.getName(),
                    c.getCourseId() + " | " + c.getCredits() + " credits");
        }

        int cChoice = console.getInput().read("Select course to revoke (0 to cancel)", new IntegerParser(0, enrolled.size()));
        if (cChoice == 0) return;

        Course toRevoke = enrolled.get(cChoice - 1);
        enrolled.remove(toRevoke);
        console.getRenderer().renderSuccess("Registration for " + toRevoke.getName()
                + " revoked for " + student.getFullName());
        console.getInput().waitForEnter();
    }


    private void viewEmployeeRequests() {
        console.getRenderer().renderHeader("Employee Requests");

        List<Employee> employees = UniversityKernel.getInstance().getUsers().stream()
                .filter(u -> u instanceof Employee)
                .map(u -> (Employee) u)
                .filter(e -> !e.getRequests().isEmpty())
                .collect(Collectors.toList());

        if (employees.isEmpty()) {
            console.getRenderer().renderMessage("No employee requests found.");
            console.getInput().waitForEnter();
            return;
        }

        List<Request> allRequests = new java.util.ArrayList<>();
        List<String> allLabels   = new java.util.ArrayList<>();
        for (Employee e : employees) {
            for (Request r : e.getRequests()) {
                allRequests.add(r);
                allLabels.add(e.getFullName() + " – " + r.getRequestId());
            }
        }

        for (int i = 0; i < allRequests.size(); i++) {
            Request r = allRequests.get(i);
            console.getRenderer().renderData("[" + (i + 1) + "] " + allLabels.get(i),
                    "Status: " + r.getStatus() + " | " + r.getContent());
        }

        int choice = console.getInput().read("Select request to advance status (0 to cancel)",
                new IntegerParser(0, allRequests.size()));
        if (choice == 0) return;

        Request selected = allRequests.get(choice - 1);
        switch (selected.getStatus()) {
            case PENDING -> {
                selected.updateStatus(RequestStatus.SIGNED_BY_DEAN);
                console.getRenderer().renderSuccess("Request advanced to SIGNED_BY_DEAN.");
            }
            case SIGNED_BY_DEAN -> {
                selected.updateStatus(RequestStatus.SIGNED_BY_RECTOR);
                console.getRenderer().renderSuccess("Request advanced to SIGNED_BY_RECTOR.");
            }
            case SIGNED_BY_RECTOR ->
                    console.getRenderer().renderMessage("This request is already fully signed.");
        }
        console.getInput().waitForEnter();
    }
}
