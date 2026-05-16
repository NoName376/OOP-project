package academic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import users.Teacher;

public class Course implements Serializable {
    public Course(String courseId, String name, int credits, CourseStatus status, int targetYear) {
        this.courseId = courseId;
        this.name = name;
        this.credits = credits;
        this.status = status;
        this.targetYear = targetYear;
        this.instructors = new ArrayList<>();
    }

    public int getTargetYear() {
        return targetYear;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public List<Teacher> getInstructors() {
        return instructors;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public void addInstructor(Teacher t) {
        this.instructors.add(t);
    }

    @Override
    public String toString() {
        return "Course [" + courseId + ": " + name + "]";
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void addLesson(Lesson l) {
        this.lessons.add(l);
    }

    private String courseId;
    private String name;
    private int credits;
    private CourseStatus status;
    private List<Teacher> instructors;
    private List<Lesson> lessons = new ArrayList<>();
    private String syllabus;
    private int targetYear;
}
