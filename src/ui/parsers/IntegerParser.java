package ui.parsers;

public class IntegerParser implements IInputParser<Integer> {
    public IntegerParser(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public Integer parse(String input) throws IllegalArgumentException {
        try {
            int value = Integer.parseInt(input.trim());
            if (value < min || value > max) {
                throw new IllegalArgumentException("Value must be between " + min + " and " + max);
            }
            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format");
        }
    }

    private final int min;
    private final int max;
}
