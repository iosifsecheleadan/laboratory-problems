package repository.sort;

import org.junit.Test;

import static org.junit.Assert.*;

public class SortTest {
    private Sort sort;

    @Test
    public void testConstructor() {
        this.sort = new Sort();
        this.sort = new Sort(Sort.Direction.DESC);
        this.sort = new Sort("one", "two", "three");
        this.sort = new Sort("rt", "rt", "8i", "0iuy", "zsdrt", "gbfd");
        try {
            this.sort = new Sort(null);
            fail();
        } catch (NullPointerException e) {
            assertTrue(true);
        }
        this.sort = new Sort(Sort.Direction.ASC, "bla");
    }

    @Test
    public void testGetDirection() {
        this.sort = new Sort();
        assertEquals(Sort.Direction.ASC, sort.getDirection());
        this.sort = new Sort(Sort.Direction.ASC);
        assertEquals(Sort.Direction.ASC, sort.getDirection());
        this.sort = new Sort(Sort.Direction.DESC);
        assertEquals(Sort.Direction.DESC, sort.getDirection());
    }

    @Test
    public void testGetAttributes() {
        this.sort = new Sort("one", "two", "three");
        assertEquals("one", this.sort.getAttributes().get(0));
        assertEquals( "two", this.sort.getAttributes().get(1));
        assertEquals("three", this.sort.getAttributes().get(2));
    }

    @Test
    public void testGetAttributesReversed() {
        this.sort = new Sort("one", "two", "three");
        assertEquals("three", this.sort.getAttributesReversed().get(0));
        assertEquals( "two", this.sort.getAttributesReversed().get(1));
        assertEquals("one", this.sort.getAttributesReversed().get(2));
    }
}
