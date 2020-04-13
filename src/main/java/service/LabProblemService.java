package service;

import domain.LabProblem;
import domain.validators.ValidatorException;
import repository.GenericDataBaseRepository;
import repository.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class LabProblemService {
    private Repository<Long, LabProblem> repository;

    public LabProblemService(Repository<Long, LabProblem> repository) {
        this.repository = repository;
    }

    public void addLabProblem(LabProblem labProblem) throws ValidatorException {
        this.repository.save(labProblem);
    }

    public void updateLabProblem(LabProblem labProblem) {
        this.repository.update(labProblem);
    }

    public Set<LabProblem> getAllLabProblems() {
        Iterable<LabProblem> labProblems = this.repository.findAll();
        return StreamSupport.stream(labProblems.spliterator(), false).collect(Collectors.toSet());
    }


    /**
     * Returns all LabProblems whose name contains the given string.
     * @param  string String
     * @return Set<LabProblem>
     */
    public Set<LabProblem> filterByName(String string) {
        Iterable<LabProblem> labProblems = this.repository.findAll();
        Set<LabProblem> filtered = new HashSet<>();

        labProblems.forEach(filtered::add);
        filtered.removeIf(labProblem -> !labProblem.getName().contains(string));

        return filtered;
    }

    /**
     * Returns all LabProblems whose description contains the given string.
     * @param string String
     * @return Set<LabProblem>
     */
    public Set<LabProblem> filterByDescription(String string) {
        Iterable<LabProblem> labProblems = this.repository.findAll();
        Set<LabProblem> filtered = new HashSet<>();

        labProblems.forEach(filtered::add);
        filtered.removeIf(labProblem -> !labProblem.getDescription().contains(string));

        return filtered;
    }

    public void removeLabProblem(LabProblem labProblem) {
        this.repository.delete(labProblem.getId());
    }

    public Optional<LabProblem> getByProblemNumber(Integer problemNumber) {
        Iterable<LabProblem> problems = this.repository.findAll();
        return StreamSupport.stream(problems.spliterator(), false)
                .filter(problem -> problem.getProblemNumber() == problemNumber)
                .findAny();
    }

    public void close() {
        if (this.repository instanceof GenericDataBaseRepository)
        ((GenericDataBaseRepository<LabProblem>) this.repository).close();
    }
}
