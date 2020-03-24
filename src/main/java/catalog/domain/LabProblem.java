package catalog.domain;

import java.util.Arrays;
import java.util.List;

public class LabProblem<ID extends Comparable<ID>>
        extends BaseEntity<ID> {
    private int problemNumber;
    private String name;
    private String description;

    public LabProblem(){}

    public LabProblem(ID id, int problemNumber, String name, String description) {
        this.setId(id);
        this.problemNumber = problemNumber;
        this.name = name;
        this.description = description;
    }

    public LabProblem(String string) {
        List<String> values = Arrays.asList(string.split(","));
        this.setId((ID) values.get(0));
        this.problemNumber = Integer.parseInt(values.get(1));
        this.name = values.get(2);
        this.description = values.get(3);
    }

    public String getName() {
        return this.name;
    }
    public String getDescription() {
        return this.description;
    }
    public int getProblemNumber() {
        return this.problemNumber;
    }

    // todo : like student

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        LabProblem<ID> problem = (LabProblem<ID>) other;

        if (this.problemNumber != problem.problemNumber) return false;
        if (!this.description.equals(problem.description)) return false;
        return this.name.equals(problem.name);
    }

    @Override
    public String toString() {
        return "LabProblem{" +
                "problemNumber=" + problemNumber +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public String toString(String separator) {
        return this.getId().toString() + separator
                + String.valueOf(this.problemNumber) + separator
                + this.name.toString() + separator
                + this.description.toString();
    }
}
