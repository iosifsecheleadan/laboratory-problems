package catalog.domain;

import java.util.Collection;

public class LabProblem extends BaseEntity<Long> {
    private int problemNumber;
    private String name;
    private String description;

    public String getName() {
        return this.name;
    }
    public String getDescription() {
        return this.description;
    }
    // todo : like student
}
