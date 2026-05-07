package utils;

import core.UniversityKernel;
import java.io.*;

public class DataStorage {
    public static void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(UniversityKernel.getInstance());
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            UniversityKernel loadedKernel = (UniversityKernel) ois.readObject();
            UniversityKernel.setInstance(loadedKernel);
            System.out.println("Data loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static final String FILE_NAME = "university_data.ser";
}
