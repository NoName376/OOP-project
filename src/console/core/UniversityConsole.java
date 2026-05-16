package console.core;

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
                InitialPage();
            } else {
                showRolePage(currentUser);
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

    private void InitialPage() {
        // страница если не авторизован челик
    }
    private void showRolePage(User user) {
        String role = currentUser.getClass().getSimpleName();

        // показать страницу с ролью как-то
    }

    private static UniversityConsole instance;
    private final InputSystem input;
    private final ConsoleRenderer renderer;
    private User currentUser;
    private boolean running;
}
