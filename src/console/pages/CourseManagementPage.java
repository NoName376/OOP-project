package console.pages;

import academic.Course;
import academic.CourseStatus;
import console.pagescore.Page;
import core.UniversityKernel;
import users.Teacher;
import users.User;
import parsers.*;
import java.util.List;
import java.util.stream.Collectors;
import research.*;

public class CourseManagementPage extends Page {
    public CourseManagementPage() {
        super("Course Management");
        addAction("List All Courses", this::listCourses);
        addAction("Filter by Year", this::filterByYear);
        addAction("Add Course", this::addCourse);
        addAction("Delete Course by ID", this::deleteCourse);
    }

    private void listCourses() {
        renderCourseList(UniversityKernel.getInstance().getCourses(), "All Courses");
    }

    private void filterByYear() {
        int year = console.getInput().read("Enter Year (1-4)", new IntegerParser(1, 4));
        List<Course> courses = UniversityKernel.getInstance().getCourses().stream()
                .filter(c -> c.getTargetYear() == year)
                .collect(Collectors.toList());
        renderCourseList(courses, "Courses for Year " + year);
    }

    private void renderCourseList(List<Course> courses, String header) {
        console.getRenderer().renderHeader(header);
        if (courses.isEmpty()) {
            console.getRenderer().renderMessage("No courses found.");
        } else {
            for (Course c : courses) {
                console.getRenderer().renderData(c.getCourseId(), c.getName() + " (" + c.getCredits() + " credits) [Year: " + c.getTargetYear() + "]");
            }
        }
        console.getInput().waitForEnter();
    }

    private void deleteCourse() {
        String id = console.getInput().read("Enter Course ID to delete", new StringParser(false));
        List<Course> courses = UniversityKernel.getInstance().getCourses();
        boolean removed = courses.removeIf(c -> c.getCourseId().equals(id));
        if (removed) {
            console.getRenderer().renderSuccess("Course " + id + " removed.");
        } else {
            console.getRenderer().renderError("Course not found.");
        }
        console.getInput().waitForEnter();
    }

    private void addCourse() {
        String id = console.getInput().read("Course ID", new StringParser(false));
        String name = console.getInput().read("Course Name", new StringParser(false));
        int credits = console.getInput().read("Credits", new IntegerParser(1, 10));
        int targetYear = console.getInput().read("Target Year (1-4)", new IntegerParser(1, 4));
        
        console.getRenderer().renderMessage("Status: 1-MAJOR, 2-MINOR, 3-ELECTIVE");
        int statusIdx = console.getInput().read("Select Status", new IntegerParser(1, 3));
        CourseStatus status = CourseStatus.values()[statusIdx - 1];

        Course course = new Course(id, name, credits, status, targetYear);

        while (true) {
            String instructorUsername = console.getInput().read("Add instructor by username (or 'done' to finish)", new StringParser(false));
            if (instructorUsername.equalsIgnoreCase("done")) break;
            
            User user = UniversityKernel.getInstance().findUserByUsername(instructorUsername);
            if (user instanceof Teacher) {
                course.addInstructor((Teacher) user);
                console.getRenderer().renderSuccess("Instructor added!");
            } else {
                console.getRenderer().renderError("User not found or is not a Teacher!");
            }
        }

        UniversityKernel.getInstance().getCourses().add(course);
        console.getRenderer().renderSuccess("Course created successfully!");
        console.getInput().waitForEnter();
    }
}
