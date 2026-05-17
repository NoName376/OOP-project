package console.pages;

import console.pagescore.*;

public class AdminPage extends Page {
    public AdminPage() {
        super("Admin");
        addAction("Back / Logout", () -> console.logout());
    }
}