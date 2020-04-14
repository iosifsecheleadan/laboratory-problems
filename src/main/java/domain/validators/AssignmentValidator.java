package domain.validators;

import domain.entities.Assignment;

public class AssignmentValidator implements Validator<Assignment> {
    @Override
    public void validate(Assignment entity) throws ValidatorException {
        try {
            if (entity.getId() < 0) {
                throw new ValidatorException("ID must be positive!");
            }
        } catch (NullPointerException e) {
            throw new ValidatorException(("StudentProblem Object is Null!"));
        }
        if(entity.getProblemID() < 0) {
            throw new ValidatorException("Problem ID must be positive!");
        }
        if(entity.getStudentID() < 0) {
            throw new ValidatorException("ID must be positive!");
        }
    }
}
