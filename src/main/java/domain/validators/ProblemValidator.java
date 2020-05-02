package domain.validators;

import domain.entities.Problem;

/**
 * @author vinczi
 */
public class ProblemValidator implements Validator<Problem>{

    /**
     * Check if Problem is valid
     * @param problem Problem
     * @throws ValidatorException If Problem does not have: name and description set, and group >= 0.
     */
    @Override
    public void validate(Problem problem) throws ValidatorException {
        try {
            if (problem.getId() < 0) throw new ValidatorException("Problem Id Must Be Positive!");
        } catch (NullPointerException e) {
            throw new ValidatorException("LabProblem Object Is Null!");
        }

        if(problem.getProblemNumber() < 0) {
            throw new ValidatorException("Problem Number Must Be Positive!");
        }

        try {
            problem.getName().charAt(0);
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            throw new ValidatorException("LabProblem Name Not Set!");
        }

        try {
            problem.getDescription().charAt(0);
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            throw new ValidatorException("LabProblem Name Not Set!");
        }
    }
}