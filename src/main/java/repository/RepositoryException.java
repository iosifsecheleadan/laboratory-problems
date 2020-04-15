package repository;

import domain.validators.LaboratoryException;

/**
 * Custom LaboratoryException
 * @author vinczi
 */
public class RepositoryException
        extends LaboratoryException {
    public RepositoryException(String message) { super(message); }

    public RepositoryException(String message, Throwable cause) { super(message, cause); }

    public RepositoryException(Throwable cause) { super(cause); }
}
