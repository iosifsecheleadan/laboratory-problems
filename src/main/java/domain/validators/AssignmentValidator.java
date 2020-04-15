package domain.validators;

import domain.entities.Assignment;

/**
 * @author vinczi
 */
public class AssignmentValidator implements Validator<Assignment> {
    /**
     * Check if Assignment is valid
     * @param assignment Assignment
     * @throws ValidatorException If Assignment does not have: ID, studentID and problemID >= 0.
     */
    @Override
    public void validate(Assignment assignment) throws ValidatorException {
        try {
            if (assignment.getId() < 0) {
                throw new ValidatorException("ID must be positive!");
            }
        } catch (NullPointerException e) {
            throw new ValidatorException(("StudentProblem Object is Null!"));
        }
        if(assignment.getProblemID() < 0) {
            throw new ValidatorException("Problem ID must be positive!");
        }
        if(assignment.getStudentID() < 0) {
            throw new ValidatorException("ID must be positive!");
        }
    }
}
