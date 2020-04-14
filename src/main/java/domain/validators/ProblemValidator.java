package domain.validators;

import domain.entities.Problem;

public class ProblemValidator implements Validator<Problem>{
    @Override
    public void validate(Problem entity) throws ValidatorException {
        try {
            if(entity.getProblemNumber() < 0) throw new ValidatorException("Problem Number Must Be Positive!");
        } catch (NullPointerException e) {
            throw new ValidatorException("LabProblem Object Is Null!");
        }

        try {
            entity.getName().charAt(0);
        } catch (StringIndexOutOfBoundsException e) {
            throw new ValidatorException("LabProblem Name Not Set!");
        }

        try {
            entity.getDescription().charAt(0);
        } catch (StringIndexOutOfBoundsException e) {
            throw new ValidatorException("LabProblem Name Not Set!");
        }
    }
}