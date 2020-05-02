package domain.entities;

import domain.entities.BaseEntity;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BaseEntityTest {

    @Test
    public void testConstructor() {
        BaseEntity<Integer> entity = new BaseEntity<>(3);
        assertEquals(Integer.valueOf(3), entity.getId());
        entity = new BaseEntity<>();
        assertNull(entity.getId());
    }

    @Test
    public void testGetSet() {
        BaseEntity<String> entity = new BaseEntity<>("Yupp");
        assertEquals("Yupp", entity.getId());
        entity.setId("Nope");
        assertEquals("Nope", entity.getId());
    }

    @Test
    public void testToXML() {
        BaseEntity<Long> entity = new BaseEntity<>(4L);
        assertEquals("<BaseEntity></BaseEntity>", entity.toXML());
        entity = new BaseEntity<>();
        assertEquals("<BaseEntity></BaseEntity>", entity.toXML());
    }

    @Test
    public void testToString() {
        BaseEntity<Double> entity = new BaseEntity<>(3.5);
        assertEquals("3.5", entity.toString("bla"));
        assertEquals("BaseEntity{id=3.5}", entity.toString());
    }

}
