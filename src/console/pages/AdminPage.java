package console.pages;

import console.pagescore.*;
import core.UniversityKernel;

import users.*;
import infrastructure.NewsEntry;
import parsers.*;
import java.time.LocalDate;
import research.ResearchDecorator;

public class AdminPage extends Page {
    public AdminPage() {
        super("Admin Panel");
        addAction("Manage Users", () -> new UserManagementPage().display());
        addAction("Manage Courses", () -> new CourseManagementPage().display());
        addAction("Manage News", this::sendNews);
        addAction("View Logs", this::viewLogs);
        addAction("System Statistics", this::viewStats);
        addAction("Save Changes to Database", this::saveToDB);
        addAction("Logout", () -> console.logout());
    }

    private void saveToDB() {
        utils.DataStorage.save();
        console.getRenderer().renderSuccess("Database updated successfully!");
        console.getInput().waitForEnter();
    }

    private void viewStats() {
        console.getRenderer().renderHeader("System Statistics");
        var kernel = UniversityKernel.getInstance();
        console.getRenderer().renderData("Total Users", kernel.getUsers().size());
        console.getRenderer().renderData("Total Courses", kernel.getCourses().size());
        console.getRenderer().renderData("News Entries", kernel.getNews().size());
        console.getInput().waitForEnter();
    }

    private void sendNews() {
        String title = console.getInput().read("Title", new StringParser(false));
        String content = console.getInput().read("Content", new StringParser(false));
        
        UniversityKernel.getInstance().getNews().add(new NewsEntry(title, content));
        console.getRenderer().renderSuccess("News published successfully!");
        console.getInput().waitForEnter();
    }

    private void viewLogs() {
        console.getRenderer().renderHeader("System Logs");
        UniversityKernel.getInstance().getLogger().getLogs()
                .forEach(log -> console.getRenderer().renderMessage(log));

        console.getInput().waitForEnter();
    }
}