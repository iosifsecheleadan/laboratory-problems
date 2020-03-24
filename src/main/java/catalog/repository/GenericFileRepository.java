package catalog.repository;

import catalog.domain.BaseEntity;
import catalog.domain.validators.Validator;
import catalog.domain.validators.ValidatorException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public class GenericFileRepository<ID extends Serializable & Comparable<ID>, Type extends BaseEntity<ID>>
        extends InMemoryRepository<ID, Type> {
    private final String fileName;
    private final String className;

    public GenericFileRepository(Validator<Type> validator, String fileName, String className) {
        super(validator);
        this.fileName = fileName;
        this.className = className;

        this.loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);
        try {
            Files.lines(path).forEach(line -> {
                Type instance = null;
                try {
                    //instance = (Type) Class.forName(this.className).getConstructor(String.class).newInstance(line);
                    instance = (Type) instance.getClass().getConstructor(String.class).newInstance(line);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e){
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                try{
                    super.save(instance);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Type> save(Type entity) throws ValidatorException {
        Optional<Type> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        this.saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(Type entity) {
        Path path = Paths.get(this.fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)){
            bufferedWriter.write(entity.toString(","));
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Type> delete(ID id) {
        // todo : HERE delete first line starting with ID from file
        return super.delete(id);
    }

    @Override
    public Optional<Type> update(Type entity) throws ValidatorException {
        // todo : HERE replace first line starting with ID from file with entity.toString(",")
        return super.update(entity);
    }

}
