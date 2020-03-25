package catalog.domain.validators;

import catalog.domain.StudentProblem;

public class StudentProblemValidator implements Validator<StudentProblem> {
    @Override
    public void validate(StudentProblem entity) throws ValidatorException {
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
