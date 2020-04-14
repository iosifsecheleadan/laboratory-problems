package domain.entities;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.Serializable;

/**
 * @author radu.
 */
public class BaseEntity<ID> implements Serializable {
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

    public String toXML() { return "<BaseEntity></BaseEntity>"; }

    public Element toXML(Document document) {
        Element element = document.createElement("BaseEntity");
        return element;
    }
}
