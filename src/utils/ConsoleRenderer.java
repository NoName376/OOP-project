package utils;

import java.util.List;

public class ConsoleRenderer {
    public ConsoleRenderer() {
    }

    public void renderHeader(String title) {
        System.out.println("\n".repeat(20));
        System.out.println("=".repeat(40));
        System.out.println("  " + title.toUpperCase());
        System.out.println("=".repeat(40));
    }

    public void renderMenu(List<String> options) {
        for (int i = 0; i < options.size(); i++) {
            System.out.println(" [" + (i + 1) + "] " + options.get(i));
        }
        System.out.println(" [0] Close");
        System.out.println("-".repeat(40));
    }

    public void renderMessage(String message) {
        System.out.println(" >> " + message);
    }

    public void renderError(String error) {
        System.err.println(" !! ERROR: " + error);
    }

    public void renderSuccess(String message) {
        System.out.println(" ++ SUCCESS: " + message);
    }

    public void renderData(String label, Object value) {
        System.out.printf(" %-15s: %s\n", label, value);
    }

    public void renderDivider() {
        System.out.println("-".repeat(40));
    }
}
