package console.pages;

import console.pagescore.Page;

public class ManagerPage extends Page {
    public ManagerPage() {
        super("Manager");
        addAction("Logout", () -> console.logout());
    }
}