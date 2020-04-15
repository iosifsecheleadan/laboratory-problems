package service.repo;

import domain.entities.Problem;
import domain.validators.ValidatorException;
import repository.sort.GenericDataBaseRepository;
import repository.Repository;
import service.ProblemService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Implementation of ProblemService with Repository as Collection
 * @author sechelea
 */
public class ProblemRepoService
        implements ProblemService {
    private Repository<Long, Problem> repository;

    /**
     * Parametrized Constructor
     * @param repository Repository
     */
    public ProblemRepoService(Repository<Long, Problem> repository) {
        this.repository = repository;
    }

    public void addLabProblem(Problem problem) throws ValidatorException {
        this.repository.save(problem);
    }

    public void updateLabProblem(Problem problem) {
        this.repository.update(problem);
    }

    public Set<Problem> getAllProblems() {
        Iterable<Problem> labProblems = this.repository.findAll();
        return StreamSupport.stream(labProblems.spliterator(), false).collect(Collectors.toSet());
    }

    public Set<Problem> filterByName(String name) {
        Iterable<Problem> labProblems = this.repository.findAll();
        Set<Problem> filtered = new HashSet<>();

        labProblems.forEach(filtered::add);
        filtered.removeIf(labProblem -> !labProblem.getName().contains(name));

        return filtered;
    }

    public Set<Problem> filterByDescription(String string) {
        Iterable<Problem> labProblems = this.repository.findAll();
        Set<Problem> filtered = new HashSet<>();

        labProblems.forEach(filtered::add);
        filtered.removeIf(labProblem -> !labProblem.getDescription().contains(string));

        return filtered;
    }

    public void removeLabProblem(Problem problem) {
        this.repository.delete(problem.getId());
    }

    public Optional<Problem> getByProblemNumber(int problemNumber) {
        Iterable<Problem> problems = this.repository.findAll();
        return StreamSupport.stream(problems.spliterator(), false)
                .filter(problem -> problem.getProblemNumber() == problemNumber)
                .findAny();
    }

    public void close() {
        if (this.repository instanceof GenericDataBaseRepository)
        ((GenericDataBaseRepository<Problem>) this.repository).close();
    }
}
