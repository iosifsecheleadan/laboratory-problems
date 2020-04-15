package repository.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class for sorting Collections by class Attributes.
 * @author sechelea
 */
public class Sort {
    /**
     * Enum for specifying Sort direction
     */
    public enum Direction {ASC,  DESC}
    private final Direction direction;
    private final List<String> attributes;

    /**
     * Parametrized Constructor with default {@code ASC} direction.
     * @param attributeNames Array of attribute names
     */
    public Sort(String... attributeNames) {
        this.direction = Direction.ASC;
        this.attributes = Arrays.asList(attributeNames);
    }

    /**
     * Parametrized Constructor
     * @param direction Direction
     * @param attributeNames Array of attribute names
     */
    public Sort(Direction direction, String... attributeNames) {
        this.direction = direction;
        this.attributes = Arrays.asList(attributeNames);
    }

    /**
     * Get Direction
     * @return Direction
     */
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * Get Attributes in sorting importance order
     * @return List
     */
    public List<String> getAttributes() {
        return this.attributes;
    }

    /**
     * Get Attributes in reverse sorting importance order
     * @return List
     */
    public List<String> getAttributesReversed() {
        List<String> temp = this.attributes.subList(0, this.attributes.size());
        Collections.reverse(temp);
        return temp;
    }
}
