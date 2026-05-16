package users;

import academic.Course;
import academic.Mark;
import academic.Transcript;
import exceptions.CreditLimitExceededException;
import exceptions.IndexTooLowException;
import java.util.ArrayList;
import java.util.List;
import research.IResearcher;
import research.ResearchDecorator;
import research.ResearchPaper;

public class Student extends User implements IResearcher {
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
        if (totalCredits + c.getCredits() > 30) {
            throw new CreditLimitExceededException("Credit limit exceeded (max 30 credits)");
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
        t.addRating(score);
    }

    public int getTotalCredits() {
        return totalCredits;
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

    @Override
    public int getHIndex() {
        return researchComponent != null ? researchComponent.getHIndex() : 0;
    }

    @Override
    public List<ResearchPaper> getPapers() {
        return researchComponent != null ? researchComponent.getPapers() : new ArrayList<>();
    }

    @Override
    public void printPapers(java.util.Comparator<ResearchPaper> sorter) {
        if (researchComponent != null) researchComponent.printPapers(sorter);
    }

    @Override
    public void addPaper(ResearchPaper p) {
        if (researchComponent != null) researchComponent.addPaper(p);
    }

    public void setResearchComponent(ResearchDecorator researchComponent) {
        this.researchComponent = researchComponent;
    }

    public ResearchDecorator getResearchComponent() {
        return researchComponent;
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

    public void addMark(Course c, Mark m) {
        transcript.addRecord(c, m);
        if (m.getTotal() < 50) {
            failedCount++;
        }
    }

    public int getFailedCount() {
        return failedCount;
    }

    public double getGpa() {
        return transcript.calculateGPA();
    }

    public void addAttendance(Course c) {
        attendance.put(c, attendance.getOrDefault(c, 0) + 1);
    }

    public int getAttendance(Course c) {
        return attendance.getOrDefault(c, 0);
    }

    private DegreeType degreeType;
    private int yearOfStudy;
    private double gpa;
    private int totalCredits;
    private int failedCount;
    private Transcript transcript;
    private List<Course> currentCourses;
    private IResearcher researchSupervisor;
    private ResearchDecorator researchComponent;
    private java.util.Map<Course, Integer> attendance = new java.util.HashMap<>();
}
