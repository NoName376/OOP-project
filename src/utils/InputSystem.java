package utils;

import java.util.Scanner;
import parsers.IInputParser;

public class InputSystem {
    public InputSystem() {
        this.scanner = new Scanner(System.in);
    }

    public <T> T read(String prompt, IInputParser<T> parser) {
        while (true) {
            System.out.print(prompt + ": ");
            String input = scanner.nextLine();
            try {
                return parser.parse(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public String readString(String prompt) {
        return read(prompt, new parsers.StringParser(false));
    }

    public void waitForEnter() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private final Scanner scanner;
}
