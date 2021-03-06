package repository.inMemory;

import domain.validators.Validator;
import domain.validators.ValidatorException;
import domain.entities.BaseEntity;
import repository.Repository;

import java.io.Serializable;
import java.util.*;

/**
 * Implementation of Repository with In Memory Storage
 * @author radu
 */
public class InMemoryRepository<ID extends Serializable, Type extends BaseEntity<ID>>
        implements Repository<ID, Type> {
    private Map<ID, Type> entities;
    private Validator<Type> validator;

    public InMemoryRepository(Validator<Type> validator) {
        this.validator = validator;
        this.entities = new HashMap<>();
    }

    @Override
    public Optional<Type> findOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<Type> findAll() {
        return new HashSet<>(entities.values());
    }

    @Override
    public Optional<Type> save(Type entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<Type> delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<Type> update(Type entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }
}
