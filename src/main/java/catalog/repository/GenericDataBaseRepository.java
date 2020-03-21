package catalog.repository;

import catalog.domain.BaseEntity;
import catalog.domain.validators.Validator;
import catalog.domain.validators.ValidatorException;

import java.util.Optional;

public class GenericDataBaseRepository<Type extends BaseEntity<Long>>
        extends SortingRepository<Long, Type> {
    private final String className;
    private final String dataBaseName;
    private final String tableName;


    public GenericDataBaseRepository(Validator<Type> validator, String dataBaseName, String tableName, String className) {
        super(validator);
        this.className = className;
        this.dataBaseName = dataBaseName;
        this.tableName = tableName;

        this.loadData();
    }

    private void loadData() {
        // todo load data from DataBase
        // try {
            // DataBaseConnection(dataBaseName).getTable(tableName).getEntities.forEach(entity -> {
                Type instance = null;
            // instance = (Type) Class.forName(this.className).getConstructor(Entity.class).newInstance(entity);
            // instance = (Type) Class.forName(this.className).getConstructor(String.class).newInstance(entity);
                try {
                    super.save(instance);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
        // } catch (DataBaseConnectionException e) {
        //     e.printStackTrace();
        // }
    }

    public Optional<Type> save(Type entity) throws ValidatorException {
        Optional<Type> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        this.saveToDataBase(entity);
        return Optional.empty();
    }

    private void saveToDataBase(Type entity) {
        // todo save data to DataBase
    }

    @Override
    public Optional<Type> delete(Long ID) {
        // todo HERE delete entity with given ID from database
        return super.delete(ID);
    }

    @Override
    public Optional<Type> update(Type entity) throws ValidatorException {
        // todo HERE update entity in DataBase
        return super.update(entity);
    }
}
