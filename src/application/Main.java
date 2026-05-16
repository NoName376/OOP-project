package application;

import core.UniversityKernel;
import console.core.UniversityConsole;
import users.Admin;
import utils.DataStorage;

public class Main {
    public static void main(String[] args) {
        DataStorage.load();

        if (UniversityKernel.getInstance().getUsers().isEmpty()) {
            Admin defaultAdmin = new Admin(
                "ADM-001",
                "admin",
                "admin",
                "System",
                "Administrator",
                "admin@university.edu"
            );
            UniversityKernel.getInstance().getUsers().add(defaultAdmin);
            DataStorage.save();
        }

        UniversityConsole.getInstance().run();
    }
}
