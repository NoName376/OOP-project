package console.pages;

import console.pagescore.*;
import core.UniversityKernel;

public class AdminPage extends Page {
    public AdminPage() {
        super("Admin Panel");
        addAction("View Logs", this::viewLogs);
        addAction("Logout", () -> console.logout());
    }

    private void viewLogs() {
        console.getRenderer().renderHeader("System Logs");
        UniversityKernel.getInstance().getLogger().getLogs().forEach(log -> console.getRenderer().renderMessage(log));
        console.getInput().waitForEnter();
    }
}
