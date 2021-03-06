package repository.sort;

import domain.entities.BaseEntity;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import repository.Repository;
import repository.RepositoryException;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Implementation of Repository with In Memory Storage and Sorting mechanisms
 * @param <ID>
 * @param <Type>
 * @author sechelea
 */
public class SortingRepository<ID extends Serializable, Type extends BaseEntity<ID>>
        implements Repository<ID, Type> {
    private Map<ID, Type> entities;
    private Validator<Type> validator;

    public SortingRepository(Validator<Type> validator) {
        this.validator = validator;
        this.entities = new TreeMap<>();
    }

    @Override
    public Optional<Type> findOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(this.entities.get(id));
    }

    @Override
    public Iterable<Type> findAll() {
        return new HashSet<>(this.entities.values());
    }

    public Iterable<Type> findAll(Sort sort) {
        List<Type> allEntries = StreamSupport.stream(this.findAll().spliterator(), false)
                .collect(Collectors.toList());
        sort.getAttributesReversed().forEach(attribute -> {
            String getter = "get" + attribute.substring(0,1).toUpperCase() + attribute.substring(1);
            allEntries.sort((first, second) -> {
                try {
                    return ((Comparable) first.getClass().getDeclaredMethod(getter).invoke(first)).compareTo(
                            second.getClass().getDeclaredMethod(getter).invoke(second));
                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    throw new RepositoryException("No such method " + getter);
                }
            });
        });
        return allEntries;
    }

    @Override
    public Optional<Type> save(Type entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        validator.validate(entity);
        return Optional.ofNullable(this.entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<Type> delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(this.entities.remove(id));
    }

    @Override
    public Optional<Type> update(Type entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        if (! this.entities.containsKey(entity.getId())) {
            return Optional.of(entity);
        }
        validator.validate(entity);
        this.entities.put(entity.getId(), entity);
        return Optional.empty();
    }

}
