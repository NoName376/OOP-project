package ui.parsers;

public interface IInputParser<T> {
    T parse(String input) throws IllegalArgumentException;
}
