package ui.tcp.common;

import domain.validators.LaboratoryException;

/**
 * Custom LaboratoryException
 * @author sechelea
 */
public class MessageException extends LaboratoryException {
    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }
}
