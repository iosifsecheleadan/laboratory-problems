package catalog.domain;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class LabProblem extends BaseEntity<Long> {
    private int problemNumber;
    private String name;
    private String description;

    public LabProblem(){}

    public LabProblem(Long ID, int problemNumber, String name, String description) {
        this.setId(ID);
        this.problemNumber = problemNumber;
        this.name = name;
        this.description = description;
    }

    public LabProblem(String string) {
        List<String> values = Arrays.asList(string.split(","));
        this.setId(Long.parseLong(values.get(0)));
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
    public String toString(String separator) {
        return this.getId().toString() + separator
                + String.valueOf(this.problemNumber) + separator
                + this.name.toString() + separator
                + this.description.toString();
    }
}
