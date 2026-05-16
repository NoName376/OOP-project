package core;

import academic.Course;
import infrastructure.NewsEntry;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import users.User;

public class UniversityKernel implements Serializable {
    public static UniversityKernel getInstance() {
        if (instance == null) {
            instance = new UniversityKernel();
        }
        return instance;
    }

    public static void setInstance(UniversityKernel newInstance) {
        instance = newInstance;
    }

    public User findUserById(String id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public User findUserByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void logAction(User u, String action) {
        logger.log(u.getFullName() + " performed action: " + action);
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<NewsEntry> getNews() {
        return news;
    }

    public SystemLogger getLogger() {
        return logger;
    }

    public List<research.ResearchProject> getResearchProjects() {
        return researchProjects;
    }

    private UniversityKernel() {
        this.users = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.news = new ArrayList<>();
        this.researchProjects = new ArrayList<>();
        this.logger = new SystemLogger();
    }

    private static UniversityKernel instance;
    private List<User> users;
    private List<Course> courses;
    private List<NewsEntry> news;
    private List<research.ResearchProject> researchProjects;
    private SystemLogger logger;
}
