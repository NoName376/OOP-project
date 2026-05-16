package console.core;

import console.pages.AdminPage;
import console.pages.LoginPage;
import console.pages.StudentDashboardPage;
import console.pages.TeacherDashboardPage;
import utils.ConsoleRenderer;
import utils.InputSystem;
import users.User;
import utils.DataStorage;

public class UniversityConsole {
    private UniversityConsole() {
        this.input = new InputSystem();
        this.renderer = new ConsoleRenderer();
        this.running = true;
    }

    public static UniversityConsole getInstance() {
        if (instance == null) {
            instance = new UniversityConsole();
        }
        return instance;
    }

    public void run() {
        renderer.renderMessage("Welcome to WSP");
        while (running) {
            if (currentUser == null) {
                initialPage();
                if (currentUser == null) {
                    break;
                }
            } else {
                showRolePage(currentUser);
                if (currentUser != null && running) {
                    logout();
                }
            }
        }

        DataStorage.save();
        renderer.renderMessage("Data saved!");
    }

    public void login(User user) {
        this.currentUser = user;
    }

    public void logout() {
        this.currentUser = null;
    }
    public void exit() {
        this.running = false;
    }

    public User getCurrentUser() {
        return currentUser;
    }
    public InputSystem getInput() {
        return input;
    }
    public ConsoleRenderer getRenderer() {
        return renderer;
    }

    public boolean isRunning() {
        return running;
    }

    private void initialPage() {
        var loginPage = new LoginPage();

        loginPage.display();
    }
    private void showRolePage(User user) {
        String role = currentUser.getClass().getSimpleName();

        switch (role) {
            case "Admin":
                new AdminPage().display();
                break;
            case "Student":
                new StudentDashboardPage().display();
                break;
            case "Teacher":
                new TeacherDashboardPage().display();
                break;

            default:
                renderer.renderError("Page for " + role + " not implemented.");
                logout();
                break;
        }
    }

    private static UniversityConsole instance;
    private final InputSystem input;
    private final ConsoleRenderer renderer;
    private User currentUser;
    private boolean running;
}
