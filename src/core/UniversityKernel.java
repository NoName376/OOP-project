package core;

import academic.Course;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import users.User;

public class UniversityKernel implements Serializable {
    public static synchronized UniversityKernel getInstance() {
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

    public void logAction(User u, String action) {
        logger.log(u.getFullName() + " performed action: " + action);
    }

    public List<User> getUsers() { return users; }
    public List<Course> getCourses() { return courses; }
    public SystemLogger getLogger() { return logger; }

    private UniversityKernel() {
        users = new ArrayList<>();
        courses = new ArrayList<>();
        logger = new SystemLogger();
    }

    private static UniversityKernel instance;
    private List<User> users;
    private List<Course> courses;
    private SystemLogger logger;
}
