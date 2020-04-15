package domain.validators;

/**
 * Custom LaboratoryException
 * @author vinczi
 */
public class ValidatorException extends LaboratoryException {
    public ValidatorException(String message) {
        super(message);
    }

    public ValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidatorException(Throwable cause) {
        super(cause);
    }
}
