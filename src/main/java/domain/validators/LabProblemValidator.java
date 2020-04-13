package domain.validators;

import domain.LabProblem;

public class LabProblemValidator implements Validator<LabProblem>{
    @Override
    public void validate(LabProblem entity) throws ValidatorException {
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