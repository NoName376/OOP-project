package console.pages;

import console.pagescore.Page;

import users.Student;
import academic.Course;
import academic.Mark;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class StudentDashboardPage extends Page {
    public StudentDashboardPage() {
        super("WSP Desktop");
        addAction("My Cabinet", () -> new MyProfilePage().display());
        addAction("My Schedule", () -> new SchedulePage().display());
        addAction("Course Registration", () -> new CourseRegistrationPage().display());
        addAction("Course Catalog", this::viewCatalog);
        addAction("Academic Progress", this::viewProgress);
        addAction("View Transcript", this::viewTranscript);
        addAction("Get Transcript", this::getTranscript);
        addAction("My Teachers", this::viewTeachers);
        addAction("Researching", this::handleResearch);
        addAction("Research Supervisor", this::assignSupervisor);
        addAction("View Attendance", this::viewAttendance);
        addAction("News", () -> new NewsPage().display());
        addAction("Logout", () -> console.logout());
    }

    private void handleResearch() {
        Student student = (Student) console.getCurrentUser();
        if (student.getResearchComponent() != null) {
            new ResearchCabinetPage().display();
        } else {
            console.getRenderer().renderHeader("Researcher Application");
            console.getRenderer().renderMessage("You are not currently a researcher. Do you want to apply?");
            console.getRenderer().renderMenu(java.util.List.of("Yes, I want to become a researcher", "No, back"));
            int choice = console.getInput().read("Option", new parsers.IntegerParser(0, 2));
            if (choice == 1) {
                student.setResearchComponent(new research.ResearchDecorator(student));
                console.getRenderer().renderSuccess("Congratulations! You are now a researcher.");
                console.getInput().waitForEnter();
            }
        }
    }

    private void assignSupervisor() {
        Student student = (Student) console.getCurrentUser();
        if (student.getYearOfStudy() < 4) {
            console.getRenderer().renderError("Only 4th year students can have a research supervisor.");
            console.getInput().waitForEnter();
            return;
        }

        List<users.User> users = core.UniversityKernel.getInstance().getUsers();
        List<research.IResearcher> researchers = new ArrayList<>();
        for (users.User u : users) {
            if (u instanceof research.IResearcher && u != student) {
                researchers.add((research.IResearcher) u);
            }
        }

        if (researchers.isEmpty()) {
            console.getRenderer().renderMessage("No researchers found in the university.");
            console.getInput().waitForEnter();
            return;
        }

        console.getRenderer().renderHeader("Select Research Supervisor");
        for (int i = 0; i < researchers.size(); i++) {
            research.IResearcher r = researchers.get(i);
            String name = (r instanceof users.User) ? ((users.User) r).getFullName() : "Unknown";
            console.getRenderer().renderData((i + 1) + ". " + name, "h-index: " + r.getHIndex());
        }

        int choice = console.getInput().read("Select supervisor (0 to cancel)", new parsers.IntegerParser(0, researchers.size()));
        if (choice == 0) return;

        research.IResearcher selected = researchers.get(choice - 1);
        try {
            student.setResearchSupervisor(selected);
            console.getRenderer().renderMessage("Successfully assigned supervisor: " + ((users.User) selected).getFullName());
        } catch (exceptions.IndexTooLowException e) {
            console.getRenderer().renderError(e.getMessage());
        }
        console.getInput().waitForEnter();
    }

    private void viewAttendance() {
        Student student = (Student) console.getCurrentUser();
        List<Course> courses = student.viewCourses();
        console.getRenderer().renderHeader("My Attendance");
        if (courses.isEmpty()) {
            console.getRenderer().renderMessage("No courses registered.");
        } else {
            for (Course c : courses) {
                console.getRenderer().renderData(c.getName(), "Lessons attended: " + student.getAttendance(c));
            }
        }
        console.getInput().waitForEnter();
    }

    private void viewProgress() {
        Student student = (Student) console.getCurrentUser();
        console.getRenderer().renderHeader("Academic Progress");
        console.getRenderer().renderData("Degree", student.getDegreeType().toString());
        console.getRenderer().renderData("Year of Study", String.valueOf(student.getYearOfStudy()));
        console.getRenderer().renderData("GPA", String.format("%.2f", student.getGpa()));
        console.getRenderer().renderData("Credits Earned", String.valueOf(student.getTotalCredits()));
        console.getRenderer().renderData("Failures Count", String.valueOf(student.getFailedCount()));
        console.getInput().waitForEnter();
    }

    private void getTranscript() {
        Student student = (Student) console.getCurrentUser();
        console.getRenderer().renderHeader("OFFICIAL TRANSCRIPT");
        console.getRenderer().renderMessage("Generating official document for " + student.getFullName() + "...");
        viewTranscript();
    }

    private void viewCatalog() {
        List<Course> courses = core.UniversityKernel.getInstance().getCourses();
        console.getRenderer().renderHeader("University Course Catalog");
        if (courses.isEmpty()) {
            console.getRenderer().renderMessage("No courses found in the system.");
            console.getInput().waitForEnter();
            return;
        }

        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);
            console.getRenderer().renderData((i + 1) + ". " + c.getCourseId(), 
                String.format("%s | Credits: %d | Status: %s", c.getName(), c.getCredits(), c.getStatus()));
        }

        int choice = console.getInput().read("Select course to view details (0 to back)", new parsers.IntegerParser(0, courses.size()));
        if (choice == 0) return;

        Course selected = courses.get(choice - 1);
        console.getRenderer().renderHeader("Course Details: " + selected.getName());
        console.getRenderer().renderData("ID", selected.getCourseId());
        console.getRenderer().renderData("Credits", String.valueOf(selected.getCredits()));
        console.getRenderer().renderData("Status", selected.getStatus().toString());
        
        console.getRenderer().renderMessage("Instructors:");
        if (selected.getInstructors().isEmpty()) {
            console.getRenderer().renderMessage("  No instructors assigned yet.");
        } else {
            for (users.Teacher t : selected.getInstructors()) {
                console.getRenderer().renderData("  - " + t.getFullName(), 
                    "Title: " + t.getTitle() + " | Rating: " + String.format("%.1f", t.getAverageRating()));
            }
        }
        console.getInput().waitForEnter();
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


    private void viewTeachers() {
        Student student = (Student) console.getCurrentUser();
        List<Course> courses = student.viewCourses();
        if (courses.isEmpty()) {
            console.getRenderer().renderError("You have no courses yet.");
            return;
        }

        console.getRenderer().renderHeader("My Teachers");
        List<users.Teacher> allTeachers = new java.util.ArrayList<>();
        for (Course c : courses) {
            for (users.Teacher t : c.getInstructors()) {
                if (!allTeachers.contains(t)) {
                    allTeachers.add(t);
                    console.getRenderer().renderData(allTeachers.size() + ". " + t.getFullName(), 
                        "Title: " + t.getTitle() + " | Rating: " + String.format("%.1f", t.getAverageRating()));
                }
            }
        }

        if (allTeachers.isEmpty()) {
            console.getRenderer().renderMessage("No teachers assigned to your courses.");
            console.getInput().waitForEnter();
            return;
        }

        int choice = console.getInput().read("Select teacher to rate (0 to back)", new parsers.IntegerParser(0, allTeachers.size()));
        if (choice == 0) return;

        users.Teacher selected = allTeachers.get(choice - 1);
        int rating = console.getInput().read("Enter rating (1-10)", new parsers.IntegerParser(1, 10));
        student.rateTeacher(selected, rating);
        console.getRenderer().renderMessage("Thank you for your feedback!");
        console.getInput().waitForEnter();
    }
}
