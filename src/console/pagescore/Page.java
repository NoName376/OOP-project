package console.pagescore;

import core.UniversityKernel;
import java.util.ArrayList;
import java.util.List;
import console.core.UniversityConsole;
import parsers.IntegerParser;
import users.User;

public abstract class Page {
    public Page(String title) {
        this.title = title;
        this.actions = new ArrayList<>();
        this.labels = new ArrayList<>();
        this.console = UniversityConsole.getInstance();
    }

    public void display() {
        while (console.isRunning()) {
            console.getRenderer().renderHeader(title);
            console.getRenderer().renderMenu(labels);

            int choice = console.getInput().read("Option", new IntegerParser(0, actions.size()));
            if (choice == 0)
                return;

            try {
                User userBefore = console.getCurrentUser();
                if (console.getCurrentUser() != null) {
                    UniversityKernel.getInstance().logAction(
                            console.getCurrentUser(),
                            "Page: " + title + " -> Action: " + labels.get(choice - 1)
                    );
                }
                actions.get(choice - 1).start();
                if (userBefore != console.getCurrentUser()) {
                    return;
                }
            } catch (Exception e) {
                console.getRenderer().renderError(e.getMessage());
            }
        }
    }

    protected void addAction(String label, IAction action) {
        labels.add(label);
        actions.add(action);
    }

    protected final String title;
    protected final List<IAction> actions;
    protected final List<String> labels;

    protected final UniversityConsole console;
}

