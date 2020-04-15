package service;

import domain.entities.Problem;
import domain.validators.ValidatorException;

import java.util.Optional;
import java.util.Set;

/**
 * Interface for CRUD and filter operations on a Collection of Problems
 * @see Problem
 * @author sechelea
 */
public interface ProblemService {
    /**
     * Add a Problem
     * @param problem Problem
     * @throws ValidatorException If given Problem is invalid
     */
    public void addLabProblem(Problem problem) throws ValidatorException;

    /**
     * Replace Problem that has given Problem's ID, with given Problem
     * @param problem Problem
     * @throws ValidatorException If given Problem is invalid
     */
    public void updateLabProblem(Problem problem) throws ValidatorException;

    /**
     * Remove given Problem
     * @param problem Problem
     */
    public void removeLabProblem(Problem problem);

    /**
     * Get All Problems
     * @return Set
     */
    public Set<Problem> getAllProblems();

    /**
     * Get All Problems that have given string in their Name
     * @param name String
     * @return Set
     */
    public Set<Problem> filterByName(String name);

    /**
     * Get All Problems that have given string in their Description
     * @param description String
     * @return Set
     */
    public Set<Problem> filterByDescription(String description);

    /**
     * Get Problem with given number
     * @param problemNumber int
     * @return Optional
     */
    public Optional<Problem> getByProblemNumber(int problemNumber);

    /**
     * Close any connections, open files, etc...
     */
    public void close();
}
