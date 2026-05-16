package console.pages;

import console.pagescore.Page;

public class TeacherDashboardPage extends Page {
    public TeacherDashboardPage() {
        super("Teacher Dashboard");
        addAction("University News", () -> new NewsPage().display());
        addAction("View My Courses", () -> System.out.println("Courses..."));
        addAction("Manage Grades", () -> System.out.println("Grades..."));
        addAction("Logout", () -> console.logout());
    }
}

