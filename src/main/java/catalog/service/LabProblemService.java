package catalog.service;

import catalog.domain.LabProblem;
import catalog.domain.validators.ValidatorException;
import catalog.repository.Repository;

import java.util.HashSet;
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
}
