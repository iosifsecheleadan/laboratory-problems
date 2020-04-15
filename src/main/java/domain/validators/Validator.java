package domain.validators;

/**
 * Interface for Generic Domain Entity Validators
 * @param <T> generic Type
 */
public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
