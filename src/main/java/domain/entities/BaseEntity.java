package domain.entities;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.Serializable;

/**
 * @author vinczi
 * @param <ID> generic Type
 */
public class BaseEntity<ID>
        implements Serializable {
    private ID id;

    /**
     * Default Constructor
     */
    public BaseEntity() {}

    /**
     * Parametrized Constructor
     * @param id ID (generic Type)
     */
    public BaseEntity(ID id) {
        this.id = id;
    }

    /**
     * Get Entity ID
     * @return ID (generic)
     */
    public ID getId() {
        return id;
    }

    /**
     * Set Entity ID
     * @param id ID (generic)
     */
    public void setId(ID id) {
        this.id = id;
    }

    /**
     * Get Entity as XML formatted String
     * @return String
     */
    public String toXML() { return "<BaseEntity></BaseEntity>"; }

    /**
     * Get Entity as Element of given Document
     * @param document Document (org.w3c.dom.Document)
     * @return Element (org.w3c.dom.Element)
     */
    public Element toXML(Document document) {
        return document.createElement("BaseEntity");
    }

    /**
     * Get Entity String formatted with given separator
     * @param separator String
     * @return String
     */
    public String toString(String separator) {
        return id.toString();
    }

    /**
     * Get basic Entity String
     * @return String
     */
    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }
}
