package catalog.domain.validators;

import catalog.domain.Student;

/**
 * @author radu.
 */
public class StudentValidator implements Validator<Student> {
    @Override
    public void validate(Student entity) throws ValidatorException {

        try {
            entity.getSerialNumber().charAt(0);
        } catch (StringIndexOutOfBoundsException e) {
            throw new ValidatorException("Serial Number Not Set!");
        }

        try {
            entity.getName().charAt(0);
        } catch (StringIndexOutOfBoundsException e) {
            throw new ValidatorException("Student Name Not Set!");
        }

        if(entity.getGroup() < 0)
            throw new ValidatorException("Group Not Set!");
    }
}