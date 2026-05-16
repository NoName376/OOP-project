package console.pages;

import console.pagescore.Page;

public class StudentDashboardPage extends Page {
    public StudentDashboardPage() {
        super("Student Dashboard");
        addAction("University News", () -> new NewsPage().display());
        addAction("Course Registration", () -> { System.out.println("registration"); });
        addAction("My Schedule", () -> new SchedulePage().display());
        addAction("Logout", () -> console.logout());
    }
}
