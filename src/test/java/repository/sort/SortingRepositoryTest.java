package repository.sort;

import domain.entities.Student;
import domain.validators.StudentValidator;
import domain.validators.Validator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

public class SortingRepositoryTest {
    private SortingRepository<Long, Student> students;
    private final Validator<Student> validator = new StudentValidator();

    @Before
    public void setUp() {
        this.students = new SortingRepository<>(this.validator);
        Student one = new Student(1L,"one01", "One", 1);
        Student two = new Student(2L, "two02", "Two", 2);
        Student three = new Student(3L, "thr03", "Three", 3);
        this.students.save(one);
        this.students.save(two);
        this.students.save(three);
    }

    @After
    public void tearDown() {
        this.students = null;
    }

    @Test
    public void testSave() {
        assertEquals(new Student(1L, "one01", "One", 1),
                this.students.findOne(1L).get());
        assertEquals(new Student(2L, "two02", "Two", 2),
                this.students.findOne(2L).get());
        assertEquals(new Student(3L, "thr03", "Three", 3),
                this.students.findOne(3L).get());
    }

    @Test
    public void testDelete() {
        this.students.delete(3L);
        this.students.delete(5L);
        try {
            this.students.delete(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        assertEquals(2, StreamSupport.stream(this.students.findAll().spliterator(), false)
                .collect(Collectors.toSet()).size());
    }

    @Test
    public void testUpdate() {
        Student student = new Student(4L, "sdf", "rtyu", 6789);
        this.students.save(student);
        this.students.update(new Student(4L, "for04", "Four", 4));
        assertNotEquals(student, this.students.findOne(4L).get());
    }

    @Test
    public void testFindOne() {
        Student student = new Student(4L, "sdf", "rtyu", 6789);
        this.students.save(student);
        assertEquals(student, students.findOne(student.getId()).get());
    }

    @Test
    public void testFindAll() {
        assertEquals(3, StreamSupport.stream(this.students.findAll().spliterator(), false)
                .collect(Collectors.toSet()).size());
    }

    @Test
    public void testFindAllSort() {
        List<Student> ordered = StreamSupport.stream(
                this.students.findAll(new Sort("name", "group")).spliterator(), false)
                .collect(Collectors.toList());
        assertEquals(new Student(1L,"one01", "One", 1), ordered.get(0));
        assertEquals(new Student(2L, "two02", "Two", 2), ordered.get(2));
        ordered = StreamSupport.stream(
                this.students.findAll(new Sort(Sort.Direction.DESC, "group")).spliterator(), false)
                .collect(Collectors.toList());
        assertEquals(new Student(1L,"one01", "One", 1), ordered.get(0));
        assertEquals(new Student(2L, "two02", "Two", 2), ordered.get(1));

    }
}
