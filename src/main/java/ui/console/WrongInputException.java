package ui.console;

public class WrongInputException extends RuntimeException{
    public WrongInputException() {
        super("Wrong input. Try again.");
    }

    public WrongInputException(String message) {
        super(message);
    }

    public WrongInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongInputException(Throwable cause) {
        super(cause);
    }
}
