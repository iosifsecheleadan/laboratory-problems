package domain.validators;

import domain.entities.Student;

/**
 * @author vinczi
 */
public class StudentValidator implements Validator<Student> {
    /**
     * Check if Student is valid
     * @param student Student
     * @throws ValidatorException If Student does not have: name and serial number set, and group >= 0.
     */
    @Override
    public void validate(Student student) throws ValidatorException {
        try {
            if (student.getId() < 0) throw new ValidatorException("Student Id Must Be Positive!");
        } catch (NullPointerException ignored) {
            throw new ValidatorException("Student Object is null!");
        }

        try {
            student.getSerialNumber().charAt(0);
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            throw new ValidatorException("Serial Number Not Set!");
        }

        try {
            student.getName().charAt(0);
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            throw new ValidatorException("Student Name Not Set!");
        }

        if(student.getGroup() < 0) {
            throw new ValidatorException("Group Not Set!");
        }
    }
}