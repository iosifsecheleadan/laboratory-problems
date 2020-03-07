package catalog.repository;

import catalog.domain.LabProblem;
import catalog.domain.Student;
import catalog.domain.validators.Validator;

public class LabProblemFileRepository extends InMemoryRepository<Long, LabProblem> {
    private final String fileName;

    public LabProblemFileRepository(Validator<LabProblem> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

        //loadData();
    }
    // todo : like student
}
