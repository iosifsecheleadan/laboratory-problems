package catalog.repository;

import catalog.domain.BaseEntity;
import catalog.domain.validators.Validator;
import catalog.domain.validators.ValidatorException;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
            allEntries.sort((first, second) -> {
                try {
                    return ((Comparable) first.getClass().getField(attribute).get(first)).compareTo(
                            second.getClass().getField(attribute).get(second));
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                } return 0;
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
        validator.validate(entity);
        return Optional.ofNullable(this.entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }
}
