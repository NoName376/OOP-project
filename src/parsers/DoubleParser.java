package parsers;

public class DoubleParser implements IInputParser<Double> {
    public DoubleParser() {
        this(Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public DoubleParser(double min, double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public Double parse(String input) throws IllegalArgumentException {
        try {
            double value = Double.parseDouble(input);
            if (value < min || value > max) {
                throw new IllegalArgumentException("Value must be between " + min + " and " + max);
            }
            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid double value: " + input);
        }
    }

    private final double min;
    private final double max;
}
