package service;

import domain.entities.Problem;
import domain.validators.ValidatorException;

import java.util.Optional;
import java.util.Set;

public interface ProblemService {
    // todo Write method specifications
    public void addLabProblem(Problem problem) throws ValidatorException;
    public void updateLabProblem(Problem problem);
    public void removeLabProblem(Problem problem);

    public Set<Problem> getAllLabProblems();
    public Set<Problem> filterByName(String string);
    public Set<Problem> filterByDescription(String string);
    public Optional<Problem> getByProblemNumber(int problemNumber);

    public void close();
}
