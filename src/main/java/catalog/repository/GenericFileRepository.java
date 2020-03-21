package catalog.repository;

import catalog.domain.BaseEntity;
import catalog.domain.validators.Validator;
import catalog.domain.validators.ValidatorException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public class GenericFileRepository<Type extends BaseEntity<Long>>
        extends InMemoryRepository<Long, Type> {
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
                    //instance = (Type) this.clazz.getConstructor().newInstance(line);
                    instance = (Type) Class.forName(this.className).getConstructor(String.class).newInstance(line);
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
                } catch (ClassNotFoundException e) {
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
    public Optional<Type> delete(Long ID) {
        // todo : HERE delete first line starting with ID from file
        return super.delete(ID);
    }

    @Override
    public Optional<Type> update(Type entity) throws ValidatorException {
        // todo : HERE replace first line starting with ID from file with entity.toString(",")
        return super.update(entity);
    }

}
