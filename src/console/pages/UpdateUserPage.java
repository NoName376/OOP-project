package console.pages;

import console.pagescore.Page;
import core.UniversityKernel;
import users.User;
import parsers.StringParser;

public class UpdateUserPage extends Page {
    public UpdateUserPage() {
        super("Update User Profile");
        addAction("Change Name/Email", this::updateProfile);
    }

    private void updateProfile() {
        String id = console.getInput().read("Enter User ID", new StringParser(false));
        User u = UniversityKernel.getInstance().findUserById(id);
        
        if (u == null) {
            console.getRenderer().renderError("User not found!");
            console.getInput().waitForEnter();
            return;
        }

        console.getRenderer().renderHeader("Editing: " + u.getFullName());
        console.getRenderer().renderMessage("Tip: enter '~' to keep the current value");

        String newFirst = console.getInput().read("First Name (" + u.getFirstName() + ")", new StringParser(false));
        if (!newFirst.equals("~")) {
            u.setFirstName(newFirst);
        }

        String newLast = console.getInput().read("Last Name (" + u.getLastName() + ")", new StringParser(false));
        if (!newLast.equals("~")) {
            u.setLastName(newLast);
        }

        String newEmail = console.getInput().read("Email (" + u.getEmail() + ")", new StringParser(false));
        if (!newEmail.equals("~")) {
            u.setEmail(newEmail);
        }

        console.getRenderer().renderSuccess("User profile updated!");
        console.getInput().waitForEnter();
    }
}
