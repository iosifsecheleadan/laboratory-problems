package catalog.domain;

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
}
