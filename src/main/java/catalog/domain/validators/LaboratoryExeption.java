package catalog.domain.validators;

/**
 * Created by radu.
 */
public class LaboratoryExeption extends RuntimeException{

    public LaboratoryExeption(String message) {
        super(message);
    }

    public LaboratoryExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public LaboratoryExeption(Throwable cause) {
        super(cause);
    }
}
