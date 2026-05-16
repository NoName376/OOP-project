package users;

import academic.Course;
import academic.Mark;
import academic.Transcript;
import exceptions.CreditLimitExceededException;
import exceptions.IndexTooLowException;
import java.util.ArrayList;
import java.util.List;
import research.IResearcher;

public class Student extends User {
    public Student(String id, String username, String passwordHash, String firstName, String lastName, String email,
                   DegreeType degreeType, int yearOfStudy) {
        super(id, username, passwordHash, firstName, lastName, email);
        this.degreeType = degreeType;
        this.yearOfStudy = yearOfStudy;
        this.transcript = new Transcript();
        this.currentCourses = new ArrayList<Course>();
        this.totalCredits = 0;
        this.failedCount = 0;
        this.gpa = 0.0;
    }

    public boolean registerForCourse(Course c) throws CreditLimitExceededException {
        if (totalCredits + c.getCredits() > 21) {
            throw new CreditLimitExceededException("Credit limit exceeded (max 21 credits)");
        }
        currentCourses.add(c);
        totalCredits += c.getCredits();
        return true;
    }

    public List<Course> viewCourses() {
        return currentCourses;
    }

    public List<Mark> viewMarks() {
        return new ArrayList<>(transcript.getRecords().values());
    }

    public void rateTeacher(Teacher t, int score) {
    }

    public void setResearchSupervisor(IResearcher supervisor) throws IndexTooLowException {
        if (supervisor != null && supervisor.getHIndex() < 3) {
            throw new IndexTooLowException("Supervisor h-index must be at least 3 for " + getFullName());
        }
        this.researchSupervisor = supervisor;
    }

    public IResearcher getResearchSupervisor() {
        return researchSupervisor;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public DegreeType getDegreeType() {
        return degreeType;
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public double getGpa() {
        return gpa;
    }

    private DegreeType degreeType;
    private int yearOfStudy;
    private double gpa;
    private int totalCredits;
    private int failedCount;
    private Transcript transcript;
    private List<Course> currentCourses;
    private IResearcher researchSupervisor;
}
