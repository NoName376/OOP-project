package console.pages;

import core.UniversityKernel;
import users.User;
import console.pagescore.*;
import parsers.*;

public class LoginPage extends Page {
    public LoginPage() {
        super("System Login");

        addAction("Login", this::handleLogin);
    }

    private void handleLogin() {
        String username = console.getInput().read("Username", new StringParser(false));
        String password = console.getInput().read("Password", new StringParser(false));

        User user = UniversityKernel.getInstance().findUserByUsername(username);

        if (user != null && user.login(password)) {
            console.getRenderer().renderSuccess("Welcome, " + user.getFullName());
            console.login(user);
        } else {
            console.getRenderer().renderError("Invalid credentials");
        }
    }
}

