package catalog.domain;

import java.io.Serializable;

/**
 * @author radu.
 */
public class BaseEntity<ID> {
    private ID id;

    public BaseEntity() {}

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }

    public String toString(String separator) {
        return id.toString();
    }


    public <Type extends BaseEntity<ID>> int compareTo(Type that, String attribute) throws IllegalAccessException {
        return 0;
    }
}
