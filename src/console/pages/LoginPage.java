package console.pages;

import console.pagescore.*;


public class LoginPage extends Page {
    public LoginPage() {
        super("Login");

        addAction("Login", this::handleLogin);

    }

    private void handleLogin() {

    }
}
