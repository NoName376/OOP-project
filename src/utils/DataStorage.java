package utils;

import core.UniversityKernel;
import java.io.*;

public class DataStorage {
    public static void save() {
        File dir = new File("database");
        if (!dir.exists())
            dir.mkdirs();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(dir, "data")))) {
            oos.writeObject(UniversityKernel.getInstance());
        } catch (IOException e) {
            System.err.println("Database save error: " + e.getMessage());
        }
    }

    public static void load() {
        File file = new File("database/data");

        if (!file.exists()) {
            System.err.println("File does no exist");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            UniversityKernel loadedKernel = (UniversityKernel) ois.readObject();
            UniversityKernel.setInstance(loadedKernel);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Database load error: " + e.getMessage() + ". Fresh data will be populated.");
        }
    }
}
