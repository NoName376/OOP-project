package application;

import console.core.UniversityConsole;
import core.UniversityKernel;
import utils.DataStorage;
import utils.DatabaseSeeder;

public class Main {
    public static void main(String[] args) {
        DataStorage.load();

        if (UniversityKernel.getInstance().getUsers().isEmpty()) {
            DatabaseSeeder.seed();
            DataStorage.save();
        }

        UniversityConsole.getInstance().run();
    }
}