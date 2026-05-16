package console.pages;

import console.pagescore.Page;

public class SchedulePage extends Page {
    public SchedulePage() {
        super("Student's Schedule");

        addAction("View Schedue", () -> { console.getRenderer().renderMessage("Тут должно быть расписание"); });
        addAction("Back", () -> { });
    }
}
