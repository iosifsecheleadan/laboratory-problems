package catalog.repository;

import catalog.domain.StudentProblem;
import catalog.domain.validators.Validator;

public class StudentProblemFileRepostory extends InMemoryRepository<Long, StudentProblem> {
    private final String fileName;

    public StudentProblemFileRepostory(Validator<StudentProblem> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

        //loadData();
    }
    // todo : like student
    // todo : implements InMemoryRepository<ID, ID> // will be instantiated with called with <Long, Long>
}
