package parsers;

public class StringParser implements IInputParser<String> {
    public StringParser(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }

    @Override
    public String parse(String input) throws IllegalArgumentException {
        String trimmed = input.trim();
        if (!allowEmpty && trimmed.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty");
        }
        return trimmed;
    }

    private final boolean allowEmpty;
}
