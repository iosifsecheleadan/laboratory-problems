package catalog.repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Sort {
    public enum Direction {ASC,  DESC}
    private final Direction direction;
    private final List<String> attributes;

    public Sort(String... attributeNames) {
        this.direction = Direction.ASC;
        this.attributes = Arrays.asList(attributeNames);
    }

    public Sort(Direction direction, String... attributeNames) {
        this.direction = direction;
        this.attributes = Arrays.asList(attributeNames);
    }

    public Direction getDirection() {
        return this.direction;
    }

    public List<String> getAttributes() {
        return this.attributes;
    }

    public List<String> getAttributesReversed() {
        List<String> temp = this.attributes.subList(0, this.attributes.size());
        Collections.reverse(temp);
        return temp;
    }

}
