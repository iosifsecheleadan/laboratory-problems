package repository;

import domain.validators.ValidatorException;
import domain.entities.BaseEntity;

import java.util.Optional;

/**
 * Interface for generic CRUD operations on a repository for a specific type.
 * @author radu
 */
public interface Repository<ID, Type extends BaseEntity<ID>> {
    /**
     * Find the entity with the given {@code id}.
     * @param id must be not null.
     * @return an {@code Optional} encapsulating the entity with the given id.
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    Optional<Type> findOne(ID id);

    /**
     * @return all entities.
     */
    Iterable<Type> findAll();

    /**
     * Saves the given entity.
     * @param entity must not be null.
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     * @throws ValidatorException if the entity is not valid.
     */
    Optional<Type> save(Type entity) throws ValidatorException;

    /**
     * Removes the entity with the given id.
     * @param id must not be null.
     * @return an {@code Optional} - null if there is no entity with the given id, otherwise the removed entity.
     * @throws IllegalArgumentException if the given id is null.
     */
    Optional<Type> delete(ID id);

    /**
     * Updates the given entity.
     * @param entity must not be null.
     * @return an {@code Optional} - null if the entity was updated otherwise (e.g. id does not exist) returns the
     *         entity.
     * @throws IllegalArgumentException if the given entity is null.
     * @throws ValidatorException if the entity is not valid.
     */
    Optional<Type> update(Type entity) throws ValidatorException;
}
